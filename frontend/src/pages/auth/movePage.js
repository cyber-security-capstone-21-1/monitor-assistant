import React, { useState, useEffect, useRef } from "react";
import { withRouter } from "react-router";
import { Redirect } from "react-router-dom";
import { Login, SignUp } from "@/pages";
import { RouteWithLayout, AuthLayout } from "../../shared/layouts";
import AuthenticationService from "@/shared/AuthenticationService";

const MovePage = ({ location, children }) => {
  const [history, setHistory] = useState("");
  const timeId = useRef();

  const sessionManage = () => {
    timeId.current = setTimeout(() => {
      console.log("로그인 세션 만료!");
      AuthenticationService.logout();
    }, 30 * 60 * 1000);
  };

  const checkToken = async () => {
    console.log('체크 토큰')
    AuthenticationService.executeJwtAuthenticationService().then((res) => {
        if(!res) {
          console.log('유효여부 : ',res);
        }
      }).catch((e) => {
        console.log('만료 되었음');
        AuthenticationService.getNewAccessTokenWithRefreshToken().then(res => {
          AuthenticationService.setupAxiosInterceptors(res.data.data.accessToken);
        });
      });
  };

  if (location.pathname !== history && location.pathname !== "/") {
    console.log(history, "에서", location.pathname, "으로 이동");

    if(location.pathname !== "/auth/login" || location.pathname !== "/auth/signup") {
      checkToken();
    }

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
      {/* {children} */}
    </>
  );
};

export default withRouter(MovePage);
