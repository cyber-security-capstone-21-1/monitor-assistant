import React from 'react';

import PageHeader from '@/components/PageHeader/PageHeader';
import IntelligenceViewer from "@/components/PDF/PDFViewer";

function PDFViewer (props) {
    return (
        <>
            <PageHeader title="문서 뷰어" desc="문서 뷰어" />
            <IntelligenceViewer {...props} />
        </>
    );
}

export default PDFViewer;