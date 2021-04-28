import React from "react";

// import SinglePageViewer from './types/single-page';
import IntelligenceCreator from "./types/Intelligence-creator";

import "./PDFViewer.scss";

function PDFViewer(props) {
  return (
    <section>
      <article>
        {/* <SinglePageViewer pdf={props.file} /> */}
        <IntelligenceCreator />
      </article>
    </section>
  );
}

export default PDFViewer;
