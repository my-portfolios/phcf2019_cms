package egovframework.phcf.popbill.message.service;

import java.util.Date;

import com.popbill.api.message.Message;

import egovframework.phcf.popbill.PopbillProperties;
import egovframework.phcf.popbill.PopbillVO;

public class PopbillMessageVO extends PopbillVO {
	/** 서비스 요청에 대한 접수 번호*/
	private String receiptNum;
	/** 전송요청 번호 : 팝빌이 접수 단위를 식별할 수 있도록 파트너가 할당한 식별번호
		영문 대소문자, 숫자, 특수문자('-','_')만 이용 가능*/
	private String requestNum;
	/** 메세지 서비스 종류 (SMS, LMS, MMS 등)*/
	private String serviceType;
	/** 발신자 번호 */
	private String sender = PopbillProperties.POPBILL_SENDER_NUM;
	/** 발신자명 */
	private String senderName = PopbillProperties.POPBILL_SENDER_NAME;
	/** 수신 번호 */
	private String receiver;
	/** 수신자명*/
	private String receiverName;
	/** 수신자 수*/
	private int numReceiver = 1;
	/** 메세지 제목 */
	private String subject;
	/** 메세지 내용 */
	private String content;
	/** 대량 전송 시의 수신자  정보 */
	private Message[] receivers;
	/** 이미지 파일 ID */
	private String fileId;
	/** 접수 일시 */
	private Date registDt;
	/** 예약 일시  (yyyyMMddHHmmss)*/
	private Date reserveDT;
	/** 광고메세지 전송 여부 */
	private boolean adsYn;
	
	
	public String getReceiptNum() {
		return receiptNum;
	}
	/** 접수 번호 설정*/
	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}
	public String getRequestNum() {
		return requestNum;
	}
	/** 요청 번호 설정*/
	public void setRequestNum(String requestNum) {
		this.requestNum = requestNum;
	}
	public String getServiceType() {
		return serviceType;
	}
	/** 문자 메세지 종류 설정 (SMS, LMS, MMS 등)*/
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getSender() {
		return sender;
	}
	/** 발송 번호 설정 */
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSenderName() {
		return senderName;
	}
	/** 발송자명 설정 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getReceiver() {
		return receiver;
	}
	/** 수신 번호 설정 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceiverName() {
		return receiverName;
	}
	/** 수신자명 설정 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public int getNumReceiver() {
		return numReceiver;
	}
	/** 수신자 수 설정(대량 전송 시) */
	public void setNumReceiver(int numReceiver) {
		this.numReceiver = numReceiver;
	}
	public String getSubject() {
		return subject;
	}
	/** 메세지 제목 설정 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	/** 메세지 내용 설정 */
	public void setContent(String content) {
		this.content = content;
	}
	public Message[] getReceivers() {
		return receivers;
	}
	/** 수신자 목록 설정*/
	public void setReceivers(Message[] receivers) {
		this.receivers = receivers;
	}
	public String getFileId() {
		return fileId;
	}
	/** 첨부 파일 ID 설정 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public Date getRegistDt() {
		return registDt;
	}
	/** 메세지 발송 요청 등록 일시 설정 */
	public void setRegistDt(Date registDt) {
		this.registDt = registDt;
	}
	public Date getReserveDT() {
		return reserveDT;
	}
	/** 발송 예약 일시  설정 */
	public void setReserveDT(Date reserveDT) {
		this.reserveDT = reserveDT;
	}
	public boolean isAdsYn() {
		return adsYn;
	}
	/** 광고 메세지 여부 설정 */
	public void setAdsYn(boolean adsYn) {
		this.adsYn = adsYn;
	}
	
	
}
