<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<form name="frm" id="frm" action="${ctx}/cms/popup/edit.do" method="post"  enctype="multipart/form-data">
<input type="hidden" id="CSRFToken" name="CSRFToken" value="${paramMap.CSRFToken}" />
<input type="hidden" name="com_hbz_request_token" value="${paramMap.com_hbz_request_token}"/>
<input type="hidden" name="hbzEvent" id="hbzEvent" value="${paramMap.hbzEvent}"  />
<input type="hidden" name="page_no" id="page_no" value="${paramMap.page_no}" />
<input type="hidden" name="LANG" id="LANG" value="${paramMap.LANG}" />
<input type="hidden" name="POPUP_SEQ" id="POPUP_SEQ" value="${popupInfo.POPUP_SEQ}" />
		<div class="area">
			<table class="tablestyle_green">
				<caption class="dpnone">팝업관리</caption>
				<tbody>
				<tr>
					<th class="table_id">제목</th>
					<td><div class="inputtextarea table_email floatleft"><input type="text" class="resetinput"  name="POPUP_NAME" value="${popupInfo.POPUP_NAME}"  /></div><span class="floatleft margintop5 marginleft5 colorgray">관리자가 알아볼 수 있도록 제목을 입력해주세요</span></td>
				</tr>
				<tr>
					<th>기간</th>
					<td>
						<div class="floatleft"><span class="floatleft margintop5">시작일 : </span>
						<div class="marginleft5 inputtextarea width80px floatleft"><input type="text"  class="resetinput" id="startDate" name="START_DT" value="${popupInfo.START_DT}"  /></div></div>
						<div class="floatleft"><span class="floatleft margintop5 marginleft5">종료일 : </span>
						<div class="marginleft5 inputtextarea width80px floatleft"><input type="text"  class="resetinput" id="endDate" name="END_DT" value="${popupInfo.END_DT}"  /></div></div>
					</td>
				</tr>
				<tr>
					<th>이미지설명</th>
					<td><textarea class="textarea width99 height100" name="IMG_TEXT" ><c:out value="${popupInfo.IMG_TEXT }" /></textarea></td>
				</tr>
				<!-- 추가 시작 -->
				<tr>
					<th>이미지링크</th><!-- 스토리보드에는 없는 내용입니다 추가되야될거 같아 넣어놓습니다 -->
					<td><div class="inputtextarea width99 floatleft"><input type="text" class="resetinput"  name="LINK_PATH" value="${popupInfo.LINK_PATH}"  /></div></td>
				</tr>
				<tr>
					<th>내용 ( html )</th>
					<td><textarea class="textarea width99 height100" name="HTML"><c:out value="${popupInfo.HTML}" /></textarea></td>
				</tr>
				<!-- 추가 끝 -->
				<tr>
					<th>팝업위치(x)</th>
					<td><div class="inputtextarea width80px floatleft"><input type="text" class="resetinput" name="LOC_X" value="${popupInfo.LOC_X}"  /></div><span class="margintop5 marginleft5 floatleft">브라우저 기준 PX단위</span></td>
				</tr>
				<tr>
					<th>팝업위치(y)</th>
					<td><div class="inputtextarea width80px floatleft"><input type="text" class="resetinput" name="LOC_Y" value="${popupInfo.LOC_Y}"  /></div><span class="margintop5 marginleft5 floatleft">브라우저 기준 PX단위</span></td>
				</tr>
				<!-- 추가 시작 -->
				<tr>
					<th>팝업넓이</th>
					<td><div class="inputtextarea width80px floatleft"><input type="text" class="resetinput"  name="POPUP_WIDTH" value="${popupInfo.POPUP_WIDTH}"  /></div><span class="margintop5 marginleft5 floatleft">단위 px (입력하지 않으면 컨텐츠 크기에 따라 자동으로 늘어납니다)</span></td>
				</tr>
				<tr>
					<th>팝업높이</th>
					<td><div class="inputtextarea width80px floatleft"><input type="text" class="resetinput"  name="POPUP_HEIGHT" value="${popupInfo.POPUP_HEIGHT}"  /></div><span class="margintop5 marginleft5 floatleft">단위 px (입력하지 않으면 컨텐츠 크기에 따라 자동으로 늘어납니다)</span></td>
				</tr>
                <tr>
                    <th>새창여부</th>
                    <td><div class="width80px floatleft"><input id="c1" type="checkbox" class="checkbox" name="WIN_OPEN_YN" value="Y"  ${popupInfo.WIN_OPEN_YN.equals("Y")?"checked":"" } />&nbsp;&nbsp;<label for="c1" class="marginleft5">새창</label></div>
                    &nbsp;<span class="margintop5 marginleft5 floatleft">체크박스에 체크를 하면 팝업화면의 링크가 새창으로(기본 메인화면) 열립니다.</span></td>
                </tr>
				<!-- 추가 끝 -->
				<tr>
					<th>이미지업로드</th>
					<td><input type="file" id="ATTACH_IMAGE" name="ATTACH_IMAGE" />
                       <c:if test="${not empty popupInfo.IMG_FILE_NAME }">
                           <div class="paddingtop5">현재등록된 이미지 : <a class="colorgray" href="${ctx}/common/file/download.do?upload_file_name=${popupInfo.IMG_FILE_PATH}" ><c:out value="${popupInfo.IMG_FILE_NAME }" /></a></div>
                       </c:if>
					</td>
				</tr>
				</tbody>
			</table>
		</div>
		<!--button-->
		<div class="buttonarea floatright">
            <input type="button" id='${paramMap.hbzEvent.equals("insert")?"insert_btn":"modify_btn"}' class="button btn_middle btn_blue" data-con="저장 하시겠습니까?" data-type="submit" data-src="frm" data-action="${ctx}/cms/popup/edit.do" onclick="fn_edit(this);" value="${paramMap.hbzEvent.equals("insert")?"등록":"수정"}" />
            <input type="button" class="button btn_middle btn_white" data-con="취소 하시겠습니까?" data-type="parent_pop" onclick="fn_list();" value="목록" />
		</div>
		<!--button_end-->
