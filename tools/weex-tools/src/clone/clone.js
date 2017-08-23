const child_process = require('child_process');
const chalk = require('chalk')
var fs = require('fs');
const path = require('path')
function clonejs(gitAddres, projectName) {
  return new Promise((resolve, reject) => {
    console.log(` => ${chalk.blue.bold('weex init  ...')}`)
    try {
      child_process.execSync(gitAddres, {
        encoding: 'utf8',
        stdio: [0, 1, 2]
      })
    } catch (e) {
      reject(e)
    }
    resolve()
  }).then(()=> {
    rename(projectName);
  }).then(()=> {
    rmgit(projectName);
  })
}

function rmgit(projectName) {
  try {
    const rootPath = process.cwd()
    process.chdir(path.join(rootPath, projectName))
    child_process.execSync(process.platform === 'win32' ? 'rmdir .git' : 'rm -f -r .git', {
      encoding: 'utf8',
      stdio: [0, 1, 2]
    })

    child_process.execSync(process.platform === 'win32' ? 'del .gitmodules' : 'rm -f -r .gitmodules', {
      encoding: 'utf8',
      stdio: [0, 1, 2]
    })
  } catch (e) {
    reject(e)
  }
}

function rename(projectName) {
  // const rootPath = process.cwd();
  // console.log(rootPath);
  if ('AwesomeProject'===projectName) {

    return;
  }
  fs.rename('AwesomeProject', projectName, function (err) {
    if (err) {
      throw err;
    }
    console.log('done!');
  })
}


module.exports = clonejs
