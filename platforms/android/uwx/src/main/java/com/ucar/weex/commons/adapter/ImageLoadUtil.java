package com.ucar.weex.commons.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.taobao.weex.utils.WXLogUtils;
import com.ucar.weex.init.utils.UWLog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chenxi.cui on 2017/7/3.
 */

public class ImageLoadUtil {


    public static void load(Context context, ImageView imageView, String url) {
        Picasso.with(context)
                .load(url)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    /**
     * @param url
     * @param view
     */
    public static void setImage(String url, ImageView view) {
        Drawable drawable = getDrawableByUrl(view.getContext(), url);
        if (drawable != null) {
            view.setImageDrawable(drawable);
        }
    }

    /**
     * @param pathString
     * @return
     */
    public static Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {

        }
        return bitmap;
    }

    /**
     * @param bmp
     * @return
     */
    public static Drawable bitmap2Drawable(Bitmap bmp) {
        return new BitmapDrawable(bmp);
    }

    /**
     * @param drawable
     * @return
     */
    public Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * @param context
     * @param url     file:///android_asset/android/temp.png
     *                file:///sdcard/android/temp.png
     * @return
     */
    public static Drawable getDrawableByUrl(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        if (url.startsWith("file:///android_asset")) {
            String filePath = url.substring("file:///android_asset".length());
            UWLog.v("assets:filePath=" + filePath);
            String assetsPath = "resources/" + filePath;
            return getAssetsBitmap(context, assetsPath);
        } else if (url.startsWith("file:///sdcard")) {
            UWLog.v("url=" + url);
            Bitmap bitmap = getDiskBitmap(url);
            return bitmap2Drawable(bitmap);
        } else if (url.startsWith("drawable")) {
            //适配资源文件
            if (url.contains("drawable:///local:") && url.contains(".")) {
                String imgName = url.substring("drawable:///local:".length(), url.lastIndexOf("."));
                imgName = convertPath(imgName);
                UWLog.v("drawable:imgName=" + imgName);
                return getDrawableByName(context, imgName);
            }
        } else if (url.startsWith("file")) {
            if (url.contains("file:///local:") && url.contains(".")) {
//                String imgName = url.substring("file:///local:".length());
//                imgName = convertPath(imgName);
//                UWLog.v("file:imgName=" + imgName);
//                Drawable localDrawable = getDrawableByName(view.getContext(), imgName);
//                if (localDrawable != null) {
//                    view.setImageDrawable(localDrawable);
//                }
                //// TODO: 2017/7/3
                String fileName = url.substring("file:///local:".length());
                fileName = convertPath(fileName);
                UWLog.v("assets:imgName=" + fileName);
                String assetsPath = "resources/" + fileName;
                return getAssetsBitmap(context, assetsPath);
            }
        } else if (url.startsWith("assets")) {
            if (url.contains("assets:///local:") && url.contains(".")) {
                String fileName = url.substring("assets:///local:".length());
                fileName = convertPath(fileName);
                UWLog.v("assets:imgName=" + fileName);
                String assetsPath = "resources/" + fileName;
                return getAssetsBitmap(context, assetsPath);
            }
        }
        return null;
    }

    /**
     * @param ctx
     * @param filePath
     * @return
     */
    public static Drawable getAssetsBitmap(final Context ctx, String filePath) {
        try {
            InputStream is = ctx.getResources().getAssets().open(filePath);
            return Drawable.createFromStream(is, null);
        } catch (IOException e) {
            if (e != null) {
                e.printStackTrace();
            }
        } catch (OutOfMemoryError e) {
            if (e != null) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 适配 js命名不规范
     *
     * @param imgName
     * @return
     */
    @NonNull
    private static String convertPath(String imgName) {
        imgName = imgName.replaceAll("-", "_");
        imgName = imgName.toLowerCase();
        imgName = imgName.replaceAll("@+[1-3]+[x,X]", "");
        if (!(String.valueOf(imgName.charAt(0)).matches("^[A-Za-z]+$"))) {
            imgName = "a_" + imgName;
        }
        return imgName;
    }


    public static Drawable getDrawableByName(Context context, String drawableName) {
        Resources resources = context.getResources();
        if (TextUtils.isEmpty(drawableName)) {
            WXLogUtils.e("drawableName is null");
            return null;
        }
        int id = resources.getIdentifier(drawableName, "drawable", context.getPackageName());
        return id == 0 ? null : ResourcesCompat.getDrawable(resources, id, null);
    }

}

