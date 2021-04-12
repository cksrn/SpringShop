package com.cksrn.service;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import com.cksrn.dao.MemberDAO;
import com.cksrn.domain.MemberVO;

@Service
public class MemberServiceImpl implements MemberService{

	@Inject
	MemberDAO dao;

	//회원가입
	@Override
	public void signup(MemberVO vo) throws Exception {
//		System.out.println("signupService()");
		dao.signup(vo);
	}

	//로그인
	@Override
	public MemberVO signin(MemberVO vo) throws Exception {
//		System.out.println("signinService()");
		return dao.signin(vo);
	}

	//로그아웃
	@Override
	public void signout(HttpSession session) throws Exception {
//		System.out.println("signoutService()");
		session.invalidate(); //세션 정보를 제거
	}
	
}
