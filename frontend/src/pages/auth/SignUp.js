import React, { useState } from 'react';
import axios from 'axios';
import Swal from 'sweetalert2';

import './auth.scss';

export default function SignUp () {

    const [AuthInfo, setAuthInfo] = useState({ email: "", password: "", passwordConfirm: "" });

    const handleAuthInfo = (e) => {
        setAuthInfo({
            ...AuthInfo,
            [e.target.name]: e.target.value
        })
    }

    const onSignUp = (e) => {
        e.preventDefault();
        const data = AuthInfo;
        axios.post('/api/monitor/api/auth/login', data)
            .then(response => {
                const { accessToken } = response.data;
                axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
            })
            .catch(error => {
                Swal.fire({
                    title: "로그인 실패",
                    icon: "error"
                });
                console.error(error);
            });
    }

    return (
        <section class="form-wrapper">
            <form class="form form__auth">
                <header>회원가입</header>
                <div className="form__item_wrapper">
                    <div className="form__item">
                        <legend>이메일</legend>
                        <input type="email" onChange={handleAuthInfo} name="email" required placeholder="이메일을 입력하세요" />
                        <span className="error">이메일을 입력해주세요.</span>
                    </div>
                    <div className="form__item">
                        <legend>패스워드</legend>
                        <input type="password" onChange={handleAuthInfo} name="password" required placeholder="패스워드를 입력하세요"/>
                        <span className="error">패스워드를 입력해주세요.</span>
                    </div>
                    <div className="form__item">
                        <legend>패스워드</legend>
                        <input type="password" onChange={handleAuthInfo} name="passwordConfirm" required placeholder="패스워드를 입력하세요"/>
                        <span className="error">패스워드 재입력을 입력해주세요.</span>
                    </div>
                    <div className="form__item">
                        <input type="submit" value="로그인" onClick={onSignUp}/>
                    </div>
                </div>
            </form>
        </section>
    );
};