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

	//카테고리별 상품리스트
	@Override
	public List<GoodsViewVO> shopList(int cateCode, int level) throws Exception {
		int cateCodeRef = 0; //카테고리 참조 코드. 없어도 무관
		if(level==1) { //level 1 = 1차 분류
			cateCodeRef = cateCode;
			return dao.shopList(cateCode, cateCodeRef);
		}else { // level 2 = 2차 분류
			return dao.shopList(cateCode);
		}
	}
	//상품조회
	@Override
	public GoodsViewVO goodsView(int gdsNum) throws Exception {
		return dao.goodsView(gdsNum);
	}

	//상품 소감 작성
	@Override
	public void insertReply(ReplyVO reply) throws Exception {
//		System.out.println("shopService => " + reply.getComCon());
		dao.insertReply(reply);
		
	}
	//상품 소감 리스트
	@Override
	public List<ReplyListVO> replyList(int gdsNum) throws Exception {
		return dao.replyList(gdsNum);
	}
	//상품 소감 삭제
	@Override
	public void deleteReply(ReplyVO reply) throws Exception {
		dao.deleteReply(reply);
		
	}
	//아이디체크
	@Override
	public String replyIdCheck(int comNum) throws Exception {		
		return dao.replyIdCheck(comNum);
	}
	//상품 소감 수정
	@Override
	public void updateReply(ReplyVO reply) throws Exception {
		dao.updateReply(reply);
		
	}
	//카트 담기
	@Override
	public void insertCart(CartVO cart) throws Exception {
		dao.insertCart(cart);
		
	}
	//카트 리스트
	@Override
	public List<CartListVO> cartList(String userId) throws Exception {
		
		return dao.cartList(userId);
	}
	//카트 삭제
	@Override
	public void deleteCart(CartVO cart) throws Exception {
		dao.deleteCart(cart);
		
	}
	//주문하기
	@Override
	public void insertOrder(OrderVO order) throws Exception {
		dao.insertOrder(order);
		
	}
	//주문 상세정보
	@Override
	public void insertOrderDetails(OrderDetailVO orderDetail) throws Exception {
		dao.insertOrderDetails(orderDetail);
		
	}
	//주문하면 카트비우기
	@Override
	public void cartAllDelete(String userId) throws Exception {
		dao.cartAllDelete(userId);
		
	}
	//주문 목록
	@Override
	public List<OrderVO> selectOrder(OrderVO order) throws Exception {
		return dao.selectOrder(order);
	}
	//특정 주문 목록
	@Override
	public List<OrderListVO> orderView(OrderVO order) throws Exception {
		return dao.orderView(order);
	}
	

}
