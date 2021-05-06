import React from 'react';
import { HashRouter, Redirect, Switch } from 'react-router-dom';
import { hot } from 'react-hot-loader';

import "@/assets/styles/default.scss";
import { Dashboard, Viewer, IntList, Monitor, Login, SignUp, PageNotFound } from "@/pages";
import { RouteWithLayout, AuthLayout, ServiceLayout, ErrorLayout } from './layouts';

import AuthticationService from './AuthenticationService';
import MovePage from '../pages/auth/movePage'

function App () {
  AuthticationService.logout();
  const loggedIn = AuthticationService.isUserLoggedIn();
  return (
    <HashRouter>
      <MovePage>
        <Switch>
          <RouteWithLayout layout={AuthLayout} path="/auth/signup" component={SignUp}/>   
          <RouteWithLayout layout={AuthLayout} path="/auth/login" component={Login}/>   
          { !loggedIn ? <Redirect to="/auth/login" /> : <Redirect exact from="/" to="/service" /> }
          <RouteWithLayout layout={ServiceLayout} path="/service/intelligence/:uid" component={Viewer}/>   
          <RouteWithLayout layout={ServiceLayout} path="/service/monitor" component={Monitor}/>   
          <RouteWithLayout layout={ServiceLayout} path="/service/list" component={IntList}/>   
          <RouteWithLayout layout={ServiceLayout} path="/service" component={Dashboard}/>   
          <RouteWithLayout layout={ErrorLayout} component={PageNotFound}/>   
        </Switch>
      </MovePage>
    </HashRouter>
  );
}

export default hot(module)(App);
