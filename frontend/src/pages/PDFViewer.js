import React from 'react';

import PageHeader from '@/components/PageHeader/PageHeader';
import IntelligenceViewer from "@/components/PDF/PDFViewer";

function PDFViewer (props) {
    return (
        <>
            <PageHeader title="문서 뷰어" desc="작성된 첩보를 문서로 변환합니다." />
            <IntelligenceViewer {...props} />
        </>
    );
}

export default PDFViewer;