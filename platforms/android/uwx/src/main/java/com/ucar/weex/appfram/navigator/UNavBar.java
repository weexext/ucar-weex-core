package com.ucar.weex.appfram.navigator;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taobao.weex.utils.WXViewUtils;
import com.ucar.weex.R;
import com.ucar.weex.init.model.WXInfo;
import com.ucar.weex.utils.DensityUtil;

/**
 * Created by chenxi.cui on 2017/7/28.
 */

public class UNavBar extends FrameLayout {
    private ImageView imageBack;
    private TextView tvLiftTitle;
    private TextView tvTitle;
    private TextView tvRightTitle;

    public UNavBar(Context context) {
        super(context);
        init();
    }

    public UNavBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = LinearLayout.inflate(getContext(), R.layout.nav_bar_view, this);
        imageBack = (ImageView) view.findViewById(R.id.image_back);
    }

    public void setData(WXInfo.NavBar navBar) {
//        float weexPxByReal = WXViewUtils.getWeexPxByReal(navBar.height)/2;
        LayoutParams layoutParams = new LayoutParams(WXViewUtils.getScreenWidth(getContext()),
                DensityUtil.dip2px(getContext(), navBar.height / 2 - 5));
        this.setLayoutParams(new LayoutParams(layoutParams));
        this.setBackgroundColor(Color.parseColor(navBar.navBarBackground));
        imageBack.setVisibility(navBar.hasBack ? VISIBLE : GONE);
    }
}
