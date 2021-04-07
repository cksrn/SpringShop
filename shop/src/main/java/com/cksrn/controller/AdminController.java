package com.cksrn.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cksrn.domain.CategoryVO;
import com.cksrn.domain.GoodsVO;
import com.cksrn.domain.GoodsViewVO;
import com.cksrn.domain.OrderListVO;
import com.cksrn.domain.OrderVO;
import com.cksrn.domain.ReplyListVO;
import com.cksrn.domain.ReplyVO;
import com.cksrn.service.AdminService;
import com.cksrn.utils.UploadFileUtils;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/admin/*")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Inject
	AdminService service;
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
	//관리자 index
	@RequestMapping(value="/index",method = RequestMethod.GET)
	public void getIndex() throws Exception{
		logger.info("get index");
	}
	
	// 카테고리 추가하기
	@RequestMapping(value = "/goods/register", method = RequestMethod.GET)
	public void getGoodsRegister(Model model) throws Exception {
		logger.info("get goods register");

		List<CategoryVO> category = null;
		category = service.category();
		model.addAttribute("category", JSONArray.fromObject(category));
	}
	
	// 상품 등록
	@RequestMapping(value = "/goods/register", method = RequestMethod.POST)
	public String postGoodsRegister(GoodsVO vo, MultipartFile file) throws Exception {
	 
	 String imgUploadPath = uploadPath + File.separator + "imgUpload";// 이미지를 업로드할 폴더를 설정 = /uploadPath/imgUpload
	 String ymdPath = UploadFileUtils.calcPath(imgUploadPath);// 위의 폴더를 기준으로 연월일 폴더를 생성
	 String fileName = null;// 기본 경로와 별개로 작성되는 경로 + 파일이름
	   
	 if(file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
	  // 파일 인풋박스에 첨부된 파일이 없다면(=첨부된 파일이 이름이 없다면)
	  
	  fileName =UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath);

	  // gdsImg에 원본 파일 경로 + 파일명 저장
	  vo.setGdsImg(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
	  // gdsThumbImg에 썸네일 파일 경로 + 썸네일 파일명 저장
	  vo.setGdsThumbImg(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
	  
	 } else {// 첨부된 파일이 없으면
	  fileName = File.separator + "images" + File.separator + "none.png";
	  // 미리 준비된 none.png파일을 대신 출력함
	  
	  vo.setGdsImg(fileName);
	  vo.setGdsThumbImg(fileName);
	 }
	      
	 service.insertGoods(vo);
	 
	 return "redirect:/admin/index";
	}
	
	//상품 보기
	@RequestMapping(value="/goods/list", method = RequestMethod.GET)
	public void getGoodList(Model model) throws Exception{
		logger.info("get goods list");
		
		List<GoodsViewVO> list = service.selectGoods();
		model.addAttribute("list", list);
	}
	
	//상품 상세보기
	@RequestMapping(value="/goods/view", method=RequestMethod.GET)
	public void getGoodsView(@RequestParam("n") int gdsNum, Model model) throws Exception{
		logger.info("get goods view");
		
		GoodsViewVO goods = service.getGood(gdsNum);
		
		model.addAttribute("goods", goods);
	}
	
	//상품 수정
	@RequestMapping(value="/goods/modify", method=RequestMethod.GET)
	public void getGoodsModify(@RequestParam("n") int gdsNum, Model model) throws Exception{
		logger.info("get goods view");	
		GoodsViewVO goods = service.getGood(gdsNum);
		model.addAttribute("goods", goods);
		List<CategoryVO> category = null;
		category = service.category();
		model.addAttribute("category", JSONArray.fromObject(category));
	}
	
	// 상품 수정
	@RequestMapping(value = "/goods/modify", method = RequestMethod.POST)
	public String postGoodsModify(GoodsVO vo, MultipartFile file, HttpServletRequest req) throws Exception {
		logger.info("post goods modify");

		// 새로운 파일이 등록되었는지 확인
		if (file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
			// 기존 파일을 삭제
			new File(uploadPath + req.getParameter("gdsImg")).delete();
			new File(uploadPath + req.getParameter("gdsThumbImg")).delete();

			// 새로 첨부한 파일을 등록
			String imgUploadPath = uploadPath + File.separator + "imgUpload";
			String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
			String fileName = UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(),ymdPath);

			vo.setGdsImg(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
			vo.setGdsThumbImg(
					File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);

		} else {			
			vo.setGdsImg(req.getParameter("gdsImg"));
			vo.setGdsThumbImg(req.getParameter("gdsThumbImg"));
		}
//		System.out.println("ModifyController =>" + vo.getGdsName());
		service.goodsModify(vo);

		return "redirect:/admin/index";
	}
	//상푹삭제
	@RequestMapping(value="/goods/delete",method = RequestMethod.POST)
	public String postDeleteGoods(@RequestParam("n") int gdsNum) throws Exception{
		logger.info("post delete goods");
		service.deleteGoods(gdsNum);
		return "redirect:/admin/index";
	}
	
	// ck 에디터에서 파일 업로드
	@RequestMapping(value = "/goods/ckUpload", method = RequestMethod.POST)
	public void postCKEditorImgUpload(HttpServletRequest req, HttpServletResponse res,@RequestParam MultipartFile upload) throws Exception {
		logger.info("post CKEditor img upload");

		// 랜덤 문자 생성
		UUID uid = UUID.randomUUID();

		OutputStream out = null;
		PrintWriter printWriter = null;

		// 인코딩
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");

		try {

			String fileName = upload.getOriginalFilename();// 파일 이름 가져오기
			byte[] bytes = upload.getBytes();

			// 업로드 경로
			String ckUploadPath = uploadPath + File.separator + "ckUpload" + File.separator + uid + "_" + fileName;

			out = new FileOutputStream(new File(ckUploadPath));
			out.write(bytes);
			out.flush();// out에 저장된 데이터를 전송하고 초기화

			String callback = req.getParameter("CKEditorFuncNum");
			printWriter = res.getWriter();
			String fileUrl = "/ckUpload/" + uid + "_" + fileName;// 작성화면

			// 업로드시 메시지 출력
			printWriter.println("<script type='text/javascript'>" + "window.parent.CKEDITOR.tools.callFunction("+ callback + ",'" + fileUrl + "','이미지를 업로드하였습니다.')" + "</script>");

			printWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (printWriter != null) {
					printWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return;
	}
	//주문 목록
	@RequestMapping(value="/shop/orderList", method=RequestMethod.GET)
	public void getOrderList(Model model) throws Exception{
		List<OrderVO> orderList = service.getOrder();
		
		model.addAttribute("orderList", orderList);
	}
	
	//주문 상세보기
	@RequestMapping(value="/shop/orderView",method=RequestMethod.GET)
	public void getOrderList(@RequestParam("n") String orderId, OrderVO order, Model model) throws Exception{
		order.setOrderId(orderId);
		List<OrderListVO> orderView = service.getOrderView(order);

		model.addAttribute("orderView", orderView);
	}
	
	//주문상태 - 상태변경
	@RequestMapping(value="/shop/orderView",method=RequestMethod.POST)
	public String getdelivery(OrderVO order) throws Exception{
		
		service.delivery(order);
		
		List<OrderListVO> orderView = service.getOrderView(order);
		GoodsVO goods = new GoodsVO();
		
		for(OrderListVO i : orderView) {
			goods.setGdsNum(i.getGdsNum());
			goods.setGdsStock(i.getCartStock());
			service.changeStock(goods);
		}
		return "redirect:/admin/shop/orderView?n=" + order.getOrderId();
	}
	//모든 댓글 보기
	@RequestMapping(value ="/shop/allReply", method=RequestMethod.GET)
	public void getAllReply(Model model) throws Exception{
		List<ReplyListVO> reply = service.allReply();
		
		model.addAttribute("reply", reply);
	}
	//댓글삭제
	@RequestMapping(value="/shop/allReply", method=RequestMethod.POST)
	public String postAllReply(ReplyVO reply) throws Exception{
		
		service.deleteReply(reply.getComNum());
		
		return "redirect:/admin/shop/allReply";
	}
	
	
}
