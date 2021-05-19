import React, { useState, useRef, useEffect } from "react";
import Swal from "sweetalert2";
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch } from "@fortawesome/free-solid-svg-icons";

import Constants from "@/shared/constants";
import PageHeader from "@/components/PageHeader/PageHeader";
import uuidv4 from "@/shared/services/uuid";

import "./Monitor.scss";

function Monitor(props) {
  var crawlSiteList = [];
  const [activeTab, setActiveTab] = useState(0);
  const [reqSiteCodeList, setReqSiteCodeList] = useState([]);
  const [postList, setPostList] = useState([]);
  const [siteList, setSiteList] = useState([]);
  const [result, setResult] = useState([]);
  const useinput = useRef();

  useEffect(() => {
    async function getSiteList() {
      const sites = await axios.get("/api/monitor/");
      if ("data" in sites.data) {
        setReqSiteCodeList([...sites.data.data]);
      }
    }
    getSiteList();
  }, []);

  const search = async () => {
    const word = useinput.current.value;

    if (word.length > 0) {
      Swal.fire({
        title: "검색 요청 중입니다...",
        html: "검색이 완료되는 순서대로 화면에 표시됩니다.",
        allowOutsideClick: false,
        didOpen: async () => {
          Swal.showLoading();
          setResult([]);
          setPostList([]);
          setSiteList(["전체"]);

          for (let site of reqSiteCodeList) {
            axios
              .get(
                `${Constants.SPRING_BACKEND.APIs.MONITOR}/${site["code"]}?keyword=${word}`
              )
              .then(({ data: { data, message } }) => {
                if (message !== "ok") {
                  throw Error(message);
                }
                setPostList((state) => [...state, ...data]);
                setResult((state) => [...state, ...data]);
                setSiteList((sites) => [...sites, site["name"]]);
              })
              .catch((error) => {
                console.error(error);
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
        },
      });
    } else {
      Swal.fire({
        title: "오류",
        icon: "error",
        html: "모니터링 키워드 입력이 필요합니다.",
        confirmButtonText: "확인",
        showCancelButton: false,
      });
    }
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
      confirmButtonText: `<svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="arrow-right" class="svg-inline--fa fa-arrow-right fa-w-14 " role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><path fill="currentColor" d="M190.5 66.9l22.2-22.2c9.4-9.4 24.6-9.4 33.9 0L441 239c9.4 9.4 9.4 24.6 0 33.9L246.6 467.3c-9.4 9.4-24.6 9.4-33.9 0l-22.2-22.2c-9.5-9.5-9.3-25 .4-34.3L311.4 296H24c-13.3 0-24-10.7-24-24v-32c0-13.3 10.7-24 24-24h287.4L190.9 101.2c-9.8-9.3-10-24.8-.4-34.3z"></path></svg> 다음`,
      confirmButtonAriaLabel: "다음",
      confirmButtonColor: "#25cbcb",
      cancelButtonText: `<svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="times" class="svg-inline--fa fa-times fa-w-11 " role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 352 512"><path fill="currentColor" d="M242.72 256l100.07-100.07c12.28-12.28 12.28-32.19 0-44.48l-22.24-22.24c-12.28-12.28-32.19-12.28-44.48 0L176 189.28 75.93 89.21c-12.28-12.28-32.19-12.28-44.48 0L9.21 111.45c-12.28 12.28-12.28 32.19 0 44.48L109.28 256 9.21 356.07c-12.28 12.28-12.28 32.19 0 44.48l22.24 22.24c12.28 12.28 32.2 12.28 44.48 0L176 322.72l100.07 100.07c12.28 12.28 32.2 12.28 44.48 0l22.24-22.24c12.28-12.28 12.28-32.19 0-44.48L242.72 256z"></path></svg> 취소`,
      cancelButtonAriaLabel: "취소",
      cancelButtonColor: "#e7237f",
      showCancelButton: true,
      focusConfirm: true,
      progressSteps: ["1", "2", "3", "4", "5"],
      showLoaderOnConfirm: true,
    })
      .queue([
        {
          title: `<header>${item.title}</header>`,
          html: item.content,
        },
        {
          title: "첩보 제목 입력",
          input: "text",
          inputPlaceholder: "첩보 제목을 입력해주세요",
          inputAttributes: {
            required: true,
          },
          validationMessage: "입력값 누락 혹은 오류",
        },
        {
          title: "유형 입력",
          input: "select",
          inputOptions: {
            정보통신망이용촉진및정보보호등에관한법률: {
              정보통신망이용촉진및정보보호등에관한법률:
                "정보통신망이용촉진및정보보호등에관한법률",
            },
          },
          inputPlaceholder: "관련 법률 선택",
          inputAttributes: {
            required: true,
          },
          validationMessage: "입력값 누락 혹은 오류",
        },
        {
          title: "첩보 내용을 입력해주세요",
          input: "textarea",
          inputPlaceholder: "어떤 내용의 것인가요?",
          inputAttributes: {
            "aria-label": "메모 입력",
            required: true,
          },
          validationMessage: "입력값 누락 혹은 오류",
        },
        {
          title: "대응방안을 입력해주세요",
          input: "textarea",
          inputLabel: "대응 방안 입력",
          inputPlaceholder: "내용을 입력해주세요.",
          inputAttributes: {
            "aria-label": "메모 입력",
            required: true,
          },
          validationMessage: "입력값 누락 혹은 오류",
          confirmButtonText: `<svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="save" class="svg-inline--fa fa-save fa-w-14 " role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><path fill="currentColor" d="M433.941 129.941l-83.882-83.882A48 48 0 0 0 316.118 32H48C21.49 32 0 53.49 0 80v352c0 26.51 21.49 48 48 48h352c26.51 0 48-21.49 48-48V163.882a48 48 0 0 0-14.059-33.941zM224 416c-35.346 0-64-28.654-64-64 0-35.346 28.654-64 64-64s64 28.654 64 64c0 35.346-28.654 64-64 64zm96-304.52V212c0 6.627-5.373 12-12 12H76c-6.627 0-12-5.373-12-12V108c0-6.627 5.373-12 12-12h228.52c3.183 0 6.235 1.264 8.485 3.515l3.48 3.48A11.996 11.996 0 0 1 320 111.48z"></path></svg> 저장`,
          confirmButtonAriaLabel: "저장",
        },
      ])
      .then((result) => {
        if (result.value && result.value.length === 5) {
          item.intelligence_title = result.value[1];
          item.crime_type = result.value[2];
          item.description = result.value[3];
          item.action_plan = result.value[4];
          item.search_keyword = useinput.current.value;
          let success = true;
          Swal.fire({
            title: "데이터 저장 작업 진행 중",
            html: "페이지 아카이빙 및 스크린샷 데이터를 저장하고 있습니다. 완료 후 창은 자동으로 닫힙니다.",
            allowOutsideClick: false,
            didOpen: async () => {
              Swal.showLoading();
              let uid;
              axios
                .post(`${Constants.AWS.STAGE}${Constants.AWS.APIs.ARCHIVER}`, {
                  url: item.url,
                })
                .then(
                  ({
                    data: {
                      body: { data },
                    },
                  }) => {
                    uid = data.uid;
                    axios
                      .post(
                        `${Constants.AWS.STAGE}${Constants.AWS.APIs.SCREENSHOOTER}`,
                        { url: item.url, uid: data.uid }
                      )
                      .then(({ data }) => {
                        console.log("스크린샷 : ", data);
                        item.created_at = new Date();
                        item.uid = uid;
                        delete item["view"];
                        axios
                          .post(
                            `${Constants.SPRING_BACKEND.APIs.INTLIST}`,
                            item
                          )
                          .then((result) => {
                            Swal.close();
                          })
                          .catch((e) => {
                            success =false
                          });
                      })
                      .catch((e) => {
                        success =false
                      });
                  }
                )
                .catch((e) => {
                  success =false
                });
            },
            willClose: () => {
              console.log(success);
              
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
                title: '저장이 완료되었습니다.',
              });
            },
          });
        }
      })
      .catch((error) => {
        Swal.fire({
          title: "오류 발생!",
          icon: "error",
          html: "알 수 없는 에러가 발생하였습니다. 지속적으로 동일 현상이 발생하면 서버 관리자에게 문의해주세요.",
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
          <div className="notice notice__monitor_description">
            <p>옆으로 스크롤하여 사이트별로 필터링이 가능합니다.</p>
          </div>
        </nav>
        <table className="table table__monitor">
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
                  <tr onClick={() => openDialog(post)} key={uuidv4()}>
                    <td>{post.site}</td>
                    <td>{post.title}</td>
                    <td>{post.view}</td>
                    <td>{post.author}</td>
                    <td>
                      {post.created_at !== ""
                        ? new Date(post.created_at).toLocaleDateString()
                        : ""}
                    </td>
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
