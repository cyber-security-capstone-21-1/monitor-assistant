import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import { withRouter } from "react-router";

const MovePage = (props) => {
  const [history, setHistory] = useState("");
  const timeId = useRef();
  if (props.location.pathname !== history && props.location.pathname != "/") {
    console.log("페이지 이동");
    console.log(history, "에서", props.location.pathname, "으로");
    // const user = {
    //   "email" : "jwurbane97@ajou.ac.kr",
    //   "password" : "test",
    // }
    // axios.post(`http://3.36.186.72/authenticate`, user).then(console.log).catch(console.log);
    setHistory(props.location.pathname);
    clearTimeout(timeId.current);

    timeId.current = setTimeout(() => {
      console.log("로그인 세션 만료");
    }, 3000);
  }

  useEffect(() => {
    setHistory(props.location.pathname);
    timeId.current = setTimeout(() => {
      console.log("로그인 세션 만료!");
    }, 3000);
  }, []);

  return props.children;
};

export default withRouter(MovePage);
