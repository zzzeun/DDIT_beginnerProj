package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;

public class BusDAO {
	private static BusDAO instance = null;

	private BusDAO() {}

	public static BusDAO getInstance() {
		if (instance == null)
			instance = new BusDAO();
		return instance;
	}

	JDBCUtil jdbc = JDBCUtil.getInstance();

	

//	public int signUp(List<Object> param) {
//		StringBuffer sb = new StringBuffer();
//		sb.append(" INSERT INTO TBL_MEM (MEM_ID, MEM_NAME, MEM_HP, MEM_PASS) ");
//		sb.append(" VALUES (?, ?, ?, ?) ");
//
//		String sql = sb.toString();
//		return jdbc.update(sql, param);
//	}

//	public Map<String, Object> getMemberInfo(String mid) {
//		String sql = "SELECT * FROM TBL_MEM WHERE MEM_ID = '" + mid + "' ";
//		return jdbc.selectOne(sql);
//	}
//
//	public int resign(String setString, List<Object> param) {
//		String sql = " UPDATE TBL_MEM SET";
//		sql += setString;
//		sql += " WHERE MEM_ID = ? ";
//
//		return jdbc.update(sql, param);
//	}
	
	
	
	public List<Map<String, Object>> busList_s() {
		String sql = " SELECT B.BUS_NO , R.RA_DT , B.BUS_GR , RI.RI_DATE , RI.RI_TM , RI.RI_RT , RI.RI_PAY , B.BUS_MAX ,(B.BUS_MAX - (SELECT COUNT(*) FROM TICKETING T WHERE T.RI_NO = RI.RI_NO)) AS A , R.RA_HOME " + 
				"FROM RACE R " + 
				"JOIN RACEINFO RI ON R.RA_NO = RI.RA_NO " + 
				"JOIN BUS B ON RI.BUS_NO = B.BUS_NO " + 
				"WHERE R.RA_DT = '서울' AND RI.RI_DATE = '2023-10-23' " + 
				"ORDER BY R.RA_DT, RI.RI_TM ASC ";
		return jdbc.selectList(sql);
	}
	
	
//서울 예매
	public List<Map<String, Object>> busList() {
		String sql = " SELECT * FROM BUS_LIST WHERE RA_DT = ? ";
		List<Object> param = new ArrayList<Object>(); //여러 타입의 데이터값을 받기위해 list 사용
		param.add(Controller.sessionStorage.get("DT"));
		return jdbc.selectList(sql,param);
	}
	
	
	
	
	
	
	
	public Map<String, Object> reservation(int bus){
		String sql = " SELECT * FROM MEMBER WHERE MEM_EM = ?   ";
		List<Object> param = new ArrayList<Object>(); //여러 타입의 데이터값을 받기위해 list 사용
		param.add(bus);
		
		
		return jdbc.selectOne(sql, param); // 한 사람의 데이터 값을 반환한 것을 받아와서 다시 LoginService 반환
	}
	
	
	
	//예매조회
	public List<Map<String, Object>> rsvList() {
		String sql = " SELECT * FROM CHECK_VIEW WHERE MEM_ID = ?  ORDER BY TK_CODE ";
		List<Object> param = new ArrayList<Object>(); //여러 타입의 데이터값을 받기위해 list 사용
		param.add(Controller.sessionStorage.get("ID"));
		return jdbc.selectList(sql,param);
	}
	
	
	
	//버스확인
	public List<Map<String, Object>> buscheck(){
		String sql = " SELECT * FROM BUS_LIST WHERE BUS_NO = ?  ";
		List<Object> param = new ArrayList<Object>(); //여러 타입의 데이터값을 받기위해 list 사용
		param.add(Controller.sessionStorage.get("rsv"));
		return jdbc.selectList(sql, param); // 한 사람의 데이터 값을 반환한 것을 받아와서 다시 LoginService 반환
	}
	
	
	
	
	//가격체크
		public List<Map<String, Object>> pricecheck(){
			String sql = " SELECT * FROM BUS_LIST WHERE BUS_NO = ? ";
			List<Object> param = new ArrayList<Object>(); //여러 타입의 데이터값을 받기위해 list 사용
			param.add(Controller.sessionStorage.get("rsv"));
			
			
			return jdbc.selectList(sql, param); // 한 사람의 데이터 값을 반환한 것을 받아와서 다시 LoginService 반환
		}
	
	
	//
		public List<Map<String, Object>> checkbussum(){
			String sql = " SELECT * FROM CHECK_VIEW WHERE BUS_NO = ?   ";
			List<Object> param = new ArrayList<Object>(); //여러 타입의 데이터값을 받기위해 list 사용
			param.add(Controller.sessionStorage.get("rsv"));
			
			
			return jdbc.selectList(sql, param); // 한 사람의 데이터 값을 반환한 것을 받아와서 다시 LoginService 반환
		}
	
	
		
		
		
		public List<Map<String, Object>> realbuscheck(){
			String sql = " SELECT * FROM SELECT_BUS WHERE BUS_NO = ?   ";
			List<Object> param = new ArrayList<Object>(); //여러 타입의 데이터값을 받기위해 list 사용
			param.add(Controller.sessionStorage.get("rsv"));
			return jdbc.selectList(sql, param); // 한 사람의 데이터 값을 반환한 것을 받아와서 다시 LoginService 반환
		}
		
		
		
		
		//지이이이인짜 리얼 예매 아예 그냥 데이터베이스에 갔다 박는거임
		public int realrsv(){
			StringBuffer sb = new StringBuffer();
			sb.append(" INSERT INTO TICKETING (TK_CODE, TK_DATE, TK_AMOUNT, MEM_ID, RI_NO) ");
			sb.append("VALUES (TK_CODE.NEXTVAL,SYSDATE, ? , ? , ?) ");
			
			String sql=sb.toString();
			
			
			List<Object> param = new ArrayList<Object>(); //여러 타입의 데이터값을 받기위해 list 사용
			param.add(Controller.sessionStorage.get("checkSum"));
			param.add(Controller.sessionStorage.get("ID"));
			param.add(Controller.sessionStorage.get("RI_NO2"));
		
			
			return jdbc.update(sql,param); // 한 사람의 데이터 값을 반환한 것을 받아와서 다시 LoginService 반환
		}
	
		
		
		
		
		
	    //예매한 표 값
		public List<Map<String, Object>> rsvpay(){
			String sql = " SELECT RI_PAY FROM LIST_VIEW WHERE BUS_NO = ?   ";
			List<Object> param = new ArrayList<Object>(); //여러 타입의 데이터값을 받기위해 list 사용
			param.add(Controller.sessionStorage.get("rsv"));
			
			
			return jdbc.selectList(sql, param); // 한 사람의 데이터 값을 반환한 것을 받아와서 다시 LoginService 반환
		}
	
	
	
	
	
	
	
	
	
	
}
