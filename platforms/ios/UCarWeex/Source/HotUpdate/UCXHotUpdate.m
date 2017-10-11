//
//  UCXHotUpdate.m
//  Pods
//
//  Created by huyujin on 2017/8/23.
//

/**
    存储于userdefaults,KEY:UCX_US_UCAR_WEEX_KEY:,以数组形式存储，每个元素的结构如下
    {
     "appName": "ucarweex",
     "appIdAndroid": "ab134cd1-da99-456b-8238-5fb034d7b4d8",
     "appIdIos": "1b4e789c-8867-46aa-8f4e-f6ad2af08027",
     "versionNameAndroid": "1.0.2",
     "versionNameIos": "1.0.2",
     "versionCodeAndroid": "3",
     "versionCodeIos": "3",
     "versionDes": "1.0.3"
     "groupId": "vid_001",
     "md5": "b8751466a1994c61a00d77d5307a481d",
     "length": 577705,
     "time": "20170825101245",
     "path": "ucarweex_2_20170825101245"
     
     "url": "http://10.99.44.46:3000/file/ucarweex_2_20170825101245",
     "unzipFilePath":""
    }

 */

#define UCX_TEST_UPGRADE_URL @"http://fcardownloadtest.10101111.com/fcarapp/upgrade/getUpgradeInfo"
#define UCX_PRE_UPGRADE_URL @"https://fcardownloadpre.10101111.com/fcarapp/upgrade/getUpgradeInfo"
#define UCX_PRODUCTION_UPGRADE_URL @"https://download.carbank.cn/fcarapp/upgrade/getUpgradeInfo"

#import "UCXHotUpdate.h"
#import <WeexSDK/WeexSDK.h>

#import "UCXHotUpdateManager.h"
#import "UCXHotUpdateDownloader.h"
#import "UCXUtil.h"
#import "UCXAppConfiguration.h"
#import "UCXNetworkHelper.h"


@interface UCXHotUpdate ()

@property (nonatomic, strong) UCXHotUpdateManager *fileManager;
/**
 * options 内部结构如下：
 {
    url:
    packageInfo:
    {
         "appName": "ucarweex",
         "versionCode": 2,
         "versionName": "1.0",
         "versionDes": "新版说明",
         "androidMinVersion": 1,
         "iosMinVersion": 1,
         "groupId": "vid_001",
         "md5": "b8751466a1994c61a00d77d5307a481d",
         "length": 577705,
         "time": "20170825101245",
         "path": "ucarweex_2_20170825101245"
    }
 }
 */
@property (nonatomic, strong) NSMutableDictionary *currentOptions;  //当前获取的配置信息：
@property (nonatomic, strong) NSMutableDictionary *lastOptions;     //应用中最后的配置信息

@end

@implementation UCXHotUpdate

+ (instancetype)shared {
    static dispatch_once_t once = 0;
    static UCXHotUpdate *instance;
    dispatch_once(&once, ^{
        instance = [[self alloc] init];
    });
    return instance;
}

- (instancetype)init {
    self = [super init];
    if (self) {
        [self initData];
    }
    return self;
}

/**初始化信息*/
- (void)initData {
    //
    _currentOptions = [NSMutableDictionary dictionary];
    _lastOptions = [NSMutableDictionary dictionary];
    _fileManager = [[UCXHotUpdateManager alloc] init];
    //若目录不存在，则创建指定目录:::
    BOOL success = [self.fileManager createDir:UCXDownloadDir];
    if (!success) {
        UCXLog(@"[UCAR WEEX]:::目录创建未成功:::%@",UCXDownloadDir);
    }
    //读取应用最后使用的配置信息
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    NSArray *optionsArr = [userDefaults objectForKey:UCX_US_UCAR_WEEX_KEY];
    if (optionsArr && optionsArr.count>0) {
        self.lastOptions = [optionsArr lastObject];
    }
}

