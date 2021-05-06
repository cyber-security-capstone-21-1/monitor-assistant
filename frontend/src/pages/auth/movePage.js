import React, { useState, useEffect, useRef } from "react";
import { withRouter } from "react-router";
import { Redirect } from "react-router-dom";
import { Login } from "@/pages";
import { RouteWithLayout, AuthLayout} from '../../shared/layouts';
import AuthenticationService from '@/shared/AuthenticationService';

const MovePage = ({ location, children }) => {
  console.log('move page')
  const [history, setHistory] = useState("");
  const timeId = useRef();

  const sessionManage = () => {
    timeId.current = setTimeout(() => {
      console.log("로그인 세션 만료!");
      AuthenticationService.logout();
    }, 30000);
  }

  console.log(history, "에서", location.pathname, "으로");
  if (location.pathname !== history && location.pathname !== "/") {
    console.log("페이지 이동");
    console.log(history, "에서", location.pathname, "으로");
    setHistory(location.pathname);
    clearTimeout(timeId.current);
    sessionManage();
  }

  useEffect(() => {
    setHistory(location.pathname);
    // sessionManage();
  }, []);

  return (
    <>
      <RouteWithLayout layout={AuthLayout} path="/auth/login" component={Login}/>   
      {!AuthenticationService.isUserLoggedIn() ? (
        <Redirect to="/auth/login" />
      ) : (
        children
      )}
    </>);
};

export default withRouter(MovePage);
