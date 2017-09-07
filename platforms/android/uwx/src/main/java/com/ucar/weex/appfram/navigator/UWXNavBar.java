package com.ucar.weex.appfram.navigator;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taobao.weex.utils.WXViewUtils;
import com.ucar.weex.R;
import com.ucar.weex.UWXApplication;
import com.ucar.weex.init.activity.UWXTheme;
import com.ucar.weex.init.utils.UWLog;
import com.ucar.weex.utils.DensityUtil;

/**
 * Created by chenxi.cui on 2017/7/28.
 */

public class UWXNavBar extends FrameLayout {
    private TextView textBack;

    public UWXNavBar(Context context) {
        super(context);
        init();
    }

    public UWXNavBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = LinearLayout.inflate(getContext(), R.layout.nav_bar_view, this);
        textBack = (TextView) view.findViewById(R.id.text_back);
        textBack.setTypeface(UWXApplication.getIconFont());
        textBack.setText(R.string.icon_back);
    }

    public void setData(UWXTheme.NavBar navBar) {
//        float weexPxByReal = WXViewUtils.getWeexPxByReal(navBar.height)/2;
        LayoutParams layoutParams = new LayoutParams(WXViewUtils.getScreenWidth(getContext()),
                DensityUtil.dip2px(getContext(), navBar.height / 2 - 5));
        this.setLayoutParams(layoutParams);
        this.textBack.setVisibility(navBar.hasBack ? VISIBLE : GONE);
        try {
            this.setBackgroundColor(Color.parseColor(navBar.navBarColor));
            this.textBack.setTextColor(Color.parseColor(navBar.backColor));
        } catch (IllegalArgumentException e) {
            UWLog.e(e.getMessage());
        }

    }

}
