package egovframework.phcf.indiplus.service;

import java.util.HashMap;
import java.util.List;

public interface IndiplusService {
	public List<HashMap<String, Object>> selectRestDay(HashMap<String, Object> paramMap) throws Exception;
	public int selectRestDayCnt() throws Exception;
	public void insertRestDay(String date) throws Exception;
	public void updateRestDay(String seq, String date) throws Exception;
	public void deleteRestDay(String seq) throws Exception;
}
