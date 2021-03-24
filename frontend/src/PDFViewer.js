import React, { Component } from 'react';

import AllPagesPDFViewer from './components/PDF/all-pages';

class PDFViewer extends Component {
    render () {
        return (
            <AllPagesPDFViewer pdf={this.props.file} />
        )
    }
}

export default PDFViewer;