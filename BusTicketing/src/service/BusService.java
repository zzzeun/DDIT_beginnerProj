package service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.Checksum;

import controller.Controller;
import dao.BusDAO;
import dao.MemberDAO;
import util.PrintUtil;
import util.ScanUtil;
import util.View;

public class BusService {

	List<String> StringList = new ArrayList<String>();

	private static BusService instance = null;

	private BusService() {
	}

	public static BusService getInstance() {
		if (instance == null)
			instance = new BusService();
		return instance;
	}

	BusDAO dao = BusDAO.getInstance();
	MemberDAO memberDao = MemberDAO.getInstance(); // 회원 번호를 회원 이름으로 바꿔서 출력하기 위함
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Scanner sc = new Scanner(System.in);

	public int buslist_s() {
		// 관리자면 모든 기능이 가능
		// 사용자이지만 내가 작성한 게시글에 대해서만 수정, 삭제가 가능

		// 게시판 전체 리스트가 보여지고
		List<Map<String, Object>> result = dao.busList();
		System.out.println();
		System.out.println("버스번호     목적지      등급         출발시간     소요시간(분)     가격          최대좌석         남은좌석    ");
		System.out.println("-----------------------------------------------------------------");

		for (int i = 0; i < result.size(); i++) {
			Map<String, Object> res = result.get(i);
			System.out.printf("%-8s", res.get("BUS_NO"));
			StringList.add(String.valueOf(res.get("BUS_NO")));
			System.out.printf("%-10s", res.get("RA_DT"));
			System.out.printf("%-11s", res.get("BUS_GR"));
			System.out.printf("%-10s", res.get("RI_TM"));
			System.out.printf("%-10s", res.get("RI_RT"));
			System.out.printf("%-10s", res.get("RI_PAY"));
			System.out.printf("%-10s", res.get("BUS_MAX"));
			System.out.printf("%-10s", res.get("REMAINING_SEATS"));
			System.out.println();
			//
		}
		System.out.println("-----------------------------------------------------------------");
		System.out.println("                          (뒤로가기:9)");
		while (true) {
			boolean found = false;
			String buscheck = ScanUtil.nextLine("예매하고싶은 버스의 번호를 입력하시오 >> ");
			if (buscheck.equals("9")) {
				return View.BUS_TICK;
			}
			;
			for (String value : StringList) {
				if (value.equals(buscheck)) {

					int a = Integer.valueOf(buscheck);
					Controller.sessionStorage.put("rsv", a);
					found = true;
					return View.BUS_CHECK;

				}
			}
			if (!found) {
				System.out.println("목록에 없는 버스번호입니다. \n다시입력해주세요.");
			}

		}
	}

	
	// 예약확인
	public int busCheck() {
		List<Map<String, Object>> result = dao.buscheck();
		System.out.println();
		System.out.println("-----------------------------------------------------------------");

		for (int i = 0; i < result.size(); i++) {
			Map<String, Object> res = result.get(i);
			System.out.print(Controller.sessionStorage.get("name") + "님 ");
			System.out.printf(" %s행", res.get("RA_DT"));
			System.out.printf(" %s시", res.get("RI_TM"));
			System.out.printf(" %s호 버스가 맞으십니까?", res.get("BUS_NO"));
			System.out.println();
		}
		System.out.println("-----------------------------------------------------------------");
		System.out.println("                          (뒤로가기:9)");

		while (true) {
			int check = ScanUtil.nextInt("예 (1)  /   아니오 (2) >> ");
			if (check == 1) {
				int checkSum = ScanUtil.nextInt("예매할 인원수를 입력하시오>>");
				Controller.sessionStorage.put("checkSum", checkSum);
				return View.BUS_PRICE;
			} else if (check == 9 || check == 2) {
				return View.BUS_S;
			} else {
				System.out.println(" 입력을 잘못했습니다 다시입력해주세요 ");
			}
		}
	}

	
	
