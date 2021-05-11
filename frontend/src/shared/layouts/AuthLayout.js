import React from 'react';

import Logo from "@/assets/images/logo.png";

import './AuthLayout.scss';

export default function AuthLayout({ children }) {
    return (
        <section className="form-wrapper">
            <h1>
                <img src={Logo} />
                <span>사이버수사관<br />모니터링업무보조시스템</span>
            </h1>
            <div className="cover"></div>
            <form className="form form__auth">
                { children }
            </form>
            <footer className="footer footer__auth">&copy; 2021 경찰청. 사이버보안캡스톤디자인 5조.</footer>
        </section>
    );
}