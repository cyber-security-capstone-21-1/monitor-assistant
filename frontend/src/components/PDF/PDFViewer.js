import React, { Component } from 'react';

import SinglePageViewer from './types/single-page';

import "./PDFViewer.scss";

class PDFViewer extends Component {
    render () {
        return (
            <section>
                <article>
                    <header>문서 뷰어</header>
                    <SinglePageViewer pdf={this.props.file} />
                </article>
            </section>
        );
    }
}

export default PDFViewer;