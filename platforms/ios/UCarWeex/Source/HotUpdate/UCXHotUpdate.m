//
//  UCXHotUpdate.m
//  Pods
//
//  Created by huyujin on 2017/8/23.
//  Copyright © 2017年 ucarinc. All rights reserved.
//

#import "UCXHotUpdate.h"
#import <WeexSDK/WeexSDK.h>

#import "UCXHotUpdateManager.h"
#import "UCXHotUpdateDownloader.h"
#import "UCXUtil.h"
#import "UCXAppConfiguration.h"


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
@property (nonatomic, strong) NSMutableDictionary *options;  //配置信息参数：

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

- (instancetype)init
{
    self = [super init];
    if (self) {
        _fileManager = [[UCXHotUpdateManager alloc] init];
        _options = [NSMutableDictionary dictionary];
    }
    return self;
}

- (void)dealloc {
    NSLog(@"UCXHotUpdate dealloc");
}

#pragma mark -
+ (__kindof NSURLSessionTask *)POST:(NSString *)URL
                         parameters:(id)parameters
                            success:(UCXRequestSuccess)success
                            failure:(UCXRequestFailure)failure
{
    UCXHotUpdate *hotUpdate = [UCXHotUpdate shared];
    [hotUpdate POST:URL parameters:parameters success:^(NSDictionary *responseObj) {
        success(responseObj);
    } failure:^(NSError *error) {
        failure(error);
    }];
}

/**
 *  POST请求
 *
 *  @param URL        请求地址
 *  @param parameters 请求参数
 *  @param success    请求成功的回调
 *  @param failure    请求失败的回调
 *
 *  @return NSURLSessionTask
 */
- (__kindof NSURLSessionTask *)POST:(NSString *)URL
                         parameters:(id)parameters
                            success:(UCXRequestSuccess)success
                            failure:(UCXRequestFailure)failure;
{
    //
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:parameters options:NSJSONWritingPrettyPrinted error:nil];
    NSString *jsonStr = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
    NSData *httpBody = [jsonStr dataUsingEncoding:NSUTF8StringEncoding];
    //
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:URL]];
    
    //设置请求头参数：
    [request setValue:@"application/json" forHTTPHeaderField:@"Content-Type"];// content-type
    
    // Convert POST string parameters to data using UTF8 Encoding
    [request setHTTPMethod:@"POST"];
    [request setHTTPBody:httpBody];
    
    // Create the URLSession on the default configuration
    NSURLSessionConfiguration *sessionConfiguration = [NSURLSessionConfiguration defaultSessionConfiguration];
    NSURLSession *session = [NSURLSession sessionWithConfiguration:sessionConfiguration];
    
    NSURLSessionDataTask *dataTask = [session dataTaskWithRequest:request completionHandler:^(NSData * _Nullable data, NSURLResponse * _Nullable response, NSError * _Nullable error) {
        if (error) {
            failure(error);
        }else {
            NSError *jsonError = nil;
            NSDictionary *dataDict = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:&jsonError];
            if (dataDict) {
                success(dataDict);
            }else {
               NSError *jsonError = [self errorWithMessage:UCX_ERROR_JSON_PARSE];
               failure(jsonError);
            }
        }
    }];
    [dataTask resume];
    
    return dataTask;
}

#pragma mark - 下载更新包
+ (void)hotUpdate:(UCXHotUpdateType)type options:(NSDictionary *)options callback:(void (^)(NSError *error))callback
{
    UCXHotUpdate *instance = [UCXHotUpdate shared];
    [instance hotUpdate:type options:options callback:callback];
}

