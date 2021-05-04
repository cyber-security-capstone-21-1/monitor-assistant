import React, { useState, useEffect, useRef } from "react";
import axios from 'axios';
import { withRouter } from "react-router";

const MovePage = (props) => {
  const [history, setHistory] = useState("");
  const timeId = useRef();
  if (props.location.pathname !== history) {
    //페이지 이동 감지 백엔드 측에 api 호출.

    const user = {
      "email" : "jwurbane97@ajou.ac.kr",
      "password" : "test",
    }
    axios.post(`http://localhost:8080/authenticate`, user).then(console.log).catch(console.log);

    console.log('페이지 ㅇㄷ');
    setHistory(props.location.pathname);
    clearTimeout(timeId.current);

    timeId.current = setTimeout(() => {
      console.log('로그인 세션 만료')
    }, 3000);
  }

  useEffect(() => {
    setHistory(props.location.pathname);

    timeId.current = setTimeout(() => {
      console.log('로그인 세션 만료!')
    }, 3000);
  }, []);

  return props.children;
};

export default withRouter(MovePage);