#pragma mark -
+ (void)unpack:(NSDictionary *)options callback:(void (^)(NSError *error))callback {
    UCXHotUpdate *instance = [UCXHotUpdate shared];
    [instance unpack:options callback:^(NSError *error) {
        if (error) {
            UCXLog(@"[UCAR WEEX]:::ERROR:::%@",[error localizedDescription]);
        }
        callback(error);
    }];
}

- (void)unpack:(NSDictionary *)options callback:(void (^)(NSError *error))callback {
    
    //同步方式解压文件 & 解析配置
    //找到压缩文件 & 配置文件
    NSString *assetsPath = [NSString stringWithFormat:@"%@/%@/%@",[[NSBundle mainBundle] resourcePath],@"assets",@"weex"];
    NSString *url = [options objectForKey:@"url"];
    if (url.length>0) {
        assetsPath = [NSString stringWithFormat:@"%@/%@",[[NSBundle mainBundle] resourcePath],url];
    }
    NSFileManager *fileManager = [NSFileManager defaultManager];
    NSDirectoryEnumerator *myDirEnumerator = [fileManager enumeratorAtPath:assetsPath];  //assetsPath
    
    NSString *fileName;
    NSString *jsonFilePath;
    NSString *zipFilePath;
    NSString *tmpName;
    while((tmpName = [myDirEnumerator nextObject])) {    //遍历当前目录
        if([[tmpName pathExtension] isEqualToString:@"json"]) { //取得后缀名为.json的文件名
            jsonFilePath = [assetsPath stringByAppendingPathComponent:tmpName];
        }else if ([[tmpName pathExtension] isEqualToString:@"zip"]) {//取得后缀名为.zip的文件名
            zipFilePath = [assetsPath stringByAppendingPathComponent:tmpName];
            NSRange range = [tmpName rangeOfString:@"." options:NSBackwardsSearch];
            fileName = [tmpName substringToIndex:range.location];
        }
    }
    // 解析配置文件
    NSData *jsonData = [NSData dataWithContentsOfFile:jsonFilePath];
    NSError *jsonError;
    NSDictionary *jsonDict = [NSJSONSerialization JSONObjectWithData:jsonData options:kNilOptions error:&jsonError];
    if (jsonError) {
        callback([UCXUtil errorWithMessage:UCX_ERROR_JSON_PARSE]);
        return;
    }
    //校验文件信息，判断是否需要更新
    self.currentOptions = [jsonDict mutableCopy];
    BOOL isNeedUpdate = [self validateOptions:self.currentOptions];
    if (!isNeedUpdate) {
        callback(nil);
        return;
    }

    NSString *unzipFilePath = [UCXDownloadDir stringByAppendingPathComponent:fileName];
    //本地文件解压,需同步
    __block NSError *zipError;
    dispatch_semaphore_t sema = dispatch_semaphore_create(0);
    [self.fileManager unzipFileAtPath:zipFilePath toDestination:unzipFilePath progressHandler:^(NSString *entry,long entryNumber, long total) {
        //压缩进度设置...
    } completionHandler:^(NSString *path, BOOL succeeded, NSError *error) {
        zipError = error;
        dispatch_semaphore_signal(sema);
    }];
    dispatch_semaphore_wait(sema, DISPATCH_TIME_FOREVER);
    if (zipError) { //解压文件失败
        callback(zipError);
        return;
    }
    //解压完成，保存本次更新的必要信息到本地存储
    [self unpackPackageInfo:fileName];
}

#pragma mark - 下载更新包
+ (void)hotUpdate:(NSDictionary *)options
         callback:(void (^)(NSError *error))callback
{
    UCXHotUpdate *instance = [UCXHotUpdate shared];
    [instance hotUpdate:options callback:^(NSError *error) {
        if (error) {
            UCXLog(@"[UCAR WEEX]:::ERROR:::%@",[error localizedDescription]);
        }
        callback(error);
    }];
}

