<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta content="IE=edge" http-equiv="X-UA-Compatible">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>이미지 선택</title>
        <link href="<c:url value='/css/egovframework/phcf/editimage/bootstrap.min.css'/>" rel="stylesheet">
        <link href="<c:url value='/css/egovframework/phcf/editimage/cropper.css'/>" rel="stylesheet">
        <link href="<c:url value='/css/egovframework/phcf/editimage/crop.css'/>" rel="stylesheet">
    </head>
    <body>
        <div class="container docs-overview">
            <div class="row">
                <div class="container-fluid eg-container" id="basic-example">
                    <div class="row eg-main">
                        <div class="col-xs-12 col-sm-9">
                            <div class="eg-wrapper">
                                <img class="cropper" />
                            </div>
                        </div>
                    </div>
                    <div class="clearfix">
                        <div class="col-xs-12">
                            <div class="eg-button">
                            	크기 조정 : <input type="number" id="percentage" style="text-align:center;"value="100"/> %<br/>
                            	- 크기가 클수록 이미지를 표시하는 시간이 증가합니다.<br/><br/>
                                <label class="btn btn-primary" for="inputImage" title="이미지 파일 업로드">
                                    <input class="hide" id="inputImage" name="file" accept="image/*" type="file">
                                    <span data-original-title="파일 선택..." class="docs-tooltip" data-toggle="tooltip" title="">
                                        <span class="glyphicon glyphicon-upload">파일 선택...</span>
                                    </span>
                                </label>
                                <button class="btn btn-primary" id="getDataURL" type="button">완료</button>
                            </div>
                            <div class="row eg-output">
                                <div class="col-md-12">
                                    <div id="err"></div>
                                </div>
                                <div class="col-md-6">
                                </div>
                                <div class="col-md-6">
                                    <div id="showDataURL"></div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="hidden-print docs-sidebar" role="complementary">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
		<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="<c:url value='/js/egovframework/phcf/editimage/bootstrap.min.js'/>"></script>
        <script src="<c:url value='/js/egovframework/phcf/editimage/cropper.js'/>"></script>
        <script src="<c:url value='/js/egovframework/phcf/editimage/docs.js'/>"></script>
        <script>			
            $(function () {
                var $image = $(".cropper"),
                    $dataX = $("#dataX"),
                    $dataY = $("#dataY"),
                    $dataHeight = $("#dataHeight"),
                    $dataWidth = $("#dataWidth"),
                    console = window.console || {log: $.noop},
            		cropper = $image.data("cropper");
                	
                    if('<%=request.getParameter("ratio")%>' != null)
                		$image.cropper("setAspectRatio", '<%=request.getParameter("ratio")%>');
                    //0.72

                $("#getDataURL").click(function () {
                	if($("#percentage").val() == '' || $("#percentage").val() > 100 || $("#percentage").val() < 0){
                		alert('올바른 사진 크기를 입력하십시오!');
                		return false;
                	}
                	
                	var dataURL = $image.cropper("getDataURL");
                    if(dataURL=="") {
                    	$("#err").text("이미지를 선택하세요!");
                        $("#err").css("color", "red");
                        return false;
                    }
                    var DATA = $image.cropper("getData");
                   
                    console.log(dataURL);
                    var maxWidth = DATA.width * ($("#percentage").val() / 100);
    				var maxHeight = DATA.height * ($("#percentage").val() / 100);
    				
                    var canvas = document.createElement("canvas");
              	  	var ctx = canvas.getContext("2d");
              	  	var canvasCopy = document.createElement("canvas");
              	  	var copyContext = canvasCopy.getContext("2d");

	              	var img = new Image();
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
	      	        	console.log("resized : " + dataURL);
	      	        	dataURL = dataURL.split(",")[1];
	      	        	
	      	        	$("#popupImageView", opener.document).attr("src","data:image/png;base64," + dataURL);
		      	      	$("#popupImage", opener.document).val(dataURL);
		                $("#popupWSize", opener.document).val(canvas.width+20);
		                $("#popupHSize", opener.document).val(canvas.height+40);
		                window.close();
    	          	}
    	          	img.src = dataURL;
                });
            });  
		</script>
    </body>
</html>
