package com.cksrn.dao;

import java.util.List;

import com.cksrn.domain.CategoryVO;
import com.cksrn.domain.GoodsVO;
import com.cksrn.domain.GoodsViewVO;
import com.cksrn.domain.OrderListVO;
import com.cksrn.domain.OrderVO;
import com.cksrn.domain.ReplyListVO;

public interface AdminDAO {

	//카테고리
	public List<CategoryVO> category() throws Exception;
	
	//상품등록
	public void insertGoods(GoodsVO vo) throws Exception;
	
	//상품보기
	public List<GoodsViewVO> selectGoods() throws Exception;
	
	//상품 상세보기 + 카테고리
	public GoodsViewVO getGood(int gdsNum) throws Exception;
	
	//상품수정
	public void goodsModify(GoodsVO vo) throws Exception;
	
	//상품삭제
	public void deleteGoods(int gdsNum) throws Exception;
	
	//전체 주문목록
	public List<OrderVO> getOrder() throws Exception;
	
	//특정 주문목룍
	public List<OrderListVO> getOrderView(OrderVO order) throws Exception;
	
	//배송상태
	public void delivery(OrderVO order) throws Exception;
	
	//배송중,완료 상품수량 조절
	public void changeStock(GoodsVO goods) throws Exception;
	
	//모든 소감  보기
	public List<ReplyListVO> allReply() throws Exception;
	
	// 댓글 삭제
	public void deleteReply(int comNum) throws Exception;
}
