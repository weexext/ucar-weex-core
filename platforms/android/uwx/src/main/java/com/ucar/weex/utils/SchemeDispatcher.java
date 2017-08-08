package com.ucar.weex.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;

/**
 * Created by chenxi.cui on 2017/8/7.
 */

public abstract class SchemeDispatcher {
    public static final String RESULT_BROADCAST_MESSAGE_ACTION = "_RESULT_BROADCAST_MESSAGE_ACTION_";
    private static String homeScheme = null;

    public SchemeDispatcher() {
    }

    public static String getHomeScheme(Context ctx) {
        if(TextUtils.isEmpty(homeScheme)) {
            homeScheme = getMetaData(ctx.getApplicationContext(), "MAIN_SCHEME");
        }

        return homeScheme;
    }

    public static String getMetaData(Context app, String name) {
        Bundle bundle = app.getApplicationInfo().metaData;
        if(bundle != null && !bundle.isEmpty()) {
            return bundle.getString(name);
        } else {
            XmlResourceParser parser = null;
            AssetManager assmgr = null;

            try {
                assmgr = (AssetManager)AssetManager.class.newInstance();
                Method e = AssetManager.class.getDeclaredMethod("addAssetPath", new Class[]{String.class});
                e.setAccessible(true);
                int cookie = ((Integer)e.invoke(assmgr, new Object[]{app.getApplicationInfo().sourceDir})).intValue();
                if(cookie == 0) {
                    return null;
                } else {
                    String ANDROID_RESOURCES = "http://schemas.android.com/apk/res/android";
                    parser = assmgr.openXmlResourceParser(cookie, "AndroidManifest.xml");
                    boolean findAppMetadata = false;
                    int event = parser.getEventType();

                    while(event != 1) {
                        switch(event) {
                            case 2:
                                String nodeName = parser.getName();
                                String metadataName;
                                if("meta-data".equals(nodeName)) {
                                    findAppMetadata = true;
                                    metadataName = parser.getAttributeValue(ANDROID_RESOURCES, "name");
                                    if(metadataName.equals(name)) {
                                        String var12 = parser.getAttributeValue(ANDROID_RESOURCES, "value");
                                        return var12;
                                    }
                                } else if(findAppMetadata) {
                                    metadataName = null;
                                    return metadataName;
                                }
                            default:
                                event = parser.next();
                        }
                    }

                    return null;
                }
            } catch (Throwable var16) {
                var16.printStackTrace();
                return null;
            } finally {
                if(parser != null) {
                    parser.close();
                }

                if(assmgr != null) {
                    assmgr.close();
                }

            }
        }
    }

    public static void sendSchemeAndClearStack(Fragment context, String url) {
        sendSchemeAndClearStack(context, url, getHomeScheme(context.getActivity()));
    }

    public static void sendSchemeAndClearStack(Context context, String url) {
        sendSchemeAndClearStack(context, url, getHomeScheme(context));
    }

    public static void sendSchemeAndClearStack(Fragment context, String url, Bundle bundle) {
        sendSchemeAndClearStack(context, getHomeScheme(context.getActivity()), url, bundle);
    }

    public static void sendSchemeAndClearStack(Context context, String url, Bundle bundle) {
        sendSchemeAndClearStack(context, getHomeScheme(context), url, bundle);
    }

    public static void sendSchemeAndClearStack(Fragment context, String launcherScheme, String url) {
        sendSchemeAndClearStack((Fragment)context, launcherScheme, url, (Bundle)null);
    }

    public static void sendSchemeAndClearStack(Context context, String launcherScheme, String url) {
        sendSchemeAndClearStack((Context)context, launcherScheme, url, (Bundle)null);
    }

