//
//  WeexSDKManager.m
//  WeexDemo
//
//  Created by yangshengtao on 16/11/14.
//  Copyright © 2016年 taobao. All rights reserved.
//

#import "WeexSDKManager.h"
#import "DemoDefine.h"
#import "WeexBundleUrlLoder.h"
#import <WeexSDK/WeexSDK.h>
#import <ATSDK/ATManager.h>
#import <TBWXDevTool/WXDevTool.h>

#import "WXDemoViewController.h"
#import "WeexPluginManager.h"
#import "WXImgLoaderDefaultImpl.h"
#import "WXConfigCenterDefaultImpl.h"
#import "WXSelectComponent.h"

#import "WXEventModule.h"
#import "UCXNavigatorModule.h"


@implementation WeexSDKManager

+ (void)setup;
{
    NSURL *url = nil;
#if DEBUG
    //If you are debugging in device , please change the host to current IP of your computer.
    WeexBundleUrlLoder *loader = [WeexBundleUrlLoder new];
    url = [loader jsBundleURL];
    if (!url) {
        url = [NSURL URLWithString:BUNDLE_URL];
    }
#else
    url = [NSURL URLWithString:BUNDLE_URL];
#endif
    
    [self initWeexSDK];
    [WeexPluginManager registerWeexPlugin];
    [self loadCustomContainWithScannerWithUrl:url];
}

+ (void)initWeexSDK
{
    
#ifdef DEBUG
    [WXDevTool setDebug:YES];
    [WXDevTool launchDevToolDebugWithUrl:[NSString stringWithFormat:@"ws://%@:8088/debugProxy/native",CURRENT_IP]];
#else
    //    [WXDevTool setDebug:NO];
#endif
    [WXAppConfiguration setAppGroup:@"AliApp"];
    [WXAppConfiguration setAppName:@"WeexDemo"];
    [WXAppConfiguration setAppVersion:@"1.8.3"];
    [WXAppConfiguration setExternalUserAgent:@"ExternalUA"];
    
    [WXSDKEngine initSDKEnvironment];
    
    [WXSDKEngine registerHandler:[WXImgLoaderDefaultImpl new] withProtocol:@protocol(WXImgLoaderProtocol)];
    [WXSDKEngine registerHandler:[WXEventModule new] withProtocol:@protocol(WXEventModuleProtocol)];
    [WXSDKEngine registerHandler:[WXConfigCenterDefaultImpl new] withProtocol:@protocol(WXConfigCenterProtocol)];
    
    [WXSDKEngine registerComponent:@"select" withClass:NSClassFromString(@"WXSelectComponent")];
    
    [WXSDKEngine registerModule:@"event" withClass:[WXEventModule class]];
    [WXSDKEngine registerModule:@"uNavigator" withClass:[UCXNavigatorModule class]];
    
#ifdef DEBUG
    [[self class] atAddPlugin];
    [WXDebugTool setDebug:YES];
    [WXLog setLogLevel:WXLogLevelLog];
    
    #ifndef UITEST
        [[ATManager shareInstance] show];
    #endif
    
#else
    [WXDebugTool setDebug:NO];
    [WXLog setLogLevel:WXLogLevelError];
#endif
}

+ (void)atAddPlugin {

    [[ATManager shareInstance] addPluginWithId:@"weex" andName:@"weex" andIconName:@"../weex" andEntry:@"" andArgs:@[@""]];
    [[ATManager shareInstance] addSubPluginWithParentId:@"weex" andSubId:@"logger" andName:@"logger" andIconName:@"log" andEntry:@"WXATLoggerPlugin" andArgs:@[@""]];
//    [[ATManager shareInstance] addSubPluginWithParentId:@"weex" andSubId:@"viewHierarchy" andName:@"hierarchy" andIconName:@"log" andEntry:@"WXATViewHierarchyPlugin" andArgs:@[@""]];
    [[ATManager shareInstance] addSubPluginWithParentId:@"weex" andSubId:@"test2" andName:@"test" andIconName:@"at_arr_refresh" andEntry:@"" andArgs:@[]];
    [[ATManager shareInstance] addSubPluginWithParentId:@"weex" andSubId:@"test3" andName:@"test" andIconName:@"at_arr_refresh" andEntry:@"" andArgs:@[]];
}

+ (void)loadCustomContainWithScannerWithUrl:(NSURL *)url
{
    UIViewController *demo = [[WXDemoViewController alloc] init];
    ((WXDemoViewController *)demo).url = url;
    [[UIApplication sharedApplication] delegate].window.rootViewController = [[WXRootViewController alloc] initWithRootViewController:demo];
}

@end
