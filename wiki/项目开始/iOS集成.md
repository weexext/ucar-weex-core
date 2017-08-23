## iOS集成

#### 通过cocoaPods 集成 UCarWeex 到你的项目
	
	pod 'UCarWeex', :git=>'http://10.3.4.127:8888/mat/ucar-weex.git'
	pod 'WXDevtool', '~>0.15.3', :configurations => ['Debug']
	

  换到你已有项目 Podfile 这个文件存在的目录，执行如下命令，
  
    pod install 
    

#### 项目代码集成
在 AppDelegate.m 文件中做初始化操作，一般会在 didFinishLaunchingWithOptions 方法中如下添加。

```objectivec

#import <UCarWeex/UCarWeex.h>

#ifdef DEBUG
#import <TBWXDevTool/WXDevTool.h>
#endif

#import "UCImgLoaderDefaultImpl.h"

	
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
      
```

#### EXAMPLE项目
EXAMPLE项目地址：[http://10.3.4.127:8888/mat/ucar-weex](http://10.3.4.127:8888/mat/ucar-weex)