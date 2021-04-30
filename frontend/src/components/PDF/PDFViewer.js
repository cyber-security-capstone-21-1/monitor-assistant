import React, { useState } from "react";

import { PDFDownloadLink } from "@react-pdf/renderer";
import IntelligenceDocument from "./IntelligenceDocument";

function Viewer(props) {
  const [PDFBinary, setPDFBinary] = useState("");

  return (
    <section>
      <article>
        <PDFDownloadLink
          document={<IntelligenceDocument />}
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
