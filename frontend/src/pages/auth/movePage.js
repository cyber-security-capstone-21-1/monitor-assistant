import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import { withRouter } from "react-router";

import Constants from '@/shared/constants';
import AuthenticationService from '@/shared/AuthenticationService';

const MovePage = ({ location, children }) => {
  console.log('move page')
  const [history, setHistory] = useState("");
  const timeId = useRef();

  const sessionManage = () => {
    timeId.current = setTimeout(() => {
      console.log("로그인 세션 만료!");
      // AuthenticationService.logout();
    }, 3000);
  }

  console.log(history, "에서", location.pathname, "으로");
  if (location.pathname !== history && location.pathname != "/") {
    console.log("페이지 이동");
    setHistory(location.pathname);
    clearTimeout(timeId.current);
    sessionManage();
  }

  useEffect(() => {
    setHistory(location.pathname);
    sessionManage();
  }, []);

  return children;
};

export default withRouter(MovePage);
