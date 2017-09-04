//
//  UCXDebugTool.h
//  Pods
//
//  Created by huyujin on 2017/9/4.
//
//

#import <Foundation/Foundation.h>

@interface UCXDebugTool : NSObject

+ (instancetype)shared;


+ (void)initDebugInfo;

#pragma mark - debug
/**
 *  set debug status
 *  @param isDebug  : YES:open debug model and inspect model;
 *                    default is NO,if isDebug is NO, open inspect only;
 **/
+ (void)setDebug:(BOOL)isDebug;

/**
 *  get debug status
 **/
+ (BOOL)isDebug;

+ (void)setDebugIP:(NSString *)ip;

/**
 *  @param url  : ws://ip:port/debugProxy/native, ip and port is your devtool server address
 *                eg:@"ws://30.30.29.242:8088/debugProxy/native"
 */
+ (void)setDevDebugUrl:(NSString *)url;
+ (NSString *)debugUrl;

/**
 *  @param url  : http://ip:port/dist/native, ip and port is your devtool server address
 *               eg:@"http://30.30.29.242:12588/dist/native"
 */
+ (void)setDevWebUrl:(NSString *)url;
+ (NSString *)webUrl;


@end
