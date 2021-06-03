import React, { useState, useEffect } from 'react';

import axios from 'axios';

import Logo from "@/assets/images/logo.png";

import './AuthLayout.scss';

export default function AuthLayout({ children }) {

    const [count, setCount] = useState(0);

    useEffect(() => {
        async function getCommitCount() {
            const count = await axios.get("/api/nthDeploy");
            setCount(count.data.count);
        }
        getCommitCount();
    }, []);

    return (
        <section className="form-wrapper">
            <h1>
                <img src={Logo} alt="LOGO" />
                <span>사이버수사관<br />모니터링업무보조시스템</span>
            </h1>
            <h2>
                <p>
                    <span>3월 18일 첫 배포로부터 </span>
                    <br />
                    <em>{count}</em>번째
                </p>
            </h2>
            <div className="cover"></div>
            <form className="form form__auth">
                { children }
            </form>
            <footer className="footer footer__auth">
                <span>&copy; 2021 사이버보안캡스톤디자인 5조.</span><br />
                <span>이 사이트는 프로토타입으로, 실제 현장에서 사용하지 않습니다.</span>
            </footer>
        </section>
    );
}