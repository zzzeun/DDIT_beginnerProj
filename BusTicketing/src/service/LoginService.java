package service;

import java.util.HashMap;
import java.util.Map;

import controller.Controller;
import dao.LoginDAO;
import util.ScanUtil;
import util.View;

public class LoginService {
	// 싱글톤 패턴을 만든다.

	
	
	
	private static LoginService instance = null;
	private LoginService() {} // default 생성자 메서드
	public static LoginService getInstance() {
		if(instance == null) 
			instance = new LoginService();
		return instance;
	}
	
	
	
	
	
	public static int loginCount = 0; // 로그인 횟수 제한
	
	
	
	
	
	// Dao를 부른다
	LoginDAO dao = LoginDAO.getInstance();
	int pageNo = 0;
	
	
	
	
	
	//로그인
	public int login(){
		String id = ScanUtil.nextLine("아이디 입력>>> ");
		String pass = ScanUtil.nextLine("비밀번호 입력>>> ");
		Map<String, Object> result = dao.login(id,pass); //LoginDao 호출
		
		
		if(result != null && result.get("MEM_ID").equals(id)){
			Controller.sessionStorage.put("login", true);
			Controller.sessionStorage.put("loginInfo", result); //result Map값(한 사람의 데이터 5개)이 들어감
			System.out.println();
			Controller.sessionStorage.put("ID",id);   //id 비번 저장
			
			
			System.out.println(result.get("MEM_NAME") + "님 로그인 되었습니다");
			Controller.sessionStorage.put("name",result.get("MEM_NAME"));
			pageNo = View.MEMBER;
			
		}else{
			System.out.println();
			System.out.println("회원정보가 일치하지않습니다 다시 로그인해주세요");
			pageNo = View.HOME;
		}
		return pageNo;
	}
	
	
	
	//아이디찾기
	public int idSearch(){
		System.out.println("<<  아이디찾기   >>");
		String em = ScanUtil.nextLine("이메일 입력>>> ");
		
		Map<String, Object> result = dao.ids(em); //LoginDao 호출
		
		if(result != null && result.get("MEM_EM").equals(em)){
			System.out.println(result.get("MEM_NAME")+"님이 찾으시는 아이디는 [ "+result.get("MEM_ID") + " ] 입니다");
			pageNo = View.HOME;
		}else{
			System.out.println();
			System.out.println("없는 회원정보입니다");
			pageNo = View.HOME;
		}
		return pageNo;
	}
	
	
	
	//비번찾기
	public int pwSearch(){
		System.out.println("<<  아이디찾기   >>");
		String id = ScanUtil.nextLine("아이디 입력>>> ");
		String tel = ScanUtil.nextLine("전화번호 입력>>> ");
		//9번 누르면 뒤에가는거 구현해야함 (끝나고)
		Map<String, Object> result = dao.pws(id,tel); //LoginDao 호출
		
		if(result != null && result.get("MEM_ID").equals(id) && result.get("MEM_HP").equals(tel)){
			System.out.println(result.get("MEM_NAME")+"님의 찾으시는 비밀번호는 [ "+result.get("MEM_PW") + " ] 입니다");
			pageNo = View.HOME;
		}else{
			System.out.println();
			System.out.println("아이디랑 전화번호를 다시확인해주세요");
			pageNo = View.HOME;
		}
		return pageNo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