- (void)hotUpdate:(NSDictionary *)options
         callback:(void (^)(NSError *error))callback
{
    // 网络请求url
    NSString *mode = [options objectForKey:@"mode"];
    NSString *url = [self buildUpgradeUrl:mode];
    // 拼接参数
    NSDictionary *parameters = [self buildParameters];
    [[UCXNetworkHelper shared] GET:url parameters:parameters success:^(NSDictionary *responseObj) {
        NSNumber *code = [responseObj objectForKey:@"code"];
        if (code && code.intValue==1) {
            NSDictionary *content = [responseObj objectForKey:@"content"];
            if (content && [content count]>0) {
                
                NSNumber *upgrade = [content objectForKey:@"upgrade"];
                NSNumber *totalUpgrade = [content objectForKey:@"totalUpgrade"];
                if (upgrade && upgrade.boolValue==true && totalUpgrade && totalUpgrade.boolValue==false) {
                    //更新升级信息
                    NSString *versionName = [content objectForKey:@"versionName"];
                    if (versionName) {
                        [self.currentOptions setObject:versionName forKey:@"versionNameIos"];
                    }
                    NSString *versionCode = [content objectForKey:@"versionCode"];
                    if (versionCode) {
                        [self.currentOptions setObject:versionCode forKey:@"versionCodeIos"];
                    }
                    //若是需要升级 && 并且是增量升级时，根据更新包地址下载更新包
                    NSString *updateUrl = [content objectForKey:@"downloadUrl"];
                    if (updateUrl.length<=0) {
                        callback([UCXUtil errorWithMessage:UCX_ERROR_OPTIONS]);
                        return;
                    }
                    // 根据更新包地址下载更新包
                    // 解析相关参数
                    versionName = [versionName stringByReplacingOccurrencesOfString:@"." withString:@""];
                    NSString *fileName = [self buildFileName:versionName];
                    
                    NSString *unzipFilePath = [UCXDownloadDir stringByAppendingPathComponent:fileName];
                    NSString *lastPathComponent = [NSString stringWithFormat:@"%@.zip",fileName];
                    NSString *zipFilePath = [UCXDownloadDir stringByAppendingPathComponent:lastPathComponent];
                    //
                    UCXLog(@"HotUpdate -- download file %@", updateUrl);
                    [UCXHotUpdateDownloader download:updateUrl savePath:zipFilePath progressHandler:^(long long receivedBytes, long long totalBytes) {
                        //下载进度设置...
                    } completionHandler:^(NSString *path, NSError *error) {
                        if (error) {
                            callback(error);
                        } else {
                            UCXLog(@"HotUpdate -- unzip file %@", path);
                            //校验文件md5
//                            BOOL flag = [self validateFile:path];
                            BOOL flag = YES; //暂未开启校验
                            if (flag) { //文件合法
                                //解压缩
                                [self.fileManager unzipFileAtPath:path toDestination:unzipFilePath progressHandler:^(NSString *entry,long entryNumber, long total) {
                                    //压缩进度设置...
                                } completionHandler:^(NSString *path, BOOL succeeded, NSError *error) {
                                    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
                                        if (error) {
                                            callback(error);
                                        } else {
                                            //解压完成，保存本次更新的必要信息到本地存储
                                            [self hotUpdatePackageInfo:fileName];
                                            callback(nil);
                                        }
                                    });
                                }];
                            }else {
                                //文件校验不通过
                                callback([UCXUtil errorWithMessage:UCX_ERROR_FILE_VALIDATE]);
                            }
                        }
                    }];
                }
            }
        }
    } failure:^(NSError *error) {
        callback(error);
        return;
    }];
}


