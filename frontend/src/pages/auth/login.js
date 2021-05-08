import React, { useState, useCallback } from 'react';
import { Link, Redirect } from 'react-router-dom';

import AuthenticationService from '@/shared/AuthenticationService';

import axios from 'axios';
import Swal from 'sweetalert2';
import Constants from '@/shared/constants';

export default function Login (context) {
    console.log('로그인 렌더링');
    console.log(AuthenticationService.isUserLoggedIn());

    const [AuthInfo, setAuthInfo] = useState({ email: "", password: "" });
    const handleAuthInfo = (e) => {
        setAuthInfo({
            ...AuthInfo,
            [e.target.name]: e.target.value
        });
    };

    const onLogin = useCallback((e) => {
        e.preventDefault();
        const data = AuthInfo;
        // AuthenticationService.executeJwtAuthenticationService(data.email, data.password);
        
        axios.post(`${Constants.ENDPOINT}/api/auth/login`, data).then(response => {
                const { accessToken } = response.data;
                axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
                AuthenticationService.registerSuccessfulLoginForJwt(data.email, accessToken);
                setAuthInfo('','');
            })
            .catch(error => {
                Swal.fire({
                    title: "로그인 실패",
                    icon: "error"
                });
                console.error(error);
            });
            
    }, []);

    const loggedIn = AuthenticationService.isUserLoggedIn();
    return (
        <>
            { !loggedIn ?
                (
                <>
                <header>로그인</header>
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
                        <input type="submit" value="로그인" onClick={onLogin}/>
                    </div>
                    <hr />
                    <p>아직 서비스에 가입하지 않으셨다면 <Link to="/auth/signup">이곳</Link>을 눌러 가입하세요.</p>
                </div>
                </>
            )
            :
            (<Redirect to="/service" />)
            }
        </>
    );
};
