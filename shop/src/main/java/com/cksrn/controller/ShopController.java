package com.cksrn.controller;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cksrn.domain.CartListVO;
import com.cksrn.domain.CartVO;
import com.cksrn.domain.GoodsViewVO;
import com.cksrn.domain.MemberVO;
import com.cksrn.domain.OrderDetailVO;
import com.cksrn.domain.OrderListVO;
import com.cksrn.domain.OrderVO;
import com.cksrn.domain.ReplyListVO;
import com.cksrn.domain.ReplyVO;
import com.cksrn.service.ShopService;

@Controller
@RequestMapping("/shop/*")
public class ShopController {

	@Inject
	private ShopService service;
	//카테고리별 상품 리스트
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public void getList(@RequestParam("c") int cateCode, @RequestParam("L") int level, Model model) throws Exception{
		List<GoodsViewVO> list = service.shopList(cateCode,level);
		model.addAttribute("list", list);
	}
	
	//상품조회
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public void getView(@RequestParam("n") int gdsNum, Model model) throws Exception{
		GoodsViewVO view = service.goodsView(gdsNum);
		model.addAttribute("view", view);
	}
	
	//댓글작성
//	@RequestMapping(value="/view", method=RequestMethod.POST)
//	public String insertReply(ReplyVO reply, HttpSession session) throws Exception{
//		
//		MemberVO member = (MemberVO)session.getAttribute("member");
//		reply.setUserId(member.getUserId());
//		System.out.println("shopController => " + reply.getComCon());
//		service.insertReply(reply);
//		return "redirect:/shop/view?n=" + reply.getGdsNum();
//	}
	
	//댓글작성 ajax
	@ResponseBody
	@RequestMapping(value = "view/registReply", method = RequestMethod.POST)
	public void insertReply(ReplyVO reply, HttpSession session) throws Exception{
		MemberVO member = (MemberVO)session.getAttribute("member");
		reply.setUserId(member.getUserId());
		
		service.insertReply(reply);
	}
	
	// 댓글보기
	@ResponseBody
	@RequestMapping(value="/view/replyList",method = RequestMethod.GET)
	public List<ReplyListVO> getReplyList(@RequestParam("n") int gdsNum) throws Exception{	
		List<ReplyListVO> reply = service.replyList(gdsNum);
		return reply;
	}
	
	//댓글삭제
	@ResponseBody
	@RequestMapping(value="/view/deleteReply",method=RequestMethod.POST)
	public int getReplyList(ReplyVO reply, HttpSession session) throws Exception{
		int result = 0;
		
		MemberVO member = (MemberVO)session.getAttribute("member");//현재 로그인한 member 세션을 가져옴
		String userId = service.replyIdCheck(reply.getComNum());//소감을 작성한 사용자의 아이디를 가져옴
//		System.out.println("member =>" + member.getUserId());
//		System.out.println("userId =>" + userId);
		//로그인한 아이디와, 소감을 작성한 아이디를 비교
		if(member.getUserId().equals(userId)) {
			//로그인한 아이디가 작성한 아이디와 같다면
			reply.setUserId(member.getUserId()); //reply에 userId 저장
			service.deleteReply(reply); //서비스의 deleteReply 메서드 실행
			//결과값변경
			result =1;
		}
		//정상적으로 실행되면 소감 삭제가 진행되고, result값은 1이지만
		//비정상적으로 실행되면 소감 삭제가 안되고,result값이 0
		return result;
	}
	
	//상품 댓글 수정
	@ResponseBody
	@RequestMapping(value="/view/modifyReply",method = RequestMethod.POST)
	public int modifyReply(ReplyVO reply, HttpSession session) throws Exception{
		int result = 0;
		MemberVO member = (MemberVO)session.getAttribute("member");
		String userId = service.replyIdCheck(reply.getComNum());
//		System.out.println("member =>" + member.getUserId());
//		System.out.println("userId =>" + userId);
		if(member.getUserId().equals(userId)) {
			reply.setUserId(member.getUserId());
			service.updateReply(reply);
			result=1;
		}
		return result;
	}
	
