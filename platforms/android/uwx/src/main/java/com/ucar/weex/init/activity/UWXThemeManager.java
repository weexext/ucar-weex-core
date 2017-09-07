package com.ucar.weex.init.activity;

import com.ucar.weex.R;

/**
 * Created by chenxi.cui on 2017/9/7.
 */

public class UWXThemeManager {
    private UWXTheme pageTheme;
    private static UWXThemeManager manager;

    public static UWXThemeManager getInstance() {
        if (manager == null) {
            synchronized (UWXThemeManager.class) {
                if (manager == null) {
                    manager = new UWXThemeManager();
                }
            }
        }
        return manager;
    }

    public UWXThemeManager() {
        pageTheme = new UWXTheme(new UWXTheme.NavBar("#ffffff", "#fffff"), R.style.wx_theme_app);
    }

    public UWXTheme getPageTheme() {
        return pageTheme;
    }

    /**
     * @param pageTheme
     */
    public void setPageTheme(UWXTheme pageTheme) {
        this.pageTheme = pageTheme;
    }
}
