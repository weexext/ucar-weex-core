// 默认值
const DEFAULTS = {
    //导航栏是否显示返回按钮
    hasBack: true,
    // 导航栏返回按钮颜色
    backColor: '#ffffff',
    // 导航栏背景
    navBarColor: '#3e50b5',
    // [全局/页面]背景色，默认 蓝
    backgroundColor: '#ffffff',
    // [全局/页面]左侧按钮文字，默认 '返回'
    leftButtonText: '返回',
    // 导航栏高度
    height: weex.config.env.platform == 'android' ? 100.0 : 64.0
};
export default DEFAULTS;
