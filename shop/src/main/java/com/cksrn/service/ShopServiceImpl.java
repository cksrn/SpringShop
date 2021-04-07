package com.cksrn.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.cksrn.dao.ShopDAO;
import com.cksrn.domain.CartListVO;
import com.cksrn.domain.CartVO;
import com.cksrn.domain.GoodsViewVO;
import com.cksrn.domain.OrderDetailVO;
import com.cksrn.domain.OrderListVO;
import com.cksrn.domain.OrderVO;
import com.cksrn.domain.ReplyListVO;
import com.cksrn.domain.ReplyVO;

@Service
public class ShopServiceImpl implements ShopService{
	
	@Inject
	private ShopDAO dao;

	@Override
	public List<GoodsViewVO> shopList(int cateCode, int level) throws Exception {
		int cateCodeRef = 0;
		if(level==1) {
			cateCodeRef = cateCode;
			return dao.shopList(cateCode, cateCodeRef);
		}else {
			return dao.shopList(cateCode);
		}
	}

	@Override
	public GoodsViewVO goodsView(int gdsNum) throws Exception {
		return dao.goodsView(gdsNum);
	}

	@Override
	public void insertReply(ReplyVO reply) throws Exception {
//		System.out.println("shopService => " + reply.getComCon());
		dao.insertReply(reply);
		
	}

	@Override
	public List<ReplyListVO> replyList(int gdsNum) throws Exception {
		return dao.replyList(gdsNum);
	}

	@Override
	public void deleteReply(ReplyVO reply) throws Exception {
		dao.deleteReply(reply);
		
	}

	@Override
	public String replyIdCheck(int comNum) throws Exception {		
		return dao.replyIdCheck(comNum);
	}

	@Override
	public void updateReply(ReplyVO reply) throws Exception {
		dao.updateReply(reply);
		
	}

	@Override
	public void insertCart(CartVO cart) throws Exception {
		dao.insertCart(cart);
		
	}

	@Override
	public List<CartListVO> cartList(String userId) throws Exception {
		
		return dao.cartList(userId);
	}

	@Override
	public void deleteCart(CartVO cart) throws Exception {
		dao.deleteCart(cart);
		
	}

	@Override
	public void insertOrder(OrderVO order) throws Exception {
		dao.insertOrder(order);
		
	}

	@Override
	public void insertOrderDetails(OrderDetailVO orderDetail) throws Exception {
		dao.insertOrderDetails(orderDetail);
		
	}

	@Override
	public void cartAllDelete(String userId) throws Exception {
		dao.cartAllDelete(userId);
		
	}

	@Override
	public List<OrderVO> selectOrder(OrderVO order) throws Exception {
		return dao.selectOrder(order);
	}

	@Override
	public List<OrderListVO> orderView(OrderVO order) throws Exception {
		return dao.orderView(order);
	}
	

}
