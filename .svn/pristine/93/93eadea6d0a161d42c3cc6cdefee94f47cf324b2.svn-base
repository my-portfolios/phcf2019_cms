package egovframework.phcf.scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

public abstract class TheBillCronQuartz extends EgovAbstractServiceImpl {
	
	String theBillHome = EgovProperties.getProperty("theBillHome");
	
	protected boolean sendTheBillFile(File sendFile) {
		
		// 회원 등록의 경우 매일 오전 12시에 수행 한다.
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		
		// OS 정보 확인...
		String osName = System.getProperty("os.name").toLowerCase();
		
		System.out.println("== osName : " + osName);
		
		String batchFile = null;
		StringBuilder sb = new StringBuilder();
		
		String[] cmd = null;
		
		if(osName.indexOf("win") >= 0) {
			
			batchFile = "batch_send.bat";
			
			// CMS send batch 파일을 실행시킨다.....
			File batchSendBat = new File(theBillHome, "bin" + File.separator + batchFile);
			if(!batchSendBat.exists()) { throw new NullPointerException("== batch를 실행 할 파일이 없습니다. : current time = " + sdf.format(System.currentTimeMillis())); }
			
			sb.append(batchSendBat.getAbsolutePath());
			
			cmd = new String[]{"cmd", "/c", "start", sb.toString()};
			
		} else if(osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0 || osName.indexOf("aix") >= 0) {
			
			batchFile = "batch_send.sh";
			
			// CMS send batch 파일을 실행시킨다.....
			File batchSendBat = new File(theBillHome, "bin" + File.separator + batchFile);
			if(!batchSendBat.exists()) { throw new NullPointerException("== batch를 실행 할 파일이 없습니다. : current time = " + sdf.format(System.currentTimeMillis())); }
			
			sb.append(batchSendBat.getAbsolutePath());
			
			cmd = new String[]{"/bin/sh", "-c", sb.toString()};
		}
		
		
		Process p = null;
		BufferedReader br = null;
		
		try {
			
			System.out.println("== batchFile cmd : " + StringUtils.join(cmd, ","));
			
			p = Runtime.getRuntime().exec(cmd);
		    
			System.out.println("== stringbuffer 생성");
			
			StringBuffer stdMsg = new StringBuffer();

			System.out.println("== ProcessOutputThread 생성");
			
			// 스레드로 inputStream 버퍼 비우기
			ProcessOutputThread o = new ProcessOutputThread(p.getInputStream(), stdMsg);

			System.out.println("== ProcessOutputThread start 실행");
			
			o.start();
			
			System.out.println("== err StringBuffer 생성");

			StringBuffer errMsg = new StringBuffer();

			// 스레드로 errorStream 버퍼 비우기
			
			System.out.println("== 스레드로 errorStream 버퍼 생성");
			
			o = new ProcessOutputThread(p.getErrorStream(), errMsg);
			
			System.out.println("== 스레드로 errorStream 버퍼 비우기 시작");
			o.start();

			// 수행종료시까지 대기
			System.out.println("== 수행종료시까지 대기 : " + sdf.format(System.currentTimeMillis()));
			p.waitFor();

		    System.out.println("== batchSendBat 종료 : current time = " + sdf.format(System.currentTimeMillis()));
		    
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
			
		} finally {
			
			p.destroy();

			sb.delete(0, sb.length());
			sb.setLength(0);
			sb = null;
		}
		
		// CMS send 파일 전송 완료 후 send_ok 폴더를 확인해 정보가 전송 완료 되었다면 
		// tb_support_cms 테이블의 user_send_yn을 변경 시켜 준다.
		File sendOkDir = new File(theBillHome, "data" + File.separator + "cms" + File.separator + "send_ok");
		// 디렉토리 검사는 안해도 된다.. 없다면 만든다...
		if(!sendOkDir.exists()) { sendOkDir.mkdir(); }
		
		File sendOkMem = new File(sendOkDir, sendFile.getName());
		if(!sendOkMem.exists()) { 
			 System.out.println("== batch send 실패 : current time = " + sdf.format(System.currentTimeMillis()));
			 return false;
		}
		
		return true;
	}
	
