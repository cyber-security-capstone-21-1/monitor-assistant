import React, { useState, useEffect, useRef } from "react";
import { withRouter } from "react-router";
import { Redirect } from "react-router-dom";
import { Login, SignUp } from "@/pages";
import { RouteWithLayout, AuthLayout } from "../../shared/layouts";
import AuthenticationService from "@/shared/AuthenticationService";

const MovePage = ({ location, children }) => {
  console.log("0509 11:16 테스트");
  const [history, setHistory] = useState("");
  const timeId = useRef();

  const sessionManage = () => {
    timeId.current = setTimeout(() => {
      console.log("로그인 세션 만료!");
      AuthenticationService.logout();
    }, 30 * 60 * 1000);
  };

  if (location.pathname !== history && location.pathname !== "/") {
    console.log("페이지 이동");
    console.log(history, "에서", location.pathname, "으로");
    setHistory(location.pathname);
    clearTimeout(timeId.current);
    sessionManage();
  }

  useEffect(() => {
    setHistory(location.pathname);
  }, []);

  
  return (
    <>
      <RouteWithLayout
        layout={AuthLayout}
        path="/auth/login"
        component={Login}
      />
      <RouteWithLayout
        layout={AuthLayout}
        path="/auth/signup"
        component={SignUp}
      />
      {!AuthenticationService.isUserLoggedIn() ? (
        <Redirect to="/auth/login" />
      ) : (
        children
      )}
    </>
  );
};

export default withRouter(MovePage);