	//카트 담기
	@ResponseBody
	@RequestMapping(value="/view/addCart",method = RequestMethod.POST)
	public int addCart(CartVO cart, HttpSession session) throws Exception{
		
		int result = 0;	
		
		MemberVO member = (MemberVO)session.getAttribute("member");
			
		if(member != null) {
			cart.setUserId(member.getUserId());
			service.insertCart(cart);
			result=1;
		}
		return result;
	}
	//카트리스트보기
	@RequestMapping(value="/cartList", method = RequestMethod.GET)
	public void getCartList(HttpSession session, Model model) throws Exception{
		MemberVO member = (MemberVO)session.getAttribute("member");
		String userId = member.getUserId();
		
		List<CartListVO> cartList = service.cartList(userId);
		
		model.addAttribute("cartList", cartList);
	}
	
	//카트리스트 삭제
	@ResponseBody
	@RequestMapping(value="/deleteCart",method = RequestMethod.POST)
	public int deleteCart(HttpSession session,@RequestParam(value="chbox[]") List<String> chArr,CartVO cart) throws Exception{
		MemberVO member = (MemberVO)session.getAttribute("member");
		String userId = member.getUserId();
		
		int result = 0;
		int cartNum = 0;
		//로그인 여부 구분
		if(member != null) {
			cart.setUserId(userId);
			
			for(String i : chArr) {//에이젝스에서 받은 chArr의 갯수만큼 반복
				cartNum = Integer.parseInt(i);// i번재 데이터를 cartNum에저장
				cart.setCartNum(cartNum);
				service.deleteCart(cart);
			}
			result = 1;
		}
		return result;
	}
	
	//주문하기
	@RequestMapping(value="/cartList",method=RequestMethod.POST)
	public String insertOrder(HttpSession session, OrderVO order, OrderDetailVO orderDetail) throws Exception{
		MemberVO member = (MemberVO)session.getAttribute("member");
		String userId = member.getUserId();
		//캘린더 호출
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//연도 추출
		String ym = year + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);//월 추출
		String ymd = ym + new DecimalFormat("00").format(cal.get(Calendar.DATE));//일 추출
		String subNum =""; //랜덤 숫자를 저장할 문자열 변수
		for(int i = 1; i<=6; i++) {//6회반복
			subNum += (int)(Math.random() * 10);//0~9까지 숫자를 생성하고 subNum에 저장
		}
		String orderId = ymd + "_" + subNum; //연월일_랜덤숫자로 구성된 문자
		
//		System.out.println("orderId =>" + order.getOrderId());
//		System.out.println("detailId => " + orderDetail.getOrderId());
		order.setOrderId(orderId);
		order.setUserId(userId);
		
		service.insertOrder(order);
		
		orderDetail.setOrderId(orderId);
		service.insertOrderDetails(orderDetail);
		//주문 테이블, 주문 상세 테이블에 데이터를 전송하고 카트비우기
		service.cartAllDelete(userId);
		return "redirect:/shop/orderList";
	}
	
	//주문 목록
	@RequestMapping(value="/orderList", method=RequestMethod.GET)
	public void getSelectOrder(HttpSession session,OrderVO order, Model model) throws Exception{
		MemberVO member = (MemberVO)session.getAttribute("member");
		String userId = member.getUserId();
		
		order.setUserId(userId);
		
		List<OrderVO> orderList = service.selectOrder(order);
		
		model.addAttribute("orderList", orderList);
	}
	
	//주문 상세 목록
	@RequestMapping(value ="/orderView",method=RequestMethod.GET)
	public void getOrderList(HttpSession session, @RequestParam("n") String orderId, OrderVO order, Model model) throws Exception{
		MemberVO member = (MemberVO)session.getAttribute("member");
		String userId = member.getUserId();
		
		order.setUserId(userId);
		order.setOrderId(orderId);
		
		List<OrderListVO> orderView = service.orderView(order);
		
		model.addAttribute("orderView", orderView);
	}
}
