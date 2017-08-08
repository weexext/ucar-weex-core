import Ext from './lib/core.js'

// 必须先引入 redux
import './plugins/redux'

// 引入 router
import './plugins/router'

// 引入 webx
import './plugins/webx'

// 配置全局插件
Ext.defaults.globalPlugins = ['redux', 'router', 'webx'];

export default Ext
