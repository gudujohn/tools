const express = require('express');
const path = require('path');
var app = express();

// 1.0 设置静态资源路径
app.use(express.static('src/public'));
// 2.0 设置自定义请求处理中间件
app.use(function (req, res, next) {
	console.log(req.path);
	next();
});
// 3.0 将所有api的请求响应content-type设置为application/json
app.all('*', (req, res, next) => {
	//设置允许跨域响应报文头
	//设置跨域
	// 启用 Node 服务器端的 cors 跨域
	res.header("Access-Control-Allow-Origin", "*");
	res.header("Access-Control-Allow-Headers", "*");
	res.header("Access-Control-Allow-Methods", "*");

	res.setHeader('Content-Type', 'application/json;charset=utf-8');
	next();
});
app.get("/", function (req, res) {
	res.sendFile(__dirname + "/public/index.html")
})
var server = app.listen(9000, '127.0.0.1', () => {
	var host = server.address().address;
	var port = server.address().port;
	console.log('Example app listening at http://%s:%s', host, port);
});
var io = require('socket.io')(server);
var userNumber = 0;
var faceUtil = require('./util/faceutil');
io.on('connection', function (socket) {
	var addedUser = false;
	socket.on('chat', function (data) {
		if(typeof(socket.user) == "undefined") {
			console.log("id:" + data.id + ";name:" + data.name + ";已经下线,但他想说" + JSON.stringify(data.msg));
			data.id = -1;
			data.msg = "已注销,请重新登陆！"
			io.emit("chat", data);
		} else {
			data.msg = faceUtil.getFaceHtml(data.msg);
			io.emit("chat", data);
			console.log("id:" + socket.user.id + ";name:" + socket.user.name + ";说了:" + JSON.stringify(data.msg));
		}
	});
	socket.on("login", function (data) {
		if (addedUser) return;
		++userNumber;
		addedUser = true;
		socket.emit('login', {
			userNumber: userNumber
		});
		socket.user = data;
		socket.broadcast.emit("userJoin", {
			name: socket.user.name,
			userNumber: userNumber
		});
		console.log(socket.user.name + "进入房间");
		console.log("当前在线人数:" + userNumber);
	});
	socket.on("typing", function () {
		if (!socket.user) { return; }
		socket.broadcast.emit("typing", {
			name: socket.user.name
		})
	});
	socket.on("stopTyping", function () {
		if (!socket.user) { return; }
		socket.broadcast.emit("stopTyping", {
			name: socket.user.name
		})
	})
	socket.on("disconnect", function () {
		if (addedUser) {
			--userNumber;
			socket.broadcast.emit("userLeave", {
				name: socket.user.name,
				userNumber: userNumber
			})
			console.log(socket.user.name + "离开了房间")
			console.log("当前在线人数:" + userNumber);
		}
	})
});