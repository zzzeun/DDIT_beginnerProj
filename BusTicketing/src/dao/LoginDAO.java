package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;
import vo.Member;
// 데이터베이스로 쿼리를 날려서 결과를 얻는다.
public class LoginDAO {
	// 싱글톤 패턴을 만든다.
	private static LoginDAO instance = null;
	private LoginDAO() {}
	public static LoginDAO getInstance() {
		if(instance == null) 
			instance = new LoginDAO();
		return instance;
	}
	
	// JDBC를 부른다.
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public Map<String, Object> login(String id, String pass){
		// 로그인
		// 내가 입력한 아이디, 비밀번호에 해당하는 회원정보를 주세요
		// SELECT * FROM MEMBER WHERE MEM_ID = ? AND MEM_PW = ?
		// SELECT * FROM MEMBER WHERE MEM_ID = 'admin' AND MEM_PW = '1234'
		String sql = " SELECT * FROM MEMBER WHERE MEM_ID = ? AND MEM_PW = ? ";
		List<Object> param = new ArrayList<Object>(); //여러 타입의 데이터값을 받기위해 list 사용
		param.add(id);
		param.add(pass);
		
		return jdbc.selectOne(sql, param); // 한 사람의 데이터 값을 반환한 것을 받아와서 다시 LoginService 반환
	}
	
	public Map<String, Object> ids(String em){
		String sql = " SELECT * FROM MEMBER WHERE MEM_EM = ?  ";
		List<Object> param = new ArrayList<Object>(); //여러 타입의 데이터값을 받기위해 list 사용
		param.add(em);
		
		
		return jdbc.selectOne(sql, param); // 한 사람의 데이터 값을 반환한 것을 받아와서 다시 LoginService 반환
	}
	
	//비번찾기
	public Map<String, Object> pws(String id,String tel){
		String sql = " SELECT * FROM MEMBER WHERE MEM_ID = ?  AND MEM_HP = ? ";
		List<Object> param = new ArrayList<Object>(); //여러 타입의 데이터값을 받기위해 list 사용
		param.add(id);
		param.add(tel);
		
		return jdbc.selectOne(sql, param); // 한 사람의 데이터 값을 반환한 것을 받아와서 다시 LoginService 반환
	}
	
	
	
	
}
