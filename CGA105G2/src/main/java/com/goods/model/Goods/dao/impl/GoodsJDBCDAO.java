package com.goods.model.Goods.dao.impl;

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
import com.goods.jdbc.util.goodsUtil;
import com.goods.model.Goods.dao.GoodsDAO_interface;
import com.goods.model.Goods.pojo.Goods;

public class GoodsJDBCDAO implements GoodsDAO_interface {
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(Goods Goods) {

		final String sql = "INSERT INTO cga105g2.goods (store_id,goods_img,goods_name,goods_status,goods_price,goods_text) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection con = DriverManager.getConnection(Common.URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {

			pstmt.setInt(1, Goods.getStoreId());
			pstmt.setBytes(2, Goods.getGoodsImg());
			pstmt.setString(3, Goods.getGoodsName());
			pstmt.setInt(4, Goods.getGoodsStatus());
			pstmt.setInt(5, Goods.getGoodsPrice());
			pstmt.setString(6, Goods.getGoodsText());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
	}

	@Override
	public void update(Goods Goods) {
		final String sql = "UPDATE cga105g2.goods set store_id=?, goods_img=?, goods_name=?, goods_status=?, goods_price=?, goods_text=? where goods_id = ?";
		try (Connection con = DriverManager.getConnection(Common.URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
			pstmt.setInt(1, Goods.getStoreId());
			pstmt.setBytes(2, Goods.getGoodsImg());
			pstmt.setString(3, Goods.getGoodsName());
			pstmt.setInt(4, Goods.getGoodsStatus());
			pstmt.setInt(5, Goods.getGoodsPrice());
			pstmt.setString(6, Goods.getGoodsText());
			pstmt.setInt(7, Goods.getGoodsId());
			pstmt.executeUpdate();
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
	}

	@Override
	public void delete(Integer goodsno) {
		final String sql = "DELETE FROM cga105g2.goods where goods_id = ?";
		try (Connection con = DriverManager.getConnection(Common.URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
			pstmt.setInt(1, goodsno);
			pstmt.executeUpdate();
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
	}

	@Override
	public Goods getById(Integer goodsno) {
		final String sql = "SELECT * FROM cga105g2.goods where goods_id = ?";
		Goods goods = null;
		try (Connection con = DriverManager.getConnection(Common.URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
			pstmt.setInt(1, goodsno);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				goods = new Goods();
				goods.setGoodsId(rs.getInt("goods_id"));
				goods.setGoodsImg(rs.getBytes("goods_img"));
				goods.setGoodsName(rs.getString("goods_name"));
				goods.setGoodsStatus(rs.getInt("goods_status"));
				goods.setGoodsPrice(rs.getInt("goods_price"));
				goods.setGoodsText(rs.getString("goods_text"));
				goods.setGoodsTime(rs.getTimestamp("goods_time"));
				goods.setGoodsRtime(rs.getTimestamp("goods_rtime"));
				goods.setStoreId(rs.getInt("store_id"));
			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
		return goods;
	}

	@Override
	public List<Goods> getAll(Integer storeno) {
		final String sql = "SELECT goods_id, store_id, goods_img, goods_name, goods_status, goods_price, goods_text, goods_time, goods_rtime FROM cga105g2.goods WHERE store_id = ?;  ";
		List<Goods> list = new ArrayList<Goods>();
		Goods goods = null;
		try (Connection con = DriverManager.getConnection(Common.URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
			pstmt.setInt(1, storeno);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				goods = new Goods();
				goods.setGoodsId(rs.getInt("goods_id"));
				goods.setStoreId(rs.getInt("store_id"));
				goods.setGoodsImg(rs.getBytes("goods_img"));
				goods.setGoodsName(rs.getString("goods_name"));
				goods.setGoodsStatus(rs.getInt("goods_status"));
				goods.setGoodsPrice(rs.getInt("goods_price"));
				goods.setGoodsText(rs.getString("goods_text"));
				goods.setGoodsTime(rs.getTimestamp("goods_time"));
				goods.setGoodsRtime(rs.getTimestamp("goods_rtime"));
				list.add(goods);
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}
		return list;
	}

	@Override
	public List<Goods> getAllGoods(Map<String, String[]> map) {
		List<Goods> list = new ArrayList<Goods>();
		Goods goods = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Common.URL, USER, PASSWORD);
			String finalSQL = "select * from goods " + goodsUtil.getWhereCondition(map) + "order by goods_id";
			pstmt = con.prepareStatement(finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				goods = new Goods();
				goods .setGoodsId(rs.getInt("goods_id"));
				goods.setStoreId(rs.getInt("store_id"));
				goods.setGoodsImg(rs.getBytes("goods_img"));
				goods.setGoodsName(rs.getString("goods_name"));
				goods.setGoodsStatus(rs.getInt("goods_status"));
				goods.setGoodsPrice(rs.getInt("goods_price"));
				goods.setGoodsText(rs.getString("goods_text"));
				goods.setGoodsTime(rs.getTimestamp("goods_time"));
				goods.setGoodsRtime(rs.getTimestamp("goods_rtime"));
				list.add(goods);
			
			}
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
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
		return list;
	}


}
