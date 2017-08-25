//
//  UCXHotUpdate.h
//  Pods
//
//  Created by huyujin on 2017/8/23.
//  Copyright © 2017年 ucarinc. All rights reserved.
//

#import <Foundation/Foundation.h>

// error def
static NSString * const UCX_ERROR_OPTIONS = @"options error";
static NSString * const UCX_ERROR_FILE_OPERATION = @"file operation error";
static NSString * const UCX_ERROR_JSON_PARSE = @"json parse error";

typedef NS_ENUM(NSInteger, UCXHotUpdateType) {
    UCXHotUpdateTypeFullDownload = 1,      //全量
    UCXHotUpdateTypePatchFromPackage = 2,  //部分,暂不支持.
};

/** 请求成功的Block */
typedef void(^UCXRequestSuccess)(NSDictionary *responseObj);
/** 请求失败的Block */
typedef void(^UCXRequestFailure)(NSError *error);

@interface UCXHotUpdate : NSObject


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

/** 更新包下载解压校验
 *  options:
        updateUrl:更新包下载地址
 */
- (void)hotUpdate:(UCXHotUpdateType)type
          options:(NSDictionary *)options
         callback:(void (^)(NSError *error))callback;


@end
