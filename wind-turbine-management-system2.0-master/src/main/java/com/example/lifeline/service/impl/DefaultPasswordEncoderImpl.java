package com.example.lifeline.service.impl;

import com.example.lifeline.utils.PasswordEncode.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("PasswordEncoder")
public class DefaultPasswordEncoderImpl implements PasswordEncoder {

    //进行MD5加密
    @Override
    public String encode(CharSequence charSequence) {
        return MD5.encrypt(charSequence.toString());
    }
    //进行密码比对
    @Override
    public boolean matches(CharSequence charSequence, String encodedPassword) {
        return encodedPassword.equals(MD5.encrypt(charSequence.toString()));
    }
}
