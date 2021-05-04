import React from 'react';

import './AuthLayout.scss';

export default function AuthLayout({ children }) {
    return (
        <section className="form-wrapper">
            <form className="form form__auth">
                { children }
            </form>
        </section>
    );
}