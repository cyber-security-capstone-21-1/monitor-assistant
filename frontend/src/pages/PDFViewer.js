import React, { Component } from 'react';
import Viewer from "@/components/PDF/PDFViewer";

import file from "@/assets/files/idea-presentation-5.pdf";

class PDFViewer extends Component {
    render () {
        return (
            <>
                <Viewer file={file} />
            </>
        )
    }
}

export default PDFViewer;