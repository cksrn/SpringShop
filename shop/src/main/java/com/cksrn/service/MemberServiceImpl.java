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

	@Override
	public void signup(MemberVO vo) throws Exception {
//		System.out.println("signupService()");
		dao.signup(vo);
	}

	@Override
	public MemberVO signin(MemberVO vo) throws Exception {
//		System.out.println("signinService()");
		return dao.signin(vo);
	}

	@Override
	public void signout(HttpSession session) throws Exception {
//		System.out.println("signoutService()");
		session.invalidate();
	}
	
}
