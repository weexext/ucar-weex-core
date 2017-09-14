package com.ucar.weex.commons.adapter;

import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.taobao.weex.common.WXImageStrategy;
import com.ucar.weex.devsup.UWXEnvManager;

/**
 * Created by chenxi.cui on 2017/8/15.
 */

public class FrescoImageHelper {
    public static void setPlaceholder(final ImageView view, WXImageStrategy strategy) {
        String url = strategy.placeHolder;
        if (TextUtils.isEmpty(url)) {
            return;
        }
        String temp = url;
        if (url.startsWith("assets:///")) {
//            temp = "http:" + downloadUrl;
            temp = temp.replace("assets://", UWXEnvManager.getWXResHost());
        }
        Uri uri = Uri.parse(temp);
        ImageDecodeOptions decodeOptions = ImageDecodeOptions.newBuilder()
                .setBackgroundColor(Color.GREEN)
                .build();

        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setImageDecodeOptions(decodeOptions)
                .setAutoRotateEnabled(true)
                .setLocalThumbnailPreviewsEnabled(true)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .setProgressiveRenderingEnabled(false)
                .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(request, new Object());
        DataSubscriber dataSubscriber =
                new BaseDataSubscriber<CloseableReference<CloseableImage>>() {
                    @Override
                    public void onNewResultImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

                        CloseableReference<CloseableImage> imageReference = dataSource.getResult();
                        if (imageReference != null) {
                            try {
                                // do something with the image
                                Preconditions.checkState(CloseableReference.isValid(imageReference));
                                CloseableImage closeableImage = imageReference.get();
                                if (closeableImage instanceof CloseableStaticBitmap) {
                                    CloseableStaticBitmap closeableStaticBitmap = (CloseableStaticBitmap) closeableImage;
                                    Object viewTag = view.getTag();
                                    if (viewTag instanceof Boolean || viewTag == null) {
                                        boolean hasLoaded = viewTag == null ? false : (boolean) view.getTag();
                                        boolean hasResult = null != closeableStaticBitmap.getUnderlyingBitmap();
                                        if (!hasLoaded && hasResult) {
                                            view.setImageBitmap(closeableStaticBitmap.getUnderlyingBitmap());
                                        }
                                    }

                                } else {
                                    throw new UnsupportedOperationException("Unrecognized image class: " + closeableImage);
                                }
                            } finally {
                                imageReference.close();
                            }
                        }
                    }

                    @Override
                    public void onFailureImpl(DataSource dataSource) {
                    }
                };

        dataSource.subscribe(dataSubscriber, UiThreadImmediateExecutorService.getInstance());
    }
}
