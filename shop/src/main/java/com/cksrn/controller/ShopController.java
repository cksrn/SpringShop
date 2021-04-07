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
		
		MemberVO member = (MemberVO)session.getAttribute("member");
		String userId = service.replyIdCheck(reply.getComNum());
//		System.out.println("member =>" + member.getUserId());
//		System.out.println("userId =>" + userId);
		if(member.getUserId().equals(userId)) {
			reply.setUserId(member.getUserId());
			service.deleteReply(reply);
			
			result =1;
		}
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
		
		if(member != null) {
			cart.setUserId(userId);
			
			for(String i : chArr) {
				cartNum = Integer.parseInt(i);
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
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String ym = year + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
		String ymd = ym + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		String subNum ="";
		for(int i = 1; i<=6; i++) {
			subNum += (int)(Math.random() * 10);
		}
		String orderId = ymd + "_" + subNum;
		
//		System.out.println("orderId =>" + order.getOrderId());
//		System.out.println("detailId => " + orderDetail.getOrderId());
		order.setOrderId(orderId);
		order.setUserId(userId);
		
		service.insertOrder(order);
		
		orderDetail.setOrderId(orderId);
		service.insertOrderDetails(orderDetail);
		
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
