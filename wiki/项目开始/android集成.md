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
//更新需要
 compile 'com.squareup.okhttp3:okhttp:3.8.1'
 //weex chrome 调试相关 debug 引入
 debugCompile 'com.google.code.findbugs:jsr305:2.0.1'
 debugCompile 'com.squareup.okhttp:okhttp:2.3.0'
 debugCompile 'com.squareup.okhttp:okhttp-ws:2.3.0'
 debugCompile 'com.taobao.android:weex_inspector:0.10.0.5'
 debugCompile 'com.journeyapps:zxing-android-embedded:3.4.0'

// taobao weexsdk
compile 'com.ucar:weex_sdk:1.6@aar'

// ucar weexsdk 拓展
compile 'com.ucar:weexext_sdk:1.0.8@aar'

```
最后，还需要在 app 的 build.gradle 中对 ndk 进行配置：

```
defaultConfig {
    ...
    ndk {
        abiFilters "x86"
        abiFilters "armeabi"
    }
}
```
添加该 ndk 配置可解决部分手机架构无法加载到 weex 所需 .so 文件的问题。
## 初始化

```
/**
 * weex 初始化
 */
public class WXApplication extends Application {
    public static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        UWXInit.init(this);
        /**
         * assets/weex/ucar-weex_3_20170828123442
         */
        UWXResManager.getInstance().addWXResFromAssert(this, getWXPackageFileName("weex"));
//        UWXResManager.getInstance().setServerUrl("");
        UWXResManager.getInstance().checkUpdate(new UWXResManager.CheckUpdateCallback() {
            @Override
            public void callback(int code, String msg, WXPackageInfo info) {
                Toast.makeText(WXApplication.this, msg, Toast.LENGTH_LONG).show();
                UWLog.d("WXApp", msg);
                //重启 提示
            }
        });
    }

    @NonNull
    public static String getWXPackageFileName(String weexFileName) {
        try {
            String[] assets = instance.getAssets().list(weexFileName);
            if (!ArrayUtils.isEmpty(assets)) {
                String asset = assets[0];
                int i = asset.indexOf(".");
                String rnName = asset.substring(0, i);
                weexFileName = weexFileName + "/" + rnName;
                UWLog.v("Weex", "Weex=" + weexFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weexFileName;
    }
}

```

### 源码依赖

1. 下载源码 git clone https://github.com/alibaba/weex
2. 创建 Android 工程。
3. 通过以下路径引入 SDK Module
File->New-Import Module-> 选择 UCAR-WEEX SDK Module(weex/android/sdk) -> Finish
app 的 build.gradle 中添加如下依赖:compile project(':ucar-weex')

