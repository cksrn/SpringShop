package com.cksrn.service;

import java.util.List;

import com.cksrn.domain.CartListVO;
import com.cksrn.domain.CartVO;
import com.cksrn.domain.GoodsViewVO;
import com.cksrn.domain.OrderDetailVO;
import com.cksrn.domain.OrderListVO;
import com.cksrn.domain.OrderVO;
import com.cksrn.domain.ReplyListVO;
import com.cksrn.domain.ReplyVO;

public interface ShopService {
	//카테고리별 상품
	public List<GoodsViewVO> shopList(int cateCode, int level) throws Exception;
	//상품조회
	public GoodsViewVO goodsView(int gdsNum) throws Exception;
	
	//댓글등록
	public void insertReply(ReplyVO reply) throws Exception;
	
	//댓글목록
	public List<ReplyListVO> replyList(int gdsNum) throws Exception;
	
	//댓글삭제
	public void deleteReply(ReplyVO reply) throws Exception;
	
	//아이디 검색
	public String replyIdCheck(int comNum) throws Exception;
	
	//댓글 수정
	public void updateReply(ReplyVO reply) throws Exception;
	
	//카트 담기
	public void insertCart(CartVO cart) throws Exception;
	
	//카트담은 리스트
	public List<CartListVO> cartList(String userId) throws Exception;
	
	//카트담은 리스트 삭제
	public void deleteCart(CartVO cart) throws Exception;
	
	//주문 정보
	public void insertOrder(OrderVO order) throws Exception;
	
	//주문 상세정보
	public void insertOrderDetails(OrderDetailVO orderDetail) throws Exception;
	
	//주문하면 카트리스트 삭제
	public void cartAllDelete(String userId) throws Exception;
	
	//주문 목록
	public List<OrderVO> selectOrder(OrderVO order) throws Exception;
	
	//특정 주문 목록
	public List<OrderListVO> orderView(OrderVO order) throws Exception;
}
