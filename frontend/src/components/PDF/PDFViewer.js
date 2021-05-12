import React, { useState, useEffect } from "react";

import { PDFDownloadLink } from "@react-pdf/renderer";
import axios from 'axios';

import IntelligenceDocument from "./IntelligenceDocument";
import Constants from '@/shared/constants';

import "./PDFViewer.scss";

function Viewer({ match: { params: { uid } }}) {
  const [PDFBinary, setPDFBinary] = useState("");
  const [Intelligence, setIntelligence] = useState({});

  useEffect(() => {
    async function getIntelligence() {
      const request = await axios.get(`${Constants.SPRING_BACKEND.APIs.INTLIST}/${uid}`);
      setIntelligence(request.data);
    }
    getIntelligence();
  }, {});

  return (
    <section className="section section__pdf_viewer">
      <iframe
        title="보고서"
        type="application/pdf"
        src={PDFBinary}
        style={{ width: "100%", minHeight: "1000px" }}
      ></iframe>
      <hr />
      <PDFDownloadLink
        document={<IntelligenceDocument content={Intelligence} />}
        fileName={`intelligence-${uid}.pdf`}
      >
        {
          ({ blob, url, loading, error }) => {
            setPDFBinary(url);
            return (loading ? "문서 로드 중입니다." : "PDF 다운로드")
          }
        }
      </PDFDownloadLink>
    </section>
  );
}

export default Viewer;
