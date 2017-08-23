'use strict';

let gulp = require('gulp');
let webserver = require('gulp-webserver');
let shell = require('gulp-shell')
let optimist = require('optimist');
let http = require('http');
let fs = require('fs');
let sysPath = require('path');
let crypto = require('crypto');
let os = require('os');

let filePath = optimist.argv.p;
let cacheVersion = '3';

gulp.task('clean', () => {
    let rnVersion = require('./node_modules/react-native/package.json').version;
    let pathKey = __dirname.split(sysPath.sep).join('-');
    let mtime;
    try {
      mtime = fs.statSync(sysPath.join(__dirname, './node_modules/react-native/packager/transformer.js')).mtime;
      mtime = String(mtime.getTime());
    } catch (error) {
      mtime = '';
    }
    let cacheKey = ['react-packager-cache', rnVersion, cacheVersion, pathKey, mtime].join('$');
    let hash = crypto.createHash('md5');
    hash.update(cacheKey);
    let cacheFilePath = sysPath.join(os.tmpdir(), hash.digest('hex'));
    try {
        fs.unlinkSync(cacheFilePath);
    } catch(e) {}
    console.log('Delete Cache File: ' + cacheFilePath);
});

gulp.task('run', ['clean'], () => {
    gulp.src('react').pipe(webserver({
        host: '127.0.0.1',
        port: 8082,
        middleware: (req, res, next) => {
            let basePath = 'example' + (filePath ? '/' + filePath : '');
            let url = 'http://127.0.0.1:8081' + (req.url.indexOf(basePath) > -1 ? req.url : ('/' + basePath + req.url));
            http.get(url, (resp) => {
                resp.pipe(res);
            }).on('error', (e) => {
                next();
            });
        }
    }));
});

gulp.task('packager', shell.task(['/usr/local/bin/npm start']))
gulp.task('all', ['run', 'packager']);
