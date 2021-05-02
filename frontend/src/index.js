import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Switch, Route } from 'react-router-dom';

import App from './shared/App';
import PageNotFound from './shared/PageNotFound';
import AuthRouter from './pages/auth';

import axios from 'axios';

import reportWebVitals from './reportWebVitals';

axios.defaults.withCredentials = true;

ReactDOM.render(
  <React.StrictMode>
    <BrowserRouter>
      <Switch>
        <Route path="/auth" component={AuthRouter} />
        <Route path="/:service" component={App} />
        <Route path="*" component={PageNotFound} />
      </Switch>
    </BrowserRouter>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
