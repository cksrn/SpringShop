package com.cksrn.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.cksrn.dao.AdminDAO;
import com.cksrn.domain.CategoryVO;
import com.cksrn.domain.GoodsVO;
import com.cksrn.domain.GoodsViewVO;
import com.cksrn.domain.OrderListVO;
import com.cksrn.domain.OrderVO;
import com.cksrn.domain.ReplyListVO;
@Service
public class AdminServiceImpl implements AdminService{

	@Inject
	private AdminDAO dao;
	
	//카테고리
	@Override
	public List<CategoryVO> category() throws Exception {
		return dao.category();
	}
	//상품등록
	@Override
	public void insertGoods(GoodsVO vo) throws Exception {
//		System.out.println("goodsService vo :" + vo.getGdsName());
		dao.insertGoods(vo);

	}
	//상품보기
	@Override
	public List<GoodsViewVO> selectGoods() throws Exception {
		return dao.selectGoods();
	}
	//상품 상세보기 + 카테고리 조인
	@Override
	public GoodsViewVO getGood(int gdsNum) throws Exception {
		return dao.getGood(gdsNum);
	}
	//수정하기
	@Override
	public void goodsModify(GoodsVO vo) throws Exception {
//		System.out.println("ModefyService=>" + vo.getGdsName());
		dao.goodsModify(vo);
		
	}
	//삭제하기
	@Override
	public void deleteGoods(int gdsNum) throws Exception {
		dao.deleteGoods(gdsNum);
		
	}
	//주문 목록
	@Override
	public List<OrderVO> getOrder() throws Exception {
		return dao.getOrder();
	}
	
	//특정주문목록
	@Override
	public List<OrderListVO> getOrderView(OrderVO order) throws Exception {
		return dao.getOrderView(order);
	}
	//배송상태
	@Override
	public void delivery(OrderVO order) throws Exception {
		dao.delivery(order);
		
	}
	//배송하면 수량감소
	@Override
	public void changeStock(GoodsVO goods) throws Exception {
		dao.changeStock(goods);
		
	}
	//모든 댓글보기
	@Override
	public List<ReplyListVO> allReply() throws Exception {
		return dao.allReply();
	}
	
	//모든 댓글에서 댓글삭제
	@Override
	public void deleteReply(int comNum) throws Exception {
		
		dao.deleteReply(comNum);
		
	}
	

}
