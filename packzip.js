// const fs = require('fs')
const path = require('path')
const gzip = require('zlib').createGzip()
//
// function packzip (filePath, callback) {
//     const read = fs.createReadStream(filePath)
//     const write = fs.createWriteStream(filePath + '.gz')
//     read.pipe(gzip).pipe(write).on('close', () => {
//         report(filePath + '.gz')
//         callback && callback()
//     })
// }
// function report (filePath) {
//     const size = (fs.statSync(filePath).size / 1024).toFixed(2) + 'KB'
//     const file = path.relative(process.cwd(), filePath)
//     console.log(` => write ${file} (${size})`)
// }
var filePath = path.resolve();
var src = filePath+"/dist/native/"
console.log(src)
// module.exports = packzip
// packzip(src,()=>{})
var fs = require('fs');
var archiver = require('archiver');

var output = fs.createWriteStream(filePath+'/dist/archiver-unzip.zip');
var archive = archiver('zip');

archive.on('error', function(err){
    throw err;
});

archive.pipe(output);
archive.directory(src, false);

archive.finalize();
