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

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.phcf.nicepay.service.NicepayService;
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
 * 
 * @version 1.0
 */
public class CmsPayCreateCronQuartz_02 extends TheBillCronQuartz {

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
			Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);

			// 현재 날짜 +1 day 를 해야 됨...
			Date dt = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(dt);
			c.add(Calendar.DATE, 1);
			dt = c.getTime();

			String curYear = String.valueOf( c.get(Calendar.YEAR) );
			String curMonth = String.valueOf( c.get(Calendar.MONTH) + 1 );
			
			List<String> hollydayList = DateUtil.getHollydayWithWeekendList(curYear, curMonth);
			
			System.out.println("== 휴일 정보 : " + hollydayList);
			
			// 다음달 일자 정보
			SimpleDateFormat sDf = new SimpleDateFormat("yyyyMM");
			SimpleDateFormat sDf2 = new SimpleDateFormat("yyyyMMdd");
			
			Calendar mC = Calendar.getInstance();
			Calendar calen = Calendar.getInstance();
			mC.add ( mC.MONTH, + 1 );
			System.out.println((mC.getTime())); 
			String nextYear = String.valueOf( mC.get(Calendar.YEAR) );
			String nextMonth = String.valueOf( mC.get(Calendar.MONTH) + 1 );
			List<String> nextMonthHollydayList = DateUtil.getHollydayWithWeekendList(nextYear, nextMonth);
			
			String cYcM = sDf.format(c.getTime());
			String nYnM = sDf.format(mC.getTime());
			
			String curDay = sDf2.format(calen.getTime());
			
			//월 마지막 일자 구하기
			int monthLastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			// 출금 신청 예정일 Array List 
			List<String> calPayDayArr = calPayDay(cYcM, nYnM, nextMonthHollydayList, hollydayList, Integer.valueOf(sdfDay.format(today)), monthLastDay);
			
			System.out.println("== 출금 신청 예정일 : " + calPayDayArr);
			
			List<String> ctPayDayArr = new ArrayList<String>();
			
			// 출금 신청 Array List  에 금일 날짜가 존재하는지 확인
			if(calPayDayArr.contains(curDay)){
				System.out.println("== 금일은 출금 신청일입니다. : " + curDay );
			}else{
				// 출금 신청일이 아닌 경우, 로직 종료.
				System.out.println("== 금일은 출금 신청일이 아닙니다. : " + curDay );
				return;
			}

			// 출금 신청일에 해당하는 출금 날 조회 
			// Array List Index > 출금 신청일 
			// 0 > 5 ,  1 > 10 ,  2 > 15 , 3 > 20 , 4 > 25, 5 > 다음달 1~5
			List<Integer> indexAllArr = indexOfAll("20190514",calPayDayArr);
			for(int i : indexAllArr){
				System.out.println("== indexAllArr: " + indexAllArr);
				if(i==0){
					ctPayDayArr.add("05");
				}else if(i==1){
					ctPayDayArr.add("10");
				}else if(i==2){	
					ctPayDayArr.add("15");
				}else if(i==3){
					ctPayDayArr.add("20");
				}else if(i==4){
					ctPayDayArr.add("25");
				}else if(i==5){
					ctPayDayArr.add("01");
				}
			}


			
			// ctPayDayArr 는 출금 신청일 배열 , 출금 신청일 개수 만큼 출금 신청 파일 생성
			for(int idx=0; idx<ctPayDayArr.size(); idx++){
				
				// 만약 01 값을 가지고 있을 경우, 다음달 1~5일 사이에 출금 가능날이 존재하지 않으므로, 
				// payDayYM 값을 다음달 5일 출금신청일로 설정, 아닌 경우, 이번달 날짜로 설정.
				String payDayYM;
				if(ctPayDayArr.get(idx) == "01"){
					payDayYM = nYnM;
					ctPayDayArr.set(idx, "05");
				}else{
					payDayYM = cYcM;
				}
				String payDayStr =  payDayYM + ctPayDayArr.get(idx);
				
				Date payDay = sDf2.parse(payDayStr);
				
				int ctPayDay = Integer.valueOf(sdfDay.format(payDay));
				List<HashMap<String, Object>> createPayList = nicepayService.selectCreatePayList(ctPayDay);
				
				// 출금 대상 목록을 가져옴.
				if(createPayList == null || createPayList.size() < 1) {
					System.out.println("== 등록 할 출금 정보가 없습니다. : current time = " + sdf.format(System.currentTimeMillis()));
				}else{
					// 출금정보를 등록 할 File을 만든다.
					File sendDir = new File(theBillHome, "data" + File.separator + "cms" + File.separator + "send");
					if(!sendDir.exists()) { throw new NullPointerException("== send 폴더가 존재 하지 않습니다. : current time = " + sdf.format(System.currentTimeMillis())); }
					
					//////////////////////////////////////////////////////////////////////////////////////////////
					// 파일생성명은 실제출금일에 맞추어 생성해야 된다.
					// 이것도 휴일 및 공휴일을 경우 전송 에러가 난다.
					// 규칙이 있어야 된다. 생성한 날짜가 토요일or일요일을 경우 그 다음주 월요일
					// 공휴일일 경우 그 다음주 평일... ㄷㄷㄷ 이네...
					// 출금 전문은 은행영업일에만 등록이 가능해 진다..
					//////////////////////////////////////////////////////////////////////////////////////////////
					
					// plusDayStr 값이 휴일 리스트에 포함 되어 있는지 여부를 확인 하여 변경 처리(은행영업일 만 가능) 해야 된다.
					// 날짜 포맷 ? 2019055 (2019년 05월 5일 ) 이렇게인지, 20190505 인지
					String plusDayStr = payDayYM + ctPayDay; 
		
//					// ex. 오늘이 19일이고 실제 출금일이 20일일 경우 실 파일 생성명도 20일로 생성되어야 함.
//					// 이용기관코드
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
					
					// createPayList에 있는 sc_price_sum 값을 가져 와야 된다.
					int payPriceTotalSum = 0;
					for(HashMap<String, Object> payInfo : createPayList) {
						payPriceTotalSum += Integer.valueOf( String.valueOf( payInfo.get("sc_price") ));
					}
					
					// Header
					writer.write(new PayCreateHeader(createPayList.size(), payPriceTotalSum).getHeaderStr());
					
					// Data처리..
					for(int i = 0; i < createPayList.size(); i++) {
						
						HashMap<String, Object> agreeNumParam = new HashMap<String, Object>();
						
						HashMap<String, Object> createPay = createPayList.get(i);
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
					
					writer.write(new PayCreateTail(createPayList.size(), payPriceTotalSum).getTailStr());
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
	
	// 출금 날짜 영업일 계산 
	// 오늘 날짜와 출금날짜 (5,10,15,20,25) 사이의 최소 예정 출금날짜를 계산
	private List<String> calPayDay(String cym, String nym, List<String>nextMonthHollydayList, List<String> hollyDayList, Integer today, Integer lastDay) throws Exception {
		
			ArrayList<String> dateForCal = new ArrayList<String>();

			// 이번달 일자 배열
			for(int idx=0; idx<lastDay; idx++){
				if(idx<9){
					dateForCal.add(cym +"0"+ (idx +1));
				}else{
					dateForCal.add(cym + (idx +1));
				}
			}
			
			// 다음달 5일까지 일자 추가
			for(int jdx=0; jdx<4; jdx++){
				dateForCal.add(nym + "0" + (jdx +1));
			}
			
			// 주말 & 공휴일 제거 
			for(int xdx = 0; xdx < hollyDayList.size(); xdx++){
				String rmStr = hollyDayList.get(xdx);
				dateForCal.remove(rmStr);
			}
			
			// 주말 & 공휴일 제거된 날짜 중에 5, 10 , 15, 20, 25 일에 대한 출금 신청일 결정 로직
			// 1. [ 1~5 ] , [5 ~ 9 ] , [10 ~ 14 ] , [15 ~ 19 ] , [20 ~ 25 ] 각 구간 별 출금 예정일 체크
			List<Integer> cMhList = new ArrayList<Integer>(hollyDayList.size());
			
			for(String i : dateForCal){
				// 이번달의 주말 & 공휴일 제거한 날짜 리스트
				cMhList.add(Integer.parseInt(i));
			}
			String[] arrPayDay = new String[6];
			for(Integer j : cMhList){
				Integer condiStr02 = Integer.parseInt(cym + "05");
				Integer condiStr03 = Integer.parseInt(cym + "10");
				Integer condiStr04 = Integer.parseInt(cym + "15");
				Integer condiStr05 = Integer.parseInt(cym + "20");
				Integer condiStr06 = Integer.parseInt(cym + "25");
				Integer condiStr07 = Integer.parseInt(cym + "31");
				Integer condiStr08 = Integer.parseInt(nym + "01");
				Integer condiStr09 = Integer.parseInt(nym + "05");
				// 출금 가능 예정일(공휴일,주말을 뺀) 중에 출금 예정일과 가장 가까운 날짜를 배열에 삽입 
				if( j < condiStr02 ){
					// 5일
					arrPayDay[0] = j.toString();
				}else if( condiStr02 <= j && j < condiStr03){
					// 10일
					arrPayDay[1] = j.toString();
				}else if( condiStr03 <= j && j < condiStr04){
					// 15일
					arrPayDay[2] = j.toString();
				}else if( condiStr04 <= j && j < condiStr05){
					// 20일
					arrPayDay[3] = j.toString();
				}else if( condiStr05 <= j && j < condiStr06){
					// 25일
					arrPayDay[4] = j.toString();
				}else if( condiStr06 <= j && j <= condiStr07){
					// 25일 이후에는 다음달 1~5일 공휴일 체크후, 출금 신청 가능날짜가 없을 경우 사용한다.
					arrPayDay[5] = j.toString();
				}else{
					if(condiStr08 <= j && j <condiStr09){
						// 만약 존재한다면, 출금가능날이 존재하므로, 배열 index 5를 null 처리하고, 반복문을 종료한다.
						arrPayDay[5] = null;
						break;
					}
				}
			}

			//  출금 가능 예정일(공휴일,주말을 뺀)이 존재하지 않는 경우, 앞의 예정일의 날짜를 삽입.
			// 배열 Index[0] 은 존재하지 않을 경우에, 이전달 배열 Index[5] 에서 추가해주므로 0,5는 제외하고 삽입
			// 1,2,3,4 의 경우에만 해당되는 로직.
			for(int i=1; i<arrPayDay.length-1; i++){
				if(arrPayDay[i] == null){
						arrPayDay[i] = arrPayDay[i-1];
				}
			}
			
			// 완성된 배열을 Array List 에 담아서, Return;
			ArrayList<String> rArrList = new ArrayList<>();
			for(String val : arrPayDay){
				rArrList.add(val);
			}
			
			return rArrList;
		}
	

	// Pay LIST Index 계산
	static <T> List<Integer> indexOfAll(T obj, List<T> list) { 
		final List<Integer> indexList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				if (obj.equals(list.get(i))) {
					indexList.add(i);
				}
			}
		return indexList;	
	}
}

