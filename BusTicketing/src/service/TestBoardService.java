package service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import dao.MemberDAO;
import dao.TestBoardDAO;
import util.ScanUtil;
import util.View;

public class TestBoardService {
	
	
	
	private static TestBoardService instance = null;

	private TestBoardService() {
	}

	public static TestBoardService getInstance() {
		if (instance == null)
			instance = new TestBoardService();
		return instance;
	}

	TestBoardDAO dao = TestBoardDAO.getInstance();
	MemberDAO memberDao = MemberDAO.getInstance(); // 회원 번호를 회원 이름으로 바꿔서 출력하기 위함
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public int list() {
		// 관리자면 모든 기능이 가능
		// 사용자이지만 내가 작성한 게시글에 대해서만 수정, 삭제가 가능

		// 게시판 전체 리스트가 보여지고
		List<Map<String, Object>> result = dao.viewList();
		System.out.println();
		System.out.println(" 도착지         버스등급       총좌석                출발시간            소요시간                 가격             승차홈");
		System.out.println("-----------------------------------------------------------------");

		for (int i = 0; i < result.size(); i++) {
			Map<String, Object> res = result.get(i);
			System.out.printf("   %-14s", res.get("RA_DT"));  //도착지
			System.out.printf("%-15s", res.get("BUS_GR"));  //버스등급
			System.out.printf("%-11s", res.get("BUS_MAX"));  //총좌석
			System.out.printf("%-11s", res.get("RI_TM"));  //출발시간
			System.out.printf("%-11s", res.get("RI_RT"));   //소요시간(분)
			System.out.printf("%-11s", res.get("RI_PAY"));   //가격
			System.out.printf("%s", res.get("RA_HOME"));   //승차홈
			System.out.println();
		}
		System.out.println("-----------------------------------------------------------------");

		
		while (true) {
			System.out.println("1.지역별 상세조회  0.뒤로가기 ");
			System.out.print("선택 >> ");
			int choice = ScanUtil.nextInt();

			if (choice == 0)
				break;
			if (choice == 1) {
				return View.BUSJH;
			}
			else {
				System.out.println();
				System.out.println("잘못입력하였습니다");
				System.out.println();
			}
		}
		return View.MEMBER;
	}

	public void getBoard(int boardNum, List<Map<String, Object>> result) {
		for (int i = 0; i < result.size(); i++) {
			Map<String, Object> res = result.get(i);
			int bnum = Integer.parseInt(String.valueOf(res.get("BOARD_NUM")));
			if (bnum == boardNum) {
				System.out.println("----------------------------------------------");
				System.out.println("게시글 번호 : " + bnum);
//				System.out.println("게시글 번호 : " + res.get("BOARD_NUM");
				System.out.println("제목 : " + res.get("TITLE"));
				System.out.println("내용 : " + res.get("CONTENT"));

				String date = formatter.format(res.get("BDATE"));
				System.out.println("작성일자 : " + date);

				String mid = (String) res.get("MEM_ID");
				Map<String, Object> memInfo = memberDao.getMemberInfo(mid);
				String name = (String) memInfo.get("MEM_NAME");
				System.out.println("작성자 : " + name);
				System.out.println("----------------------------------------------");
			}
		}
	}
	
	
	
	
	
	public int s_list() {
		//서울선택시 리스트
		List<Map<String, Object>> result = dao.selectListS();
		System.out.println();
		System.out.println(" 도착지          버스등급               총좌석               출발시간            소요시간              가격        승차홈");
		System.out.println("-----------------------------------------------------------------");

		for (int i = 0; i < result.size(); i++) {
			Map<String, Object> res = result.get(i);
			System.out.printf("  %-16s", res.get("RA_DT"));  //도착지
			System.out.printf("%-23s", res.get("BUS_GR"));  //버스등급
			System.out.printf("%-10s", res.get("BUS_MAX"));  //총좌석
			System.out.printf("%-12s", res.get("RI_TM"));  //출발시간
			System.out.printf("%-9s", res.get("RI_RT"));   //소요시간(분)
			System.out.printf("%-9s", res.get("RI_PAY"));   //가격
			System.out.printf("%s", res.get("RA_HOME"));   //승차홈
			System.out.println();
		}
		System.out.println("-----------------------------------------------------------------");

		
		while (true) {
			System.out.println("  9.뒤로가기 ");
			System.out.print("선택 >> ");
			int choice = ScanUtil.nextInt();
			
			if (choice == 9) {
				return View.BUSJH;
			}
			else {
				System.out.println();
				System.out.println("잘못입력하였습니다");
				System.out.println();
				break;
			}
		}
		return View.MEMBER;
	}
	
	
	
	
	
	
		
	
}