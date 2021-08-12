package egovframework.phcf.ticketLink.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.uss.umt.service.EgovMberManageService;
import egovframework.com.uss.umt.service.MberManageVO;
import egovframework.com.uss.umt.service.UserDefaultVO;
import egovframework.phcf.ticketLink.service.TicketLinkMberVO;
import egovframework.phcf.ticketLink.service.TicketLinkProperties;
import egovframework.phcf.ticketLink.service.TicketLinkService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import kr.co.ticketlink.common.cipher.Crypto;

@Service("TicketLinkService")
public class TicketLinkServiceImpl implements TicketLinkService {
	
	/** TicketLinkMberDAO */
	@Resource(name="TicketLinkMberDAO")
	private TicketLinkMberDAO ticketLinkMberDAO;
	
	/** mberManageService */
	@Resource(name="mberManageService")
	private EgovMberManageService egovMberManageService;
	
	/** ticketLink Crypto */
	@Autowired @Qualifier("aes256Crypto")
    Crypto crypto;
	
	
	
	@Override
	public void insertTklinkMber(TicketLinkMberVO mberVO) throws Exception {
		ticketLinkMberDAO.insertTklinkMber(mberVO);
	}
	
	@Override
	public void updateTklinkMber(TicketLinkMberVO mberVO) throws Exception {
		ticketLinkMberDAO.updateTklinkMber(mberVO);
	}
	
	/**
	 * @return partnerNo, memberQualificationCode, memberId, <p>
	 * memberName, membershipNo, cellPhoneNo, email를 가진 HashMap
	 * 
	 * 
	 */
	@Override
	public HashMap<String, Object> getMemberInfoForTklink(String id) throws Exception {
		UserDefaultVO userSearchVO = new UserDefaultVO();
	    userSearchVO.setSearchKeyword(id);
	    userSearchVO.setFirstIndex(0);
	    userSearchVO.setRecordCountPerPage(20);
	   
		
	    HashMap<String, Object> memberHashMap = new HashMap<>();
	    List<?> voList = egovMberManageService.selectMberList(userSearchVO);
	    EgovMap vo = (EgovMap)voList.get(0);
	    
	    String esntl_id = vo.get("uniqId").toString();
	    esntl_id = esntl_id.replaceAll("[^0-9]", "");
	    
	    vo.put("uniqId", esntl_id);
	    System.out.println("user info === " + vo);
	    
	    String memberQualificationCode = vo.get("membershipType").equals("N") ?
	    		TicketLinkProperties.TKLINK_MEMBER_QUALIFICATION_CODE_FREE
	    		: TicketLinkProperties.TKLINK_MEMBER_QUALIFICATION_CODE_MEMBERSHIP;
	    
	    
	    memberHashMap.put("partnerNo", EgovProperties.getProperty("ticketlink.partnerNo"));
	    memberHashMap.put("memberQualificationCode", memberQualificationCode);
	    memberHashMap.put("memberId", vo.get("userId"));
	    memberHashMap.put("memberName", vo.get("userNm").toString());
	    memberHashMap.put("membershipNo", vo.get("uniqId").toString());
	    memberHashMap.put("cellPhoneNo", vo.get("moblphonNo").toString());
	    memberHashMap.put("email", vo.get("emailAdres"));

	    
	    return memberHashMap;
	}
	/**
	 * ticket link API를 위한
	 * 암호화 데이터 획득
	 * 
	 * 
	 * @param id 회원 아이디
	 * @return memberHashMap 암호화 정보
	 */
	@Override
	public HashMap<String, Object> getCryptoInfoForTklink(String id) throws Exception {

	    HashMap<String, Object> memberHashMap = new HashMap<>();
//	    List<?> voList = egovMberManageService.selectMberList(userSearchVO);
//	    EgovMap vo = (EgovMap)voList.get(0);
//	    
//	    String esntl_id = vo.get("uniqId").toString();
//	    esntl_id = esntl_id.replaceAll("[^0-9]", "");
//	    
//	    vo.put("uniqId", esntl_id);
//	    System.out.println("user info === " + vo);
//	    
//	    String memberQualificationCode = vo.get("membershipType").equals("N") ?
//	    		TicketLinkProperties.TKLINK_MEMBER_QUALIFICATION_CODE_FREE
//	    		: TicketLinkProperties.TKLINK_MEMBER_QUALIFICATION_CODE_MEMBERSHIP;
//	    
//	    
//	    memberHashMap.put("partnerNo", EgovProperties.getProperty("ticketlink.partnerNo"));
//	    memberHashMap.put("memberQualificationCode", memberQualificationCode);
//	    memberHashMap.put("memberId", vo.get("userId"));
//	    memberHashMap.put("memberIdEnc", crypto.encrypt(vo.get("userId").toString()));
//	    memberHashMap.put("memberName", crypto.encrypt(vo.get("userNm").toString()));
//	    memberHashMap.put("membershipNo", crypto.encrypt(vo.get("uniqId").toString()));
//	    memberHashMap.put("cellPhoneNo", crypto.encrypt(vo.get("moblphonNo").toString()));
//	    memberHashMap.put("email", vo.get("emailAdres"));
	    
	    memberHashMap = getMemberInfoForTklink(id);
	    
	    memberHashMap.put("memberIdEnc", crypto.encrypt(memberHashMap.get("memberId").toString()));
	    memberHashMap.put("memberName", crypto.encrypt(memberHashMap.get("memberName").toString()));
	    memberHashMap.put("membershipNo", crypto.encrypt(memberHashMap.get("membershipNo").toString()));
	    memberHashMap.put("cellPhoneNo", crypto.encrypt(memberHashMap.get("cellPhoneNo").toString()));
	    
	    return memberHashMap;
	}
	
