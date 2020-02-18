<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css">

<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script> 
<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>


<div class="wTableFrm">
<!--등록/수정 -->
<h1>카드및CMS등록</h1>
<form name="frm" id="frm" action="#none" method="post"  enctype="multipart/form-data">
	<div class="search_box" >
		<ul>
			<li class="div-left">* 표시항목은 필수 입력 사항 입니다.</li>
		</ul>
	</div>
	
	<table class="wTable">
	    <caption class="dpnone">개인,기업,단체구분</caption>
	    <tbody>
	    <tr>
	        <th class="table_id" width="15%">구분</th>
	        <td class="left">
	        	<input type="radio" class="radio" name="user_tp" id="user_tp_u" value="U" checked="checked"><label for="user_tp_u">개인</label> &nbsp;&nbsp;
	        	<input type="radio" class="radio" name="user_tp" id="user_tp_c"  value="C"><label for="user_tp_c">기업</label> &nbsp;&nbsp;
	        	<input type="radio" class="radio" name="user_tp" id="user_tp_g"  value="G"><label for="user_tp_g">단체</label>
	        </td>
	    </tr>
	    </tbody>
	</table>
	
	<br>
	
	<div id="divUserInfo">
	<h2>* 본 시스템에 등록된 회원만 입력 가능합니다.</h2>
	<table id="tb_user" class="wTable">
		<caption class="dpnone">개인 정보 등록</caption>
	    <tbody>
	    <tr id="sp_mh_tp">
	        <th class="table_id" width="15%">후원종류<span class="pilsu">*</span></th>
	        <td colspan="3" class="left">
	       		* 개인회원 후원정보 등록은 CMS만 가능 합니다.
	        </td>
	    </tr>
	    <tr id="sc_price_tp_m_user">
	        <th class="table_id">결제방식<span class="pilsu">*</span></th>
	        <td colspan="3" class="left">
	       		<input type="radio" class="radio" name="sc_price_tp" id="sc_price_tp_user" value="S"><label for="sc_price_tp_user">CMS 계좌이체</label>
	        </td>
	    </tr>
	    <tr>
	        <th class="table_id">아이디<span class="pilsu">*</span></th>
	        <td class="left">
		      	<div style="width: 300px" class="inputtextarea">
	       		 	<input type="text" class="resetinput" name="user_id" value="">
	       		 </div>  
	        </td>
	        <th class="table_id">이름<span class="pilsu">*</span></th>
	        <td class="left">
		      	<div style="width: 300px" class="inputtextarea">
	       		 	<input type="text" class="resetinput" name="user_nm" value="">
	       		 </div>  
	        </td>
	    </tr>
	    <tr>
	        <th class="table_id">전화번호<span class="pilsu">*</span></th>
	        <td class="left">
		      	<div>
	       		 	<input type="text" style="width: 100px; height: 20px;" class="input" name="user_phone1" value="">
	       		 	- 
	       		 	<input type="text" style="height: 20px;" class="input" name="user_phone2" value="">
	       		 	-
	       		 	<input type="text" style="height: 20px;" class="input" name="user_phone3" value="">
	       		 </div>  
	        </td>
	        <th class="table_id">e-mail</th>
	        <td class="left">
		      	<div>
	       		 	<input type="text" style="height: 20px;" class="input" name="user_email1" value="">
	       		 	@ 
	       		 	<input type="text" style="height: 20px;" class="input" name="user_email2" value="">
	       		 </div>
	        </td>
	    </tr>
	    </tbody>
	</table>
	</div>
	
	<div id="divCompInfo">
	<h2 class="tit02">기업후원의 경우 일시후원은 정보 등록만 진행하며 [무통장입금]만 진행합니다.(별도 결제 없음)</h2>
	<h2 class="tit02">정기후원은 금융결제원에 등록 하나 매월 이체된 금액은 별도 프로그램으로 관리 하여야 합니다.(PG사 제공 프로그램 사용)</h2>
	<table id="tb_comp" class="wTable">
		<caption class="dpnone">기업 및 단체정보 등록</caption>
	    <tbody>
	    <tr>
	        <th class="table_id" width="15%">기업 및 단체명<span class="pilsu">*</span></th>
	        <td class="left"> 
	        	<div style="width: 300px" class="inputtextarea">
	        		<input type="text" class="resetinput" name="user_comp_nm" value="">
	        	</div>
	        </td>
	        <th class="table_id">사업자번호 & 단체번호<span class="pilsu">*</span></br>('-' 제외)</th>
	        <td class="left">
	       		 <div style="width: 300px" class="inputtextarea">
	       		 	<input type="text" class="resetinput" name="user_comp_number" value=""/>
	       		 </div>
	        </td>
	    </tr>
	    <tr id="sp_mh_tp">
	        <th class="table_id" width="15%">후원종류<span class="pilsu">*</span></th>
	        <td colspan="3" class="left">
	       		<input type="radio" class="radio" name="sp_mh_tp" id="sp_mh_tp_o" value="O"><label for="sp_mh_tp_o">일시후원</label>
	       		&nbsp;&nbsp;
	       		<input type="radio" class="radio" name="sp_mh_tp" id="sp_mh_tp_m" value="M"><label for="sp_mh_tp_m">정기후원</label>
	        </td>
	    </tr>
	    <tr id="sc_price_tp_o_comp">
	        <th class="table_id">결제방식<span class="pilsu">*</span></th>
	        <td colspan="3" class="left">
	       		<input type="radio" class="radio" name="sc_price_tp" id="sc_price_tp_m" value="M"><label for="sc_price_tp_m">무통장입금</label>
	        </td>
	    </tr>
	    <tr id="sc_price_tp_m_comp">
	        <th class="table_id">결제방식<span class="pilsu">*</span></th>
	        <td colspan="3" class="left">
	        	<input type="radio" class="radio" name="sc_price_tp" id="sc_price_tp_z" value="Z"><label for="sc_price_tp_z">정보만 등록</label>
	       		&nbsp;&nbsp;
	       		<input type="radio" class="radio" name="sc_price_tp" id="sc_price_tp_d" value="D"><label for="sc_price_tp_d">카드결제</label>
	       		&nbsp;&nbsp;
	       		<input type="radio" class="radio" name="sc_price_tp" id="sc_price_tp_s" value="S"><label for="sc_price_tp_s">CMS 계좌이체</label>
	        </td>
	    </tr>
	    <tr>
	        <th class="table_id">담당자 아이디</br>(비회원은 미입력)</th>
	        <td class="left">
		      	<div style="width: 300px" class="inputtextarea">
	       		 	<input type="text" class="resetinput" name="manager_id" value="">
	       		 </div>  
	        </td>
	        <th class="table_id">담당자 이름<span class="pilsu">*</span></th>
	        <td class="left">
		      	<div style="width: 300px" class="inputtextarea">
	       		 	<input type="text" class="resetinput" name="manager_nm" value="">
	       		 </div>  
	        </td>
	    </tr>
	    <tr>
	        <th class="table_id">담당자 전화번호<span class="pilsu">*</span></th>
	        <td class="left">
		      	<div>
	       		 	<input type="text" style="width: 100px; height: 20px;" class="input" name="comp_manager_phone1" value="">
	       		 	- 
	       		 	<input type="text" style="height: 20px;" class="input" name="comp_manager_phone2" value="">
	       		 	-
	       		 	<input type="text" style="height: 20px;" class="input" name="comp_manager_phone3" value="">
	       		 </div>  
	        </td>
	        <th class="table_id">담당자 e-mail</th>
	        <td class="left">
		      	<div>
	       		 	<input type="text" style="height: 20px;" class="input" name="comp_manager_email1" value="">
	       		 	@ 
	       		 	<input type="text" style="height: 20px;" class="input" name="comp_manager_email2" value="">
	       		 </div>
	        </td>
	    </tr>
	    </tbody>
	</table> 
	</div>
	
	<br>
	
	<div id="divLogoInfo">
	<h2>기업명 및 단체명, 사업자번호를 입력 하면 자동 검색 됩니다.</h2>
	<table class="wTable">
	    <caption class="dpnone">기업및단체로고</caption>
	    <tbody>
	    <tr>
	        <th class="table_id">대표사이트</th>
	        <td class="left">
	        	<div style="width: 300px" class="inputtextarea">
	       		 	<input type="text" class="resetinput" name="scg_url" value="">
	       		 </div>
	       		 ex) http://www.hubizict.com
	        </td>
	    </tr>
	    <tr>
			<th>Logo 이미지</th>
			<td class="fileaddfield left" >
				<div id="divAttachLogo" class="marginbottom5 margintop5 fileclone width100per">
					<input id="attach_logo" type="file" name="attach_logo" />
					<div id="img_wrap" style="width: 200px; margin-top: 10px;">
						<img id="logoImg" style="max-width: 100%;"/>
					</div>
				</div>
				
			</td>	
		</tr>
	    </tbody>
	</table>
	</div>
	
	<br>
	
	<div id="divCardInsertInfo">
	<h2>정기후원 카드 정보 등록을 위한 입력 항목 입니다.(해당 정보는 DB로 저장 하지 않습니다.)</h2>
	<h2 class="tit02">결제 처리시 카드 주인은 본 시스템에 회원 가입이 되어 있어야 합니다.(비회원은 카드 및 CMS 등록 불가)</h2>
	<table class="wTable">
	    <caption class="dpnone">카드 자동이체 등록 정보</caption>
	    <tbody>
	    	<tr>
	    		<th class="table_id" width="15%">회원정보 동일여부</th>
	    		<td colspan = "3" class="left">
	    			<input type="radio" class="radio" name="same_info_card" id="same_info_card_false" value="false"><label for="same_info_card_false">동일하지 않음</label>
	    			&nbsp;&nbsp;
	    			<input type="radio" class="radio" name="same_info_card" id="same_info_card_true" value="true"><label for="same_info_card_true">동일함</label>
	    		</td>
	    	</tr>
		    <tr>
		    	<th class="table_id">카드주 아이디<span class="pilsu">*</span></th>
		        <td class="left">
		        	<div style="width: 300px" class="inputtextarea">
		       		 	<input type="text" class="resetinput" name="card_user_id" value="">
		       		 </div>
		        </td>
		        <th class="table_id">카드주 성명<span class="pilsu">*</span></th>
		        <td class="left">
		        	<div style="width: 300px" class="inputtextarea">
		       		 	<input type="text" class="resetinput" name="card_user_nm" value="">
		       		 </div>
		        </td>
		    </tr>
		    <tr>
		    	<th class="table_id">카드주 연락처<span class="pilsu">*</span></br>('-'제외)</th>
		        <td class="left">
		        	<div style="width: 300px" class="inputtextarea">
		       		 	<input type="text" class="resetinput" name="card_user_phone" value="">
		       		 </div>
		        </td>
		        <th class="table_id">카드주 이메일</th>
		        <td class="left">
		        	<div style="width: 300px" class="inputtextarea">
		       		 	<input type="text" class="resetinput" name="card_user_email" value="">
		       		 </div>
		        </td>
		    </tr>
		    <tr>
		    	<th class="table_id">입금액(원)<span class="pilsu">*</span></th>
		    	<td class="left">
		    		<div style="width: 300px" class="inputtextarea">
		       		 	<input type="text" class="resetinput" name="card_sc_price" value="">
		       		 </div>
		       	</td>
		       	<th>출금요청일<span class="pilsu">*</span></th>
				<td colspan="3" class="fileaddfield left">
					<div class="marginbottom5 margintop5 fileclone width100per">
						<select id="card_sc_req_dd" name="card_sc_req_dd">
			       			<option value="5">5</option>
			       			<option value="10">10</option>
			       			<option value="15">15</option>
			       			<option value="20">20</option>
			       			<option value="25">25</option>
						</select>
						일
					</div>
				</td>	
		    </tr>
		    <tr>
		    	<th class="table_id">카드번호<span class="pilsu">*</span></br>('-'제외)</th>
		    	<td class="left">
		    		<div style="width: 300px" class="inputtextarea">
		       		 	<input type="text" class="resetinput" name="card_number" value="">
		       		 </div>
		       	</td>
		       	<th class="table_id">카드유효기간<span class="pilsu">*</span></th>
		    	<td class="left">
		    		<div>
		       		 	<input type="text" class="input" name="card_mm" value="" style="width: 50px; height: 20px" maxlength="2">(MM)
		       		 	&nbsp;/&nbsp;
		       		 	<input type="text" class="input" name="card_yy" value="" style="width: 50px; height: 20px" maxlength="2">(YY)
	       		 	</div>
		       	</td>
		    </tr>
	    </tbody>
	</table>
	</div>
	
	<div id="divDefaultInsertInfo">
	<h2>결제방식이 [정보만등록]일 경우 입금액만 관리합니다.(실제 입금여부는 별도 PG모듈에서 확인)</h2>
	<table class="wTable">
		<caption class="dpnone">기본정보 등록 정보</caption>
		<tbody>
	    	<tr>
	    		<th class="table_id" width="15%">입금액(원)<span class="pilsu">*</span></th>
		    	<td class="left">
		    		<div style="width: 300px" class="inputtextarea">
		       		 	<input type="text" class="resetinput" name="default_sc_price" value="">
		       		 </div>
		       	</td>
	    	</tr>
	    </tbody>
	</table>
	</div>
	
	<div id="divCmsInsertInfo">
	<h2>결제 처리시 CMS 계좌 주인은 본 시스템에 회원 가입이 되어 있어야 합니다.(비회원은 카드 및 CMS 등록 불가)</h2>
	<table class="wTable">
	    <caption class="dpnone">CMS 계좌이체 등록 정보</caption>
	    <tbody>
	    	<tr>
	    		<th class="table_id" width="15%">회원정보 동일여부</th>
	    		<td colspan = "3" class="left">
	    			<input type="radio" class="radio" name="same_info_cms" id="same_info_cms_false" value="false"><label for="same_info_cms_false">동일하지 않음</label>
	    			&nbsp;&nbsp;
	    			<input type="radio" class="radio" name="same_info_cms" id="same_info_cms_true" value="true"><label for="same_info_cms_true">동일함</label>
	    		</td>
	    	</tr>
	    	<tr>
		        <th class="table_id">은행</th>
		        <td class="left">
		        	<select name="bankList" class="select">
		        		<option value="003">기업은행</option>
		        		<option value="004">국민은행</option>
		        		<option value="005">외환은행</option>
		        		<option value="007">수협</option>
		        		<option value="011">농협</option>
		        		<option value="020">우리은행</option>
		        		<option value="023">제일은행</option>
		        		<option value="027">씨티은행</option>
		        		<option value="031">대구은행</option>
		        		<option value="032">부산은행</option>
		        		<option value="034">광주은행</option>
		        		<option value="035">제주은행</option>
		        		<option value="037">전북은행</option>
		        		<option value="039">경남은행</option>
		        		<option value="045">새마을금고</option>
		        		<option value="048">신협</option>
		        		<option value="071">우체국</option>
		        		<option value="081">하나은행</option>
		        		<option value="088">신한은행</option>
		        	</select>
		        </td>
		        <th class="table_id">계좌번호<span class="pilsu">*</span>('-'제외)</th>
		        <td class="left">
		        	<div style="width: 300px" class="inputtextarea">
		       		 	<input type="text" class="resetinput" name="acc_number" value="">
		       		 </div>
		        </td>
		    </tr>
		    <tr>
		        <th class="table_id">예금주 아이디<span class="pilsu">*</span></th>
		        <td colspan="3" class="left">
		        	<div style="width: 300px" class="inputtextarea">
		       		 	<input type="text" class="resetinput" name="acc_user_id" value="">
		       		 </div>
		        </td>
		    </tr>
		    <tr>
		        <th class="table_id">예금주 성명<span class="pilsu">*</span></th>
		        <td class="left">
		        	<div style="width: 300px" class="inputtextarea">
		       		 	<input type="text" class="resetinput" name="acc_user_nm" value="">
		       		 </div>
		        </td>
		        <th class="table_id">예금주 주민번호<span class="pilsu">*</span>('-'제외, 생년월일(6자리))</th>
		        <td class="left">
		        	<div style="width: 300px" class="inputtextarea">
		       		 	<input type="text" class="resetinput" name="acc_user_number" value="">
		       		 </div>
		        </td>
		    </tr>
		    <tr>
		    	<th class="table_id">예금주 연락처<span class="pilsu">*</span>('-'제외)</th>
		        <td class="left">
		        	<div style="width: 300px" class="inputtextarea">
		       		 	<input type="text" class="resetinput" name="acc_user_phone" value="">
		       		 </div>
		        </td>
		        <th class="table_id">예금주 이메일</th>
		        <td class="left">
		        	<div style="width: 300px" class="inputtextarea">
		       		 	<input type="text" class="resetinput" name="acc_user_email" value="">
		       		 </div>
		        </td>
		    </tr>
		    <tr>
		    	<th class="table_id">입금액(원)<span class="pilsu">*</span></th>
		    	<td class="left">
		    		<div style="width: 300px" class="inputtextarea">
		       		 	<input type="text" class="resetinput" name="cms_sc_price" value="">
		       		 </div>
		       	</td>
		       	<th class="table_id">현금영수증 발행</th>
		    	<td class="left">
		    		<input type="radio" class="radio" name="tax_yn" id="tax_yn_cms_n" value="N"><label for="tax_yn_cms_n">미발행</label>
	    			&nbsp;&nbsp;
	    			<input type="radio" class="radio" name="tax_yn" id="tax_yn_cms_y" value="Y"><label for="tax_yn_cms_y">발행</label>
		       	</td>
		    </tr>
		    <tr>
				<th>출금요청일<span class="pilsu">*</span></th>
				<td colspan="3" class="fileaddfield left">
					<div class="marginbottom5 margintop5 fileclone width100per">
						<select id="sc_req_dd" name="sc_req_dd">
			       			<option value="5">5</option>
			       			<option value="10">10</option>
			       			<option value="15">15</option>
			       			<option value="20">20</option>
			       			<option value="25">25</option>
						</select>
						일
					</div>
				</td>	
			</tr>
		    <tr>
				<th>CMS이체 동의서<span class="pilsu">*</span></th>
				<td colspan="3" class="fileaddfield" class="left">
					<div class="marginbottom5 margintop5 fileclone width100per">
						<input id="attach_agree" type="file" accept=".jpg, .jpeg, .gif, .tif" name="attach_argree" />
					</div>
				</td>	
			</tr>
	    </tbody>
	</table>
	</div>	
	
	<!--button-->
	<div class="btn">
	<button id="list_btn" type="button" class="s_submit" >목록</button>
	<button id="insert_btn" type="button" class="s_submit">등록하기</button>
	</div>
