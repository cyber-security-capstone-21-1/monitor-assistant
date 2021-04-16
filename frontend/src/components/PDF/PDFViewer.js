import React from 'react';

import SinglePageViewer from './types/single-page';

import "./PDFViewer.scss";

function PDFViewer (props) {
    return (
        <section>
            <article>
                <SinglePageViewer pdf={props.file} />
            </article>
        </section>
    );
}

export default PDFViewer;