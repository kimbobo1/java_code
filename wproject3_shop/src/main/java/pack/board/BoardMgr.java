package pack.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardMgr {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private DataSource ds;
	
	// paging 처리
	private int rectot;  		// tot : 전체 레코드 수
	private int pList = 5;      // 페이지 당 출력 행 수 
	private int pageSu;         // 전체 페이지 수 
	
	public BoardMgr() {
		try {
			Context context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jdbc_maria");
		} catch (Exception e) {
			System.out.println("DB 연결 실패 : " + e);
		}
	}
	
	//public ArrayList<BoardDto> getDataAll(int page) {
	public ArrayList<BoardDto> getDataAll(int page,String stype, String sword) {
		ArrayList<BoardDto> list = new ArrayList<BoardDto>();
		
		String sql = "select * from board"; //order by gnum desc, onum asc";
		try {
			conn = ds.getConnection();
			if(sword == null) { //검색이 없을경우
				sql += " order by gnum desc,onum asc";
				pstmt = conn.prepareStatement(sql);
			}else { //있는경우
				sql +=" where " + stype + " like ?";
				sql += " order by gnum desc,onum asc";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + sword + "%");
			}
			
			
			rs = pstmt.executeQuery();
			
			//paging 
			for(int i=0; i < (page -1) * pList; i++) {
				rs.next(); //레코드 포인터 이동  0, 4, 
			}
			int k = 0;
			
			while(rs.next() && k < pList) {
				BoardDto dto = new BoardDto();
				dto.setNum(rs.getInt("num"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setBdate(rs.getString("bdate"));
				dto.setReadcnt(rs.getInt("readcnt"));
				dto.setNested(rs.getInt("nested"));
				list.add(dto);
				k++;
			}
		} catch (Exception e) {
			System.out.println("getDataAll11() err : " + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) { }
		}
		
		return list;
	}
	public int currentMaxNum() { //board 테이블의 초대 번호 반납
		String sql = "select max(num) from board";
		int num = 0; //레코드가 없으면 0을 리턴 
		try {
			conn =ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) num= rs.getInt(1); 
				
			
		} catch (Exception e) {
			System.out.println("getDataAll() err : " + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) { 
				
			}
		}
		return num;
		
	}
	
	public void saveData(BoardFormBean bean) { //board insert
		String sql = "insert into board values(?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			conn =ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bean.getNum());
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getPass());
			pstmt.setString(4, bean.getMail());
			pstmt.setString(5, bean.getTitle());
			pstmt.setString(6, bean.getCont());
			pstmt.setString(7, bean.getBip());
			pstmt.setString(8, bean.getBdate());
			pstmt.setInt(9, 0);   //readcnt
			pstmt.setInt(10, bean.getGnum());
			pstmt.setInt(11, 0);  //onum
			pstmt.setInt(12, 0); //nested
			
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("saveDataAll() err : " + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) { 
				
			}
		}
		
	}
	
	public void totalList() { // 전체 레코드 수 구하기
		String sql = "select count(*) from board";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			rs.next();
			rectot = rs.getInt(1);
			System.out.println("전체 레코드수 " + rectot);
		} catch (Exception e) {
			System.out.println("saveDataAll() err : " + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) { 
				
			}
		}
	}
	public int getPageSu() { //전체 페이지수 반환
		pageSu = rectot / pList;
		if(rectot % pList > 0) pageSu++; //자투리가 있으면 페이지 하나 플러스
		return pageSu;
	}
	public void updateReadcnt(String num) { //조회수 증가
		String sql = "update board set readcnt=readcnt + 1 where num=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("updateReadcnt() err : " + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) { 
				
			}

		}
	}
	public BoardDto getData(String num) {
		String sql = "select * from board where num=?";
		BoardDto dto = null;
		
		try {
			conn =ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { //만약에 자료가 있다면 dto에 자료를 담고 없으면 바로 리턴
				dto = new BoardDto();
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setMail(rs.getString("mail"));
				dto.setTitle(rs.getString("title"));
				dto.setCont(rs.getString("cont"));
				dto.setBip(rs.getString("bip"));
				dto.setBdate(rs.getString("bdate"));
				dto.setReadcnt(rs.getInt("readcnt"));
				
			}
			
			
		} catch (Exception e) {
			System.out.println("updateReadcnt() err : " + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) { 
				
			}

		}
		
		return dto;
	}
	public BoardDto getReplyData(String num) { //댓글용
		String sql = "select * from board where num=?";
		BoardDto dto = null;
		
		try {
			conn =ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { //만약에 자료가 있다면 dto에 자료를 담고 없으면 바로 리턴
				dto = new BoardDto();
			
				dto.setTitle(rs.getString("title"));
				dto.setGnum(rs.getInt("gnum"));
				dto.setOnum(rs.getInt("onum"));
				dto.setNested(rs.getInt("nested"));
			}
		} catch (Exception e) {
			System.out.println("updateReadcnt() err : " + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) { 
				
			}

		}
		
		return dto;
	}
	public void updateOnum(int gnum,int onum) {//댓글용 :onum 갱신
		//같은 그룹의 레코드는 모두 작업에 참여 - 같은 그룹의 onum 값 갱신
		//댓글의 onum은 이미 db에 있는 onum 보다 크거나 같은 값을 변경함
		String sql = "update board set onum=onum +1 where onum >= ? and gnum=?";
		try {
			conn =ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, onum);
			pstmt.setInt(2, gnum);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("updateReadcnt() err : " + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) { 
				
			}

		}
	}
	
	public void replySave(BoardFormBean bean) { //댓글용 저장
		String sql = "insert into board values(?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			conn =ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bean.getNum());
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getPass());
			pstmt.setString(4, bean.getMail());
			pstmt.setString(5, bean.getTitle());
			pstmt.setString(6, bean.getCont());
			pstmt.setString(7, bean.getBip());
			pstmt.setString(8, bean.getBdate());
			pstmt.setInt(9, 0);   //readcnt
			pstmt.setInt(10, bean.getGnum());
			pstmt.setInt(11, bean.getOnum());  //onum
			pstmt.setInt(12, bean.getNested()); //nested
			
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("replySave() err : " + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) { 
				
			}
		}
	}
	public boolean checkPass(int num, String user_pass) { //글수정에서 비번 비교용
		boolean b = false;
		
		String sql = "select pass from board where num=?";
		try {
			conn =ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs= pstmt.executeQuery();
			if(rs.next()) {
				if(user_pass.equals(rs.getString("pass"))) {
					b= true;
				}
			}
		} catch (Exception e) {
			System.out.println("chekpass() err : " + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) { 
				
			}
		}
		return b;
	}
	
	public void saveEdit(BoardFormBean bean) {
		String sql = "update board set name=?,mail=?,title=?,cont=? where num=?";
		try {
			conn =ds.getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getMail());
			pstmt.setString(3, bean.getTitle());
			pstmt.setString(4, bean.getCont());
			pstmt.setInt(5, bean.getNum());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("saveEdit() err : " + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) { 
				
			}
		}
	}
	public void delData(String num) {
		String sql = "delete from board where num=?";
		try {
			conn =ds.getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, num);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("saveEdit() err : " + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) { 
				
			}
		}
	}
}
