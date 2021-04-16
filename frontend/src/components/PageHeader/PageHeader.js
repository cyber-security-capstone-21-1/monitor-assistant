import React from 'react';
import './PageHeader.scss';

function PageHeader (props) {
    return (
        <header>
            <h2>{props.title}</h2>
            <p>{props.desc}</p>
        </header>
    );
}

export default PageHeader;
