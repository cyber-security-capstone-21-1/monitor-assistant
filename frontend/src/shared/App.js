import React from 'react';
import { Route, Switch } from 'react-router-dom';

import { Dashboard, Viewer, IntList, Monitor } from "@/pages";
import ReactTooltip from 'react-tooltip';

import "@/assets/styles/default.scss";

import Aside from '@/components/Aside/Aside';
import Footer from '@/components/Footer/Footer';

import { hot } from 'react-hot-loader';

function App ({ match }) {
  console.log(match);

  return (
    <div className="wrapper">
      <ReactTooltip />
      <main>
        <Aside />
        <div className="content">
          <div className="row">
            <Switch>
              <Route path="/" component={Dashboard} />
              <Route exact path={`/intelligence/:id`} component={Viewer} />
              <Route exact path={`/list`} component={IntList} />
              <Route exact path={`/monitor`} component={Monitor} />
            </Switch>
          </div>
        </div>
      </main>
      <Footer />
    </div>
  );
}

export default hot(module)(App);
