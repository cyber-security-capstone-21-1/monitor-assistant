import React, { useState, useEffect } from "react";

import { PDFDownloadLink } from "@react-pdf/renderer";
import IntelligenceDocument from "./IntelligenceDocument";

import axios from 'axios';

function Viewer({ match: { params }}) {
  const [PDFBinary, setPDFBinary] = useState("");
  const [Intelligence, setIntelligence] = useState({});

  useEffect(() => {
    async function getIntelligence() {
      const data = await axios.get(`/api/monitor/api/intelligences/${params.uid}`);
      setIntelligence(data);
    }
    getIntelligence();
  }, {});

  return (
    <section>
      <article>
        <PDFDownloadLink
          document={<IntelligenceDocument data={Intelligence} />}
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
