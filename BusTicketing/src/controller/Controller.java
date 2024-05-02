package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import service.BoardService;
import service.BusService;
import service.FreeBoardService;
import service.LoginService;
import service.MemberService;
import service.TestBoardService;
import util.ScanUtil;
import util.View;

public class Controller {
	// sessionStorage선언
	static public Map<String, Object> sessionStorage = new HashMap<>();
	// 필요한 서비스 클래스 객체 생성
	// getInstance()로 만들어지는 것은 싱글톤 패턴
	MemberService memberService = MemberService.getInstance();
	LoginService loginService = LoginService.getInstance();
	BoardService boardService = BoardService.getInstance();
	FreeBoardService freeboardService = FreeBoardService.getInstance();
	TestBoardService testBoardService = TestBoardService.getInstance();
	BusService busService = BusService.getInstance();
	
	
	
	
	
	public static void main(String[] args) {
		new Controller().start(); // static을 쓰지않기위해 자기 자신 호출
		//start하나로 실행
	}



	private void start() {
		
		
		
		
		// login 키값에 false를 넣고 loginInfo 키값에 null을 넣음
		sessionStorage.put("login", false); // false: 로그인 안됨
		sessionStorage.put("loginInfo", null);
		int view = View.HOME; // view = 1
		while (true) {
			switch (view) {
			//홈
			case View.HOME:
				view = home();
				break;
			//아이디찾기
			case View.MEMBER_IDSER:
				view = loginService.idSearch(); //loginService클래스의 idSearch로감
				break;
			//비번찾기
			case View.MEMBER_PWSER:
				view = loginService.pwSearch(); //MEMBER_PWSER은 loginService클래스의 pwSearch로 감
				break;
			//버스 조회
			case View.BUSJH:
				view = busjh();	//BUSJH는 Controller클래스의 busjh로 가게됨
				break;
			//마이페이지
			case View.MYPAGE:
				view = mypage(); //View.MYPAGE는 mypage로 감
				break;
			//메인 메뉴
			case View.MEMBER:
				view = memMenu();  //View.MEMBER는 memMenu로 감
				break;
			//회원찾기
			case View.MEMBER_SEARCH: //View.MEMBER_SEARCH는 memSearch로 가게됌
				view = memSearch();  
				break;
			
			//그리고 보시면 memSearch가 있고 어떤거는 loginService.login으로 되있는데
			//앞에 아무것도 안붙인 memSearch는 지금 현재있는 클래스인 Controller에 있는거고
			//앞에 뭔가 붙어있는 loginService.login은 앞에 LoginService에 있음
			//ex) memberService.signUp은 MemberService에 있음
				
				
			//회원로그인
			case View.MEMBER_LOGIN: 
				view = loginService.login();
				break;
			// 회원등록
			case View.MEMBER_SIGNUP:
				view = memberService.signUp();
				break;

			// 회원 정보 수정
			case View.MEMBER_RESIGN:
				view = memberService.resign();
				break;
			//예매한 목록
			case View.MEMBER_RSV:
				view = busService.MyResList();
				break;
			
			case View.BUS_CHECK:
				view = busService.busCheck();
				break;
			case View.BUS_PRICE:
				view = busService.priceCheck();
				break;
			case View.BUS_PAY:
				view = busService.realpay();
				break;

			case View.TEST_LIST_S:
				view = testBoardService.s_list();
				break;

			case View.BUS_S:
				view = busService.buslist_s();
				break;

			case View.BOARD_LIST:
				view = boardService.list();
				break;

			case View.FREE_LIST:
				view = freeboardService.list();
				break;

			case View.BUS_TICK:
				view = busticketing();
				break;

			case View.TEST_LIST:
				view = testBoardService.list();
				break;

			case View.BUS_RESERVATION:
				view = busService.PayOfFinal();
				break;

			}
		}
	}



	// *****************************처음 홈 화면 시작페이지
	private int home() {
		System.out.println();
		System.out.println("____________________대덕복합터미널______________________");
		System.out.println();
		System.out.println("   1.로그인                            2.회원가입                     3.회원찾기");
		System.out.println();
		System.out.println("____________________________________________________");
		System.out.print("번호 입력 >> ");

		switch (ScanUtil.nextInt()) { // 정수 한 개 입력
		case 1:	//1번 누르시 View.MEMBER_LOGIN으로
			return View.MEMBER_LOGIN; // return을 썼기 때문에 break를 안 써도 됨
		case 2:	//2번 누르시 View.MEMBER_SIGNUP으로
			return View.MEMBER_SIGNUP;
		case 3:	//3번 누르시 View.MEMBER_SEARCH으로
			return View.MEMBER_SEARCH;
		default:	//위에 조건이 없으면 메시지 출력 및 다시 홈으로이동
			System.out.println("잘못 누르셨습니다");
			return View.HOME;
		}

	}

	//***********************************메뉴
	public int memMenu() {
		System.out.println();
		System.out.println();
		System.out.println
		();
		//sessionStorage "name"에 저장된 로그인하신 분의 이름을 출력
		System.out.println("_____________<" + Controller.sessionStorage.get("name") + ">고객님 환영합니다 _____________");
		System.out.println();
		System.out.println("    1.버스예매             2.시간표조회          3.마이페이지   ");
		System.out.println();
		System.out.println("                                 (로그아웃: 9번)  ");
		System.out.println("____________________________________________");
		System.out.print("번호입력>> ");
		switch (ScanUtil.nextInt()) {
		case 1:	//1번 누르시 View.BUS_TICK
			return View.BUS_TICK;
		case 2:	//2번 누르시 View.TEST_LIST
			return View.TEST_LIST;
		case 3:	//3번 누르시 View.MYPAGE
			return View.MYPAGE;
		case 9:	//9번 누르시 View.HOME
			return View.HOME;
		default:	//조건에 없는 것을 입력시 View.MEMBER
			return View.MEMBER;
		}
	}

