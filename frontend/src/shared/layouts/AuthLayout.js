import React from 'react';

import './AuthLayout.scss';

export default function AuthLayout({ children }) {
    return (
        <section className="form-wrapper">
            <div className="cover"></div>
            <form className="form form__auth">
                { children }
            </form>
        </section>
    );
}