	/**
	 *  ticketlink 회원 가입 
	 *  
	 * @param paramMap 
	 *  <p> partnerNo, memberQualificationCode, membershipNo(암호화), memberId, 
	 * 		<p>memberName(암호화), cellPhoneNo(암호화), birthday, email <p> 를 key 값으로 가지고 있어야함.
	 * 
	 * */
	@Override
	public int joinTkLink(HashMap<String, Object> paramMap) throws Exception {
		String tklinkJoinUrl = TicketLinkProperties.TKLINK_JOIN_URI;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//		headers.setContentType(MediaType.APPLICATION_XML);
		
		JSONObject postJsonObject = new JSONObject(paramMap);
		RestTemplate restTemplate = new RestTemplate();
		
		HttpEntity<String> requestEntity = new HttpEntity<>(postJsonObject.toString(), headers);
		
		String resultAsJsonStr = 
			      restTemplate.postForObject(tklinkJoinUrl, requestEntity, String.class);
		System.out.println(tklinkJoinUrl + " jsonStr Result === " + resultAsJsonStr);
		
		final ObjectMapper objectMapper = new ObjectMapper();
		JsonNode resultJsonNode = null;
		
		try {
			resultJsonNode = objectMapper.readTree(resultAsJsonStr);
		} catch (JsonProcessingException e) {
			System.out.println("objectMapper.readTree() error: " + e.getMessage());
			e.printStackTrace();
			return TicketLinkProperties.TKLINK_RESPONSE_DEFAULT_CODE;
		} catch (IOException e) {
			e.printStackTrace();
			return TicketLinkProperties.TKLINK_RESPONSE_DEFAULT_CODE;
		}
		
		int resultCode = resultJsonNode.get("result").get("code").asInt(TicketLinkProperties.TKLINK_RESPONSE_DEFAULT_CODE);
		if(resultCode == TicketLinkProperties.TKLINK_RESPONSE_JOIN_SUCCESS_CODE) {
			JsonNode userDataJsonNode = getUserDetailTkLink(paramMap).get("data");
			
			TicketLinkMberVO tkMberVO = new TicketLinkMberVO();
			tkMberVO.setMemberId(userDataJsonNode.get("memberId").asText());
			tkMberVO.setMberQualCode(userDataJsonNode.get("memberQualificationCode").asText());
			tkMberVO.setMembershipNo(userDataJsonNode.get("membershipNo").asText());
			tkMberVO.setMemberName(userDataJsonNode.get("memberName").asText());
			tkMberVO.setBirthday(userDataJsonNode.get("birthday").asText());
			tkMberVO.setCellPhoneNo(userDataJsonNode.get("cellPhoneNo").asText());
			tkMberVO.setEmail(userDataJsonNode.get("email").asText());
			insertTklinkMber(tkMberVO);
		}
		
		return resultCode;
	}
	
	/**
	 *  ticketlink 회원 가입 여부 확인(회원 조회)
	 *  
	 * @param paramMap  <p> partnerNo, memberId를 key 값으로 가지고 있어야함.
	 * 
	 * */
	@Override
	public int checkTkLinkJoin(HashMap<String, Object> paramMap) throws Exception {
		JsonNode resultJsonNode = getUserDetailTkLink(paramMap);
		if(resultJsonNode == null) 
			return TicketLinkProperties.TKLINK_RESPONSE_DEFAULT_CODE;
		else 
			return resultJsonNode.get("result").get("code").asInt(TicketLinkProperties.TKLINK_RESPONSE_DEFAULT_CODE);
	}
	
	
	
