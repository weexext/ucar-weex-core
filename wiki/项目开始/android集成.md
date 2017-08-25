# Android 项目集成

## 添加依赖

首先，在您 Project 下的 build.gradle 中添加如下 maven 仓库

```
allprojects {
     repositories {
         maven { url 'https://raw.githubusercontent.com/weexext/sdklibs/master/repository' }
     }
 }
```

然后，在您相应 Module 下的 build.gradle 中添加如下依赖

```
//weex 依赖 Android SDK version > 23
 compile "com.facebook.fresco:fresco:0.10.0"
 compile "com.android.support:appcompat-v7:23.1.1"
 compile "com.android.support:recyclerview-v7:23.1.1"
 compile "com.android.support:support-v4:23.1.1"
 compile "com.alibaba:fastjson:1.1.46.android"

 //weex chrome 调试相关 debug 引入
 debugCompile 'com.google.code.findbugs:jsr305:2.0.1'
 debugCompile 'com.squareup.okhttp:okhttp:2.3.0'
 debugCompile 'com.squareup.okhttp:okhttp-ws:2.3.0'
 debugCompile 'com.taobao.android:weex_inspector:0.10.0.5'
 debugCompile 'com.journeyapps:zxing-android-embedded:3.4.0'

// taobao weexsdk
compile 'com.ucar:weex_sdk:1.6@aar'

// ucar weexsdk 拓展
compile 'com.ucar:weexext_sdk:1.0.5@aar'

```

## 初始化

```
package com.ucar.wxd;

import android.app.Application;

import com.ucar.weex.UWXInit;

/**
* weex 初始化
*/
public class WXApplication extends Application {
 @Override
 public void onCreate() {
     super.onCreate();
     //ucar weex 初始化
     UWXInit.init(this);
 }
}
```

### 源码依赖

1. 下载源码 git clone https://github.com/alibaba/weex
2. 创建 Android 工程。
3. 通过以下路径引入 SDK Module
File->New-Import Module-> 选择 UCAR-WEEX SDK Module(weex/android/sdk) -> Finish
app 的 build.gradle 中添加如下依赖:compile project(':ucar-weex')

