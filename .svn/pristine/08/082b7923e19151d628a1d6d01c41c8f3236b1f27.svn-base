package egovframework.phcf.scheduler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.phcf.nicepay.NicepayService;
import egovframework.phcf.scheduler.payspec.PayCreateData;
import egovframework.phcf.scheduler.payspec.PayCreateHeader;
import egovframework.phcf.scheduler.payspec.PayCreateTail;
import egovframework.phcf.util.DateUtil;

/**
 * CMS 계좌이체 회원등록 스케줄링.
 * 
 * 사전 작업내역 : 
 * 
 * 1. 5, 10, 15, 20, 25일 날 출금을 할 수 있도록 설정 하므로 최소 2일 전에 작업을 처리 해야 된다. 
 * 		2일 전 회원 신청 및 등록을 완료 처리 하고 출금 신청은 1일 전 수행 하여야 설정한 일자에 출금이 진행 된다.
 *     실제 수행일은 4, 9, 14, 19, 24 일날 오전 16시50분에 실행 한다(설정 : applicationContext.xml)
 * 2. 금일+1에 해당 하는 출금 대상 회원을 찾아와 PAY 파일을 만들고
 * 3. CMS 모듈의 batch_send.sh 파일을 실행한다.
 * 4. 전송 성공 여부를 체크 하여 DB 테이블에 반영 처리 한다.
 * 
 * ==== 개발 정보 ====
 * 생성일 : 2018.09.07
 * 개발사 : 휴비즈ICT
 * 
 * ==== 개발 이력 ====
 *    날짜       |        변경 정보       |   변경자
 * 2018.09.07 |         최초등록       |   김경식
 * 2019.05.15 |         로직수정       |   김다빈
 * 
 * @version 1.0
 */
@Service("CmsPayCreateCronQuartz_03")
public class CmsPayCreateCronQuartz_03 extends TheBillCronQuartz {

	@Resource(name = "NicepayService")
	NicepayService nicepayService;

