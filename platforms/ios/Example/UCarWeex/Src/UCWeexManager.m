//
//  UCWeexManager.m
//  UCarWeex
//
//  Created by huyujin on 2017/8/11.
//  Copyright © 2017年 hyj223. All rights reserved.
//

#import "UCWeexManager.h"

#import <UCarWeex/UCarWeex.h>

#ifdef DEBUG
#import <TBWXDevTool/TBWXDevTool.h>
#endif

#import "UCImgLoaderDefaultImpl.h"

@interface UCWeexManager ()

@end

@implementation UCWeexManager

+ (void)setup {
    // 初始化UCARWEEX的可设置项
    [UCarWeexService setAppGroup:@"ucarinc"];
    [UCarWeexService setAppName:APP_NAME];
    [UCarWeexService setAppVersion:APP_VERSION];
#ifdef DEBUG
    [UCarWeexService setLogLevel:WXLogLevelLog];
    [WXDevTool setDebug:YES];
    [WXDevTool launchDevToolDebugWithUrl:[NSString stringWithFormat:@"ws://%@:8088/debugProxy/native",LOCAL_IP]];
#else
    [UCarWeexService setLogLevel:WXLogLevelError];
#endif
    [UCarWeexService initUCarWeexService];
    //
    [UCarWeexService registerHandler:[UCImgLoaderDefaultImpl new] withProtocol:@protocol(WXImgLoaderProtocol)];
    
    //从远程拉取
    if (UC_JS_LOAD_TYPE==0) {
        [UCarWeexService hotUpdate:UCXHotUpdateTypeLocal options:@{} callback:^(NSError *error) {
            //解压回调...
            NSLog(@"error:::%@",[error localizedDescription]);
        }];
    } else {
        NSDictionary *options = @{@"url":@"http://10.99.21.32:12588/dist/ucar-weex_1_20170824151141.zip"};
        [UCarWeexService hotUpdate:UCXHotUpdateTypeRemote options:options callback:^(NSError *error) {
            //下载解压回调...
            NSLog(@"error:::%@",[error localizedDescription]);
        }];
    }
}

@end
