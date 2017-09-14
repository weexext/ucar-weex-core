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
 * 判断是否处于调试状态，若是则可以设置 setWeexDebug || setRemote
 */
+ (void)setDebug:(BOOL)isDebug;
+ (BOOL)isDebug;

/**
 *  设置是否处于浏览器调试模式
 *  @param isWeexDebug  : YES:open debug model and inspect model;
 *                    default is NO,if isDebug is NO, open inspect only;
 **/
+ (void)setWeexDebug:(BOOL)isWeexDebug;
+ (BOOL)isWeexDebug;

/**
 *  设置资源是从本地拉取还是远程拉取
 **/
+ (void)setRemote:(BOOL)isRemote;
+ (BOOL)isRemote;

/**
 *  eg::: 10.99.21.32
 */
+ (void)setWeexDebugIP:(NSString *)ip;

/** 更自由的自定义 debug url
 *  @param url  : ws://ip:port/debugProxy/native, ip and port is your devtool server address
 *                eg:@"ws://30.30.29.242:8088/debugProxy/native"
 */
+ (void)setWeexDebugUrl:(NSString *)url;
+ (NSString *)weexDebugUrl;

/**
 *  eg::: 10.99.21.32
 */
+ (void)setWebIP:(NSString *)ip;

/** 更自由的自定义 web url
 *  @param url  : http://ip:port/dist/native, ip and port is your devtool server address
 *               eg:@"http://30.30.29.242:12588/dist/native"
 */
+ (void)setWebUrl:(NSString *)url;
+ (NSString *)webUrl;


@end
