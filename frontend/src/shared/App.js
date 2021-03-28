import React, { Component } from 'react';
import { Route } from 'react-router-dom';

import {Dashboard, Classify, Viewer} from "@/pages";
import ReactTooltip from 'react-tooltip';

import "@/assets/styles/default.scss";

import Aside from '@/components/Aside/Aside';
import Footer from '@/components/Footer/Footer';

import { hot } from 'react-hot-loader';

class App extends Component {
  render () {
    return (
      <div className="wrapper">
        <ReactTooltip />
        <main>
          <Aside />
          <div className="content">
            <div className="row">
              
              <Route exact path="/" component={Dashboard} />
              <Route path="/viewer" component={Viewer} />
              <Route path="/classify" component={Classify} />

            </div>
          </div>
        </main>
        <Footer />
      </div>
    );
  }
}

export default hot(module)(App);
