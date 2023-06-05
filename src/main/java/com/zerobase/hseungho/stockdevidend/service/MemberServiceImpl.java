package com.zerobase.hseungho.stockdevidend.service;

import com.zerobase.hseungho.stockdevidend.global.exception.impl.AlreadyExistUserException;
import com.zerobase.hseungho.stockdevidend.global.exception.impl.MisMatchPasswordException;
import com.zerobase.hseungho.stockdevidend.model.Auth;
import com.zerobase.hseungho.stockdevidend.persist.entity.MemberEntity;
import com.zerobase.hseungho.stockdevidend.persist.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    @Transactional
    public MemberEntity register(Auth.SignUp member) {
        validateExists(member);

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));

        return this.memberRepository.save(member.toEntity());
    }

    @Override
    @Transactional(readOnly = true)
    public MemberEntity authenticate(Auth.SignIn member) {
        MemberEntity user = this.memberRepository.findByUsername(member.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(member.getUsername()));

        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new MisMatchPasswordException();
        }

        return user;
    }

    private void validateExists(Auth.SignUp member) {
        if (this.memberRepository.existsByUsername(member.getUsername())) {
            throw new AlreadyExistUserException();
        }
    }

}
