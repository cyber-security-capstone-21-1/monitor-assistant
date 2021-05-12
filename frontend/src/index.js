import React from 'react';
import ReactDOM from 'react-dom';
import { HashRouter } from 'react-router-dom';

import Constants from "@/shared/constants";

import axios from 'axios';
import App from './shared/App';
import reportWebVitals from './reportWebVitals';

axios.defaults.withCredentials = true;
// axios.defaults.baseURL = Constants.ENDPOINT;

ReactDOM.render(
  <React.StrictMode>
    <HashRouter>
        <App />
    </HashRouter>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
