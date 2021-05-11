import React from 'react';
import { Switch } from 'react-router-dom';
import { hot } from 'react-hot-loader';
import "@/assets/styles/default.scss";
import { Dashboard, Viewer, IntList, Monitor, Login, SignUp, PageNotFound } from "@/pages";
import { RouteWithLayout, AuthLayout, ServiceLayout, ErrorLayout } from './layouts';
import MovePage from '../pages/auth/movePage'

function App () {
  return (
    <MovePage>
      <Switch>
        <RouteWithLayout layout={AuthLayout} path="/auth/signup" component={SignUp} />   
        <RouteWithLayout layout={AuthLayout} path="/auth/login" component={Login} />
        <RouteWithLayout layout={ServiceLayout} path="/service/intelligence/:uid" component={Viewer} />   
        <RouteWithLayout layout={ServiceLayout} path="/service/monitor" component={Monitor} />   
        <RouteWithLayout layout={ServiceLayout} path="/service/list" component={IntList} />   
        <RouteWithLayout layout={ServiceLayout} path="/" component={Dashboard} />   
        <RouteWithLayout layout={ErrorLayout} component={PageNotFound}/>   
      </Switch>
    </MovePage>
  );
}

export default hot(module)(App);