</form>

    <script>
    $("#frm").validate({
        onsubmit: false, 
        rules: {
        	POPUP_NAME: "required",
            START_DT: {
                required: true,
                date: true
            },
            END_DT: {
                required: true,
                date: true
            },
            LINK_PATH: "url",
            LOC_X: "digits",
            LOC_Y: "digits",
            POPUP_WIDTH: "digits",
            POPUP_HEIGHT: "digits"
        },
        messages: {
        	POPUP_NAME: "제목을 입력해 주세요",
            START_DT: {
              required: "시작일자를 입력해 주세요.",
              date: "날짜를 올바르게 입력해 주세요."
            },
            END_DT: {
                required: "종료일자를 입력해 주세요.",
                date: "날짜를 올바르게 입력해 주세요."
            },
            LINK_PATH: "URL을 올바르게 입력해 주세요",
            LOC_X: "숫자만 입력해 주세요",
            LOC_Y: "숫자만 입력해 주세요",
            POPUP_WIDTH: "숫자만 입력해 주세요",
            POPUP_HEIGHT: "숫자만 입력해 주세요"
        }
      });

    // 리스트 조회
    function fn_list() {
        document.frm.hbzEvent.value = "list";
        jQuery('#frm').attr("action", "${ctx}/cms/popup/list.do");
        jQuery('#frm').submit();
    }
    //등록,수정
    function fn_edit(obj) {
    	if( $("#ATTACH_IMAGE").val() != "" ){
    		var ext = $('#ATTACH_IMAGE').val().split('.').pop().toLowerCase();
    		      if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
	    		     alert('이미지 업로드 파일은 gif,png,jpg,jpeg 확장자만 업로드 할수 있습니다.');
	    		     return;
   		      }
   		}

        if($( "#frm" ).valid()){
            jQuery('#frm').attr("action", "${ctx}/cms/popup/edit.do");
            //jQuery('#frm').submit(); 
            fn_alert(obj);
        }
    }
    </script>
		