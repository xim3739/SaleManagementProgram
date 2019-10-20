package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import util.AlertManager;
import util.DBUtil;
import util.AlertManager.AlertInfo;

public class SaleDAO {

	public void insertSaleDB(SaleVO saleVO) {

		String dml = "insert into saleTBL (date, goods, price, count, total, coments) values (?,?,?,?,?,?)";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, saleVO.getDate());
			pstmt.setString(2, saleVO.getGoods());
			pstmt.setInt(3, saleVO.getPrice());
			pstmt.setInt(4, saleVO.getCount());
			pstmt.setInt(5, saleVO.getTotal());
			pstmt.setString(6, saleVO.getComents());

			pstmt.execute();

		} catch (Exception e) {
			e.printStackTrace();
			AlertManager.getInstance().show(AlertInfo.ERROR_TASK_DB, null);
		} finally {

			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				AlertManager.getInstance().show(AlertInfo.ERROR_TASK_DB, null);
			}

		}

	} // end of insertSaleDB

	public ArrayList<SaleVO> getSaleTotal() {
		ArrayList<SaleVO> list = new ArrayList<SaleVO>();
		String dml = "select * from saleTBL";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 디비에서 가져온 데이터를 임시로 저장하고 있는 공간
		SaleVO saleVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				saleVO = new SaleVO(rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6),
						rs.getString(7));
				list.add(saleVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			AlertManager.getInstance().show(AlertInfo.ERROR_TASK_DB, null);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
				se.printStackTrace();
				AlertManager.getInstance().show(AlertInfo.ERROR_TASK_DB, null);
			}
		}
		return list;
	}

	public int deleteSale(SaleVO saleVO) {

		String dml = "delete from saleTBL where date = ? and goods = ? and count = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		int i = 0;

		try {

			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, saleVO.getDate());
			pstmt.setString(2, saleVO.getGoods());
			pstmt.setInt(3, saleVO.getCount());

			i = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			AlertManager.getInstance().show(AlertInfo.ERROR_TASK_DB, null);
		} finally {
			try {
				// ⑥ 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				 e.printStackTrace();
				 AlertManager.getInstance().show(AlertInfo.ERROR_TASK_DB, null);
			}
		}

		return i;

	} // end of deleteGoods
	
	public ArrayList<SaleVO> getListToDate(String date) {
		
		String dml = "select date, goods, price, count, total, coments from saleTBL where date = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SaleVO saleVO = null;
		ArrayList<SaleVO> list = new ArrayList<SaleVO>();
		
		try {
			
			con = DBUtil.getConnection();
			
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				saleVO = new SaleVO(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), 
						rs.getString(6));
				
				list.add(saleVO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			AlertManager.getInstance().show(AlertInfo.ERROR_TASK_DB, null);
		}
		
		
		return list;
	}
	
	public ArrayList<SaleVO> searchGoodsVO(String goods, String date) {
		
		String dml = "select * from saleTBL where goods like ? and date = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String likeGoods = "%" + goods + "%";
		SaleVO saleVO = null;
		ArrayList<SaleVO> list = new ArrayList<SaleVO>();
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, likeGoods);
			pstmt.setString(2, date);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				saleVO = new SaleVO(rs.getString(2), rs.getString(3), rs.getInt(4), 
						rs.getInt(5), rs.getInt(6), rs.getString(7));
				list.add(saleVO);
			}
			System.out.println(list.toString());
		} catch (Exception e) {
			AlertManager.getInstance().show(AlertInfo.ERROR_TASK_DB, null);
			e.printStackTrace();
		}
		return list;
	}
	
	

}
