import React, { useState, useRef, useEffect } from "react";
import PageHeader from "@/components/PageHeader/PageHeader";

import Swal from "sweetalert2";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";

const crawlSiteList = ["nate", "humor", "clien", "fmkorea"];

function Monitor(props) {
  const [postList, setPostList] = useState([]);
  const [siteList, setSiteList] = useState([]);
  const useinput = useRef();

  console.log('dddd')
  const search = async () => {
    let resLength = 0;
    const word = useinput.current.value;

    Swal.fire({
      title: "검색 중입니다...",
      html: "검색이 완료되는 순서대로 화면에 표시됩니다.",
      allowOutsideClick: false,
      didOpen: async () => {
        Swal.showLoading();
        const word = useinput.current.value;
        setPostList([]);
        setSiteList([]);
        //==================응답 순서대로 렌더링============================//
        for (let i = 0; i < crawlSiteList.length; i++) {
          console.log('header 포함')
          axios
            .get(
              `http://localhost:8080/api/monitor/${crawlSiteList[i]}?keyword=${word}`,
              { headers: { "Access-Control-Allow-Origin": "*" } }
            )
          // axios
          //   .get(
          //     `/api/monitor/${crawlSiteList[i]}?keyword=${word}`
          //   )
            .then((response) => {
              const siteName = response.data.data[0].site;
              console.log(siteName, " 완료");
              console.log(response.data.data);
              setPostList((state) => [...state, ...response.data.data]);
              setSiteList((site) => [...site, siteName]);
              resLength += response.data.data.length;
            });
        }
        //==================응답 순서대로 렌더링============================//

        //===================한번에 가져오는 코드==========================//
        // const siteAxios = new Array();
        // for (let i = 0; i < crawlSiteList.length; i++) {
        //   axios.push(
        //     axios.get(
        //       `http://localhost:8080/test/monitor/${crawlSiteList[i]}?keyword=${word}`
        //     )
        //   );
        // }
        // await axios.all([...siteAxios]).then(
        //   axios.spread((...response) => {
        //     for (let i = 0; i < response.length; i++) {
        //       if (response[i]) {
        //         const Resp = response[i];
        //         console.log(Resp);
        //         const siteName = Resp.data.data[0].site;
        //         console.log(siteName, " 완료");
        //         setPostList((state) => [...state, ...Resp.data.data]);
        //         setSiteList((site) => [...site, siteName]);
        //         resLength += Resp.data.data.length;
        //       }
        //     }
        //   })
        // );
        //==================================코드==========================//

        Swal.close();
      },
      willClose: () => {
        const Toast = Swal.mixin({
          toast: true,
          position: "top-end",
          showConfirmButton: false,
          timer: 3000,
          timerProgressBar: true,
          didOpen: (toast) => {
            toast.addEventListener("mouseenter", Swal.stopTimer);
            toast.addEventListener("mouseleave", Swal.resumeTimer);
          },
        });

        Toast.fire({
          icon: "success",
          //title: `${resLength}개가 검색되었습니다.`,
          title: `검색을 시작합니다.`,
        });
      },
    }).then((result) => {
      if (result.dismiss === Swal.DismissReason.timer) {
        console.log("I was closed by the timer");
      }
    });
  };

  const handleKeypress = (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      search();
    }
  };

  const openDialog = (item) => {
    Swal.mixin({
      cancelButtonColor: "#d33",
      confirmButtonText: "다음",
      confirmButtonAriaLabel: "다음",
      cancelButtonText: "취소",
      cancelButtonAriaLabel: "취소",
      showCancelButton: true,
      focusConfirm: true,
      progressSteps: ["1", "2"],
      showLoaderOnConfirm: true,
    })
      .queue([
        {
          title: `<header>${item.title}</header>`,
          html: item.content,
          scrollbarPadding: true,
          confirmButtonColor: "#3085d6",
        },
        {
          title: "메모를 입력해주세요",
          input: "textarea",
          inputLabel: "메모입력",
          inputPlaceholder: "Enter ...",
          inputAttributes: {
            "aria-label": "Type your message",
          },
          confirmButtonColor: "#51cf66",
          confirmButtonText: "저장",
          confirmButtonAriaLabel: "저장",
        },
      ])
      .then(async (result) => {
        if (result.value && result.value[0]) {
          const memo = JSON.stringify(result.value);
          Swal.fire({
            title: "아카이빙 및 저장 중입니다.",
            html: "완료되면 창은 자동으로 닫힙니다.",
            allowOutsideClick: false,
            didOpen: async () => {
              Swal.showLoading();
              // axios.all([
              //   axios
              //     .post(
              //       "/v1/archive",
              //       { url: "http://naver.com" },
              //       { headers: { "Access-Control-Allow-Origin": "*" } }
              //     )
              //     .then(console.log)
              //     .catch(console.log),
              //   axios
              //     .post(
              //       "/v1/screenshot", //
              //       { url: "http://www.naver.com" },
              //       { headers: { "Access-Control-Allow-Origin": "*" } }
              //     )
              //     .then(console.log)
              //     .catch(console.log), //
              // ]);

              item.created_at = "";
              axios.post(`http://localhost:8080/api/intelligences/`, item);

              Swal.close();
            },
            willClose: () => {
              const Toast = Swal.mixin({
                toast: true,
                position: "top-end",
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                  toast.addEventListener("mouseenter", Swal.stopTimer);
                  toast.addEventListener("mouseleave", Swal.resumeTimer);
                },
              });

              Toast.fire({
                icon: "success",
                title: `저장이 완료되었습니다.`,
              });
            },
          });
        } else if (result.dismiss === Swal.DismissReason.cancel) {
          Swal.fire(
            "취소되었습니다.",
            "아카이빙 및 저장 로직 실행 되지 않음",
            "error"
          );
        }
      });
  };

  // useEffect(() => {
  //   axios.all([
  //     axios
  //       .post(
  //         "/v1/archive",
  //         { url: "http://naver.com" },
  //         { headers: { "Access-Control-Allow-Origin": "*" } }
  //       )
  //       .then((res) => {
  //         console.log(res.data.body);
  //       })
  //       .catch(console.log),
  //     axios
  //       .post(
  //         "/v1/screenshot",
  //         { url: "http://naver.com" },
  //         { headers: { "Access-Control-Allow-Origin": "*" } }
  //       )
  //       .then(console.log)
  //       .catch(console.log),
  //   ]);
  // }, []);

  return (
    <>
      <PageHeader title="모니터링" desc="모니터링" />
      <section>
        <form className="search">
          <input
            type="text"
            placeholder="검색 키워드를 입력하세요"
            onKeyPress={handleKeypress}
            ref={useinput}
          />
          <button type="button" onClick={search}>
            <FontAwesomeIcon icon={faSearch} />
          </button>
        </form>
        <nav className="monitor__site_nav">
          <ul>
            {siteList &&
              siteList.map((site) => {
                return (
                  <li>
                    <a>{site}</a>
                  </li>
                );
              })}
          </ul>
        </nav>
        <table>
          <col style={{ width: "10%" }} />
          <col style={{ width: "60%" }} />
          <col style={{ width: "10%" }} />
          <col style={{ width: "10%" }} />
          <col style={{ width: "10%" }} />
          <thead>
            <tr>
              <td>사이트</td>
              <td>제목</td>
              <td>조회수</td>
              <td>작성자</td>
              <td>게시일</td>
            </tr>
          </thead>

          <tbody>
            {postList &&
              postList.map((post) => {
                return (
                  <tr onClick={() => openDialog(post)}>
                    <td>{post.site}</td>
                    <td>{post.title}</td>
                    <td>100</td>
                    <td>{post.author}</td>
                    <td>{post.created_at}</td>
                  </tr>
                );
              })}
          </tbody>
        </table>
      </section>
    </>
  );
}

export default Monitor;
