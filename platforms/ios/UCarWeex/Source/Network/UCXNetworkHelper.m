//
//  UCXNetworkHelper.m
//  Pods
//
//  Created by huyujin on 2017/8/29.
//
//

#import "UCXNetworkHelper.h"
#import "UCXUtil.h"

@interface UCXNetworkHelper ()

@end

@implementation UCXNetworkHelper

+ (instancetype)shared {
    static dispatch_once_t once = 0;
    static UCXNetworkHelper *instance;
    dispatch_once(&once, ^{
        instance = [[self alloc] init];
    });
    return instance;
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
                            failure:(UCXRequestFailure)failure
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
                NSError *jsonError = [UCXUtil errorWithMessage:UCX_ERROR_JSON_PARSE];
                failure(jsonError);
            }
        }
    }];
    [dataTask resume];
    
    return dataTask;
}

- (__kindof NSURLSessionTask *)GET:(NSString *)URL
                        parameters:(NSDictionary *)parameters
                           success:(UCXRequestSuccess)success
                           failure:(UCXRequestFailure)failure
{
    UCXLog(@"[UCNETWORK]:\n[URL]:%@\n[PARAMS]:%@",URL,parameters);
    NSString *paramsStr = [[self class] buildGETParamsStr:parameters];
    NSString *url = [NSString stringWithFormat:@"%@?%@",URL,paramsStr];
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:url]];
    // Create the URLSession on the default configuration
    NSURLSessionConfiguration *sessionConfiguration = [NSURLSessionConfiguration defaultSessionConfiguration];
    NSURLSession *session = [NSURLSession sessionWithConfiguration:sessionConfiguration];
    
    NSURLSessionDataTask *dataTask = [session dataTaskWithRequest:request completionHandler:^(NSData * _Nullable data, NSURLResponse * _Nullable response, NSError * _Nullable error) {
        if (!error) {
            NSError *jsonError;
            NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:&jsonError];
            if (dict && [dict count]>0) {
                UCXLog(@"\n[UCNETWORK]:success\n[DATA]:%@\n",dict);
                success(dict);
            }else {
                UCXLog(@"\n[UCNETWORK]:failure\n[JSONERROR]:%@\n",[jsonError localizedDescription]);
                failure(jsonError);
            }
        } else {
            UCXLog(@"\n[UCNETWORK]:failure\n[NETRROR]:%@\n",[error localizedDescription]);
            failure(error);
        }
    }];
    [dataTask resume];
    
    return dataTask;
}

+ (NSString *)buildGETParamsStr:(NSDictionary *)params {
    NSMutableString *mutableStr = [[NSMutableString alloc] init];
    [params enumerateKeysAndObjectsUsingBlock:^(NSString *key, NSString *obj, BOOL * _Nonnull stop) {
        NSString *keyValueFormat = [NSString stringWithFormat:@"%@=%@",key,obj];
        [mutableStr appendFormat:@"%@&",keyValueFormat];
    }];
    [mutableStr deleteCharactersInRange:NSMakeRange(mutableStr.length-1, 1)];
    //
    NSString *paramsStr = [mutableStr stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    
    return paramsStr;
}

@end
