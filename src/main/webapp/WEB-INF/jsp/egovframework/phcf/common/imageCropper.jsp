<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <link  href="/css/egovframework/phcf/editimage/cropper.min.css" rel="stylesheet">
        <link href="<c:url value="/css/egovframework/com/com.css"/>" rel="stylesheet" type="text/css">        
        <script src="/js/egovframework/phcf/editimage/cropper.min.js"></script>
        <style>img {max-width: 100%;}</style>
    </head>
<body>
<div class="wTableFrm" >
    <h2>이미지 등록</h2>
    <table class="wTable" style="min-width: 500px !important;">
		<colgroup>
			<col style="width:16%">
			<col style="">
		</colgroup>
		<tbody>
		<tr>
			<th>파일 선택 <span class="pilsu">*</span></th><!-- 배너ID -->
			<td class="left">
				<input id="imgFile" type="file" value="파일 선택" accept='image/*' onchange="openFile(event)" style="width:300px;"/><br />
				
				<div style="background:#eeeeee;width:80%;height:80%;">
		            <img id="image"></img> 
		        </div>
			</td>
		</tr>
		<tr>
			<th>이미지 사이즈</th><!-- 배너명 -->
			<td class="left">
				<input type="text" id="percentage" value="100"/>%
			</td>
		</tr>
	</tbody>
	</table>	
	<div class="div_center">
		<input type="button" value="등록" onclick="done();" class="s_submit"/>
	</div>
	
    
    
</div>
</body>
<script>
    var cropper;
    var defaultAspectRatio = <%= request.getParameter("ratio") %>;
    if(defaultAspectRatio == '') defaultAspectRatio = 0;

    window.onload = function() {
        var image = document.querySelector('#image');
        cropper = new Cropper(image, {
            viewMode : 1,
            aspectRatio : defaultAspectRatio,
            background : false,
            highlight: false
        });
    }

    var openFile = function(file) {
        var input = file.target;
        var output;

        var reader = new FileReader();
        reader.onload = function(){
            var dataURL = reader.result;
            cropper.replace(dataURL);
        };
        reader.readAsDataURL(input.files[0]);
    };

    function done(){
        if($("#percentage").val() == '' || $("#percentage").val() > 100 || $("#percentage").val() < 0){
            alert('올바른 사진 크기를 입력하십시오!');
            return false;
        }
        if(cropper.getCroppedCanvas() == null) {
            alert("이미지를 선택하세요!")
            return false;
        }
        var dataURL = cropper.getCroppedCanvas().toDataURL();
        console.log(dataURL);
        var DATA = cropper.getCroppedCanvas();

        var maxWidth = DATA.width * ($("#percentage").val() / 100);
        var maxHeight = DATA.height * ($("#percentage").val() / 100);
        
        var canvas = document.createElement("canvas");
        var ctx = canvas.getContext("2d");
        var canvasCopy = document.createElement("canvas");
        var copyContext = canvasCopy.getContext("2d");

        var img = new Image();
        img.src = dataURL;
        img.onload = function(){
            var ratio = 1;
            if(img.width > maxWidth)
                ratio = maxWidth / img.width;
            else if(img.height > maxHeight)
                ratio = maxHeight / img.height;

            canvasCopy.width = img.width;
            canvasCopy.height = img.height;
            copyContext.drawImage(img, 0, 0);

            canvas.width = img.width * ratio;
            canvas.height = img.height * ratio;

            ctx.drawImage(canvasCopy, 0, 0, canvasCopy.width, canvasCopy.height, 0, 0, canvas.width, canvas.height);
            dataURL = canvas.toDataURL();
            console.log(dataURL);
            dataURL = dataURL.split(",")[1];

            $("#popupImageView", opener.document).attr("src","data:image/png;base64," + dataURL);
            $("#popupImage", opener.document).val(dataURL);
            window.close();
        }
        img.src = dataURL;
    }
</script>
</html>