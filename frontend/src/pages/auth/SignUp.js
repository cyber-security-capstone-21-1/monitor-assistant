import React, { useState } from "react";
import { Link, Redirect } from "react-router-dom";
import axios from "axios";
import Swal from "sweetalert2";

import AuthenticationService from "@/shared/AuthenticationService";

export default function SignUp() {
  const [AuthInfo, setAuthInfo] = useState({
    name: "",
    email: "",
    password: "",
    passwordConfirm: "",
  });
  const [nameMsg, setNameMsg] = useState("");
  const [passwdMsg, setPasswdMsg] = useState("");
  const [emailMsg, setEmailMsg] = useState("");
  const [passMsg, setPassMsg] = useState("");

  const handleAuthInfo = (e) => {
    setAuthInfo({
      ...AuthInfo,
      [e.target.name]: e.target.value,
    });
  };

  const onSignUp = (e) => {
    e.preventDefault();
    const data = AuthInfo;
    if(data.name === "") {
      setNameMsg("이름을 입력해주세요.");
      return;
    }
    if (data.email === "") {
      setEmailMsg("이메일을 입력해주세요.");
      return;
    } else if (data.password === "") {
      setPassMsg("비밀번호를 입력해주세요");
      return;
    }
    if(data.password.length < 4) {
      setPassMsg('비밀번호는 4글자 이상으로 입력해주세요')
      return;
    }

    if (data.password !== data.passwordConfirm) {
      setPasswdMsg("비밀번호가 일치하지 않습니다.");
      return;
    }

    delete data.passwordConfirm;

    //회원가입 성공과 동시에 로그인
    axios.post(`/api/auth/emailvalidity`, data).then((res) => {
        if (!res.data) {
          console.log('로그인 진행')
          axios.post(`/api/auth/signup`, data).then((res) => {
              console.log("회원가입 가능 signup 응답값 : ", res);
              axios.post(`/api/auth/login`, data).then((response) => {
                  const { accessToken,refreshToken } = response.data.data;
                  console.log(response);
                  console.log(response.data.data)
                  console.log(accessToken);
                  AuthenticationService.registerSuccessfulLoginForJwt(
                    refreshToken,
                    accessToken
                  );
                  setAuthInfo("", "");
                }).catch((error) => {
                  Swal.fire({
                    title: "로그인 실패",
                    icon: "error",
                    html: "로그인 정보를 다시 한번 확인해주세요.",
                    confirmButtonText: "확인"
                  });
                  console.error(error);
                });
            })
            .catch(console.log);
        } else {
          setEmailMsg("중복 이메일입니다. 다른 이메일로 시도하세요.");
          return;
        }
      })
      .catch((error) => {
        Swal.fire({
          title: "회원가입 실패",
          icon: "error",
          html: "알 수 없는 에러로 가입에 실패했습니다. 같은 현상 반복 시 서버 관리자에 문의하세요.",
          confirmButtonText: "확인"
        });
        console.error(error);
      });
  };

  const loggedIn = AuthenticationService.isUserLoggedIn();
  return (
    <>
      {!loggedIn ? (
        <>
          <header className="title">회원가입</header>
          <div className="form__item_wrapper">
            <div className="form__item">
              <legend>이름</legend>
              <input
                type="name"
                onChange={handleAuthInfo}
                name="name"
                required
                placeholder="이름을 입력하세요"
              />
              <span className="error">{nameMsg}</span>
            </div>
            <div className="form__item">
              <legend>이메일</legend>
              <input
                type="email"
                onChange={handleAuthInfo}
                name="email"
                required
                placeholder="이메일을 입력하세요"
              />
              <span className="error">{emailMsg}</span>
            </div>
            <div className="form__item">
              <legend>패스워드</legend>
              <input
                type="password"
                onChange={handleAuthInfo}
                name="password"
                required
                placeholder="패스워드를 입력하세요"
              />
              <span className="error">{passMsg}</span>
            </div>
            <div className="form__item">
              <legend>패스워드 확인</legend>
              <input
                type="password"
                onChange={handleAuthInfo}
                name="passwordConfirm"
                required
                placeholder="다시 한번 입력하세요"
              />
              <span className="error">{passwdMsg}</span>
            </div>
            <div className="form__item">
              <input type="submit" value="회원가입" onClick={onSignUp} />
            </div>
            <hr />
            <p className="info">
              <Link to="/auth/login" className="btn">
                로그인 화면으로 돌아가기
              </Link>
            </p>
          </div>
        </>
      ) : (
        <Redirect to="/service" />
      )}
    </>
  );
}
