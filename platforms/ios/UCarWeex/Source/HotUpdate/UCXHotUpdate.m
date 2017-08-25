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


@interface UCXHotUpdate ()

@property (nonatomic, strong) UCXHotUpdateManager *fileManager;

@end

@implementation UCXHotUpdate

- (instancetype)init
{
    self = [super init];
    if (self) {
        _fileManager = [[UCXHotUpdateManager alloc] init];
    }
    return self;
}

- (void)dealloc {
    NSLog(@"UCXHotUpdate dealloc");
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


- (void)hotUpdate:(UCXHotUpdateType)type options:(NSDictionary *)options callback:(void (^)(NSError *error))callback
{
    __weak typeof(self) weakSelf = self;
    //
    NSString *updateUrl = [WXConvert NSString:options[@"updateUrl"]];
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
    
    
    NSString *zipFilePath = [dir stringByAppendingPathComponent:lastPathComponent];
    NSString *unzipFilePath = [dir stringByAppendingPathComponent:fileName];
    
    WXLog(@"HotUpdate -- download file %@", updateUrl);
    [UCXHotUpdateDownloader download:updateUrl savePath:zipFilePath progressHandler:^(long long receivedBytes, long long totalBytes) {
        //下载进度设置...
    } completionHandler:^(NSString *path, NSError *error) {
        if (error) {
            callback(error);
        } else {
            WXLog(@"HotUpdate -- unzip file %@", zipFilePath);
            //校验文件
            
            //
            [self.fileManager unzipFileAtPath:zipFilePath toDestination:unzipFilePath progressHandler:^(NSString *entry,long entryNumber, long total) {
                //压缩进度设置...
            } completionHandler:^(NSString *path, BOOL succeeded, NSError *error) {
                dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
                    if (error) {
                        callback(error);
                    } else {
                        switch (type) {
                            case UCXHotUpdateTypeFullDownload:
                            {
                                //解压完成
                                //...
                                callback(nil);
                            }
                                break;
                            case UCXHotUpdateTypePatchFromPackage:
                            {
                                //...
                            }
                                break;
                            default:
                                callback(nil);
                                break;
                        }
                    }
                });
            }];
        }
    }];
}

- (NSError *)errorWithMessage:(NSString *)errorMessage
{
    return [NSError errorWithDomain:@"weex.ucar.hotupdate"
                               code:-1
                           userInfo:@{ NSLocalizedDescriptionKey: errorMessage}];
}


- (NSString *)zipExtension:(UCXHotUpdateType)type
{
    switch (type) {
        case UCXHotUpdateTypeFullDownload:
            return @".zip";
        case UCXHotUpdateTypePatchFromPackage:
            return @".zip";
        default:
            return @".zip";
            break;
    }
}

@end
