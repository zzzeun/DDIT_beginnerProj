package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;

public class TestBoardDAO {
	private static TestBoardDAO instance = null;
	private TestBoardDAO() {}
	public static TestBoardDAO getInstance() {
		if(instance == null) instance = new TestBoardDAO();
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> viewList() {
		String sql = " SELECT R.RA_DT, B.BUS_GR, B.BUS_MAX, RI.RI_TM, RI.RI_RT , RI.RI_PAY , R.RA_HOME " + 
				"FROM RACE R " + 
				"JOIN RACEINFO RI ON R.RA_NO = RI.RA_NO " + 
				"JOIN BUS B ON RI.BUS_NO = B.BUS_NO " +  
				"ORDER BY R.RA_DT, RI.RI_TM ASC ";
		return jdbc.selectList(sql);
	}
	
	
	public List<Map<String, Object>> selectListS() {  //서울
		String sql = " SELECT * FROM LIST_VIEW WHERE RA_DT = ? " + 
				"ORDER BY RI_TM ";
		List<Object> param = new ArrayList<Object>(); //여러 타입의 데이터값을 받기위해 list 사용
		param.add(Controller.sessionStorage.get("DT"));
		
		return jdbc.selectList(sql, param);
	}
	
	
	
}