	@SuppressWarnings("static-access")
	protected void executeInternal() {
		
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		System.out.println("== 출금신청 등록을 시작 합니다. : current time = " + sdf.format(time));
		
		// 출금정보 등록 유저ID
		// UserCreate경우 cms_id만 있으면 되는데.. PayCreate 경우 user id 가 필요함..
		List<String> userIdList = new ArrayList<String>();
		
		// agree_num 컬럼 : 출금승인 번호 - 해당 데이터가 출금 승인 되면 해당 컬럼을 update 처리..
		// send_ok 폴더를 읽어서 승인번호 부분을 읽어 와야 된다... 
		// 2018-10-01 변경사항 : send_ok에서 데이터를 읽어야 되는 줄 알았는데...
		//                                  우리쪽에서 생성해서 넣어 주어야 한다.(Nice모듈에서 생성해 넣어주지는 않는다.)
		List<HashMap<String, Object>> agreeNumList = new ArrayList<HashMap<String, Object>>();
		
		File userPayFile = null;
		
		Writer writer = null;
		
		// 등록할 출금 정보가 있을 경우 cnt ++;
		int payLogicCnt = 0;
		
		try {
			// tb_support_cms 테이블을 조회 하여 금일+1에 해당 하는 출금 대상 회원 목록을 가져온다.
			// 등록 할 대상이 없다면 해당 스케줄러를 종료 한다.
			// 오늘날짜(월 필요업음 일만 가져 옴 됨) + 1 값을 가져온다.
			SimpleDateFormat sdfDay = new SimpleDateFormat("dd");

			// 현재 날짜 +1 day 를 해야 됨...
			Date dt = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(dt);
			c.add(Calendar.DATE, 1);
			dt = c.getTime();

			String curYear = String.valueOf( c.get(Calendar.YEAR) );
			String curMonth = String.valueOf( c.get(Calendar.MONTH) + 1 );
			
			List<String> hollydayList = DateUtil.getHollydayWithWeekendList(curYear, curMonth);
			
			// 다음달 일자 정보
			SimpleDateFormat sDf = new SimpleDateFormat("yyyyMM");
			SimpleDateFormat sDf2 = new SimpleDateFormat("yyyyMMdd");
			
			Calendar mC = Calendar.getInstance();
			Calendar calen = Calendar.getInstance();
			mC.add ( mC.MONTH, + 1 );
			String nextYear = String.valueOf( mC.get(Calendar.YEAR) );
			String nextMonth = String.valueOf( mC.get(Calendar.MONTH) + 1 );
			List<String> nextMonthHollydayList = DateUtil.getHollydayWithWeekendList(nextYear, nextMonth);
			
			String cYcM = sDf.format(c.getTime());
			String nYnM = sDf.format(mC.getTime());
			
			String curDay = sDf2.format(calen.getTime());
			
			//월 마지막 일자 구하기
			int monthLastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			

			List<String> payDayArrList = getPayDay(cYcM, nYnM, nextMonthHollydayList, hollydayList,  monthLastDay);


//			System.out.println("hollyDayList : " + hollydayList);
//			System.out.println("nextMonthHollydayList : " + nextMonthHollydayList);
//			System.out.println("payDayArrList : " + payDayArrList);
			if(!payDayArrList.contains(curDay)){
				System.out.println("== 금일은 출금 신청이 불가능한 날입니다. ");
				return;
			}else{
				// 오늘이 출금 신청 가능일 일 경우 => 주말 , 휴일을 제외한 은행 영업일 ( 평일 )
				System.out.println("== 금일은 출금 신청이 가능한 날입니다. ");

				// 금일 출금 신청해야하는 출금 신청 일정 리스트
				List<String> payApplyDays = new ArrayList<String>();
				Date curDate = sDf2.parse(curDay);
				Calendar curCal = Calendar.getInstance();
				curCal.setTime(curDate);
				curCal.add(Calendar.DATE, 1);
				String curApplyDay = sDf2.format(curCal.getTime());
				payApplyDays.add(curApplyDay);
				
				// 출금 신청 가능 여부 체크 및 금일 출금 신청 일자 추가 로직 
				String tempDay = curDay;
				for(int i=0; i<11; i++){
					Date tempDate = sDf2.parse(tempDay);
					Calendar tempCal = Calendar.getInstance();
					tempCal.setTime(tempDate);
					tempCal.add(Calendar.DATE, 1);

					Calendar tempCal02 = Calendar.getInstance();
					tempCal02.setTime(tempDate);
					tempCal02.add(Calendar.DATE, 2);
					
					String tranDate = sDf2.format(tempCal.getTime());
					String tranDate02 = sDf2.format(tempCal02.getTime());
					tempDay = tranDate;
					if(payDayArrList.contains(tempDay)){
						// 출금 신청 가능한 날인 경우, 반복문을 종료한다.
						System.out.println(" == '" + tempDay + "' 은 출금 신청 가능 날짜입니다. ");
						break;
					}else {
						//출금 신청 불가능한 날인 경우에, 해당 일자를 Array List 에 추가한다.
						System.out.println( " == '" + tempDay + "' 은 출금 신청 불가능 날짜이므로, 금일 출금 신청합니다.");
						payApplyDays.add(tranDate02);
					}
				}
				
				System.out.println("== 금일 출금 신청 날짜 리스트 : " + payApplyDays);
				
				List<HashMap<String, Object>> allCreatePayList = new ArrayList<HashMap<String, Object>>();
				
				String applyDay = null;
				for(String str : payApplyDays){
					Date payDay = sDf2.parse(str);
					int ctPayDay = Integer.valueOf(sdfDay.format(payDay));
					List<HashMap<String, Object>> createPayList = nicepayService.selectCreatePayList(ctPayDay);
					
					applyDay = str;
					allCreatePayList.addAll(createPayList);
				}
				
				// 출금 대상 목록을 가져옴.
				if(allCreatePayList == null || allCreatePayList.size() < 1) {
					System.out.println("== 등록 할 출금 정보가 없습니다. : current time = " + sdf.format(System.currentTimeMillis()));
					return;
				}else{
					// 출금정보를 등록 할 File을 만든다.
					File sendDir = new File(theBillHome, "data" + File.separator + "cms" + File.separator + "send");
					if(!sendDir.exists()) { throw new NullPointerException("== send 폴더가 존재 하지 않습니다. : current time = " + sdf.format(System.currentTimeMillis())); }
					
//					//////////////////////////////////////////////////////////////////////////////////////////////
//					// 파일생성명은 실제출금일에 맞추어 생성해야 된다.
//					// 이것도 휴일 및 공휴일을 경우 전송 에러가 난다.
//					// 규칙이 있어야 된다. 생성한 날짜가 토요일or일요일을 경우 그 다음주 월요일
//					// 공휴일일 경우 그 다음주 평일... ㄷㄷㄷ 이네...
//					// 출금 전문은 은행영업일에만 등록이 가능해 진다..
//					//////////////////////////////////////////////////////////////////////////////////////////////
//					
//					// plusDayStr 값이 휴일 리스트에 포함 되어 있는지 여부를 확인 하여 변경 처리(은행영업일 만 가능) 해야 된다.
					String plusDayStr = calHollyDay(cYcM, nYnM, nextMonthHollydayList, hollydayList, applyDay, monthLastDay);
					
					System.out.println(" plusDayStr  : "  + plusDayStr);
////					// ex. 오늘이 19일이고 실제 출금일이 20일일 경우 실 파일 생성명도 20일로 생성되어야 함.
////					// 이용기관코드
					String dealID = EgovProperties.getProperty("dealID");
					userPayFile = new File(sendDir.getAbsolutePath(), plusDayStr + "." + dealID + ".PAY");
					// 기존에 파일이 이미 존재 하면 삭제 처리 한다.
					if(userPayFile.exists()) {
						System.out.println("== 기존 파일이 존재 합니다. 파일을 삭제 합니다. : current time = " + sdf.format(System.currentTimeMillis()));
						userPayFile.delete(); 
					}
					
					System.out.println("== 출금정보 등록 파일을 만듭니다. : current time = " + sdf.format(System.currentTimeMillis()));
					
					writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(userPayFile), "euc-kr"));
					
					// 실제 데이터를 쓰는 부분...
//					
					// createPayList에 있는 sc_price_sum 값을 가져 와야 된다.
					int payPriceTotalSum = 0;
					for(HashMap<String, Object> payInfo : allCreatePayList) {
						payPriceTotalSum += Integer.valueOf( String.valueOf( payInfo.get("sc_price") ) );
					}
					
					// Header
					writer.write(new PayCreateHeader(allCreatePayList.size(), payPriceTotalSum).getHeaderStr());
					
					// Data처리..
					for(int i = 0; i < allCreatePayList.size(); i++) {
						
						HashMap<String, Object> agreeNumParam = new HashMap<String, Object>();
						
						HashMap<String, Object> createPay = allCreatePayList.get(i);
						String accTp = String.valueOf( createPay.get("acc_tp") );
						String userId = String.valueOf( createPay.get("user_id") );
						// 출금정보 등록 완료 후 pay_send_yn 및 agree_num 컬럼 변경용으로 사용함
						userIdList.add( userId );
						agreeNumParam.put("userId", userId);
						
						String userNm = String.valueOf( createPay.get("user_name") );
						
						PayCreateData payCreateData = new PayCreateData(accTp, i+1, userId, userNm);
						
						// 입금회차 default 가 0이므로 출금정보 등록시 + 1 값을 셋팅해 주어야 한다.
						String scCnt = String.valueOf( Integer.valueOf( String.valueOf( createPay.get("sc_cnt") ) ) + 1 ); 
						String scPrice = String.valueOf( createPay.get("sc_price") );	// 입금액
						String agreeNum = plusDayStr + StringUtils.leftPad(String.valueOf( i+1 ), 2, '0');
						
						// Pay전문 데이터를 생성한다.
						payCreateData.setCmsPayCreateData(scCnt, scPrice, agreeNum);
						// 인증정보를 ParamMap에 저장한다.
						agreeNumParam.put("agreeNum", agreeNum);
						
						// 인증정보 리스트에 생성한 paramMap을 추가한다.
						agreeNumList.add(agreeNumParam);
						
						writer.write(payCreateData.getDataStr());
					}
					
					writer.write(new PayCreateTail(allCreatePayList.size(), payPriceTotalSum).getTailStr());
					System.out.println("== PAY 파일 생성이 완료 되었습니다. :  current time = " + sdf.format(System.currentTimeMillis()));
					
					payLogicCnt++;
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			// 파일 쓰기 종료..
			try {
				if(writer != null) writer.close();
			} catch (IOException e) { 
				e.printStackTrace();
				System.out.println("== 파일 쓰기 종료 중 오류 발생 : current time = " + sdf.format(System.currentTimeMillis()));
			}
		}
		
		// 생성된 파일이 존재하는 경우. 파일을 전송한다.
		if(payLogicCnt > 0){
			System.out.println("PayLoginCnt : " + payLogicCnt);
			// 생성된 파일을 전송 한다.
			if(!sendTheBillFile(userPayFile)) { return; }
			
			///////////////////////////////////////////////////////////////////////
			// send_ok 폴더를 읽어서 인증정보를 받아올려고 했는데
			// cms모듈쪽에서 생성하지 않는다...
			///////////////////////////////////////////////////////////////////////
			
			try {
				// sc_cnt 컬럼 : 출금 신청이 완료 된 후 sc_cnt 값을 증가 시켜 주어야 된다.(default 0 이다..)
				nicepayService.updatePaySendYn(userIdList, agreeNumList);
				System.out.println("== 출금정보 변경 완료 : current time = " + sdf.format(System.currentTimeMillis()));
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	// GET 출금 가능일 리스트 
	private List<String> getPayDay(String cym, String nym, List<String>nextMonthHollydayList, List<String> hollyDayList, Integer lastDay) throws Exception{
		ArrayList<String> payDayArrList = new ArrayList<String>();

		// 이번달 일자 배열
		for(int idx=0; idx<lastDay; idx++){
			if(idx<9){
				payDayArrList.add(cym +"0"+ (idx +1));
			}else{
				payDayArrList.add(cym + (idx +1));
			}
		}
		
		// 다음달 10일까지 일자 추가
		for(int jdx=0; jdx<20; jdx++){
			if(jdx<9){
				payDayArrList.add(nym +"0"+ (jdx +1));
			}else{
				payDayArrList.add(nym + (jdx +1));
			}
		}
		
		// 이번달 주말 & 공휴일 제거 
		for(int xdx = 0; xdx < hollyDayList.size(); xdx++){
			String rmStr = hollyDayList.get(xdx);
			payDayArrList.remove(rmStr);
		}
		// 다음달 주말 & 공휴일 제거 
		for(int jdx = 0; jdx < nextMonthHollydayList.size(); jdx++){
			String rmStr = nextMonthHollydayList.get(jdx);
			payDayArrList.remove(rmStr);
		}
		
		return payDayArrList;
	}
	
	private String calHollyDay(String cym, String nym, List<String>nextMonthHollydayList, List<String> hollyDayList, String day , Integer lastDay) throws Exception{
	
		System.out.println(" day : " + day);
		List<String> payDayArrList = getPayDay(cym, nym, nextMonthHollydayList, hollyDayList, lastDay);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		if(!payDayArrList.contains(day)){
			//출금 가능일 여부체크 ( 출금 불가능 일자 ) , 가능일을 찾아서 넘겨준다
			while(!payDayArrList.contains(day)){
				Date tempDate = dateFormat.parse(day);
				Calendar tempCal = Calendar.getInstance();
				tempCal.setTime(tempDate);
				tempCal.add(Calendar.DATE, 1);
				String tempApplyDay = dateFormat.format(tempCal.getTime());
				day = tempApplyDay;
			}
		}
		return day;
	}
}

