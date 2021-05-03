import React from 'react';
import { Redirect, Route, Switch } from 'react-router-dom';

import { Dashboard, Viewer, IntList, Monitor } from "@/pages";
import ReactTooltip from 'react-tooltip';

import "@/assets/styles/default.scss";

import Aside from '@/components/Aside/Aside';
import Footer from '@/components/Footer/Footer';
import AuthRouter from '@/pages/auth';
import PageNotFound from './PageNotFound';

import { hot } from 'react-hot-loader';

function App (props) {
  //임시ㅣ 처리
  const loggedIn = true;

  return (
    <Switch>
      <Route path="/auth" component={AuthRouter} />
      <Route path="/">
        { !loggedIn ? 
          <Redirect to="/auth/login" /> :
          (
            <div className="wrapper">
              <ReactTooltip />
              <main>
                <Aside />
                <div className="content">
                  <div className="row">
                      <Route exact path="/" component={Dashboard} />
                      <Route path="/intelligence/:uid" render={(props) => <Viewer {...props} />} />
                      <Route exact path={`/list`} component={IntList} />
                      <Route exact path={`/monitor`} component={Monitor} />
                  </div>
                </div>
              </main>
              <Footer />
            </div>
          )
        }
      </Route>
    </Switch>
  );
}

export default hot(module)(App);
