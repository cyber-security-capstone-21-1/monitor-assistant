import React from 'react';
import { Route } from 'react-router-dom';

import {Dashboard, Viewer, IntList, Monitor} from "@/pages";
import ReactTooltip from 'react-tooltip';

import "@/assets/styles/default.scss";

import Aside from '@/components/Aside/Aside';
import Footer from '@/components/Footer/Footer';

import { hot } from 'react-hot-loader';

function App (props) {
  return (
    <div className="wrapper">
      <ReactTooltip />
      <main>
        <Aside />
        <div className="content">
          <div className="row">
            
            <Route exact path="/" component={Dashboard} />
            <Route path="/intelligence/:uid" render={(props) => <Viewer {...props} />} />
            <Route path="/list" component={IntList} />
            <Route path="/monitor" component={Monitor} />

          </div>
        </div>
      </main>
      <Footer />
    </div>
  );
}

export default hot(module)(App);