	/**
	 *  ticketlink 회원 조회
	 *  
	 * @param paramMap  <p> partnerNo, memberId를 key 값으로 가지고 있어야함.
	 * 
	 * */
	@Override
	public JsonNode getUserDetailTkLink(HashMap<String, Object> paramMap) throws Exception {
		String tklinkUserDetailUrl = TicketLinkProperties.TKLINK_MEMBER_DETAIL_URI;
		
		String parterNo = paramMap.get("partnerNo").toString();
		String urlEncodedPartnerNo = URLEncoder.encode(parterNo, "UTF-8");
		
		String urlEncodedEncryptedPartnerNo = TicketLinkProperties.TKLINK_PARTNER_NO_URL_ENCODED;
		
		String memberIdEnc = crypto.encrypt(paramMap.get("memberId").toString());
//		memberIdEnc = paramMap.get("memberIdEnc").toString();
		String urlEncodedMemberIdEnc = URLEncoder.encode(memberIdEnc, "UTF-8");

		
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("partnerNo", urlEncodedPartnerNo);
		requestParams.add("memberId", urlEncodedMemberIdEnc);
		
		
		UriComponents uriComponentAlternate = UriComponentsBuilder.fromHttpUrl(tklinkUserDetailUrl).queryParams(requestParams)
				.build()
				.encode("UTF-8");
		
		RestTemplate restTemplate = new RestTemplate();
		URI uri =  new URI(tklinkUserDetailUrl);
		
		try {
			uri = new URI(tklinkUserDetailUrl + "?partnerNo=" + urlEncodedPartnerNo + "&memberId=" + urlEncodedMemberIdEnc);
		} catch (URISyntaxException e) {
			uri = uriComponentAlternate.toUri();
			e.printStackTrace();
		}
		
		String userDetailResultAsJsonStr = 
				restTemplate.getForObject(uri, String.class);
		System.out.println("userDetailResultAsJsonStr==" + userDetailResultAsJsonStr);
		
		final ObjectMapper objectMapper = new ObjectMapper();
		JsonNode resultJsonNode = null;
		
		try {
			resultJsonNode = objectMapper.readTree(userDetailResultAsJsonStr);
		} catch (JsonProcessingException e) {
			System.out.println("objectMapper.readTree() error: " + e.getMessage());
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		return resultJsonNode;
		
	}
	
	@Override
	public JsonNode getReservePageTkLink(HashMap<String, Object> paramMap) throws Exception {
		String productReserveUrl = TicketLinkProperties.TKLINK_PRODUCT_RESERVE_URI;
		
		String urlEncodedEncryptedPartnerNo = EgovProperties.getProperty("ticketlink.partnerNo.urlencoded");
		String memberId = paramMap.get("memberId").toString();
		
		String memberIdEnc = crypto.encrypt(memberId);
		
		String urlEncodedMemberIdEnc = URLEncoder.encode(memberIdEnc, "UTF-8");
		
		URI uri = new URI(productReserveUrl + "?partnerNo=" + urlEncodedEncryptedPartnerNo + "&memberIdEnc=" + urlEncodedMemberIdEnc);
		RestTemplate restTemplate = new RestTemplate();
		String reservePageResultAsStr = restTemplate.getForObject(uri, String.class);
		
		System.out.println("reservePageResultAsStr==\n" + reservePageResultAsStr);
		
		return null;
	}
	
	/**
	 * @param paramMap 필수 key: partnerNo, memberId
	 * 
	 * 
	 */
	@Override
	public int modifyMemberDetailTkLink(HashMap<String, Object> paramMap) throws Exception {
		String tklinkModifyUrl = TicketLinkProperties.TKLINK_MEMBER_MODIFY_URI;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//		headers.setContentType(MediaType.APPLICATION_XML);
		
		paramMap.put("status", "U");
		
		if(paramMap.containsKey("membershipNo")) {
			String membershipNo = paramMap.get("membershipNo").toString();
			membershipNo = crypto.encrypt(membershipNo);
			paramMap.put("membershipNo", membershipNo);
		}
		
		if(paramMap.containsKey("memberName")) {
			String memberName = paramMap.get("memberName").toString();
			memberName = crypto.encrypt(memberName);
			paramMap.put("memberName", memberName);
		}
		
		if(paramMap.containsKey("cellPhoneNo")) {
			String cellPhoneNo = paramMap.get("cellPhoneNo").toString();
			cellPhoneNo = crypto.encrypt(cellPhoneNo);
			paramMap.put("cellPhoneNo", cellPhoneNo);
		}
		
		JSONObject postJsonObject = new JSONObject(paramMap);
		RestTemplate restTemplate = new RestTemplate();
		
		HttpEntity<String> requestEntity = new HttpEntity<>(postJsonObject.toString(), headers);
		
		String resultAsJsonStr = 
			      restTemplate.postForObject(tklinkModifyUrl, requestEntity, String.class);
		System.out.println(tklinkModifyUrl + " jsonStr Result === " + resultAsJsonStr);
		
		final ObjectMapper objectMapper = new ObjectMapper();
		JsonNode resultJsonNode = null;
		
		try {
			resultJsonNode = objectMapper.readTree(resultAsJsonStr);
		} catch (JsonProcessingException e) {
			System.out.println("objectMapper.readTree() error: " + e.getMessage());
			e.printStackTrace();
			return TicketLinkProperties.TKLINK_RESPONSE_DEFAULT_CODE;
		} catch (IOException e) {
			e.printStackTrace();
			return TicketLinkProperties.TKLINK_RESPONSE_DEFAULT_CODE;
		}
		
		int resultCode = resultJsonNode.get("result").get("code").asInt(TicketLinkProperties.TKLINK_RESPONSE_DEFAULT_CODE);
		if(resultCode == TicketLinkProperties.TKLINK_RESPONSE_MODIFY_SUCCESS_CODE) {
			JsonNode userDataJsonNode = getUserDetailTkLink(paramMap).get("data");
			
			TicketLinkMberVO tkMberVO = new TicketLinkMberVO();
			tkMberVO.setMemberId(userDataJsonNode.get("memberId").asText());
			tkMberVO.setMberQualCode(userDataJsonNode.get("memberQualificationCode").asText());
			tkMberVO.setMembershipNo(userDataJsonNode.get("membershipNo").asText());
			tkMberVO.setMemberName(userDataJsonNode.get("memberName").asText());
			tkMberVO.setBirthday(userDataJsonNode.get("birthday").asText());
			tkMberVO.setCellPhoneNo(userDataJsonNode.get("cellPhoneNo").asText());
			tkMberVO.setEmail(userDataJsonNode.get("email").asText());
			updateTklinkMber(tkMberVO);
		}
		
		return resultCode;
	}
	
	/**
	 * 
	 * 회원 정보 업데이트 후 호출할 메서드
	 * @param mberManageVO 포함되어야 하는 필드: mberId, membershipType, mberNm, moblphonNo, mberEmailAdres
	 */
	@Override 
	public int checkJoinThenModifyMemberDetailTkLink(MberManageVO mberManageVO) throws Exception {
		HashMap<String, Object> tkLinkMap = new HashMap<>();
		
		tkLinkMap.put("partnerNo", TicketLinkProperties.TKLINK_PARTNER_NO);
		tkLinkMap.put("memberId", mberManageVO.getMberId());
		
		String membershipType = mberManageVO.getMembershipType();
		String memberQualificationCode = TicketLinkProperties.TKLINK_MEMBER_QUALIFICATION_CODE_FREE;
		if(membershipType != null && !membershipType.equals("")) {
			memberQualificationCode = membershipType.equals("N") ?
					TicketLinkProperties.TKLINK_MEMBER_QUALIFICATION_CODE_FREE
					: TicketLinkProperties.TKLINK_MEMBER_QUALIFICATION_CODE_MEMBERSHIP;
		}
		
		tkLinkMap.put("memberQualificationCode", memberQualificationCode);
		
		tkLinkMap.put("memberName", mberManageVO.getMberNm());
		tkLinkMap.put("cellPhoneNo", mberManageVO.getMoblphonNo());
		tkLinkMap.put("email", mberManageVO.getMberEmailAdres());
		
		
		return checkJoinThenModifyMemberDetailTkLink(tkLinkMap);
	}
	
	/**
	 * 회원 정보 업데이트 후 호출할 메서드
	 * @param paramMap 포함되어야 하는 키: mberId, membershipType, mberNm, moblphonNo, mberEmailAdres
	 */
	@Override
	public int checkJoinThenModifyMemberDetailTkLink(HashMap<String, Object> paramMap) throws Exception {
		paramMap.put("partnerNo", TicketLinkProperties.TKLINK_PARTNER_NO);
		
		/* <회원가입 API 이용>
		 * 1. 멤버 회원조회  api를 호출한다. 
		 * 2-1. 조회가 되면 업데이트 시키고
		 * 2-2. 조회가 안되면 업데이트 시키지 않는다. 
		 * 
		 */
		int checkJoinResultCode = checkTkLinkJoin(paramMap);
		if(checkJoinResultCode == TicketLinkProperties.TKLINK_RESPONSE_MEMBER_DETAIL_SUCCESS_CODE) {
			modifyMemberDetailTkLink(paramMap);
		} 
		
		return checkJoinResultCode;
	}
	
	
	
}
