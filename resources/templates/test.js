<!DOCTYPE html>
<html xmlns:th="www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<title>Reader</title>
<script th:inline="javascript" type="text/javascript">
var record=new Record();
var images_str=/* [[${images}]] */ "{}";
var images=JSON.parse(images_str);

function Record(){
	this.tagIndex=0;
	this.nodeIndex=0;
	this.tags=[];

	function toNextTag(){
		this.tagIndex++;
	}

	function addNode(scaleX,scaleY){
		if(this.tags[tagIndex]===undefined){
			this.tags[tagIndex].nodes=[];
		}
		var node={};
		node.scaleX=scaleX;
		node.scaleY=scaleY;
		this.tags[tagIndex].nodes[nodeIndex]=node;
	}
}

function Shape(canvasId, dataType, length,imagePath) {
	this.oCanvas = document.getElementById(canvasId);
	this.oGc = this.oCanvas.getContext("2d");
	this.oCanvas.width=length;
	this.oCanvas.height=length;

	this.oCanvas.width=length;
	this.oCanvas.height=length;

	var map=[];
	map["AreaTag"]="line";
	map["FrameTag"]="rect";

	this.fillStyle = '#000';
	this.strokeStyle = '#000';
	this.lineWidth = 1;
	this.drawType = map[dataType];
	this.paintType = 'stroke';

	record=new Record();

	this.init=function () {
		this.oGc.fillStyle = this.fillStyle;
		this.oGc.strokeStyle = this.strokeStyle;
		this.oGc.lineWidth = this.lineWidth;
	}

	this.draw=function (){
		this.oCanvas.onmousedown = function ( event ) {
			this.init();
			var oEvent = event;
			this.startX = oEvent.clientX - this.oCanvas.offsetLeft;
			this.startY = oEvent.clientY - this.oCanvas.offsetTop;
			record.addNode(startX/this.oCanvas.width,startY/this.oCanvas.height);

			this.oCanvas.onmousemove = function ( event ) {
				var oEvent = event;
				this.endX = oEvent.clientX - this.oCanvas.offsetLeft;
				this.endY = oEvent.clientY - this.oCanvas.offsetTop;
				if(this.drawType=="line"){
					this[this.drawType](this.startX, this.startY, this.endX, this.endY);
					record.addNode(endX/this.oCanvas.width,endY/this.oCanvas.height);
					this.startX=this.endX;
					this.startY=this.endY;
				}
			};


		};

		this.oCanvas.onmouseup = function(){
			if(this.drawType=="rect"){
				this[this.drawType](this.startX, this.startY, this.endX, this.endY);
				record.addNode(startX/this.oCanvas.width,startY/this.oCanvas.height);
				record.addNode(endX/this.oCanvas.width,endY/this.oCanvas.height);
			}
			record.toNextTag();
		};    

		var image=new Image();
		image.src=imagePath;
		image.onload=function(){
			document.getElementById(canvasId).getContext("2d").drawImage(this,0,0,length,length);
		};

	}

	this.line=function ( x1, y1, x2, y2 ) {
		this.oGc.beginPath();
		this.oGc.moveTo( x1, y1 );
		this.oGc.lineTo( x2, y2 );
		this.oGc.closePath();
		this.oGc.stroke();
	}

	this.rect=function ( x1, y1, x2, y2 ){
		this.oGc.beginPath();
		this.oGc.rect( x1, y1, x2 - x1, y2 - y1 );
		this.oGc[this.paintType]();
	}

}

function submit_tag(){// alert("hi");
	var images={};
	images.records=[];
	images.records[0]={};
	images.records[0].tags=[];
	images.records[0].tags=record.tags;
	var form1 = document.createElement("form"); 
	document.body.appendChild(form1); 
	var input1 = new Input("images",JSON.stringfy(images),form1);  
	var input2 = new Input("jsonjavaId",/* [[${jsonjavaId}]] */ 1,form1);
	// alert(JSON.stringfy(images));
	// alert(/*[[${jsonjavaId}]]*/ error);
	form1.action = "/Reader"; 
	form1.submit(); 
	document.body.removeChild(form1); 
}

function Input(name,value,parent){
	var span=document.createElement("span");

	var label=document.createElement("label");
	label.innerText=name;

	var input=document.createElement("input");
	input.type="text";
	input.name=name;
	input.value=value;

	span.appendChild(label);
	span.appendChild(input);
	parent.appendChild(span);
	return input;
}
</script>
</head>
<body>
<p>
Hello,<span th:text="${worker.workername}">guest</span>
</p>
<p>
Your point is: <span th:text="${worker.point}">null</span>
</p>
<div>
<p th:text="${content}"></p>
</div>
<p>人工智能的发展离不开深度学习，请帮助我们,我们会给予奖励</p>
<div th:if="${type ne 'ClassTag'}">
<canvas id="canvas">Canvas failed !</canvas>
<script type="text/javascript" th:inline="javascript">
var shape=new Shape("canvas",/* [[${type}]] */ "FrameTag" ,500,images.path);
shape.draw();
</script>
<button onclick="submit_tag()">submit</button>
</div>
<div th:if="${type eq 'ClassTag'}">
<img id="image_class" src="" alt="">
<form id="form_class" method="post" action="">
<script type="text/javascript">
document.getElementById("image_class").src=images.path;
var map=images.records[0].description.oneContent;
var form=document.getElementById("form_class");
for(var key in map){
	var input=new Input(key,"",form);
}
</script>
<input type="submit" value="提交">
</form>
</div>

</body>
</html>