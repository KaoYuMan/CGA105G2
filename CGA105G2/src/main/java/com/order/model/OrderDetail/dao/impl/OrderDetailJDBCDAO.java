package com.order.model.OrderDetail.dao.impl;

import static com.core.common.Common.PASSWORD;
import static com.core.common.Common.USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.core.common.Common;
import com.order.model.OrderDetail.dao.OrderDetailDAO_interface;
import com.order.model.OrderDetail.pojo.OrderDetail;

public class OrderDetailJDBCDAO implements OrderDetailDAO_interface {

	@Override
	public void insert(OrderDetail orderDetail) {
		final String sql = "INSERT INTO cga105g2.order_detail (order_id,goods_id,detail_quantity) VALUES (?, ?, ?)";
		try (Connection con = DriverManager.getConnection(Common.URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
			pstmt.setInt(1, orderDetail.getOrderId());
			pstmt.setInt(2, orderDetail.getGoodsId());
			pstmt.setInt(3, orderDetail.getDetailQuantity());
			pstmt.executeUpdate();
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
	}

	@Override
	public void update(OrderDetail orderDetail) {
		final String sql = "UPDATE cga105g2.order_detail set order_id=?, goods_id=?, detail_quantity=? where order_id = ? AND goods_id=?";
		try (Connection con = DriverManager.getConnection(Common.URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
			pstmt.setInt(1, orderDetail.getOrderId());
			pstmt.setInt(2, orderDetail.getGoodsId());
			pstmt.setInt(3, orderDetail.getDetailQuantity());
			pstmt.setInt(4, orderDetail.getOrderId());
			pstmt.setInt(5, orderDetail.getGoodsId());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
	}

	@Override
	public void delete(Integer orderDetailVO, Integer orderDetailVO2) {
		final String sql = "DELETE FROM cga105g2.order_detail where order_id = ? AND goods_id=?";
		try (Connection con = DriverManager.getConnection(Common.URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
			pstmt.setInt(1, orderDetailVO);
			pstmt.setInt(2, orderDetailVO2);
			pstmt.executeUpdate();
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
	}

	@Override
	public OrderDetail getById(Integer orderDetailno, Integer orderDetailno2) {
		final String sql = "SELECT order_id,goods_id,detail_quantity,detailprice FROM cga105g2.order_detail where order_id = ? AND goods_id=? order by order_id";
		OrderDetail orderDetail = null;
		try (Connection con = DriverManager.getConnection(Common.URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
			pstmt.setInt(1, orderDetailno);
			pstmt.setInt(2, orderDetailno2);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				// empVo �]�٬� Domain objects
				orderDetail = new OrderDetail();
				orderDetail.setOrderId(rs.getInt("order_id"));
				orderDetail.setGoodsId(rs.getInt("goods_id"));
				orderDetail.setDetailQuantity(rs.getInt("detail_quantity"));
			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
		return orderDetail;
	}

	@Override
	public List<OrderDetail> getAll(Integer orderId) {
		final String sql = "SELECT order_id,goods_id,detail_quantity,detailprice FROM cga105g2.order_detail WHERE order_id = ?";
		List<OrderDetail> list = new ArrayList<OrderDetail>();
		OrderDetail orderDetail = null;
		ResultSet rs = null;
		try (Connection con = DriverManager.getConnection(Common.URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {

			pstmt.setInt(1, orderId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderDetail = new OrderDetail();
				orderDetail.setOrderId(rs.getInt("order_id"));
				orderDetail.setGoodsId(rs.getInt("goods_id"));
				orderDetail.setDetailQuantity(rs.getInt("detail_quantity"));
				orderDetail.setDetailPrice(rs.getInt("detailprice"));
				list.add(orderDetail); // Store the row in the list
			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
		return list;
	}

	@Override
	public List<OrderDetail> getAll(Map<String, String[]> map) {
		// TODO Auto-generated method stub
		return null;
	}

	private static final String INSERT_STMT = "INSERT INTO cga105g2.order_detail (order_id,goods_id,detail_quantity,detailPrice) VALUES (?, ?, ?, ?)";

	@Override
	public void insert2(OrderDetail orderDetail, Connection con) {

		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(INSERT_STMT);

			System.out.println("orderId:" + orderDetail.getOrderId());
			System.out.println("orderDetail:" + orderDetail.getGoodsId());
			System.out.println("--------");
			pstmt.setInt(1, orderDetail.getOrderId());
			pstmt.setInt(2, orderDetail.getGoodsId());
			pstmt.setInt(3, orderDetail.getDetailQuantity());
			pstmt.setInt(4, orderDetail.getDetailPrice());

//	Statement stmt=	con.createStatement();
//  stmt.executeUpdate("set auto_increment_offset=1;"); //自增主鍵-初始值
//	stmt.executeUpdate("set auto_increment_increment=1;");   //自增主鍵-遞增
			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-orderDetail");
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
		}
	}

}