/**
 * 保存本次更新的必要信息到本地存储
 ucar_weex:
 [
     "appName": "ucarweex",
     "versionCode": 2,
     "versionName": "1.0",
     "versionDes": "新版说明",
     "androidMinVersion": 1,
     "iosMinVersion": 1,
     "groupId": "vid_001",
     "md5": "b8751466a1994c61a00d77d5307a481d",
     "length": 577705,
     "time": "20170825101245",
     "path": "ucarweex_2_20170825101245"
     
     "url": "http://10.99.44.46:3000/file/ucarweex_2_20170825101245.so",
 ]
 
 */
- (void)savePackageInfo:(NSString *)fileName {
    if ([self.currentOptions count]>0) {
//        NSString *url = [self.currentOptions objectForKey:@"url"];
        //重组新的数据结构:结构如上注释
        NSMutableDictionary *newPackageInfo = [NSMutableDictionary dictionary];
//        if (url) {//若是存在url，则是在远程加载
//            NSDictionary *originPackageInfo = [self.currentOptions objectForKey:@"packageInfo"];
//            [newPackageInfo addEntriesFromDictionary:originPackageInfo];
//            [newPackageInfo setObject:url forKey:@"url"];
//        }else { // 若是加载本地json，则结构不存在url
//            [newPackageInfo addEntriesFromDictionary:self.currentOptions];
//        }
        [newPackageInfo addEntriesFromDictionary:self.currentOptions];
        if (fileName) {
            [newPackageInfo setObject:fileName forKey:UCX_UNZIP_FILE_PATH];
        }
        //
        NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
        NSArray *weexDataArr = [userDefaults objectForKey:UCX_US_UCAR_WEEX_KEY];
        NSMutableArray *newDataArr = [NSMutableArray array];
        //
        if (weexDataArr && weexDataArr.count>0) {
            [newDataArr addObjectsFromArray:weexDataArr];
        }
        [newDataArr addObject:newPackageInfo];
        [userDefaults setObject:newDataArr forKey:UCX_US_UCAR_WEEX_KEY];
        [userDefaults synchronize];
    }
}

- (void)hotUpdatePackageInfo:(NSString *)fileName {
    if ([self.currentOptions count]>0) {
        [self savePackageInfo:fileName];
        //校验当前存储版本历史是否超出限制，若超出限制，则只保留最近的版本
        [self handleVersionHistory];
    }
}

- (void)unpackPackageInfo:(NSString *)fileName {
    if ([self.currentOptions count]>0) {
        [self savePackageInfo:fileName];
        //set cache path into memory
        [UCXAppConfiguration cachePath];
        //校验当前存储版本历史是否超出限制，若超出限制，则只保留最近的版本
        [self handleVersionHistory];
    }
}


#pragma mark - validate
- (void)handleVersionHistory {
    //获取当前存储版本历史：
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    NSArray *weexDataArr = [userDefaults objectForKey:UCX_US_UCAR_WEEX_KEY];
    NSInteger maxCacheVersionNumber = [UCXAppConfiguration maxCacheVersionNumber];
    if (weexDataArr && weexDataArr.count>maxCacheVersionNumber) {
        //倒序遍历，超出maxCacheVersionNumber的item，则删除
        __block NSInteger count = 0;
        [weexDataArr enumerateObjectsWithOptions:NSEnumerationReverse usingBlock:^(NSDictionary *dict, NSUInteger idx, BOOL * _Nonnull stop) {
            count++;
            if (count>maxCacheVersionNumber) {
                
                NSString *unzipPath = [dict objectForKey:UCX_UNZIP_FILE_PATH];
                unzipPath = [UCXDownloadDir stringByAppendingPathComponent:unzipPath];
                
                if (unzipPath) {
                    [self.fileManager deleteDir:unzipPath];
                }
            }
        }];
    }
}

/**
 * 比较应用中的配置文件信息与当前从本地或者远程获取的配置信息
 */
