package com.zerobase.hseungho.stockdevidend.service.member;

import com.zerobase.hseungho.stockdevidend.model.Auth;
import com.zerobase.hseungho.stockdevidend.model.Member;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    Member register(Auth.SignUp member);

    Member authenticate(Auth.SignIn member);

}
