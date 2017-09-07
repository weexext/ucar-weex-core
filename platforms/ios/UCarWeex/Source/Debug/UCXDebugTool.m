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

@property (nonatomic, assign) BOOL isDebug;             ///< 判断是否处于调试状态
@property (nonatomic, assign) BOOL isWeexDebug;         ///< 判断是否处于浏览器调试模式
@property (nonatomic, assign) BOOL isRemote;            ///< 判断是否从远程拉取JS及资源

@property (nonatomic, strong) NSString *weexDebugIP;    ///< debug ip
@property (nonatomic, strong) NSString *weexDebugUrl;   ///< debug url

@property (nonatomic, strong) NSString *webIP;          ///< web ip
@property (nonatomic, strong) NSString *webUrl;         ///< web url

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

- (instancetype)init {
    self = [super init];
    if (self) {
        _isDebug = NO;
        _isWeexDebug = NO;
        _isRemote = NO;
    }
    return self;
}

+ (void)initDebugInfo {
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    NSDictionary *dict = [userDefaults objectForKey:UCX_US_UCAR_WEEX_DEBUG_KEY];
    if (dict && [dict count]>0) { //若已有设置，则使用本地设置
        NSString *isDebug = [dict objectForKey:@"isDebug"];
        if (isDebug && [isDebug isEqualToString:@"true"]) {
            [UCXDebugTool setDebug:YES];
            NSString *isWeexDebug = [dict objectForKey:@"isWeexDebug"];
            if (isWeexDebug && [isWeexDebug isEqualToString:@"true"]) {
                NSString *weexDebugIP = [dict objectForKey:@"weexDebugIP"];
                NSString *weexDebugUrl = [dict objectForKey:@"weexDebugUrl"];
                [UCXDebugTool setWeexDebug:YES];
                [UCXDebugTool setWeexDebugIP:weexDebugIP];
                [UCXDebugTool setWeexDebugUrl:weexDebugUrl];
                
//                [WXDevTool setDebug:YES];
                [WXDevTool launchDevToolDebugWithUrl:weexDebugUrl];
            }
            NSString *isRemote = [dict objectForKey:@"isRemote"];
            if (isRemote && [isRemote isEqualToString:@"true"]) {
                NSString *webIP = [dict objectForKey:@"webIP"];
                NSString *webUrl = [dict objectForKey:@"webUrl"];
                [UCXDebugTool setRemote:YES];
                [UCXDebugTool setWebIP:webIP];
                [UCXDebugTool setWebUrl:webUrl];
            }
        }
    }else { //若无，则使用代码设置
        NSMutableDictionary *debugInfo = [NSMutableDictionary dictionary];
        BOOL isDebug = [UCXDebugTool isDebug];
        if (isDebug) {
            [debugInfo setObject:@"true" forKey:@"isDebug"];
            BOOL isWeexDebug = [UCXDebugTool shared].isWeexDebug;
            if (isWeexDebug) {
                NSString *weexDebugIP = [UCXDebugTool shared].weexDebugIP;
                NSString *weexDebugUrl = [UCXDebugTool shared].weexDebugUrl;
                if (!weexDebugUrl) {
                    weexDebugUrl = [NSString stringWithFormat:@"ws://%@:8088/debugProxy/native",weexDebugIP];
                    [UCXDebugTool shared].weexDebugUrl = weexDebugUrl;
                }
                [debugInfo setObject:@"true" forKey:@"isWeexDebug"];
                [debugInfo setObject:weexDebugIP forKey:@"weexDebugIP"];
                [debugInfo setObject:weexDebugUrl forKey:@"weexDebugUrl"];
                //
                [WXDevTool launchDevToolDebugWithUrl:weexDebugUrl];
            }
            BOOL isRemote = [UCXDebugTool isRemote];
            if (isRemote) {
                NSString *webIP = [UCXDebugTool shared].webIP;
                NSString *webUrl = [UCXDebugTool shared].webUrl;
                if (!webUrl) {
                    webUrl = [NSString stringWithFormat:@"http://%@:12588/dist/native",webIP];
                    [UCXDebugTool shared].webUrl = webUrl;
                }
                [debugInfo setObject:@"true" forKey:@"isRemote"];
                [debugInfo setObject:webIP forKey:@"webIP"];
                [debugInfo setObject:webUrl forKey:@"webUrl"];
            }
            [userDefaults setObject:debugInfo forKey:UCX_US_UCAR_WEEX_DEBUG_KEY];
            [userDefaults synchronize];
        }
        
    }
}

#pragma mark - debug

+ (void)setDebug:(BOOL)isDebug {
    [UCXDebugTool shared].isDebug = isDebug;
}

+ (BOOL)isDebug {
    return [UCXDebugTool shared].isDebug;
}

+ (void)setWeexDebug:(BOOL)isWeexDebug {
    [UCXDebugTool shared].isWeexDebug = isWeexDebug;
}

+ (BOOL)isWeexDebug {
    return [UCXDebugTool shared].isWeexDebug;
}

+ (void)setRemote:(BOOL)isRemote {
    [UCXDebugTool shared].isRemote = isRemote;
}

+ (BOOL)isRemote {
    return [UCXDebugTool shared].isRemote;
}

+ (void)setWeexDebugIP:(NSString *)ip {
    [UCXDebugTool shared].weexDebugIP = ip;
}

+ (void)setWeexDebugUrl:(NSString *)url {
    [UCXDebugTool shared].weexDebugUrl = url;
}

+ (NSString *)weexDebugUrl {
    return [UCXDebugTool shared].weexDebugUrl;
}

+ (void)setWebIP:(NSString *)ip {
    [UCXDebugTool shared].webIP = ip;
}
+ (void)setWebUrl:(NSString *)url {
    [UCXDebugTool shared].webUrl = url;
}
+ (NSString *)webUrl {
    return [UCXDebugTool shared].webUrl;
}

@end
