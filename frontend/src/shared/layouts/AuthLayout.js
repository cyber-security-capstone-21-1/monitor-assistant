import React from 'react';

import './AuthLayout.scss';

export default function AuthLayout({ children }) {
    return (
        <section className="form-wrapper">
            <h1>사이버수사관<br />모니터링업무보조시스템</h1>
            <div className="cover"></div>
            <form className="form form__auth">
                { children }
            </form>
        </section>
    );
}