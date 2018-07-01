function Conf_canvas(){
this.left=0;
this.top=0;
this.parentid="";
this.width=500;
this.height=500;
}

function Conf_image(){
this.imagesrc="";
}

function Canvas_image(conf_canvas,conf_image){
var parent=document.getElementById(conf_canvas.parentid);
var canvas=document.createElement("canvas");
parent.appendChild(canvas);
this.canvas=canvas;
var context=canvas.getContext("2d");
canvas.width=conf_canvas.width;
canvas.height=conf_canvas.height;
canvas.style.position="absolute";
canvas.style.left=conf_canvas.left;
canvas.style.top=conf_canvas.top;

var image=new Image();
image.src=conf_image.imagesrc;
image.onload=function(){
context.drawImage(image,0,0,500,500);
}
}

function Canvas_rectangle(conf_canvas){
var parent=document.getElementById(conf_canvas.parentid);
var canvas=document.createElement("canvas");
parent.appendChild(canvas);
this.canvas=canvas;
var context=canvas.getContext("2d");


canvas.width=conf_canvas.width;
canvas.height=conf_canvas.height;
canvas.style.position="absolute";
canvas.style.left=conf_canvas.left;
canvas.style.top=conf_canvas.top;

this.state=0;
this.startX=0;
this.startY=0;
this.endX=0;
this.endY=0;

var _this=this;

canvas.onmousedown=function(event){
_this.startX=event.clientX-canvas.offsetParent.offsetLeft+document.documentElement.scrollLeft;
_this.startY=event.clientY-canvas.offsetParent.offsetTop+document.documentElement.scrollTop;

canvas.onmousemove=function(event){
	_this.endX=event.clientX-canvas.offsetParent.offsetLeft+document.documentElement.scrollLeft;
	_this.endY=event.clientY-canvas.offsetParent.offsetTop+document.documentElement.scrollTop;
	context.lineWidth = 4; 
	context.fillStyle = 'rgba(255,0,0,0.3)'; 
	context.lineCap="round";
	context.clearRect(0,0,conf_canvas.width,conf_canvas.height);
	context.fillRect(_this.startX,_this.startY,_this.endX-_this.startX,_this.endY-_this.startY);
	context.stroke();
}
}
canvas.onmouseup=function(event){
_this.endX=event.clientX-canvas.offsetParent.offsetLeft+document.documentElement.scrollLeft;
_this.endY=event.clientY-canvas.offsetParent.offsetTop+document.documentElement.scrollTop;
context.lineWidth = 4; 
context.strokeStyle = 'rgba(255,0,0,0.7)'; 
context.lineCap="round";
context.clearRect(0,0,conf_canvas.width,conf_canvas.height);
context.rect(_this.startX,_this.startY,_this.endX-_this.startX,_this.endY-_this.startY);
context.stroke();

this.onmousedown=null;
this.onmouseup=null;
this.onmousemove=null;

_this.state=1;
}

this.clear=function(){
context.clearRect(0,0,conf_canvas.width,conf_canvas.height);
_this=null;
}
}

function Canvas_curveline(conf_canvas){
var parent=document.getElementById(conf_canvas.parentid);
var canvas=document.createElement("canvas");
parent.appendChild(canvas);
this.canvas=canvas;
var context=canvas.getContext("2d");


canvas.width=conf_canvas.width;
canvas.height=conf_canvas.height;
canvas.style.position="absolute";
canvas.style.left=conf_canvas.left;
canvas.style.top=conf_canvas.top;

this.points=[];

var _this=this;

canvas.onmousedown=function(event){
_this.startX=event.clientX-canvas.offsetParent.offsetLeft+document.documentElement.scrollLeft;
_this.startY=event.clientY-canvas.offsetParent.offsetTop+document.documentElement.scrollTop;

canvas.onmousemove=function(event){
_this.endX=event.clientX-canvas.offsetParent.offsetLeft+document.documentElement.scrollLeft;
_this.endY=event.clientY-canvas.offsetParent.offsetTop+document.documentElement.scrollTop;

var point={};
point.x=_this.startX;
point.y=_this.startY;
_this.points.push(point);

context.beginPath();
context.lineWidth = 4; 
context.strokeStyle = 'rgba(255,0,0,0.7)'; 
context.lineCap="round";
context.moveTo(_this.startX,_this.startY);
context.lineTo(_this.endX,_this.endY);
context.stroke();

_this.startX=_this.endX;
_this.startY=_this.endY;
}

}

canvas.onmouseup=function(event){
this.onmousedown=null;
this.onmousemove=null;
this.onmouseup=null;

_this.state=1;
}

this.clear=function(){
context.clearRect(0,0,conf_canvas.width,conf_canvas.height);
_this=null;
}
}













