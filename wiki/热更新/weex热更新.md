#ucar-weex 热更新

##android 
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
