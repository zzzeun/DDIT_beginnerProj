package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.MemberDAO;
import util.ScanUtil;
import util.View;

public class MemberService {
	// 싱글톤 패턴

	private static MemberService instance = null;

	private MemberService() {
	}

	public static MemberService getInstance() {
		if (instance == null)
			instance = new MemberService();
		return instance;
	}
	//

	MemberDAO dao = MemberDAO.getInstance();

	public int login() {
		if ((boolean) Controller.sessionStorage.get("login")) {
			System.out.println("이미 로그인되어있습니다.");
			return View.HOME;
		}
		System.out.println("-- 로그인 --");
		System.out.print("아이디 >> ");
		String id = ScanUtil.nextLine();
		System.out.print("비밀번호 >> ");
		String pass = ScanUtil.nextLine();
		Map<String, Object> row = dao.login(id, pass);
		if (row == null) {
			System.out.println("아이디가 없습니다.");
			return View.HOME;
		} else {
			Controller.sessionStorage.put("login", true);
			System.out.println(row.get("MEM_NAME") + "님 환영합니다!");
			return View.BOARD_LIST;
		}
	}

	
	
	
	
	
	// 회원가입
	public int signUp() {
		System.out.println(" < 회원가입   > ");
		String id = "";
		String pass="";
		// 아이디 중복체크
		while (true) {
			id = ScanUtil.nextLine("아이디  >> ");
			Map<String, Object> res = dao.getMemberInfo(id);
			if (res != null) {
				System.out.println("중복된 id입니다.");
			} else {
				if(id.length()>=5&&id.length()<=10) {   //길이제한
					break;
				}
				else {
					System.out.println("아이디 길이는 5~15자리까지 입력이 가능합니다");
					System.out.println("다시입력해주세요");
				}
			}
		}
		
		while (true) {
			pass = ScanUtil.nextLine("비밀번호 (특수기호 !@?를 1개이상 포함해주세요)>> ");
			if (pass.length()>=5 && pass.length()<=15) {
				int count =0;
				for(int i=0;i<pass.length();i++) {
					char ch=pass.charAt(i);
					if(ch=='@' || ch=='!' || ch=='?') {
						count++;
					}  					
				}
				if(count>=1) {
					break;
				}
				else {
					System.out.println("특수기호를 넣지 않았습니다 비밀번호를 다시입력해주세요");
				}
			} else {
				System.out.println("비밀번호 길이는 5~15자리까지 입력이가능합니다\n비밀번호를 다시입력해주세요");
			}
		}
		
		
		
		
		
		String name = ScanUtil.nextLine("이름>> ");
		String telNum = ScanUtil.nextLine("전화번호>> ");
		String em = ScanUtil.nextLine("이메일 >> ");
		
	
		
		
		
		
		
		
		
		
		
		
		
		List<Object> param = new ArrayList<>();
		param.add(id);
		param.add(pass);
		param.add(name);
		param.add(telNum);
		param.add(em);
		
		int result = dao.signUp(param);

		if (result > 0) {
			System.out.println("회원 가입을 성공하였습니다.");
			System.out.println("다시 로그인하십시오");
			return View.HOME;
		} else {
			System.out.println("회원 가입을 실패하였습니다.");
		}
		return View.MEMBER_LOGIN;
	}

	// 회원정보수정
	public int resign() {
		System.out.println("< 회원정보수정 >");
		String id =(String) Controller.sessionStorage.get("ID");
		String setString ="";
		String yesNo ="";

		
		Map<String, Object> res = dao.getMemberInfo(id);
			
		System.out.print("이름을 수정하겠습니까?(y/n) ");
		yesNo = ScanUtil.nextLine();
		if (yesNo.equalsIgnoreCase("y")) {
			String name = ScanUtil.nextLine("이름>> ");
			setString += " MEM_NAME = '" + name + "', ";
		}

		System.out.print("전화번호를 수정하겠습니까?(y/n) ");
		yesNo = ScanUtil.nextLine();
		if (yesNo.equalsIgnoreCase("y")) {
			String telNum = ScanUtil.nextLine("전화번호>> ");
			setString += " MEM_HP = '" + telNum + "', ";
		}
		
		System.out.print("비밀번호를 수정하겠습니까?(y/n) ");
		yesNo = ScanUtil.nextLine();
		if (yesNo.equalsIgnoreCase("y")) {
			String pass = ScanUtil.nextLine("비밀번호 >> ");
			setString += " MEM_PASS = '" + pass + "', ";
		}
		
		
		System.out.print("이메일을 수정하겠습니까?(y/n) ");
		yesNo = ScanUtil.nextLine();
		if (yesNo.equalsIgnoreCase("y")) {
			String em = ScanUtil.nextLine("이메일 >> ");
			setString += " MEM_EM = '" + em + "', ";
		}
		
		if(setString.length()!=0) {
			setString=setString.substring(0, setString.length()-2);
		
			List<Object> param = new ArrayList<Object>();
			param.add(id);
					
		int result = dao.resign(setString,param);
		
		if(result>0) {
			System.out.println();
			System.out.println("회원을 수정하였습니다.");
			System.out.println("다시 로그인해주세요");
		}else {
			System.out.println("회원수정에 실패했습니다");
		}
		}else {
			System.out.println();
			System.out.println("회원을 수정하지않았습니다");
			return View.MYPAGE;
		}
		return View.HOME;
	}

	
}