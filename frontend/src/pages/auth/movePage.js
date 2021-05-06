import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import { withRouter } from "react-router";

import Constants from '@/shared/constants';

const MovePage = ({ location, children }) => {
  const [history, setHistory] = useState("");
  const timeId = useRef();
  if (location.pathname !== history && location.pathname != "/") {
    console.log("페이지 이동");
    console.log(history, "에서", location.pathname, "으로");
    // const user = {
    //   "email" : "jwurbane97@ajou.ac.kr",
    //   "password" : "test",
    // }
    // axios.post(`${Constants.ENDPOINT}/authenticate`, user).then(console.log).catch(console.log);
    setHistory(location.pathname);
    clearTimeout(timeId.current);

    timeId.current = setTimeout(() => {
      console.log("로그인 세션 만료");
    }, 3000);
  }

  useEffect(() => {
    setHistory(location.pathname);
    timeId.current = setTimeout(() => {
      console.log("로그인 세션 만료!");
    }, 3000);
  }, []);

  return children;
};

export default withRouter(MovePage);
