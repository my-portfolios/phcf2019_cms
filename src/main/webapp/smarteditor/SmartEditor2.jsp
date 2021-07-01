<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="/smarteditor/js/service/HuskyEZCreator.js" charset="utf-8"></script>

<textarea name="emailCn" id="ir1" rows="10" cols="100" style="width:100%; height:412px; display:none;">${resultInfo.emailCn}</textarea>
<!--textarea name="ir1" id="ir1" rows="10" cols="100" style="width:100%; height:412px; min-width:610px; display:none;"></textarea-->

<script type="text/javascript">
var oEditors = [];

var sLang = "ko_KR";	// 언어 (ko_KR/ en_US/ ja_JP/ zh_CN/ zh_TW), default = ko_KR

// 추가 글꼴 목록
//var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];

nhn.husky.EZCreator.createInIFrame({
	oAppRef: oEditors,
	elPlaceHolder: "ir1",
	sSkinURI: "/smarteditor/SmartEditor2Skin.html",	
	htParams : {
		bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
		bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
		bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
		bSkipXssFilter : true,		// client-side xss filter 무시 여부 (true:사용하지 않음 / 그외:사용)
		//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
		fOnBeforeUnload : function(){
			//alert("완료!");
		},
		I18N_LOCALE : sLang
	}, //boolean
	fOnAppLoad : function(){
		//예제 코드
		//oEditors.getById["ir1"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
	},
	fCreator: "createSEditor2"
});
	
function submitContents() {
	oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용됩니다.
	var contents = $("[name=emailCn]").val();
	
	if(contents.toLowerCase().indexOf("<script") != -1 || contents.toLowerCase().indexOf("&lt;script") != -1) {
        alert("자바스크립트는 입력하실 수 없습니다.");
        jQuery("#HTML").focus();
        return false;
    }
	return true;
	
	// 에디터의 내용에 대한 값 검증은 이곳에서 document.getElementById("ir1").value를 이용해서 처리하면 됩니다.
}

</script>
