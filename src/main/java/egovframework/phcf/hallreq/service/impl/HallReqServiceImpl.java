package egovframework.phcf.hallreq.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.phcf.hallreq.service.HallReqService;


/**
 * 대관 신청 관련 서비스 구현 클래스
 * @author	권혜진
 * @since	2019-09-26
 * */

@Service("HallReqService")
public class HallReqServiceImpl implements HallReqService {

	@Resource(name="hallReqDAO")
	private HallReqDAO dao;

}
