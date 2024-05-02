package util;

public interface View {
	int HOME = 1;
	int MAIN = 11;
	// 멤버 담당 팀원
	
	
	int MEMBER = 2;
	int MEMBER_SIGNUP = 21;
	int MEMBER_LOGIN = 22;
	int MEMBER_IDSER = 23;
	int MEMBER_SEARCH=24;
	int MEMBER_PWSER=25;
	int MEMBER_RSV=26;
	
	
	// 보드 담당 팀원
	int BOARD = 3;
	int BOARD_LIST = 31;
	int BOARD_DETAIL = 32;
	int BOARD_INSERT = 33;
	int BOARD_UPDATE = 34;
	int BOARD_DELETE = 35;
	
	// 이태영 - 자유게시판
	int FREE = 4;
	int FREE_LIST = 41;
	int FREE_DETAIL = 42;
	int FREE_INSERT = 43;
	int FREE_UPDATE = 44;
	int FREE_DELETE = 45;
	int FREE_DELETE2 = 46;
	
	
	//버스
	int BUS=5;
	int BUS_LIST=51;
	int BUS_TICK=52;
	int BUSJH=53;
	int BUS_S=54;
	int BUS_I=55;
	int BUS_D=56;
	int BUS_G=57;
	int BUS_B=58;
	int BUS_U=59;
	
	
	
	
	
	
	
	
	//마이페이지
	int MYPAGE=6;
	int MEMBER_RESIGN = 61;
	int MEMBER_DELETE = 62;
	
	
	
	
	int TEST = 7;
	int TEST_LIST = 71; //로그인 다음에 수행해야할 메뉴
	int TEST_LIST_S = 72;
	int TEST_LIST_I = 73;
	int TEST_LIST_D = 74;
	int TEST_LIST_G = 75;
	int TEST_LIST_B = 76;
	int TEST_LIST_U = 77;
	
	//버스예약
	int BUS_CHECK =8;   //예매할 목록 확인
	int BUS_PRICE =81;  //돈지불
	int BUS_PAY=82;
	int BUS_RESERVATION=83;
	
	
	 
}
