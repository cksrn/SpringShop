package com.cksrn.dao;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.cksrn.domain.CartListVO;
import com.cksrn.domain.CartVO;
import com.cksrn.domain.GoodsViewVO;
import com.cksrn.domain.OrderDetailVO;
import com.cksrn.domain.OrderListVO;
import com.cksrn.domain.OrderVO;
import com.cksrn.domain.ReplyListVO;
import com.cksrn.domain.ReplyVO;
@Repository
public class ShopDaoImpl implements ShopDAO{

	@Inject
	private SqlSessionTemplate sql;
	
	// 카테고리별 상품리스트 1
	@Override
	public List<GoodsViewVO> shopList(int cateCode, int cateCodeRef) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("cateCode", cateCode);
		map.put("cateCodeRef", cateCodeRef);
		return sql.selectList("shop.shopList_1", map);
	}

	@Override
	public List<GoodsViewVO> shopList(int cateCode) throws Exception {
		return sql.selectList("shop.shopList_2",cateCode);
	}

	@Override
	public GoodsViewVO goodsView(int gdsNum) throws Exception {
		return sql.selectOne("shop.goodsView",gdsNum);
	}

	@Override
	public void insertReply(ReplyVO reply) throws Exception {
//		System.out.println("shopDAO => " + reply.getComCon());
		sql.insert("shop.insertReply", reply);
		
	}

	@Override
	public List<ReplyListVO> replyList(int gdsNum) throws Exception {
		return sql.selectList("shop.replyList",gdsNum);
	}

	@Override
	public void deleteReply(ReplyVO reply) throws Exception {
		sql.delete("shop.deleteReply", reply);
		
	}

	@Override
	public String replyIdCheck(int comNum) throws Exception {
		return sql.selectOne("shop.replyIdCheck", comNum);
		
	}

	@Override
	public void updateReply(ReplyVO reply) throws Exception {
		sql.update("shop.updateReply", reply);
		
	}

	@Override
	public void insertCart(CartVO cart) throws Exception {
		sql.insert("shop.insertCart", cart);
		
	}

	@Override
	public List<CartListVO> cartList(String userId) throws Exception {
		return sql.selectList("shop.cartList",userId);
	}

	@Override
	public void deleteCart(CartVO cart) throws Exception {
		sql.delete("shop.deleteCart",cart);
		
	}

	@Override
	public void insertOrder(OrderVO order) throws Exception {
		sql.insert("shop.insertOrder", order);
		
	}

	@Override
	public void insertOrderDetails(OrderDetailVO orderDetail) throws Exception {
		sql.insert("shop.insertOrderDetails", orderDetail);
		
	}

	@Override
	public void cartAllDelete(String userId) throws Exception {
		sql.delete("shop.cartAllDelete", userId);
		
	}

	@Override
	public List<OrderVO> selectOrder(OrderVO order) throws Exception {
		return sql.selectList("shop.selectOrder",order);
	}

	@Override
	public List<OrderListVO> orderView(OrderVO order) throws Exception {
		return sql.selectList("shop.orderView",order);
	}
	

}
