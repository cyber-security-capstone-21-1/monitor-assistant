import React, { Component } from 'react';
import { Route, Link } from 'react-router-dom';
import Aside from './components/Aside/Aside';
import Footer from './components/Footer/Footer';

import PDFViewer from './PDFViewer';
import Classifier from './Classifier';

import ReactTooltip from 'react-tooltip';

import file from './assets/files/idea-presentation-5.pdf'

import { hot } from 'react-hot-loader';
import "./assets/styles/default.scss";

class App extends Component {
  render () {
    return (
      <div className="wrapper">
        <ReactTooltip />
        <main>
          <Aside />
          <div className="content">
            <Classifier />
            <div className="row">
              <PDFViewer file={file} />
            </div>
          </div>
        </main>
        <Footer />
      </div>
    );
  }
}

export default hot(module)(App);