    public static void sendSchemeAndClearStack(Fragment context, String launcherScheme, String url, Bundle bundle) {

        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(launcherScheme));
        intent.addFlags(67108864);
        context.startActivity(intent);
        intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        context.startActivity(intent);
    }

    public static void sendSchemeAndClearStack(Context context, String launcherScheme, String url, Bundle bundle) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(launcherScheme));
        intent.addFlags(67108864);
        context.startActivity(intent);
        intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        context.startActivity(intent);
    }

    public static void sendSchemeForResultAndClearStack(Fragment activity, String url, int requestCode) {
        sendSchemeForResultAndClearStack(activity, getHomeScheme(activity.getActivity()), url, requestCode);
    }

    public static void sendSchemeForResultAndClearStack(Activity activity, String url, int requestCode) {
        sendSchemeForResultAndClearStack(activity, getHomeScheme(activity), url, requestCode);
    }

    public static void sendSchemeForResultAndClearStack(Fragment activity, String launcherScheme, String url, int requestCode) {
        sendSchemeForResultAndClearStack((Fragment)activity, launcherScheme, url, requestCode, (Bundle)null);
    }

    public static void sendSchemeForResultAndClearStack(Activity activity, String launcherScheme, String url, int requestCode) {
        sendSchemeForResultAndClearStack((Activity)activity, launcherScheme, url, requestCode, (Bundle)null);
    }

    public static void sendSchemeForResultAndClearStack(Fragment activity, String launcherScheme, String url, int requestCode, Bundle bundle) {

        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(launcherScheme));
        intent.addFlags(67108864);
        activity.startActivity(intent);
        intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        activity.startActivityForResult(intent, requestCode);
    }

    public static void sendSchemeForResultAndClearStack(Activity context, String launcherScheme, String url, int requestCode, Bundle bundle) {

        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(launcherScheme));
        intent.addFlags(67108864);
        context.startActivity(intent);
        intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        context.startActivityForResult(intent, requestCode);
    }

    public static void sendScheme(Fragment context, String url) {
        sendScheme((Fragment)context, url, (Bundle)null, false, 0);
    }

    public static void sendScheme(Context context, String url) {
        sendScheme((Context)context, url, (Bundle)null, false, 0);
    }

    public static void sendScheme(Fragment context, String url, Bundle bundle) {
        sendScheme((Fragment)context, url, bundle, false, 0);
    }

    public static void sendScheme(Context context, String url, Bundle bundle) {
        sendScheme((Context)context, url, bundle, false, 0);
    }

    public static void sendScheme(Fragment context, String url, boolean clearTop) {
        sendScheme((Fragment)context, url, (Bundle)null, clearTop, 0);
    }

    public static void sendScheme(Context context, String url, boolean clearTop) {
        sendScheme((Context)context, url, (Bundle)null, clearTop, 0);
    }

    public static void sendScheme(Fragment context, String url, int flag) {
        sendScheme((Fragment)context, url, (Bundle)null, false, flag);
    }

    public static void sendScheme(Context context, String url, int flag) {
        sendScheme((Context)context, url, (Bundle)null, false, flag);
    }

    public static void sendScheme(Fragment context, String url, Bundle bundle, boolean clearTop, int flag) {

        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        if(clearTop) {
            intent.addFlags(67108864);
        }

        if(flag != 0) {
            intent.setFlags(flag);
        }

        context.startActivity(intent);
    }

    public static void sendScheme(Context context, String url, Bundle bundle, boolean clearTop, int flag) {

        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        if(clearTop) {
            intent.addFlags(67108864);
        }

        if(flag != 0) {
            intent.setFlags(flag);
        }

        context.startActivity(intent);
    }

    public static void sendSchemeForResult(Fragment activity, String url, int requestCode) {
        sendSchemeForResult((Fragment)activity, url, requestCode, (Bundle)null);
    }

    public static void sendSchemeForResult(Activity activity, String url, int requestCode) {
        sendSchemeForResult((Activity)activity, url, requestCode, (Bundle)null);
    }

    public static void sendSchemeForResult(Fragment activity, String url, int requestCode, int flag) {
        sendSchemeForResult((Fragment)activity, url, requestCode, (Bundle)null, flag);
    }

    public static void sendSchemeForResult(Activity activity, String url, int requestCode, int flag) {
        sendSchemeForResult((Activity)activity, url, requestCode, (Bundle)null, flag);
    }

    public static void sendSchemeForResult(Fragment activity, String url, int requestCode, Bundle bundle) {
        sendSchemeForResult((Fragment)activity, url, requestCode, bundle, 0);
    }

    public static void sendSchemeForResult(Activity activity, String url, int requestCode, Bundle bundle) {
        sendSchemeForResult((Activity)activity, url, requestCode, bundle, 0);
    }

    public static void sendSchemeForResult(Fragment activity, String url, int requestCode, Bundle bundle, int flag) {

        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        if(flag != 0) {
            intent.setFlags(flag);
        }

        activity.startActivityForResult(intent, requestCode);
    }

    public static void sendSchemeForResult(Activity activity, String url, int requestCode, Bundle bundle, int flag) {
        if(url.toLowerCase().startsWith("http://scrimmage.qunar.com/bs?url=")) {
            url = url.substring("http://scrimmage.qunar.com/bs?url=".length());

            try {
                url = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException var6) {
                ;
            }
        }

        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        if(flag != 0) {
            intent.setFlags(flag);
        }

        activity.startActivityForResult(intent, requestCode);
    }
}
