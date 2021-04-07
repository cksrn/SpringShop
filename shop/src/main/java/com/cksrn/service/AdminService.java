package com.cksrn.service;

import java.util.List;

import com.cksrn.domain.CategoryVO;
import com.cksrn.domain.GoodsVO;
import com.cksrn.domain.GoodsViewVO;
import com.cksrn.domain.OrderListVO;
import com.cksrn.domain.OrderVO;
import com.cksrn.domain.ReplyListVO;

public interface AdminService {

	//카테고리
	public List<CategoryVO> category() throws Exception;
	
	//상품등록
	public void insertGoods(GoodsVO vo) throws Exception;
	
	//상품보기
	public List<GoodsViewVO> selectGoods() throws Exception;
	
	//상품 상세보기 + 카테
	public GoodsViewVO getGood(int gdsNum) throws Exception;
	
	//수정하기
	public void goodsModify(GoodsVO vo) throws Exception;
	
	//삭제하기
	public void deleteGoods(int gdsNum) throws Exception;
	
	//주문 목록
	public List<OrderVO> getOrder() throws Exception;
	
	//특정 주문 목록
	public List<OrderListVO> getOrderView(OrderVO order) throws Exception;
	
	//배송상태
	public void delivery(OrderVO order) throws Exception;
	
	//배송 중, 완료 상품수량 조절
	public void changeStock(GoodsVO goods) throws Exception;
	
	//모든댓글보기
	public List<ReplyListVO> allReply() throws Exception;
	
	//댓글삭제
	public void deleteReply(int comNum) throws Exception;
}