- (BOOL)validateOptions:(NSDictionary *)options {
    //若本地尚没有配置信息
    if ([self.lastOptions count]<=0) {
        return YES;
    }
    //比较版本号
    NSString *lastVersionName = [self.lastOptions objectForKey:@"versionNameIos"];
    NSString *currentVersionName = [options objectForKey:@"versionNameIos"];
    if (lastVersionName && currentVersionName && [currentVersionName compare:lastVersionName options:NSNumericSearch]==NSOrderedDescending) {
        return YES;
    }
    //比较versionCode
    NSString *lastVersionCode = [self.lastOptions objectForKey:@"versionCodeIos"];
    NSString *currentVersionCode = [options objectForKey:@"versionCodeIos"];
    if (lastVersionCode && currentVersionCode && [currentVersionCode compare:lastVersionCode options:NSNumericSearch]==NSOrderedDescending) {
        return YES;
    }
//    // 获取的版本时间高于应用中版本的时间
//    NSString *lastTime = [self.lastOptions objectForKey:@"time"];
//    NSString *currentTime = [options objectForKey:@"time"];
//    if(lastTime && currentTime && [currentTime compare:lastTime]==NSOrderedDescending) {
//        return YES;
//    }
    return NO;
}

/**
 * 校验文件是否合法
 */
- (BOOL)validateFile:(NSString *)path {
    BOOL flag = NO;
    NSString *computedFileMD5 = [UCXUtil fileMD5:path];
    if ([self.currentOptions count]>0) {
        NSDictionary *packageInfo = [self.currentOptions objectForKey:@"packageInfo"];
        if ([packageInfo count]>0) {
            NSString *originMD5 = [packageInfo objectForKey:@"md5"];
            if (computedFileMD5 && originMD5 && [computedFileMD5 isEqualToString:originMD5]) {
                flag = YES;
            }
        }
    }
    return flag;
}

- (NSString *)buildUpgradeUrl:(NSString *)mode {
    NSString *url = UCX_TEST_UPGRADE_URL;
    if (mode) {
        if ([mode isEqualToString:@"test"]) {
            url = UCX_TEST_UPGRADE_URL;
        }else if ([mode isEqualToString:@"pre"]) {
            url = UCX_PRE_UPGRADE_URL;
        }else if ([mode isEqualToString:@"production"]) {
            url =UCX_PRODUCTION_UPGRADE_URL;
        }
    }
    return url;
}

- (NSDictionary *)buildParameters {
    // 读取应用最后使用的配置信息
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    NSArray *optionsArr = [userDefaults objectForKey:UCX_US_UCAR_WEEX_KEY];
    if (optionsArr && optionsArr.count>0) {
        NSDictionary *options = [optionsArr lastObject];
        self.currentOptions = [NSMutableDictionary dictionaryWithDictionary:options];
    }
    //
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    if (self.currentOptions && [self.currentOptions count]>0) {
        [parameters setObject:@(2) forKey:@"appType"];
        NSString *appId = [self.currentOptions objectForKey:@"appIdIos"];
        if (appId) {
            [parameters setObject:appId forKey:@"appId"];
        }
        NSString *versionName = [self.currentOptions objectForKey:@"versionNameIos"];
        if (versionName) {
            [parameters setObject:versionName forKey:@"versionName"];
        }
        NSString *versionCode = [self.currentOptions objectForKey:@"versionCodeIos"];
        if (versionCode) {
            [parameters setObject:versionCode forKey:@"versionCode"];
        }
    }
    return parameters;
}

- (NSString *)buildFileName:(NSString *)versionName {
    NSString *currentDateStr = [UCXUtil stringFromDate:[NSDate date] format:@"YYYYMMdd"];
    NSMutableString *fileName = [[NSMutableString alloc] init];
    [fileName appendString:@"ucarweex"];
    [fileName appendString:@"_"];
    [fileName appendString:versionName];
    [fileName appendString:@"_"];
    [fileName appendString:currentDateStr];
    
    return fileName;
}

@end