</form>		
</div>


<script type="text/javascript">

$(document).ready(function() {

	// 목록 보기 버튼 클릭
	$('#list_btn').click(function() {
		location.href = '${ctx}/cms/support/listView.do';
	});

	// 담당자 아이디 체크
	// 기업 및 단체의 담당자 아이디가 회원 가입 되어 있는지 여부만 확인 한다..
	// 미회원이면 메세지 창 띠우기..
	$('[name="manager_id"]').focusout(function() {
		var managerId = $('[name="manager_id"]').val();
		if(managerId == '' || managerId.length < 1) { return; }
		
		$.post('${ctx}/cms/support/userChkProc.do', {userId: managerId}, function(result) {

			if(result.state) { return; } // 정상회원일 경우 넘어간다.

			alert('사이트에 가입된 회원이 아닙니다.');
			$('[name="manager_id"]').val('');
			$('[name="manager_id"]').focus();

			return;
			
		}).fail(function() {
			alert('담당자 회원가입 여부 체크 중 오류 발생.');
			return;
		});
	});

	// 로고 이미지 변경
	$('#attach_logo').change(function(e) {
		var files = e.target.files;
		var filesArr = Array.prototype.slice.call(files);

		filesArr.forEach(function(f) {
			if(!f.type.match('image.*')) {
				alert('[jpg, jpeg, png, gif]등의 이미지 파일만 가능 합니다.') 
				return;
			}

			var reader = new FileReader();
			reader.onload = function(e) {
				$('#logoImg').attr('src', e.target.result);
			}
			reader.readAsDataURL(f);
		});
	});

	// validation 체크 전역변수
	// 선택된 항목 마다 validate 체크를 달리 처리 해야 됨..
	var validate = $("#frm").validate({
	    onfocusout: false,	// 포커스가 아웃되면 발생됨 꺼놓음
	    invalidHandler: function(form, validator) {		// 에러발생시 처리..
	        var errors = validator.numberOfInvalids();
	        if (errors) {

	        	var message = validator.errorList[0].message; 
		              
		        if(message.length > 0) { alert(message); }		// 메세지 입력여부 확인 후 alert 창 띠우기..
	            validator.errorList[0].element.focus();
	        }
	    },
	    submitHandler: function(frm) {		// validate 체크 완료 후 실행 로직....
			if(!confirm("등록하시겠습니까?")) { return; }
			    
	    	frm.submit();
		}
	});

	// validator 기본 메세지 처리...
	$.extend( $.validator.messages,
	          { required: "필수 항목입니다.",
		          remote: "항목을 수정하세요.",
		          email: "유효하지 않은 E-Mail주소입니다.",
		          url: "유효하지 않은 URL입니다.",
		          date: "올바른 날짜를 입력하세요.",
		          dateISO: "올바른 날짜(ISO)를 입력하세요.",
		          number: "유효한 숫자가 아닙니다.",
		          digits: "숫자만 입력 가능합니다.",
		          creditcard: "신용카드 번호가 바르지 않습니다.",
		          equalTo: "같은 값을 다시 입력하세요.",
		          extension: "올바른 확장자가 아닙니다.",
		          maxlength: $.validator.format( "{0}자를 넘을 수 없습니다. " ),
		          minlength: $.validator.format( "{0}자 이상 입력하세요." ),
		          rangelength: $.validator.format( "문자 길이가 {0} 에서 {1} 사이의 값을 입력하세요." ),
		          range: $.validator.format( "{0} 에서 {1} 사이의 값을 입력하세요." ),
		          max: $.validator.format( "{0} 이하의 값을 입력하세요." ),
		          min: $.validator.format( "{0} 이상의 값을 입력하세요." ) 
		       });

	$('#insert_btn').click(function() {

		// validate 체크 및 ajax 처리
		// 사용자 구분별로 다른 Rule을 적용 해야 된다..
		switch($('[name="user_tp"]:checked').val()) {
			case 'U' : // 개인일 경우..
				// rules 만 적용 하고 메세지는 기본을 사용하자...
				validate.settings.rules = {
					user_id: { required: true }
					, user_nm: { required: true }
					, user_phone1: { required: true }
					, user_phone2: { required: true, number: true, rangelength: [3, 4] }
					, user_phone3: { required: true, number: true, rangelength: [3, 4] }
					, acc_number: { required: true, number: true }
					, acc_user_id: { required: true }
					, acc_user_nm: { required: true }
					, acc_user_number: { required: true, number: true }
					, acc_user_phone: { required:true, number: true }
					, cms_sc_price: { required: true, number: true }
					, attach_argree: { required: true }		// 테스트 진행을 위해 우선 주석 처리...
				};

				// CMS이체 동의서를 금융결제원에 등록 처리(ajax) 하고
				// 위에 내용 처리 안된다..
				// 우선 Header에 Api-key를 담을 경우
				// cross-origin에 걸리게 되고 request method가 options 에 걸려 버린다.
				// 현재 theBill 사이트에서 Access-control-method 접속 허용이 안되어 있는거 같다. ㅅㅂㅅㅂㅅㅂ
				// 결국 제공 해준 demon형 모듈을 이용하여 처리 하여야 된다.
				 
				 // 이미 연결된 카드 or CMS 계좌 정보가 있는지 확인 한다.
				 // 1회원당 한개의 연결 정보만 보유 하여야 한다.
				 var accUserIdVal = $('[name="acc_user_id"]').val();
				 $.post('/cms/support/checkAgreeUser.do', {accUserId: accUserIdVal}, function(result) {

					if(result.state) {
						// 등록된 정기 후원 정보가 있으면 관련 내용을 알려 주어야 한다.
						alert('[' + accUserIdVal + '] 회원은 이미 등록된 정기 후원 정보가 있습니다.');
						return;
					}
					 
					// 회원정보 등록 로직..
					$('#frm').attr('action', '/cms/support/userInsertProc.do');
					$('#frm').submit();
					 
				}).fail(function() {
					alert('정기후원 등록 여부 확인 중 오류 발생');
					return;
				});
				 
				break;
			case 'C' :	// 기업일 경우..
			case 'G' :	// 단체일 경우..
				
				// rules 만 적용 하고 메세지는 기본을 사용하자...
				validate.settings.rules = {
					user_comp_nm: { required: true }
					, user_comp_number: { required: true, number: true }
					, manager_nm: { required: true }
					, comp_manager_phone1: { required:true }
					, comp_manager_phone2: { required:true, number: true, rangelength: [3, 4] }
					, comp_manager_phone3: { required:true, number: true, rangelength: [3, 4] }
				};

				// 결제방식이 일시후원의 무통장입금 이거나, 정기후원의 정보만등록 할 경우 입금액이 기본으로 들어가야 됨.. 
				var scPriceTpVal = $('[name="sc_price_tp"]:checked').val();

				if(scPriceTpVal == 'M' || scPriceTpVal == 'Z') {	// 무통장입금이거나 정보만등록할 경우..
					validate.settings.rules.default_sc_price = { required:true, number: true };
					
					$('#frm').attr('action', '/cms/support/compInsertProc.do');
					$('#frm').submit();
					break;
				} else if(scPriceTpVal == 'D') { 
					// 정기후원 카드 결제일 경우 카드주 아이디 성명 연락처 입금액 카드번호 유효기간 등이 필수 임..
					validate.settings.rules.card_user_id = { required: true };
					validate.settings.rules.card_user_nm = { required: true };
					validate.settings.rules.card_user_phone = { required: true };
					validate.settings.rules.card_sc_price = { required: true, number: true };
					validate.settings.rules.card_number = { required: true, number: true, creditcard: true };
					validate.settings.rules.card_mm = { required: true, number: true };
					validate.settings.rules.card_yy = { required: true, number: true };

					var accUserIdVal = $('[name="card_user_id"]').val();
					 $.post('/cms/support/checkAgreeUser.do', {accUserId: accUserIdVal}, function(result) {

						if(result.state) {
							// 등록된 정기 후원 정보가 있으면 관련 내용을 알려 주어야 한다.
							alert('[' + accUserIdVal + '] 회원은 이미 등록된 정기 후원 정보가 있습니다.');
							return;
						}
						 
						// 회원정보 등록 로직..
						$('#frm').attr('action', '/cms/support/compCardInsertProc.do');
						$('#frm').submit();
						 
					}).fail(function() {
						alert('정기후원 등록 여부 확인 중 오류 발생');
						return;
					});
					
					break;
				} else if(scPriceTpVal == 'S')
					// 정기후원 CMS 결제일 경우 계좌번호, 예금주 아이디 등 필수 정보를 셋팅한다...
					validate.settings.rules.acc_number = { required: true, number: true };
					validate.settings.rules.acc_user_id = { required: true };
					validate.settings.rules.acc_user_nm = { required: true };
					validate.settings.rules.acc_user_number = { required: true, number: true };
					validate.settings.rules.acc_user_phone = { required: true, number: true };
					validate.settings.rules.cms_sc_price = { required: true, number: true };
					validate.settings.rules.attach_argree = { required: true };

					var accUserIdVal = $('[name="acc_user_id"]').val();
					 $.post('/cms/support/checkAgreeUser.do', {accUserId: accUserIdVal}, function(result) {

						if(result.state) {
							// 등록된 정기 후원 정보가 있으면 관련 내용을 알려 주어야 한다.
							alert('[' + accUserIdVal + '] 회원은 이미 등록된 정기 후원 정보가 있습니다.');
							return;
						}
						 
						// 회원정보 등록 로직..
						$('#frm').attr('action', '/cms/support/compCmsInsertProc.do');
						$('#frm').submit();
						 
					}).fail(function() {
						alert('정기후원 등록 여부 확인 중 오류 발생');
						return;
					});
					
				break;
			default :
				alert('잘못된 user_tp 코드 값입니다.');	return;			
		}
	});

	// 기업&단체 회원정보 동일여부 선택
	$('[name="same_info_card"]').change(function() {
		if(this.value == 'false') {
			$('[name="card_user_id"]').val('');
			$('[name="card_user_nm"]').val('');
			$('[name="card_user_phone"]').val('');
			$('[name="card_user_email"]').val('');
			return;
		}

		$('[name="card_user_id"]').val( $('[name="manager_id"]').val() );
		$('[name="card_user_nm"]').val( $('[name="manager_nm"]').val() );
		$('[name="card_user_phone"]').val( $('[name="comp_manager_phone1"]').val() + $('[name="comp_manager_phone2"]').val() + $('[name="comp_manager_phone3"]').val() );
		$('[name="card_user_email"]').val( $('[name="comp_manager_email1"]').val() + '@' + $('[name="comp_manager_email2"]').val() );
	});

	// 개인 회원정보 동일여부 선택
	$('[name="same_info_cms"]').change(function() {
		if(this.value == 'false') {

			$('[name="acc_user_id"]').val('');
			$('[name="acc_user_nm"]').val('');
			$('[name="acc_user_phone"]').val('');
			$('[name="acc_user_email"]').val('');
			return;
		}

		if($('[name="user_tp"]:checked').val() == 'U') {

			// 구분이 개인이고..
			$('[name="acc_user_id"]').val( $('[name="user_id"]').val() );
			$('[name="acc_user_nm"]').val( $('[name="user_nm"]').val() );
			$('[name="acc_user_phone"]').val( $('[name="user_phone1"]').val() + $('[name="user_phone2"]').val() + $('[name="user_phone3"]').val() );
			$('[name="acc_user_email"]').val( $('[name="user_email1"]').val() + '@' + $('[name="user_email2"]').val() );
			
		} else {
		 
			// 기업 & 단체일 경우...
			$('[name="acc_user_id"]').val( $('[name="manager_id"]').val() );
			$('[name="acc_user_nm"]').val( $('[name="manager_nm"]').val() );
			$('[name="acc_user_phone"]').val( $('[name="comp_manager_phone1"]').val() + $('[name="comp_manager_phone2"]').val() + $('[name="comp_manager_phone3"]').val() );
			$('[name="acc_user_email"]').val( $('[name="comp_manager_email1"]').val() + '@' + $('[name="comp_manager_email2"]').val() );
		}
		
	});
	

	// 화면 보이기 로직을 만들어야 겠다... 중구난방 처리가 되겠는데???
	// 개인, 기업, 단체 구분 선택시...
	$('[name="user_tp"]').change(function() {

		// 모든 데이터 삭제
		$('input:text').val('');

		$('[name="same_info_card"]').filter('[value="false"]').attr('checked', true);	// 회원정보 동일 여부 선택
		$('[name="same_info_cms"]').filter('[value="false"]').attr('checked', true);	// 회원정보 동일 여부 선택

		// 큰 틀에 대한 show, hide 처리..
		// 개인이냐
		if(this.value == 'U') {
			$('#divUserInfo').show();
			$('#divCompInfo').hide();
			$('#divCardInsertInfo').hide(); // 개인은 정기후원 카드 등록이 없다..
			$('#divCmsInsertInfo').show();
			$('#divLogoInfo').hide();
			$('#divDefaultInsertInfo').hide();

			$('#sc_price_tp_user').attr('checked', true); // CMS 계좌이체 선택
			$('[name="tax_yn"]').filter('[value="N"]').attr('checked', true); // 현금영수증 발행 여부 체크..
			
			return;
		}

		// 기업또는 단체이냐..
		$('#divUserInfo').hide();
		$('#divCompInfo').show();
		$('#divLogoInfo').show();
		$('#divDefaultInsertInfo').show();

		$('[name="sp_mh_tp"]').filter('[value="O"]').attr('checked', true).trigger('change');
		// 일시후원 결제방식 선택
		$('#sc_price_tp_m').attr('checked', true);	// 일시후원 무통장입금 선택
	});

	// 후원 종류 선택
	$('[name="sp_mh_tp"]').change(function() {

		// 결재 방식 초기화
		$('[name="sc_price_tp"]').attr('checked', false);
		$('#divCardInsertInfo').hide();
		$('#divCmsInsertInfo').hide();

		// 후원 종류 선택
		if(this.value == 'O') {
			$('#sc_price_tp_o_comp').show();
			$('#sc_price_tp_m_comp').hide();
			$('#sc_price_tp_m').attr('checked', true);
			$('#divDefaultInsertInfo').show();
			return;
		}

		$('#sc_price_tp_o_comp').hide();
		$('#sc_price_tp_m_comp').show();
		$('#sc_price_tp_z').attr('checked', true);
		$('#divDefaultInsertInfo').show();
		
	});

	// 정기후원 -> 카드결제, CMS 계좌이체 관련된 view 이벤트..
	$('[name="sc_price_tp"]').change(function() {
		if($('[name="sp_mh_tp"]:checked').val() != 'M') { return; }	// 정기후원이 아니면 넘어가기..
		if(this.value == 'Z') { // 정보등록용이면 넘어가기..
			$('#divCardInsertInfo').hide();
			$('#divCmsInsertInfo').hide();
			$('#divDefaultInsertInfo').show();
			return; 
		} 

		if(this.value == 'D') {	// 카드 결제 선택
			$('#divCardInsertInfo').show();
			$('#divCmsInsertInfo').hide();
			$('#divDefaultInsertInfo').hide();
			return;
		}

		$('#divCardInsertInfo').hide();
		$('#divCmsInsertInfo').show();
		$('#divDefaultInsertInfo').hide();
	});

	// 기업명 및 사업자번호 입력시 기존 입력된 정보 가져오기
	$('[name="user_comp_number"]').focusout(function() {

		// 구분이 개인이면 그냥 넘어간다..
		if($('[name="user_tp"]:checked').val() == 'U') { return; }

		var compNm = $('[name="user_comp_nm"]').val();
		var compNumber = $('[name="user_comp_number"]').val();
		var userTp = $('[name="user_tp"]:checked').val();

		if(compNm == '' || compNm.length < 1 || compNumber == '' || compNumber.length < 1) { return; }

		$.post('${ctx}/cms/support/compChkProc.do', {userTp: userTp, compNm: compNm, compNumber: compNumber}, function(result) {

			if(!result.state) {
				alert('등록된 기업이 없습니다.');
				return;
			}

			var compInfo = result.compInfo;

			$('[name="manager_id"]').val(compInfo.user_id);
			$('[name="manager_nm"]').val(compInfo.user_nm);
			$('[name="comp_manager_phone1"]').val(compInfo.user_phone1);
			$('[name="comp_manager_phone2"]').val(compInfo.user_phone2);
			$('[name="comp_manager_phone3"]').val(compInfo.user_phone3);			
			$('[name="comp_manager_email1"]').val(compInfo.user_email1);
			$('[name="comp_manager_email2"]').val(compInfo.user_email2);

			var scgInfo = result.scgInfo;

			$('[name="scg_url"]').val(scgInfo.scg_url);
			$('#logoImg').attr('src', '${ctx}/common/file/download.do?upload_file_name=' + scgInfo.img_thum_l_name);

		}).fail(function() {
			alert('기업회원 정보 조회 중 오류 발생.');
			return;
		});
	});
	
	// 회원 가입 여부 체크
	$('[name="user_id"]').focusout(function() {
		// 회원 ID 입력 후 focusout 처리가 될때 회원 가입 여부를 확인 한다.
		if(this.value.length < 1) { return; }
		
		$.post('${ctx}/cms/support/userChkProc.do', {userId: this.value}, function(result) {

			if(!result.state) {

				alert('[' + result.userId + ']은(는) 등록되지 않은 회원 입니다.');
				$('[name="user_id"]').val('');	// 초기화 처리...
				$('[name="user_id"]').focus();
				 
				return; 
			}

			var userInfo = result.userInfo;
			
			$('[name="user_nm"]').val(userInfo.user_name);
			$('[name="user_phone1"]').val(userInfo.user_phone1);
			$('[name="user_phone2"]').val(userInfo.user_phone2);
			$('[name="user_phone3"]').val(userInfo.user_phone3);
			$('[name="user_email1"]').val(userInfo.user_email1);
			$('[name="user_email2"]').val(userInfo.user_email2);
			
			return;
			
		}).fail(function() {
			alert('회원 여부 조회 처리 중 오류 발생.');
		});
	});
	

	// 페이지 로딩 완료 후 기본 이벤트..
	$('[name="user_tp"]').filter('[value="U"]').attr('checked', true).trigger('change');	// 구분 선택
});
</script>