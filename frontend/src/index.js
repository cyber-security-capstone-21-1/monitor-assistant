import React from 'react';
import ReactDOM from 'react-dom';
<<<<<<< HEAD
import { BrowserRouter } from 'react-router-dom';
=======

import App from './shared/App';

>>>>>>> 84eb006b9587d2c73b1a5f6f3b6610c387216f10
import axios from 'axios';

import App from './shared/App';
import reportWebVitals from './reportWebVitals';

import MovePage from './pages/auth/movePage'

axios.defaults.withCredentials = true;

ReactDOM.render(
  <React.StrictMode>
<<<<<<< HEAD
    <BrowserRouter>
      <MovePage>
        <App />
      </MovePage>
    </BrowserRouter>
=======
    <App />
>>>>>>> 84eb006b9587d2c73b1a5f6f3b6610c387216f10
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
