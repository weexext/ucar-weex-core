package com.ucar.weex;

import com.ucar.weex.commons.adapter.NavBarAdapter;

/**
 * Created by chenxi.cui on 2017/8/29.
 */

public class UInitConfig {

    private NavBarAdapter mNavBarAdapter;

    public NavBarAdapter getNavBarAdapter() {
        return mNavBarAdapter;
    }

    public static class Builder {
        NavBarAdapter mNavBarAdapter;

        public Builder setNavBarAdapter(NavBarAdapter navBarAdapter) {
            this.mNavBarAdapter = navBarAdapter;
            return this;
        }

        public UInitConfig build() {
            UInitConfig config = new UInitConfig();
            config.mNavBarAdapter = this.mNavBarAdapter;
            return config;
        }
    }
}
