import React from 'react';
import { BrowserRouter, Switch } from 'react-router-dom';
import { hot } from 'react-hot-loader';
import "@/assets/styles/default.scss";
import { Dashboard, Viewer, IntList, Monitor, Login, SignUp, PageNotFound } from "@/pages";
import { RouteWithLayout, AuthLayout, ServiceLayout, ErrorLayout } from './layouts';
import MovePage from '../pages/auth/movePage'

function App () {
  return (
    <BrowserRouter>
      <MovePage>
        <Switch>
          <RouteWithLayout layout={AuthLayout} path="/auth/signup" component={SignUp} exact={true}/>   
          <RouteWithLayout layout={AuthLayout} path="/auth/login" component={Login} exact={true}/>
          <RouteWithLayout layout={ServiceLayout} path="/service/intelligence/:uid" component={Viewer} exact={true}/>   
          <RouteWithLayout layout={ServiceLayout} path="/service/monitor" component={Monitor} exact={true}/>   
          <RouteWithLayout layout={ServiceLayout} path="/service/list" component={IntList} exact={true}/>   
          <RouteWithLayout layout={ServiceLayout} path="/" component={Dashboard} exact={true}/>   
          <RouteWithLayout layout={ErrorLayout} component={PageNotFound} exact={true}/>   
        </Switch>
      </MovePage>
    </BrowserRouter>
  );
}

export default hot(module)(App);