	// *****************회원찾기
	public int memSearch() {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("__________________회원찾기___________________");
		System.out.println();
		System.out.println("     1.아이디찾기                          2.비밀번호찾기  ");
		System.out.println();
		System.out.println("                        (뒤로가기: 9번)  ");
		System.out.println("__________________________________________");
		System.out.print("번호입력>> ");
		switch (ScanUtil.nextInt()) {
		case 1:	//1번 누르시 View.MEMBER_IDSER로 이동 
			return View.MEMBER_IDSER;
		case 2:	//2번 누르시 View.MEMBER_PWSER로 이동 
			return View.MEMBER_PWSER;
		case 9:	//9번 누르시 View.HOME로 이동 
			return View.HOME;
		default:	//위에 조건에 없는 번호 누르시 View.MEMBER로 이동
			return View.MEMBER;
		}
	}

	
	
	// ****************************마이페이지
	public int mypage() {
		System.out.println();
		System.out.println("_____________ 마이페이지 _______________");
		System.out.println();
		System.out.println("      1.예매조회               2.회원수정             ");
		System.out.println();
		System.out.println("                         (9번누르면 뒤로)");
		System.out.println("_____________________________________");
		System.out.print("번호입력>> ");
		switch (ScanUtil.nextInt()) {
		case 1:	//1번 입력시 View.MEMBER_RSV로 이동
			return View.MEMBER_RSV;
		case 2:	//2번 입력시 View.MEMBER_RESIGN으로 이동
			return View.MEMBER_RESIGN;
		case 9:	//9번 입력시 View.MEMBER로 이동
			return View.MEMBER;
		default:	//위에 없는 번호를 입력시 마이페이지로 다시 이동 (즉 아무것도 하지않음)
			return View.MYPAGE;
		}
	}

	
	
	// *********************예매목록 조회
	public int busticketing() {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("_______________ 예매 _________________");
		System.out.println("         (도착지를 선택하여주십시요)");
		System.out.println();
		System.out.println("1.서울    2.인천    3.대구    4.광주    5.부산   6.울산     ");
		System.out.println();
		System.out.println("                            (뒤로가기 9번)");
		System.out.println("_____________________________________");
		System.out.print("입력>> ");
		switch (ScanUtil.nextInt()) {
		case 1:	 //1이입력된경우 DT sessionStorage에 "서울" 저장
			Controller.sessionStorage.put("DT", "서울");
			return View.BUS_S;		//저장후 View.BUS_S로 이동
		case 2:	//2가 입력된경우 DT sessionStorage에 "인천" 저장
			Controller.sessionStorage.put("DT", "인천");
			return View.BUS_S;
		case 3:	//3이입력된경우 DT sessionStorage에 "대구" 저장
			Controller.sessionStorage.put("DT", "대구");
			return View.BUS_S;
		case 4:	//4가 입력된경우 DT sessionStorage에 "광주" 저장
			Controller.sessionStorage.put("DT", "광주");
			return View.BUS_S;
		case 5:	//5가 입력된경우 DT sessionStorage에 "부산" 저장
			Controller.sessionStorage.put("DT", "부산");
			return View.BUS_S;
		case 6:	//6이 입력된경우 DT sessionStorage에 "울산" 저장
			Controller.sessionStorage.put("DT", "울산");
			return View.BUS_S;
		default:	//아무것도 입력하지않으면 View.MEMBER로 이동
			return View.MEMBER;
		}
	}

	//
	
	
	
	
	//*********************************조회 페이지
	public int busjh() {
		System.out.println();
		System.out.println("_________________ 조회__________________");
		System.out.println("        (조회할 도착지를 선택하여주십시요)");
		System.out.println();
		System.out.println("1.서울    2.인천    3.대구    4.광주    5.부산   6.울산     ");
		System.out.println();
		System.out.println("                            (뒤로가기 9번)");
		System.out.println("_______________________________________");
		System.out.print("입력>> ");
		switch (ScanUtil.nextInt()) {
		case 1:   //1이입력된경우 DT sessionStorage에 "서울" 저장
			Controller.sessionStorage.put("DT", "서울");
			return View.TEST_LIST_S;
		case 2:		//2가 입력된경우 DT sessionStorage에 "인천" 저장
			Controller.sessionStorage.put("DT", "인천");
			return View.TEST_LIST_S;
		case 3:		//3이입력된경우 DT sessionStorage에 "대구" 저장
			Controller.sessionStorage.put("DT", "대구");
			return View.TEST_LIST_S;
		case 4:		//4가 입력된경우 DT sessionStorage에 "광주" 저장
			Controller.sessionStorage.put("DT", "광주");
			return View.TEST_LIST_S;
		case 5:		//5가 입력된경우 DT sessionStorage에 "부산" 저장
			Controller.sessionStorage.put("DT", "부산");
			return View.TEST_LIST_S;
		case 6:		//6이 입력된경우 DT sessionStorage에 "울산" 저장
			Controller.sessionStorage.put("DT", "울산");
			return View.TEST_LIST_S;
		default:	//아무것도 입력하지않으면 View.MEMBER로 이동
			return View.MEMBER;
		}
	}

}
