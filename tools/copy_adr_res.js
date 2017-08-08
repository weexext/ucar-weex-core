var fs=require('fs');
var path = require("path");
/*
 * 复制目录中的所有文件包括子目录
 * @src param{ String } 需要复制的目录   例 images 或者 ./images/
 * @dst param{ String } 复制到指定的目录    例 images images/
 */

//获取当前目录绝对路径，这里resolve()不传入参数
var filePath = path.resolve();

//删除所有的文件(将所有文件夹置空)
   var emptyDir = function(fileUrl){
     if (!fs.existsSync(fileUrl)) {
         return;
     }
       var files = fs.readdirSync(fileUrl);//读取该文件夹
       files.forEach(function(file){
           var stats = fs.statSync(fileUrl+'/'+file);
           if(stats.isDirectory()){
               emptyDir(fileUrl+'/'+file);
           }else{
               fs.unlinkSync(fileUrl+'/'+file);
               console.log("删除文件"+fileUrl+'/'+file+"成功");
           }
       });
   }
var reg= /^[A-Za-z]+$/;
var copy = function(src,dst){
  if (!fs.existsSync(dst)) {
      fs.mkdirSync(dst);
  }
    //判断文件需要时间，则必须同步
    if(fs.existsSync(src)){
        fs.readdir(src,function(err,files){
            if(err){console.log(err);return;}
            files.forEach(function(filename){

                //url+"/"+filename不能用/直接连接，Unix系统是”/“，Windows系统是”\“
                var url = path.join(src,filename);
                //     dest = path.join(dst,filename);
                //     console.log(url);
                //     console.log(dest);
                fs.stat(path.join(src,filename),function(err, stats){
                    if (err) throw err;

                     //是文件
                    if(stats.isFile()&&(filename.indexOf('.png')>0||filename.indexOf('.jpg')>0||filename.indexOf('.jpeg')>0)){
                       // filename = filename.replace('@3x','');
                       // filename = filename.replace('@2x','');
                       // filename = filename.replace(/-/g,'_');
                       //
                       // if (!reg.test(filename.charAt(0))) //判断是否符合正则表达式
                       // {
                       //   filename="a_"+filename;
                       // }
                       // filename = filename.toLowerCase();

                       dest = path.join(dst,filename)

                       console.log(url);
                        //创建读取流
                        readable = fs.createReadStream(url);
                        //创建写入流
                        writable = fs.createWriteStream(dest,{ encoding: "utf8" });
                        // 通过管道来传输流
                        readable.pipe(writable);
                    //如果是目录
                    }else if(stats.isDirectory()){
                        copy(url, dst);
                    }
                });
            });
        });
    }else{
        console.log("给定的目录不存，读取不到文件");
        return;
    }
}

exports.copy = copy;
//copy("./views/","./www/");
// const src ='../web/';
// const targe='../restemp';
const src ='../src/image/';
const  targe = '../platforms/android/app/src/main/assets/image';
emptyDir(targe);
copy(src,targe);
