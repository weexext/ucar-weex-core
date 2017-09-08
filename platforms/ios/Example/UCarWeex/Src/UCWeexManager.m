//
//  UCWeexManager.m
//  UCarWeex
//
//  Created by huyujin on 2017/8/11.
//  Copyright © 2017年 hyj223. All rights reserved.
//

#import "UCWeexManager.h"

#import <UCarWeex/UCarWeex.h>

#import "UCImgLoaderDefaultImpl.h"

@interface UCWeexManager ()

@end

@implementation UCWeexManager

+ (void)setup {
    //自定义全局环境变量
//    NSDictionary *customEnvironmentDict = @{@"url":@"http://xxx.com",@"debugState":@"false"};
//    [WXSDKEngine setCustomEnvironment:customEnvironmentDict];
    // 初始化UCARWEEX的可设置项
    [UCXAppConfiguration setAppGroup:@"ucarinc"];
    [UCXAppConfiguration setAppName:APP_NAME];
    [UCXAppConfiguration setAppVersion:APP_VERSION];

    //====================调试相关设置:::须在代码 [UCarWeexService initUCarWeexService] 前执行~================================
    /**
     * 设置是否处于调试状态
     * YES, 则 setWeexDebug && setRemote 才会生效，NO，则以下不生效
     */
    [UCXDebugTool setDebug:YES];
    /**  YES,则 weex debug mode 开启，若NO，则 weex debug mode 关闭
     *   若debug YES,则会需要设置debugIP ,若debug NO，则无需设置debugIP
     *   若current ip:::10.99.21.32
     *   会采用以下默认设置:::
     *   则 weex debug url::: ws://10.99.21.32:8088/debugProxy/native
     */
    [UCXDebugTool setWeexDebug:UC_WEEX_DEBUG_MODE];
    [UCXDebugTool setWeexDebugIP:@"10.99.21.32"];
    
    /** YES,则 从指定IP拉取JS及资源，若NO，则 从本地拉取JS及资源
     *   若isRemote YES,则会需要设置webIP ,若isRemote NO，则无需设置webIP
     *   若current ip:::10.99.21.32,则会拉取指定web url的JS及资源
     *   会采用以下默认设置:::
     *   则      web url::: http://10.99.21.32:12588/dist/native
     */
    [UCXDebugTool setRemote:UC_JS_LOAD_TYPE];
    [UCXDebugTool setWebIP:@"10.99.21.32"];
    //==========================================================================================================================
#ifdef DEBUG
    [UCarWeexService setLogLevel:WXLogLevelLog];
#else
    [UCarWeexService setLogLevel:WXLogLevelError];
#endif
    [UCarWeexService initUCarWeexService];
    //
    [UCarWeexService registerHandler:[UCImgLoaderDefaultImpl new] withProtocol:@protocol(WXImgLoaderProtocol)];
    
    //启动时默认从以下指定位置解压 本地JS & 图片资源
    // url:::assets/weex/，若未赋值，则使用默认地址：assets/weex/
    NSDictionary *dict = @{@"url":@"assets/weex/"};
    [UCXHotUpdate unpack:dict callback:^(NSError *error) {}];
//    //若使用热更新，则使用如下代码：：url为拉取指定更新信息的远程地址
//    NSDictionary *options = @{@"url":@"http://10.99.21.32:3000/ucarweex"};
//    [UCXHotUpdate hotUpdate:options callback:^(NSError *error) {
//        //...
//    }];
    
}

@end
