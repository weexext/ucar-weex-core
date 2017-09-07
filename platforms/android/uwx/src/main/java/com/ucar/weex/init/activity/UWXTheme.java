package com.ucar.weex.init.activity;

import com.ucar.weex.R;

import java.io.Serializable;

/**
 * Created by chenxi.cui on 2017/9/7.
 */

public class UWXTheme implements Serializable {
    private NavBar navBar;

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    private int theme;

    public UWXTheme(NavBar navBar, int theme) {
        this.navBar = navBar;
        this.theme = theme;
    }
    public UWXTheme(NavBar navBar) {
        this(navBar, R.style.wx_theme_app);
    }

    public UWXTheme(int theme) {
        this(new NavBar(), theme);

    }
    public UWXTheme() {
    }

    public NavBar getNavBar() {
        return navBar;
    }

    public void setNavBar(NavBar navBar) {
        this.navBar = navBar;
    }

    /**
     * //导航栏是否显示返回按钮
     * hasBack: true,
     * // 导航栏返回按钮颜色
     * backColor: '#ffffff',
     * // 导航栏背景
     * navBarColor: '3e50b5',
     * // [全局/页面]背景色，默认 蓝
     * backgroundColor: '#3e50b5',
     * // [全局/页面]左侧按钮文字，默认 '返回'
     * leftButtonText: '返回',
     * // 导航栏高度
     * height: weex.config.env.platform == 'android' ? 100.0 : 64.0
     */
    public static class NavBar implements Serializable {
        public boolean hasBack = true;
        public String backColor = "#ffffff";
        public String leftButtonText = "返回";
        public String navBarColor = "#3e50b5";
        public String backgroundColor = "#ffffff";
        public float height = 100;

        public NavBar(boolean hasBack, String backColor, String leftButtonText, String navBarColor, String backgroundColor, float height) {
            this.hasBack = hasBack;
            this.backColor = backColor;
            this.leftButtonText = leftButtonText;
            this.navBarColor = navBarColor;
            this.backgroundColor = backgroundColor;
            this.height = height;
        }

        public NavBar() {
        }

        public NavBar(String backColor, String navBarColor) {
            this(true, backColor, "返回", navBarColor, "#ffffff", 100);
        }
    }


}
