# 打包工具 - weextools

## 介绍
- weex 官方已经提供了 [weex-toolkit](http://weex.apache.org/cn/guide/tools/toolkit.html) 脚手架命令行工具，使用该工具可以方便地进行项目创建、开发调试、编译打包；
- 但是 weex-toolkit 依赖初始化配置太强，导致自己封装的native模块在运行 build、install 等命令时失败；
- 基于上述原因，我们对其进行了一定程度的定制，开发出了weextools；
- weextools 仍然支持 weex-toolkit 所有的命令，并且可以在新的项目结构下顺利地执行 build、install 等命令。

## 常用命令

weextools 支持的常用命令与 weex-toolkit 一致，具体如下：
```
create [name]         initialize a standard weex project
platform [command]    command for add or remove a  platform project
run [platform]        run weex app on the specific platform
build [platform]      build weex app generator package(apk or ipa)
install [platform]    install weex app to mobile
plugin [command]      command for add,create,login,publish weex plugins
weexplugin [command]  create a project that is a manager of plugin
help [cmd]            display help for [cmd]
```

## 补充说明

特别地，对其中的 build 和 install 命令进行说明：

- Android 项目编译打包

    执行如下命令可对 Android 项目进行编译并打包生成 apk 文件

    ```
    weextools build android
    ```
    
    执行该命令，实际上会运行脚本将项目中的 js 文件以及资源文件拷贝到 Android 项目的指定目录，并执行 gralde 命令打出 Android debug 包和 release 包

    ```
    1. copy('./dist/native', 'platforms/android/app/src/main/assets/dist/native')   // 拷贝js文件
    2. copy('./src/image', 'platforms/android/app/src/main/assets/image')  //拷贝资源文件
    3. gradlew.bat  assemble   // 打出debug release
    ```

- 安装打包好的 Android apk
    
    若已经顺利完成编译、打包过程，则可以运行如下命令安装打包好的 apk 到手机上
    ```
    weextools install android
    ```
    
    执行该命令，实际上是运行 adb 将 apk 安装到真机或模拟器中
    ```
    adb  install -r  ${apkName} //('app/build/outputs/apk/app-debug.apk)
    ```