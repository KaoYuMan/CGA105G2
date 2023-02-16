package com.order.model.Order.dao.impl;

import static com.core.common.Common.PASSWORD;
import static com.core.common.Common.USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import com.code.model.Code.pojo.Code;
import com.core.common.Common;
import com.order.model.Order.dao.OrderDAO_interface;
import com.order.model.Order.pojo.Order;
import com.order.model.OrderDetail.dao.impl.OrderDetailJDBCDAO;
import com.order.model.OrderDetail.pojo.OrderDetail;

public class OrderJDBCDAO implements OrderDAO_interface {

	@Override
	public void insert(Order order) {
		final String sql = "INSERT INTO cga105g2.order (mem_id,store_id,order_price,code_id,order_fre,order_fprice,order_text) VALUES (?, ? ,?, ?, ?, ?, ? )";
		try (Connection con = DriverManager.getConnection(Common.URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
			// 不要自動Commit
			con.setAutoCommit(false);

			pstmt.setInt(1, order.getMemId());
			pstmt.setInt(2, order.getStoreId());
			pstmt.setInt(3, order.getOrderPrice());
			pstmt.setInt(4, order.getCodeId());
			pstmt.setInt(5, order.getOrderFre());
			pstmt.setInt(6, order.getOrderFprice());
			pstmt.setString(7, order.getOrderText());

			pstmt.executeUpdate();
			// 新增訂單明細
			OrderDetailJDBCDAO orderDetailJDBCDAO = new OrderDetailJDBCDAO();
			orderDetailJDBCDAO.insert(null);

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
	}

	@Override
	public void update(Order order) {
		final String sql = "UPDATE cga105g2.order SET mem_id=?,store_id=?,order_price=?,code_id=?,order_fre=?,order_fprice=?,order_text=?,order_status=?,order_otime=? WHERE order_id = ?";
		try (Connection con = DriverManager.getConnection(Common.URL, USER, PASSWORD);

				PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
			pstmt.setInt(1, order.getMemId());
			pstmt.setInt(2, order.getStoreId());
			pstmt.setInt(3, order.getOrderPrice());
			pstmt.setInt(4, order.getCodeId());
			pstmt.setInt(5, order.getOrderFre());
			pstmt.setInt(6, order.getOrderFprice());
			pstmt.setString(7, order.getOrderText());
			pstmt.setInt(8, order.getOrderStatus());
			pstmt.setDate(9, order.getOrderOtime());
			pstmt.setInt(10, order.getOrderId());
			pstmt.executeUpdate();
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
	}

	private static final String DELETE_OEDER = "DELETE FROM cga105g2.order where order_id = ?";
	private static final String DELETE_DETAILs = "DELETE FROM cga105g2.order_detail where order_id = ?";
	private static DataSource ds = null;

	@Override
	public void delete(Integer orderno) {
		int updateCount_ORDERs = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);

			// 先刪除員工
			pstmt = con.prepareStatement(DELETE_OEDER);
			pstmt.setInt(1, orderno);
			updateCount_ORDERs = pstmt.executeUpdate();
			// 再刪除部門
			pstmt = con.prepareStatement(DELETE_OEDER);
			pstmt.setInt(1, orderno);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除部門編號" + orderno + "時,共有員工" + updateCount_ORDERs + "人同時被刪除");

			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}


	@Override
	public Order getById(Integer orderno) {
		final String sql = "SELECT order_id,mem_id,store_id,order_price,code_id,order_fre,order_fprice,order_text,order_status,order_time,order_otime,order_rtime FROM cga105g2.order where order_id = ?";
		Order order = null;
		try (Connection con = DriverManager.getConnection(Common.URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
			pstmt.setInt(1, orderno);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				order = new Order();
				order.setOrderId(rs.getInt("order_id"));
				order.setMemId(rs.getInt("mem_id"));
				order.setStoreId(rs.getInt("store_id"));
				order.setOrderPrice(rs.getInt("order_price"));
				order.setCodeId(rs.getInt("code_id"));
				order.setOrderFre(rs.getInt("order_fre"));
				order.setOrderFprice(rs.getInt("order_fprice"));
				order.setOrderText(rs.getString("order_text"));
				order.setOrderStatus(rs.getInt("order_status"));
				order.setOrderTime(rs.getTimestamp("order_time"));
				order.setOrderOtime(rs.getDate("order_otime"));
				order.setOrderRtime(rs.getTimestamp("order_rtime"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
		return order;
	}

	@Override
	public List<Order> getByMemId(Integer memId) {
		final String sql = "SELECT order_id,mem_id,store_id,order_price,code_id,order_fre,order_fprice,order_text,order_status,order_time,order_otime,order_rtime FROM cga105g2.order WHERE mem_id = ?";
		List<Order> list = new ArrayList<Order>();
		Order order = null;
		try (Connection con = DriverManager.getConnection(Common.URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
			pstmt.setInt(1, memId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				order = new Order();
				order.setOrderId(rs.getInt("order_id"));
				order.setMemId(rs.getInt("mem_id"));
				order.setStoreId(rs.getInt("store_id"));
				order.setOrderPrice(rs.getInt("order_price"));
				order.setCodeId(rs.getInt("code_id"));
				order.setOrderFre(rs.getInt("order_fre"));
				order.setOrderFprice(rs.getInt("order_fprice"));
				order.setOrderText(rs.getString("order_text"));
				order.setOrderStatus(rs.getInt("order_status"));
				order.setOrderTime(rs.getTimestamp("order_time"));
				order.setOrderOtime(rs.getDate("order_otime"));
				order.setOrderRtime(rs.getTimestamp("order_rtime"));
				list.add(order); // Store the row in the list
			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
		return list;
	}

	@Override
	public Set<Order> getOrderByStoreId(Integer storeId) {
		return null;
	}

	@Override
	public void updateOrdStat(Order order) {

	}

	@Override
	public Integer genOrderId() {
		return null;
	}

	private static final String INSERT_STMT = "INSERT INTO cga105g2.order (mem_id,store_id,order_price,order_fre,order_fprice) VALUES ( ? ,?, ?, ?, ? )";

	@Override
	public void insertWithDetail(Order order, List<OrderDetail> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = Common.URL;
		String userid = USER;
		String passwd = PASSWORD;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);

			// 先新增訂單
			String cols[] = { "ORDER_ID" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			pstmt.setInt(1, order.getMemId());
			pstmt.setInt(2, order.getStoreId());
			pstmt.setInt(3, order.getOrderPrice());
//			pstmt.setInt(4, order.getCodeId());
			pstmt.setInt(4, order.getOrderFre());
			pstmt.setInt(5, order.getOrderFprice());

			Statement stmt = con.createStatement();
//stmt.executeUpdate("set auto_increment_offset=0;");    //自增主鍵-初始值
//stmt.executeUpdate("set auto_increment_increment=1;"); //自增主鍵-遞增
			pstmt.executeUpdate();
			// 掘取對應的自增主鍵值
			String next_orderId = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_orderId = rs.getString(1);
				System.out.println("自增主鍵值= " + next_orderId + "(剛新增成功的訂單編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			// 再同時新增員工
			OrderDetailJDBCDAO dao = new OrderDetailJDBCDAO();
			System.out.println("list.size()-A=" + list.size());
			for (OrderDetail aOrderDetail : list) {
				aOrderDetail.setOrderId(Integer.valueOf(next_orderId));
				dao.insert2(aOrderDetail, con);
			}

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("list.size()-B=" + list.size());
			System.out.println("新增" + next_orderId + "時,共有明細" + list.size() + "條同時被新增");

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-order");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public Code checkCodeDiscount(String codeNum) {
		final String sql = "SELECT * FROM cga105g2.code  WHERE  code_num  = ? ";
		Code code = null;
		try (Connection con = DriverManager.getConnection(Common.URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
			pstmt.setString(1, codeNum);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				code = new Code();
				code.setCodeId(rs.getInt("code_id"));
				code.setStoreId(rs.getInt("store_id"));
				code.setEmpId(rs.getInt("emp_id"));
				code.setCodeNum(rs.getString("code_num"));
				code.setCodeOff(rs.getInt("code_off"));
				code.setCodeStatus(rs.getInt("code_status"));
				code.setCodeText(rs.getString("code_text"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}
		return code;
	}

	// 測試

}
