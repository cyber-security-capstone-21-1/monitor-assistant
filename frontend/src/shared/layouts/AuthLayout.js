import React from 'react';

import './AuthLayout.scss';

export default function AuthLayout({ children }) {
    return (
        <section class="form-wrapper">
            <form class="form form__auth">
                { children }
            </form>
        </section>
    );
}