	protected void recvTheBillFile() {
		
		// 회원 등록의 경우 매일 오전 12시에 수행 한다.
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		
		// OS 정보 확인...
		String osName = System.getProperty("os.name").toLowerCase();
		
		System.out.println("== osName : " + osName);
		
		String batchFile = null;
		StringBuilder sb = new StringBuilder();
		
		String[] cmd = null;
		
		if(osName.indexOf("win") >= 0) {
			
			batchFile = "batch_recv.bat";
			
			// CMS send batch 파일을 실행시킨다.....
			File batchSendBat = new File(theBillHome, "bin" + File.separator + batchFile);
			if(!batchSendBat.exists()) { throw new NullPointerException("== batch를 실행 할 파일이 없습니다. : current time = " + sdf.format(System.currentTimeMillis())); }
			
			sb.append(batchSendBat.getAbsolutePath());
			
			cmd = new String[]{"cmd", "/c", "start", sb.toString()};
			
		} else if(osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0 || osName.indexOf("aix") >= 0) {
			
			batchFile = "batch_recv.sh";
			
			// CMS send batch 파일을 실행시킨다.....
			File batchSendBat = new File(theBillHome, "bin" + File.separator + batchFile);
			if(!batchSendBat.exists()) { throw new NullPointerException("== batch를 실행 할 파일이 없습니다. : current time = " + sdf.format(System.currentTimeMillis())); }
			
			sb.append(batchSendBat.getAbsolutePath());
			
			cmd = new String[]{"/bin/sh", "-c", sb.toString()};
		}
		
		
		 
		Process p = null;
		BufferedReader br = null;
		
		try {
			
			System.out.println("== batchFile cmd : " + StringUtils.join(cmd, ","));
			
			p = Runtime.getRuntime().exec(cmd);
		    
			System.out.println("== stringbuffer 생성");
			
			StringBuffer stdMsg = new StringBuffer();

			System.out.println("== ProcessOutputThread 생성");
			
			// 스레드로 inputStream 버퍼 비우기
			ProcessOutputThread o = new ProcessOutputThread(p.getInputStream(), stdMsg);

			System.out.println("== ProcessOutputThread start 실행");
			
			o.start();
			
			System.out.println("== err StringBuffer 생성");

			StringBuffer errMsg = new StringBuffer();

			// 스레드로 errorStream 버퍼 비우기
			System.out.println("== 스레드로 errorStream 버퍼 생성");
			
			o = new ProcessOutputThread(p.getErrorStream(), errMsg);
			
			System.out.println("== 스레드로 errorStream 버퍼 비우기 시작");
			o.start();

			// 수행종료시까지 대기
			System.out.println("== 수행종료시까지 대기 : " + sdf.format(System.currentTimeMillis()));
			
			p.waitFor();

			System.out.println("== batchRecvBat 종료 : current time = " + sdf.format(System.currentTimeMillis()));
		    
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
			p.destroy();

			sb.delete(0, sb.length());
			sb.setLength(0);
			sb = null;

		}	
	}
	
	// 스레드로 inputStream 버퍼 비우기 위한 클래스 생성
	class ProcessOutputThread extends Thread {

		private InputStream is;
		private StringBuffer msg;

		public ProcessOutputThread(InputStream is, StringBuffer msg) {

			this.is = is;
			this.msg = msg;
		}

		public void run() {

			try {
				msg.append (getStreamString (is));
			} catch (Exception e) {
				e.printStackTrace ();
			} finally {
				if (is != null) {
					try {
						is.close ();
					} catch (IOException e) {
						e.printStackTrace ();
					}
				}
			}
		}

		private String getStreamString(InputStream is) {

			BufferedReader reader = null;

			try {

				reader = new BufferedReader (new InputStreamReader (is));
				StringBuffer out = new StringBuffer ();
				String stdLine;

				while ((stdLine = reader.readLine ()) != null) {
					out.append (stdLine);
				}

				return out.toString ();

			} catch (Exception e) {
				e.printStackTrace ();
				return "";
			} finally {

				if (reader != null) {
					try {
						reader.close ();
					} catch (IOException e) {
						e.printStackTrace ();
					}
				}
			}

		}

	}
}
