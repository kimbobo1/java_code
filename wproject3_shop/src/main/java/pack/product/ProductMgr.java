package pack.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import pack.order.OrderBean;



public class ProductMgr {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private DataSource ds;
	public ProductMgr() {
		try {
			Context context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jdbc_maria");
		} catch (Exception e) {
			System.out.println("DB 연결 실패 : " + e);
		}
	}
	public ArrayList<ProductDto> getProductAll(){
		ArrayList<ProductDto> list = new ArrayList<ProductDto>();
		try {
			conn = ds.getConnection();
			String sql = "select * from shop_product order by no desc";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				ProductDto dto = new ProductDto();
				dto.setNo(rs.getInt("no"));
				dto.setName(rs.getString("name"));
				dto.setPrice(rs.getString("price"));
				dto.setDetail(rs.getString("detail"));
				dto.setSdate(rs.getString("sdate"));
				dto.setStock(rs.getString("stock"));
				dto.setImage(rs.getString("image"));
				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println("getProductAll() err : " + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) { }
		}
		return list;
	}
	public boolean insertProduct(HttpServletRequest request) {
		boolean b= false;
		
		try {
			//업로드할 이미지 경로 : upload 폴더(절대 경로)
			String uploadDir = "/Users/bohyunkim/work/jsou/wproject3_shop/src/main/webapp/upload";
			MultipartRequest multi = new MultipartRequest(request, uploadDir,
								5 * 1014 * 1024, "UTF-8",new DefaultFileRenamePolicy());
			//System.out.println(multi.getParameter("name"));
			//System.out.println(multi.getParameter("price"));
			conn=ds.getConnection();
			String sql = "insert into shop_product(name,price,detail,sdate,stock,image)" + 
					"values(?,?,?,now(),?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, multi.getParameter("name"));
			pstmt.setString(2, multi.getParameter("price"));
			pstmt.setString(3, multi.getParameter("detail"));
			pstmt.setString(4, multi.getParameter("stock"));
			
			if(multi.getFilesystemName("image") == null) {
				//상품 등록시 이미지를 선택하지 않은 경우
				
				pstmt.setString(5, "ready.gif");
			}else {
				pstmt.setString(5, multi.getFilesystemName("image"));
			}
			if(pstmt.executeUpdate() > 0) b = true;
			
		} catch (Exception e) {
			System.out.println("insertProduct() err : " + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close(); 
			
				if(conn != null) conn.close();
			} catch (Exception e2) { } 
		}
		return b;
	}
	
	public ProductDto getProduct(String no) {
		ProductDto dto = null;
		try {
			conn = ds.getConnection();
			String sql = "select * from shop_product where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new ProductDto();
				dto.setNo(rs.getInt("no"));
				dto.setName(rs.getString("name"));
				dto.setPrice(rs.getString("price"));
				dto.setDetail(rs.getString("detail"));
				dto.setSdate(rs.getString("sdate"));
				dto.setStock(rs.getString("stock"));
				dto.setImage(rs.getString("image"));
				
				
			}
			
		} catch (Exception e) {
			System.out.println("insertProduct() err : " + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close(); 
			
				if(conn != null) conn.close();
			} catch (Exception e2) { }
		}
		return dto;
	}
	public boolean updatetProduct(HttpServletRequest request) {
		boolean b = false;
		try {
			//업로드할 이미지 경로 : upload 폴더(절대 경로)
			String uploadDir = "/Users/bohyunkim/work/jsou/wproject3_shop/src/main/webapp/upload";
			MultipartRequest multi = new MultipartRequest(request, uploadDir,
								5 * 1014 * 1024, "UTF-8",new DefaultFileRenamePolicy());
	
			conn=ds.getConnection();
			if(multi.getFilesystemName("image") == null) {
				
			String sql = "update shop_product set name=?,price=?,detail=?,stock=? where no=?"; 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, multi.getParameter("name"));
			pstmt.setString(2, multi.getParameter("price"));
			pstmt.setString(3, multi.getParameter("detail"));
			pstmt.setString(4, multi.getParameter("stock"));
			pstmt.setString(5, multi.getParameter("no"));
				
			
			}else {
				
				String sql = "update shop_product set name=?,price=?,detail=?,stock=?,image=? where no=?"; 
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, multi.getParameter("name"));
				pstmt.setString(2, multi.getParameter("price"));
				pstmt.setString(3, multi.getParameter("detail"));
				pstmt.setString(4, multi.getParameter("stock"));
				pstmt.setString(5, multi.getFilesystemName("image"));
				pstmt.setString(6, multi.getParameter("no"));
			}
			if(pstmt.executeUpdate() > 0) b = true;
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close(); 
			
				if(conn != null) conn.close();
			} catch (Exception e2) { }
		}
		return b;
	}
	
	public boolean deleteProduct(String no) {
		boolean b = false;
		
		try {
			conn = ds.getConnection();
			String sql = "delete from shop_product where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			
			if(pstmt.executeUpdate() > 0) b= true;
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close(); 
			
				if(conn != null) conn.close();
			} catch (Exception e2) { }
		}
		return b;
	}
	// 고객이 상품 주문 시 주문 수 만큼 재고량 빼기
	public void reduceProduct(OrderBean bean) {
		try {
			conn = ds.getConnection();
			String sql = "update shop_product set stock=(stock - ?) where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getQuantity());
			pstmt.setString(2, bean.getProduct_no());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close(); 
			
				if(conn != null) conn.close();
			} catch (Exception e2) { }
		}
	}
	
}