- (void)hotUpdate:(UCXHotUpdateType)type options:(NSDictionary *)options callback:(void (^)(NSError *error))callback
{
    __weak typeof(self) weakSelf = self;
    if (type==UCXHotUpdateTypeRemote) { //从远程加载
        
        self.options = [options mutableCopy];
        //
        NSString *updateUrl = [WXConvert NSString:options[@"url"]];
        if (updateUrl.length<=0) {
            callback([weakSelf errorWithMessage:UCX_ERROR_OPTIONS]);
            return;
        }
        //
        NSString *lastPathComponent = [updateUrl lastPathComponent];
        //
        NSString *fileName = lastPathComponent;
        NSRange range = [lastPathComponent rangeOfString:@"." options:NSBackwardsSearch];
        if(range.location !=NSNotFound) {
            fileName = [lastPathComponent substringToIndex:range.location];
        }
        
        NSString *dir = UCXDownloadDir;
        BOOL success = [self.fileManager createDir:dir];
        if (!success) {
            callback([weakSelf errorWithMessage:UCX_ERROR_FILE_OPERATION]);
            return;
        }
        NSString *unzipFilePath = [dir stringByAppendingPathComponent:fileName];
        NSString *zipFilePath = [dir stringByAppendingPathComponent:lastPathComponent];
        
        
        WXLog(@"HotUpdate -- download file %@", updateUrl);
        [UCXHotUpdateDownloader download:updateUrl savePath:zipFilePath progressHandler:^(long long receivedBytes, long long totalBytes) {
            //下载进度设置...
        } completionHandler:^(NSString *path, NSError *error) {
            if (error) {
                callback(error);
            } else {
                WXLog(@"HotUpdate -- unzip file %@", path);
                //校验文件md5
                BOOL flag = [weakSelf isLegalFile:path];
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
                                [weakSelf savePackageInfo:path];
                                callback(nil);
                            }
                        });
                    }];
                }else {
                    //文件校验不通过
                    callback([weakSelf errorWithMessage:UCX_ERROR_FILE_VALIDATE]);
                }
            }
        }];
    }else if (type==UCXHotUpdateTypeLocal) {
        //同步方式解压文件 & 解析配置
        //找到压缩文件 & 配置文件
        NSFileManager *fileManager = [NSFileManager defaultManager];
        NSString *assetsPath = [NSString stringWithFormat:@"%@/%@",[[NSBundle mainBundle] resourcePath],@"assets"];
        NSDirectoryEnumerator *myDirectoryEnumerator = [fileManager enumeratorAtPath:assetsPath];  //assetsPath
        
        NSString *fileName;
        NSString *jsonFilePath;
        NSString *zipFilePath;
        NSString *tmpName;
        while((tmpName= [myDirectoryEnumerator nextObject])) {    //遍历当前目录
            if([[tmpName pathExtension] isEqualToString:@"json"]) { //取得后缀名为.json的文件名
                jsonFilePath = [assetsPath stringByAppendingPathComponent:tmpName];
            }else if ([[tmpName pathExtension] isEqualToString:@"zip"]) {//取得后缀名为.zip的文件名
                zipFilePath = [assetsPath stringByAppendingPathComponent:tmpName];
                //
                NSRange range = [tmpName rangeOfString:@"." options:NSBackwardsSearch];
                fileName = [tmpName substringToIndex:range.location];
            }
        }
        //解析配置文件
        NSData *jsonData = [NSData dataWithContentsOfFile:jsonFilePath];
        NSError *jsonError;
        NSDictionary *jsonDict = [NSJSONSerialization JSONObjectWithData:jsonData options:kNilOptions error:&jsonError];
        if (jsonError) {
            callback([weakSelf errorWithMessage:UCX_ERROR_JSON_PARSE]);
            return;
        }
        //
        self.options = [jsonDict mutableCopy];
        //
        NSString *dir = UCXDownloadDir;
        BOOL success = [self.fileManager createDir:dir];
        if (!success) { //json配置文件解析失败
            callback([weakSelf errorWithMessage:UCX_ERROR_FILE_OPERATION]);
            return;
        }
        NSString *unzipFilePath = [dir stringByAppendingPathComponent:fileName];
        //解压文件,同步解压
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
        [weakSelf savePackageInfo:unzipFilePath];
    }
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
         "unzipFilePath":""
    ]
 
 */
- (void)savePackageInfo:(NSString *)unzipFilePath {
    if ([self.options count]>0 && unzipFilePath) {
        //
        NSString *url = [self.options objectForKey:@"url"];
        NSDictionary *originPackageInfo = [self.options objectForKey:@"packageInfo"];
        //重组新的数据结构:结构如上注释
        NSMutableDictionary *newPackageInfo = [NSMutableDictionary dictionaryWithDictionary:originPackageInfo];
        if (url) {
            [newPackageInfo setObject:url forKey:@"url"];
        }
        if (unzipFilePath) {
            [newPackageInfo setObject:unzipFilePath forKey:@"unzipFilePath"];
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
        //set cache path into memory
        [UCXAppConfiguration cachePath];
    }
}

/**
 * 校验文件是否合法
 */
- (BOOL)isLegalFile:(NSString *)path {
    BOOL flag = NO;
    NSString *computedFileMD5 = [UCXUtil fileMD5:path];
    if ([self.options count]>0) {
        NSDictionary *packageInfo = [self.options objectForKey:@"packageInfo"];
        if ([packageInfo count]>0) {
            NSString *originMD5 = [packageInfo objectForKey:@"md5"];
            if (computedFileMD5 && originMD5 && [computedFileMD5 isEqualToString:originMD5]) {
                flag = YES;
            }
        }
    }
    return flag;
}

#pragma mark - private method

- (NSError *)errorWithMessage:(NSString *)errorMessage
{
    return [NSError errorWithDomain:@"weex.ucar.hotupdate"
                               code:-1
                           userInfo:@{ NSLocalizedDescriptionKey: errorMessage}];
}


- (NSString *)zipExtension:(UCXHotUpdateType)type
{
    switch (type) {
        case UCXHotUpdateTypeRemote:
            return @".zip";
        case UCXHotUpdateTypeLocal:
            return @".zip";
        default:
            return @".zip";
            break;
    }
}

@end
