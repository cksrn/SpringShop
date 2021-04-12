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
	
	// 카테고리별 상품리스트 1차분류
	@Override
	public List<GoodsViewVO> shopList(int cateCode, int cateCodeRef) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("cateCode", cateCode);
		map.put("cateCodeRef", cateCodeRef);
		return sql.selectList("shop.shopList_1", map);
	}

	//카테고리별 상품 리스트 2차분류
	@Override
	public List<GoodsViewVO> shopList(int cateCode) throws Exception {
		return sql.selectList("shop.shopList_2",cateCode);
	}
	//매서드명은 shoplist로 똑같지만, 매개변수가 다르기 때문에 오버로딩 가능 

	//상품조회
	@Override
	public GoodsViewVO goodsView(int gdsNum) throws Exception {
		return sql.selectOne("shop.goodsView",gdsNum);
	}

	//상품 소감 작성
	@Override
	public void insertReply(ReplyVO reply) throws Exception {
//		System.out.println("shopDAO => " + reply.getComCon());
		sql.insert("shop.insertReply", reply);
		
	}

	//상품 소감 리스트
	@Override
	public List<ReplyListVO> replyList(int gdsNum) throws Exception {
		return sql.selectList("shop.replyList",gdsNum);
	}

	//상품 소감 삭제
	@Override
	public void deleteReply(ReplyVO reply) throws Exception {
		sql.delete("shop.deleteReply", reply);
		
	}

	//아이디체크
	@Override
	public String replyIdCheck(int comNum) throws Exception {
		return sql.selectOne("shop.replyIdCheck", comNum);
		
	}

	//상품 소감 수정
	@Override
	public void updateReply(ReplyVO reply) throws Exception {
		sql.update("shop.updateReply", reply);
		
	}

	//카트담기
	@Override
	public void insertCart(CartVO cart) throws Exception {
		sql.insert("shop.insertCart", cart);
		
	}

	//카트리스트
	@Override
	public List<CartListVO> cartList(String userId) throws Exception {
		return sql.selectList("shop.cartList",userId);
	}

	//카트삭제
	@Override
	public void deleteCart(CartVO cart) throws Exception {
		sql.delete("shop.deleteCart",cart);
		
	}
	//주문하기
	@Override
	public void insertOrder(OrderVO order) throws Exception {
		sql.insert("shop.insertOrder", order);
		
	}
	//주문 상세정보
	@Override
	public void insertOrderDetails(OrderDetailVO orderDetail) throws Exception {
		sql.insert("shop.insertOrderDetails", orderDetail);
		
	}
	//주문하면 카트비우기
	@Override
	public void cartAllDelete(String userId) throws Exception {
		sql.delete("shop.cartAllDelete", userId);
		
	}
	//주문 목록
	@Override
	public List<OrderVO> selectOrder(OrderVO order) throws Exception {
		return sql.selectList("shop.selectOrder",order);
	}

	//특정 주문 목록
	@Override
	public List<OrderListVO> orderView(OrderVO order) throws Exception {
		return sql.selectList("shop.orderView",order);
	}
	

}
