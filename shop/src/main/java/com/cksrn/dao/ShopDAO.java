package com.cksrn.dao;

import java.util.List;

import com.cksrn.domain.CartListVO;
import com.cksrn.domain.CartVO;
import com.cksrn.domain.GoodsViewVO;
import com.cksrn.domain.OrderDetailVO;
import com.cksrn.domain.OrderListVO;
import com.cksrn.domain.OrderVO;
import com.cksrn.domain.ReplyListVO;
import com.cksrn.domain.ReplyVO;

public interface ShopDAO {

	public List<GoodsViewVO> shopList(int cateCode, int cateCodeRef) throws Exception;
	
	public List<GoodsViewVO> shopList(int cateCode) throws Exception;
	
	public GoodsViewVO goodsView(int gdsNum) throws Exception;
	
	public void insertReply(ReplyVO reply) throws Exception;
	
	public List<ReplyListVO> replyList(int gdsNum) throws Exception; 
	
	public void deleteReply(ReplyVO reply) throws Exception;
	
	public String replyIdCheck(int comNum) throws Exception;
	
	public void updateReply(ReplyVO reply) throws Exception;
	
	public void insertCart(CartVO cart) throws Exception;
	
	public List<CartListVO> cartList(String userId) throws Exception;
	
	public void deleteCart(CartVO cart) throws Exception;
	
	public void insertOrder(OrderVO order) throws Exception;
	
	public void insertOrderDetails(OrderDetailVO orderDetail) throws Exception;
	
	public void cartAllDelete(String userId) throws Exception;
	
	public List<OrderVO> selectOrder(OrderVO order) throws Exception;
	
	public List<OrderListVO> orderView(OrderVO order) throws Exception;
}
