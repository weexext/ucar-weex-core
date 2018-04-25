## iOS集成

#### 通过cocoaPods 集成 UCarWeex 到你的项目

```
	pod 'SSZipArchive', '~>2.0.3'
  pod 'WeexSDK', :git => 'https://github.com/weexext/incubator-weex.git', :branch => 'ucarweex-dev'
	pod 'WXDevtool', '~>0.15.3', :configurations => ['Debug']
	pod 'UCarWeex'
```
	

  换到你已有项目 Podfile 这个文件存在的目录，执行如下命令，
  
    pod install 
    

#### 项目代码集成
在 AppDelegate.m 文件中做初始化操作，一般会在 didFinishLaunchingWithOptions 方法中如下添加。

```objectivec

#import <UCarWeex/UCarWeex.h>

#import "UCImgLoaderDefaultImpl.h"

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
     *   若current ip:::1.1.1.1
     *   会采用以下默认设置:::
     *   则 weex debug url::: ws://1.1.1.1:8088/debugProxy/native
     */
    [UCXDebugTool setWeexDebug:UC_WEEX_DEBUG_MODE];
    [UCXDebugTool setWeexDebugIP:@"1.1.1.1"];
    
    /** YES,则 从指定IP拉取JS及资源，若NO，则 从本地拉取JS及资源
     *   若isRemote YES,则会需要设置webIP ,若isRemote NO，则无需设置webIP
     *   若current ip:::1.1.1.1,则会拉取指定web url的JS及资源
     *   会采用以下默认设置:::
     *   则      web url::: http://1.1.1.1:12588/dist/native
     */
    [UCXDebugTool setRemote:UC_JS_LOAD_TYPE];
    [UCXDebugTool setWebIP:@"1.1.1.1"];
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
//    NSDictionary *options = @{@"url":@"http://1.1.1.1:3000/ucarweex"};
//    [UCXHotUpdate hotUpdate:options callback:^(NSError *error) {
//        //...
//    }]; 
    
```

#### EXAMPLE项目[仿今日头条]
EXAMPLE项目地址：[https://github.com/weexext/weex-toutiao](https://github.com/weexext/weex-toutiao)