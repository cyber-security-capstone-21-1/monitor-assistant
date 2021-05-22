import React, { useState, useEffect } from "react";
import axios from "axios";
import Swal from "sweetalert2";

import PageHeader from "@/components/PageHeader/PageHeader";
import IntelligenceCard from "@/components/IntelligenceCard/IntelligenceCard";
import AuthenticationService from "@/shared/AuthenticationService";
import Constants from "@/shared/constants";

function IntList() {
  const [intList, setIntList] = useState([]);

  useEffect(() => {
    Swal.fire({
      title: "목록 로딩 중",
      html: "데이터를 불러오고 있습니다.",
      allowOutsideClick: false,
      didOpen: async () => {
        Swal.showLoading();
        console.log("목록 불러옴");
        axios.get(`${Constants.SPRING_BACKEND.APIs.INTLIST}`)
          .then((result) => {
            setIntList([...result.data.data]);
            Swal.close();
          })
          .catch((e) => {
            console.log("토큰 만료~");
            AuthenticationService.getNewAccessTokenWithRefreshToken()
              .then((res) => {
                AuthenticationService.setupAxiosInterceptors(
                  res.data.data.accessToken
                );
                axios.get(`${Constants.SPRING_BACKEND.APIs.INTLIST}`).then(res => {
                    setIntList([...res.data.data]);
                })
              })
              .catch((e) => {
                console.log("refresh 토큰 만료");
              });
            Swal.close();
          });
      },
    });
  }, []);

  const onRemove = (id) => {
    Swal.fire({
      title: "삭제 확인",
      icon: "warning",
      html: "삭제하시겠습니까?",
      showCloseButton: false,
      showCancelButton: true,
      focusConfirm: true,
      confirmButtonText:
        '<svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="trash" class="svg-inline--fa fa-trash fa-w-14 " role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><path fill="currentColor" d="M432 32H312l-9.4-18.7A24 24 0 0 0 281.1 0H166.8a23.72 23.72 0 0 0-21.4 13.3L136 32H16A16 16 0 0 0 0 48v32a16 16 0 0 0 16 16h416a16 16 0 0 0 16-16V48a16 16 0 0 0-16-16zM53.2 467a48 48 0 0 0 47.9 45h245.8a48 48 0 0 0 47.9-45L416 128H32z"></path></svg> 삭제',
      confirmButtonColor: "#25cbcb",
      confirmButtonAriaLabel: "삭제",
      cancelButtonText:
        '<svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="times" class="svg-inline--fa fa-times fa-w-11 " role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 352 512"><path fill="currentColor" d="M242.72 256l100.07-100.07c12.28-12.28 12.28-32.19 0-44.48l-22.24-22.24c-12.28-12.28-32.19-12.28-44.48 0L176 189.28 75.93 89.21c-12.28-12.28-32.19-12.28-44.48 0L9.21 111.45c-12.28 12.28-12.28 32.19 0 44.48L109.28 256 9.21 356.07c-12.28 12.28-12.28 32.19 0 44.48l22.24 22.24c12.28 12.28 32.2 12.28 44.48 0L176 322.72l100.07 100.07c12.28 12.28 32.2 12.28 44.48 0l22.24-22.24c12.28-12.28 12.28-32.19 0-44.48L242.72 256z"></path></svg> 취소',
      cancelButtonColor: "#e7237f",
      cancelButtonAriaLabel: "취소",
      showLoaderOnConfirm: true,
      preConfirm: () => {
        return axios.delete(`/api/intelligences/${id}`);
      },
    })
      .then((result) => {
        if (result.isConfirmed && result.value.status === 200) {
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
            title: `삭제되었습니다.`,
          });

          setIntList(intList.filter((item) => item.id !== id));
        }
      })
      .catch((error) => {});
  };

  return (
    <>
      <PageHeader
        title="첩보 리스트"
        desc="수집한 게시물의 내용이 담겨있습니다."
      />
      <section className="section section__intelligence_list">
        {intList.map((item) => {
          return (
            <IntelligenceCard key={item.uid} item={item} onRemove={onRemove} />
          );
        })}
      </section>
    </>
  );
}

export default IntList;
