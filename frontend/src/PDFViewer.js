import React, { Component } from 'react';

import SinglePageViewer from './components/PDF/single-page';

import "./PDFViewer.scss";

class PDFViewer extends Component {
    render () {
        return (
            <SinglePageViewer pdf={this.props.file} />
        );
    }
}

export default PDFViewer;