package kr.co.sist.manager.restarea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kr.co.sist.manager.restarea.vo.ManageBrandVO;
import kr.co.sist.manager.restarea.vo.ManageBrandVO;
import project_second.DbConnection;

public class ManageBrandDAO {

	private static ManageBrandDAO mbDAO;
		
		private ManageBrandDAO() {
			
		}//ManageBrandDAO
		
		public static ManageBrandDAO getInstance() {
			if(mbDAO == null) {
				mbDAO = new ManageBrandDAO();
			}//end if
			return mbDAO;
		}//getInstance
	
		public boolean insertBrand(ManageBrandVO mbVO) throws SQLException {
			
			Connection con = null;
			PreparedStatement pstmt = null;
			
			DbConnection db = DbConnection.getInstance();
			
			try {
			
				con = db.getConn("jdbc/dbcp");
				
				StringBuilder insertBrand = new StringBuilder();
				
				insertBrand
				.append("	insert into brand(rano, bno, ino, bname, btel, bhome, bdate)	")
				.append("	values(?,(select max(rvno)+1 from review),?,?,?,?,sysdate)		");
				
				pstmt = con.prepareStatement(insertBrand.toString());
				
				pstmt.setString(1, mbVO.getRestAreaNum());
				pstmt.setInt(2, mbVO.getIconNum());
				pstmt.setString(3, mbVO.getBrandName());
				pstmt.setString(4, mbVO.getBrandTel());
				pstmt.setString(5, mbVO.getBrandHome());
				
				boolean flag = pstmt.execute();
				
				return flag;
			}finally {
				db.dbClose(null, pstmt, con);
			}//finally
			
		}//insertBrand
		
		public int updateBrand(ManageBrandVO mbVO) throws SQLException {
			int rowCnt = 0;
			
			Connection con = null;
			PreparedStatement pstmt = null;
			
			DbConnection db = DbConnection.getInstance();
			
			try {
				con = db.getConn("jdbc/dbcp");
				
				StringBuilder updateBrand = new StringBuilder();
				updateBrand
				.append("	update Brand							")
				.append("	set ino=?, bname=?, btel=?, bhome=?		")
				.append("	where bno=?								");
				
				pstmt = con.prepareStatement(updateBrand.toString());
				
				pstmt.setInt(1, mbVO.getIconNum());
				pstmt.setString(2, mbVO.getBrandName());
				pstmt.setString(3, mbVO.getBrandTel());
				pstmt.setString(4, mbVO.getBrandHome());
				pstmt.setInt(5, mbVO.getBrandNum());
				
				rowCnt = pstmt.executeUpdate();
				
			}finally {
				db.dbClose(null, pstmt, con);
			}//end finally
			return rowCnt;
		}//updateBrand
		
		public int deleteBrand(ManageBrandVO mbVO) throws SQLException {
			int rowCnt = 0;
			
			Connection con = null;
			PreparedStatement pstmt = null;
			
			DbConnection db = DbConnection.getInstance();
			
			try {
				con = db.getConn("jdbc/dbcp");
				
				String updateBrand = "delete from brand where bno=?";
				
				pstmt = con.prepareStatement(updateBrand);
				
				pstmt.setInt(1, mbVO.getBrandNum());
				
				rowCnt = pstmt.executeUpdate();
				
			}finally {
				db.dbClose(null, pstmt, con);
			}//end finally
			return rowCnt;
		}//deleteBrand
}
