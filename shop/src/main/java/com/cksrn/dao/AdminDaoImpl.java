package com.cksrn.dao;

import java.util.List;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.cksrn.domain.CategoryVO;
import com.cksrn.domain.GoodsVO;
import com.cksrn.domain.GoodsViewVO;
import com.cksrn.domain.OrderListVO;
import com.cksrn.domain.OrderVO;
import com.cksrn.domain.ReplyListVO;
@Repository
public class AdminDaoImpl implements AdminDAO{

	@Inject
	SqlSessionTemplate sql;
	//카테고리
	@Override
	public List<CategoryVO> category() throws Exception {
		return sql.selectList("cate.getCategory");
	}
	//상품등록
	@Override
	public void insertGoods(GoodsVO vo) throws Exception {
//		System.out.println("goodsDao vo :" + vo.getGdsName());
		sql.insert("cate.insertGoods",vo);
		
	}
	//상품보기
	@Override
	public List<GoodsViewVO> selectGoods() throws Exception {
		return sql.selectList("cate.selectGoods");
	}
	//상품 상세보기 + 카테고리
	@Override
	public GoodsViewVO getGood(int gdsNum) throws Exception {
		return sql.selectOne("cate.getGood", gdsNum);
	}
	//수정하기
	@Override
	public void goodsModify(GoodsVO vo) throws Exception {
//		System.out.println("ModefyDAO=>" + vo.getGdsName());
		sql.update("cate.goodsModify", vo);
	}
	//삭제하기
	@Override
	public void deleteGoods(int gdsNum) throws Exception {
		sql.delete("cate.deleteGoods",gdsNum);
		
	}
	//주문 목록
	@Override
	public List<OrderVO> getOrder() throws Exception {	
		return sql.selectList("cate.getOrder");
	}
	//특정 주문 목록
	@Override
	public List<OrderListVO> getOrderView(OrderVO order) throws Exception {
		return sql.selectList("cate.getOrderView",order);
	}
	//배송상태
	@Override
	public void delivery(OrderVO order) throws Exception {
		sql.update("cate.delivery", order);
		
	}
	//배송시 상품수량 감소
	@Override
	public void changeStock(GoodsVO goods) throws Exception {
		sql.update("cate.changeStock", goods);
		
	}
	//모든 댓글보기
	@Override
	public List<ReplyListVO> allReply() throws Exception {
		return sql.selectList("cate.allReply");
	}
	//모든댓글에서 댓글삭제
	@Override
	public void deleteReply(int comNum) throws Exception {
		sql.delete("cate.deleteReply", comNum);
		
	}
}
