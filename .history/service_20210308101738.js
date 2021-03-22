const express = require('express');
const app = express();
const path = require('path');

app.use(express.static(path.join(__dirname,'../dist');
// 注意: dist是npm run build 打包后生成的目录, 因为我是创建在service文件夹下面的,所以是../dist

app.listen(8081, '127.0.0.1');
console.log('服务器开启成功')