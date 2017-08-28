import uweex from './uweex'
import utils from './lib/utils'

import router from './plugins/router'
import bridge from './plugins/router/bridge'
uweex.router = router;
uweex.bridge = bridge;
uweex.utils = utils;
export default uweex
