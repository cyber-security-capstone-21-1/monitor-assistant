import React from 'react';

import "./ErrorLayout.scss";

export default function ErrorLayout ({ children }) {
    return (
        <section className="section section__error">
            <h1>X_X</h1>
            { children }
        </section>
    );
}