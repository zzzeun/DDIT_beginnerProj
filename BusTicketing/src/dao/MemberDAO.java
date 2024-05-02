package dao;

import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class MemberDAO {
	private static MemberDAO instance = null;

	private MemberDAO() {
	}

	public static MemberDAO getInstance() {
		if (instance == null)
			instance = new MemberDAO();
		return instance;
	}

	JDBCUtil jdbc = JDBCUtil.getInstance();

	public Map<String, Object> login(String id, String pass) {
		return jdbc.selectOne(" SELECT * FROM MEMBER " + " WHERE MEM_ID='" + id + "' AND MEM_PW='" + pass + "' ");
	}

	
	
	
	
	public int signUp(List<Object> param) {
		StringBuffer sb = new StringBuffer();
		sb.append(" INSERT INTO MEMBER (MEM_ID, MEM_PW , MEM_NAME, MEM_HP,  MEM_EM) ");
		sb.append(" VALUES (?, ?, ?, ?, ?) ");
		
		String sql = sb.toString();
//		System.out.println(sql);
//		return 0;
//		String sql = "INSERT INTO MEMBER (MEM_ID, MEM_NAME, MEM_HP, MEM_PASS) VALUES (?, ?, ?, ?)";
		return jdbc.update(sql, param);
	}

	
	
	
	
	
	
	
	public Map<String, Object> getMemberInfo(String mid) {
		String sql = "SELECT * FROM MEMBER WHERE MEM_ID = '" + mid + "' ";
		return jdbc.selectOne(sql);
	}
	
	public int resign(String setString, List<Object> param) {
		String sql = " UPDATE MEMBER SET";
		sql += setString;
		sql += " WHERE MEM_ID = ? ";
		
		return jdbc.update(sql, param);
	}
}
