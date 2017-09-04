//
//  UCXDebugTool.m
//  Pods
//
//  Created by huyujin on 2017/9/4.
//
//

#import "UCXDebugTool.h"
#ifdef DEBUG
#import <TBWXDevTool/TBWXDevTool.h>
#endif

#import "UCXDefine.h"

@interface UCXDebugTool ()

@property (nonatomic, assign) BOOL isDebug;         ///< 是否处于调试模式

@property (nonatomic, strong) NSString *debugIP;    ///< debug ip
@property (nonatomic, strong) NSString *debugUrl;   ///< debug url
@property (nonatomic, strong) NSString *webUrl;     ///< web url

@end

@implementation UCXDebugTool

+ (instancetype)shared {
    static dispatch_once_t once = 0;
    static UCXDebugTool *instance;
    dispatch_once(&once, ^{
        instance = [[self alloc] init];
    });
    return instance;
}

+ (void)initDebugInfo {
    BOOL isDebug = [UCXDebugTool isDebug];
    if (isDebug) {
        //
        [WXDevTool setDebug:isDebug];
        //
        NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
        NSDictionary *dict = [userDefaults objectForKey:UCX_US_UCAR_WEEX_DEBUG_KEY];
        if (dict && [dict count]>0) {
            NSArray *ipArr = [dict objectForKey:@"ipArr"];
            NSString *debugUrl = [dict objectForKey:@"debugUrl"];
            NSString *webUrl = [dict objectForKey:@"webUrl"];
            [UCXDebugTool setDevDebugUrl:debugUrl];
            [UCXDebugTool setDevWebUrl:webUrl];
            [UCXDebugTool setDebugIP:[ipArr firstObject]];
            //
            [WXDevTool launchDevToolDebugWithUrl:debugUrl];
        }else {
            NSMutableDictionary *debugInfo = [NSMutableDictionary dictionary];
            NSString *ip = [UCXDebugTool shared].debugIP;
            if (ip) {
                NSString *debugUrl = [UCXDebugTool shared].debugUrl;
                if (!debugUrl) {
                    debugUrl = [NSString stringWithFormat:@"ws://%@:8088/debugProxy/native",ip];
                }
                NSString *webUrl = [UCXDebugTool shared].webUrl;
                if (!webUrl) {
                    webUrl = [NSString stringWithFormat:@"http://%@:12588/dist/native",ip];
                }
                NSArray *ipArr = [NSArray arrayWithObject:ip];
                [debugInfo setObject:ipArr forKey:@"ipArr"];
                [debugInfo setObject:debugUrl forKey:@"debugUrl"];
                [debugInfo setObject:webUrl forKey:@"webUrl"];
                //
                [userDefaults setObject:debugInfo forKey:UCX_US_UCAR_WEEX_DEBUG_KEY];
                [userDefaults synchronize];
                //
                [WXDevTool launchDevToolDebugWithUrl:debugUrl];
            }
        }
    }
}

#pragma mark - debug

+ (void)setDebugIP:(NSString *)ip {
    [UCXDebugTool shared].debugIP = ip;
}

+ (void)setDebug:(BOOL)isDebug {
    [UCXDebugTool shared].isDebug = isDebug;
}

+ (BOOL)isDebug {
    return [UCXDebugTool shared].isDebug;
}

+ (void)setDevDebugUrl:(NSString *)url {
    [UCXDebugTool shared].debugUrl = url;
}

+ (NSString *)debugUrl {
    return [UCXDebugTool shared].debugUrl;
}

+ (void)setDevWebUrl:(NSString *)url {
    [UCXDebugTool shared].webUrl = url;
}

+ (NSString *)webUrl {
    return [UCXDebugTool shared].webUrl;
}

@end
