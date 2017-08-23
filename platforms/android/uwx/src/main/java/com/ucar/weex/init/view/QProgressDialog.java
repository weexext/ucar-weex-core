package com.ucar.weex.init.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by chenxi.cui on 2017/7/13.
 */

public class QProgressDialog extends Dialog {
    private Context context;

    public QProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public QProgressDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView();
    }

    private void setContentView() {
//        ImageView imageView = new ImageView(this.context);
//        imageView.setImageDrawable(this.context.getResources().getDrawable(drawable.pub_react_progress_loading));
//        Animation anim = AnimationUtils.loadAnimation(this.context, anim.pub_react_rotate_progress);
//        anim.setInterpolator(new LinearInterpolator());
//        imageView.setAnimation(anim);
//        this.setContentView(imageView);
    }
}
