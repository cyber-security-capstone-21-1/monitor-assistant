import React, { useState, useRef } from "react";
import Swal from "sweetalert2";
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch } from "@fortawesome/free-solid-svg-icons";

import Constants from "@/shared/constants";
import PageHeader from "@/components/PageHeader/PageHeader";

import "./Monitor.scss";

const crawlSiteList = [
  "CS01",
  "CS02",
  "CS03",
  "CS04",
  "CS05",
  "CS06",
  "CS07",
  "CS08",
  "CS09",
  "CS10",
  "CS11",
  "CS12",
  "CS13",
];
function Monitor(props) {
  const [postList, setPostList] = useState([]);
  const [siteList, setSiteList] = useState([]);
  const [result, setResult] = useState([]);
  const useinput = useRef();

  const search = async () => {

    Swal.fire({
      title: "검색 요청 중입니다...",
      html: "검색이 완료되는 순서대로 화면에 표시됩니다.",
      allowOutsideClick: false,
      didOpen: async () => {
        Swal.showLoading();
        const word = useinput.current.value;
        setResult([]);
        setPostList([]);
        setSiteList(["전체"]);

        for (let i = 0; i < crawlSiteList.length; i++) {
          axios
            .get(
              `${Constants.SPRING_BACKEND.APIs.MONITOR}/${crawlSiteList[i]}?keyword=${word}`
            )
            .then((response) => {
              console.log(response);
              console.log(response.data.data[0]);
              console.log(response.data.data[0].site);
              if(response.data.data[0].site) {
                console.log('없음');
              }
              const siteName = response.data.data[0].site;
              setPostList((state) => [...state, ...response.data.data]);
              setResult((state) => [...state, ...response.data.data]);
              setSiteList((site) => [...site, siteName]);
            });
        }

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
          title: `검색을 시작합니다.`,
        });
      }
    });
  };

  const handleKeypress = (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      search();
    }
  };

  const getSiteData = (site) => {
    if (site === "전체") {
      setPostList([...result]);
    } else {
      let filteredPost = result.filter((post) => {
        if (post.site === site) return true;
        else return false;
      });
      setPostList([...filteredPost]);
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
      progressSteps: ["1", "2", "3"],
      showLoaderOnConfirm: true,
    })
      .queue([
        {
          title: `<header>${item.title}</header>`,
          html: item.content,
          confirmButtonColor: "#3085d6",
        },
        {
          title: "메모를 입력해주세요",
          input: "textarea",
          inputLabel: "메모 입력",
          inputPlaceholder: "어떤 내용의 것인가요?",
          inputAttributes: {
            "aria-label": "메모 입력",
          },
          confirmButtonColor: "#51cf66",
          confirmButtonText: "저장",
          confirmButtonAriaLabel: "저장",
        },
        {
          title: "대응방안을 입력해주세요",
          input: "textarea",
          inputLabel: "대응 방안 입력",
          inputPlaceholder: "내용을 입력해주세요.",
          inputAttributes: {
            "aria-label": "대응 방안 입력",
          },
          confirmButtonColor: "#51cf66",
          confirmButtonText: "저장",
          confirmButtonAriaLabel: "저장",
        }
      ])
      .then(async (result) => {
        if (result.value && result.value[0]) {
          item.action_plan = result.value[1];
          Swal.fire({
            title: "데이터 저장 작업 진행 중",
            html: "페이지 아카이빙 및 스크린샷 데이터를 저장하고 있습니다. 완료 후 창은 자동으로 닫힙니다.",
            allowOutsideClick: false,
            didOpen: async () => {
              Swal.showLoading();
              let uid;
              await axios.post(`${Constants.AWS.STAGE}${Constants.AWS.APIs.ARCHIVER}`, {
                    url: item.url,
                  }).then((res) => {
                    console.log(
                      "아카이버 반환 res.data.body.data.uid : ",
                      res.data.body.data.uid
                    );
                    uid = res.data.body.data.uid;
                    console.log("uid : ", uid);
                  }).catch(console.log);
              await axios.post(`${Constants.AWS.STAGE}${Constants.AWS.APIs.SCREENSHOOTER}`, {
                    url: item.url, uid
                  }).then((res) => console.log("스크린샷 : ", res.status)).catch(console.log);

              item.created_at = "";
              item.uid = uid;
              console.log(item);
              axios.post(`${Constants.SPRING_BACKEND.APIs.INTLIST}`, item);
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
        }
      })
      .catch((error) => {
        Swal.fire({
          title: '오류 발생!',
          icon: "error",
          html: '알 수 없는 에러가 발생하였습니다. 지속적으로 동일 현상이 발생하면 서버 관리자에게 문의해주세요.'
        });
        console.error(error);
      });
  };

  return (
    <>
      <PageHeader
        title="게시물 모니터링"
        desc="커뮤니티 사이트로부터 게시물을 수집합니다."
      />
      <section className="section section__monitor">
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
                    <button onClick={() => getSiteData(site)}>{site}</button>
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
                    <td>{post.view}</td>
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