	// 돈지불 인원수확인
	public int priceCheck() {
		List<Map<String, Object>> result = dao.pricecheck();
		List<Map<String, Object>> result2 = dao.realbuscheck();
		List<Map<String, Object>> result3 = dao.rsvpay();

		for (int i = 0; i < result2.size(); i++) {
			Map<String, Object> res = result.get(i);
			Controller.sessionStorage.put("SUM", res.get("REMAINING_SEATS"));
			Controller.sessionStorage.put("RI_NO2",res.get("RI_NO"));
		}
		
		for (int i = 0; i < result2.size(); i++) {
			Map<String, Object> res = result2.get(i);
			Controller.sessionStorage.put("RI_NO2",res.get("RI_NO"));
		}
		

		int selectSum = (int) Controller.sessionStorage.get("checkSum");
		String SUM = String.valueOf(Controller.sessionStorage.get("SUM"));
		int countSum = Integer.valueOf(SUM);

		if (selectSum > countSum) {
			System.out.println("현재 선택하신 " + Controller.sessionStorage.get("rsv") + "호차는 자리는 인원수에 의하여 선택하실수없습니다");
			return View.BUS_S;
		} else {
			System.out.println("예약가능");
			for (int i = 0; i < result3.size(); i++) {
				Map<String, Object> res = result3.get(i);
				Controller.sessionStorage.put("pay", res.get("RI_PAY"));
			}
			return View.BUS_PAY;
		}

	}

	
 //예약이 가능한지 확인
//	public int realCheck() {
//		List<Map<String, Object>> result = dao.pricecheck();
//		List<Map<String, Object>> result2 = dao.realbuscheck();
//		List<Map<String, Object>> result3 = dao.rsvpay();
//
//		for (int i = 0; i < result2.size(); i++) {
//			Map<String, Object> res = result.get(i);
//			Controller.sessionStorage.put("SUM", res.get("REMAINING_SEATS"));
//			Controller.sessionStorage.put("RI_NO2",res.get("RI_NO"));
//		}
//
//		int selectSum = (int) Controller.sessionStorage.get("checkSum");
//		String SUM = String.valueOf(Controller.sessionStorage.get("SUM"));
//		int countSum = Integer.valueOf(SUM);
//
//		if (selectSum > countSum) {
//			System.out.println("현재 선택하신 " + Controller.sessionStorage.get("rsv") + "호차는 자리는 인원수에 의하여 선택하실수없습니다");
//			return View.BUS_S;
//		} else {
//			System.out.println("예약가능");
//			for (int i = 0; i < result3.size(); i++) {
//				Map<String, Object> res = result3.get(i);
//				Controller.sessionStorage.put("pay", res.get("RI_PAY"));
//			}
//			return View.BUS_PAY;
//		}
//
//	}
	
	
	// 금액지불
	public int realpay() {
		int sum = (int) Controller.sessionStorage.get("checkSum");
		String SUM = String.valueOf(Controller.sessionStorage.get("pay"));
		int pay = Integer.valueOf(SUM);
		int fullpay = sum * pay;
		System.out.println("지불하실 금액  " + fullpay + "원 입니다");
		while (true) {
			int cstpay = ScanUtil.nextInt("지불>>>");

			if (fullpay > cstpay) {
				System.out.println("돈이 부족합니다 다시입력해주세요");
			} else if (cstpay == 0) {
				return View.BUS_S;
			} else if (fullpay == cstpay) {
				System.out.println("지불이 완료되었습니다");
				return View.BUS_RESERVATION;
			} else {
				System.out.println("지불이 완료되었습니다");
				int devidePay=fullpay-cstpay;
				System.out.println("거스름 돈은 "+devidePay*-1+"원 입니다");
				
				return View.BUS_RESERVATION;
			}
		}

	}

	
	//진짜 예매 완료 INSERT시킴
	public int PayOfFinal() {
		int result = dao.realrsv();
		
		PrintUtil.println(5);
		System.out.println("------------------------------------------");
		System.out.println("                 예매가 완료되었습니다");
		System.out.println("------------------------------------------");
		
		return View.MEMBER;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//예매 목록****************
	public int MyResList() {
		List<Map<String, Object>> result = dao.rsvList();
		if(result==null) {
			System.out.println("-----------------------------------------------------------------");
			System.out.println(Controller.sessionStorage.get("name") +"님의 예매목록이 없습니다");
		}
		else {
		System.out.println();
		System.out.println();
		System.out.println(Controller.sessionStorage.get("name") + " 님의 예매목록입니다");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("     에매번호             탑승시간             목적지     예매수       승차홈   ");
		System.out.println("-----------------------------------------------------------------");

		for (int i = 0; i < result.size(); i++) {
			Map<String, Object> res = result.get(i);
			
			System.out.printf("   %-11s", res.get("TK_CODE")); // 예매코드
			System.out.printf("%-11s", res.get("RI_TM")); // 출발시간
			System.out.printf("%-13s", res.get("RA_DT")); // 목적지
			System.out.printf("%-7s", res.get("TK_AMOUNT")); // 예매수
			System.out.printf("%s", res.get("RA_HOME")); // 승차홈
			System.out.println();
			
		}
		}
		System.out.println("-----------------------------------------------------------------");

		while (true) {
			System.out.println();
			int buscheck = ScanUtil.nextInt("뒤로가기 <9> 누르세요>>");
			if (buscheck == 9) {
				return View.MYPAGE;
			} else {

			}

		}
	}

}
