import React, { useState, useEffect } from "react";

import { PDFDownloadLink, PDFViewer } from "@react-pdf/renderer";
import IntelligenceCreator from "./types/Intelligence-creator";

import "./PDFViewer.scss";

function Viewer(props) {
  const [PDFBinary, setPDFBinary] = useState("");

  useEffect(() => {

  }, []);

  return (
    <section>
      <article>
        <PDFDownloadLink
          document={<IntelligenceCreator />}
          fileName="intelligence.pdf"
        >
          {
            ({ blob, url, loading, error }) => {
              setPDFBinary(url);
              return (loading ? "문서 로드 중입니다." : "PDF 다운로드")
            }
          }
        </PDFDownloadLink>
        <hr />
        <iframe type="application/pdf" src={PDFBinary} style={{ width: "100%", minHeight: "700px" }}></iframe>
      </article>
    </section>
  );
}

export default Viewer;
