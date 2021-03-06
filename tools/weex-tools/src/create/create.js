/**
 * Created by yangshengtao on 16/11/28.
 */

const path = require('path')
const chalk = require('chalk')
const yeoman = require('yeoman-environment')
const TerminalAdapter = require('yeoman-environment/lib/adapter.js')

/**
 * Create Silent TerminalAdapter
 * suppress 'create' output generated by yeoman
 */
class SilentTerminalAdapter extends TerminalAdapter {
  constructor() {
    super()
    this.log.create = function() {}
  }
}

/**
 * Create a weex plugin template
 * @param {String} plugin name
 * @param {String} config file path
 */
function create(pluginName, configFile) {
  console.log(` => ${chalk.blue('Create a new ucar Weex plugin')} (${chalk.cyan(pluginName)})`)

  const env = yeoman.createEnv(null, null, new SilentTerminalAdapter())

  env.register(
    require.resolve(path.join(__dirname, '../../pluginTemplate')),
    'weex:app'
  )

  // TODO: get generator configs from configFile
  const args = []

  const generator = env.create('weex:app', {
    args,
    options: {
      pluginName,
    }
  })

  generator.destinationRoot(pluginName)
  generator.run()
}

module.exports = create;
