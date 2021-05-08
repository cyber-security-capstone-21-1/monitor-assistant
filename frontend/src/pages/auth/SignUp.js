import React, { useState } from 'react';
import axios from 'axios';
import Swal from 'sweetalert2';
import Constants from "@/shared/constants";

export default function SignUp () {

    const [AuthInfo, setAuthInfo] = useState({ email: "", password: "", passwordConfirm: "" });
    const [passwdMsg, setPasswdMsg] = useState('패스워드 재입력을 입력해주세요.');
    const [emailMsg, setEmailMsg] = useState('이메일을 입력해주세요.');

    const handleAuthInfo = (e) => {
        setAuthInfo({
            ...AuthInfo,
            [e.target.name]: e.target.value
        })
    }

    const onSignUp = (e) => {
        e.preventDefault();
        const data = AuthInfo;
        if(data.password !== data.passwordConfirm) {
            setPasswdMsg('비밀번호가 일치하지 않습니다.');
            return;
        }

        //회원가입 성공 과 동시에 로그인 처리.
        axios.post(`${Constants.ENDPOINT}/api/auth/signup`, data).then(console.log);
        
    }

    return (
        <>
            <header>회원가입</header>
            <div className="form__item_wrapper">
                <div className="form__item">
                    <legend>이메일</legend>
                    <input type="email" onChange={handleAuthInfo} name="email" required placeholder="이메일을 입력하세요" />
                    <span className="error">{emailMsg}</span>
                </div>
                <div className="form__item">
                    <legend>패스워드</legend>
                    <input type="password" onChange={handleAuthInfo} name="password" required placeholder="패스워드를 입력하세요"/>
                    <span className="error">패스워드를 입력해주세요.</span>
                </div>
                <div className="form__item">
                    <legend>패스워드</legend>
                    <input type="password" onChange={handleAuthInfo} name="passwordConfirm" required placeholder="패스워드를 입력하세요"/>
                    <span className="error">{passwdMsg}</span>
                </div>
                <div className="form__item">
                    <input type="submit" value="로그인" onClick={onSignUp}/>
                </div>
            </div>
        </>
    );
};