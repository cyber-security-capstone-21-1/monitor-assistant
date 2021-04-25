import React from 'react';
import PageHeader from '@/components/PageHeader/PageHeader';

import Swal from 'sweetalert2';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSearch } from "@fortawesome/free-solid-svg-icons";
import axios from 'axios';

function Monitor (props) {
    
    const search = () => {
        Swal.fire({
            title: '검색 중입니다...',
            html: '검색이 완료되면 창이 자동으로 사라집니다.',
            didOpen: () => {
                Swal.showLoading();
            },
            willClose: () => {
                const Toast = Swal.mixin({
                    toast: true,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 3000,
                    timerProgressBar: true,
                    didOpen: (toast) => {
                      toast.addEventListener('mouseenter', Swal.stopTimer);
                      toast.addEventListener('mouseleave', Swal.resumeTimer);
                    }
                  });
                  
                  Toast.fire({
                    icon: 'success',
                    title: '100개가 검색되었습니다.'
                  });
            }
        }).then((result) => {
            
            /* Read more about handling dismissals below */
            if (result.dismiss === Swal.DismissReason.timer) {
                console.log('I was closed by the timer');
            }
        });
    };
    
    const handleKeypress = (e) => {
        console.log(e.key);
        if (e.key === 'Enter') {
            e.preventDefault();
            search();
        }
    };

    const openDialog = (item) => {
        Swal.fire({
            title: `<header>게시물 제목</header>`,
            html:
              `게시물 body 영역`,
            showCloseButton: false,
            showCancelButton: true,
            focusConfirm: true,
            confirmButtonText: '저장',
            confirmButtonAriaLabel: '저장',
            cancelButtonText: '닫기',
            cancelButtonColor: "red",
            cancelButtonAriaLabel: '닫기',
            showLoaderOnConfirm: true,
            preConfirm: () => {
                return axios.all([
                    axios.post('/api/archiver/v1/archive', { "url": "http://naver.com" }, { headers: { "Access-Control-Allow-Origin": "*" } }),
                    axios.post('/api/archiver/v1/screenshot', { "url": "http://naver.com" }, { headers: { "Access-Control-Allow-Origin": "*" } }),
                ]);
            }
          }).then((result) => {
              if (result.isConfirmed) {
                const Toast = Swal.mixin({
                    toast: true,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 3000,
                    timerProgressBar: true,
                    didOpen: (toast) => {
                      toast.addEventListener('mouseenter', Swal.stopTimer);
                      toast.addEventListener('mouseleave', Swal.resumeTimer);
                    }
                  });
                  
                  Toast.fire({
                    icon: 'success',
                    title: '저장되었습니다.'
                  });
              }
          }).catch((err) => {
              console.error(err);
          });
    };

    return (
        <>
            <PageHeader title="모니터링" desc="모니터링" />
            <section>
                <form className="search">
                    <input type="text" placeholder="검색 키워드를 입력하세요" onKeyPress={handleKeypress} />
                    <button type="button" onClick={search}>
                        <FontAwesomeIcon icon={faSearch} />
                    </button>
                </form>
                <nav className="monitor__site_nav">
                    <ul>
                        <li>
                            <a>사이트</a>
                        </li>
                        <li>
                            <a>사이트</a>
                        </li>
                        <li>
                            <a>사이트</a>
                        </li>
                    </ul>
                </nav>
                <table>
                    <col style={{width: "10%"}} />
                    <col style={{width: "60%"}} />
                    <col style={{width: "10%"}} />
                    <col style={{width: "10%"}} />
                    <col style={{width: "10%"}} />
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
                        <tr onClick={openDialog}>
                            <td>일간베스트</td>
                            <td>제목</td>
                            <td>100</td>
                            <td>ㅇㅇ</td>
                            <td>{new Date().toLocaleDateString()}</td>
                        </tr>
                        <tr onClick={openDialog}>
                            <td>일간베스트</td>
                            <td>제목</td>
                            <td>100</td>
                            <td>ㅇㅇ</td>
                            <td>{new Date().toLocaleDateString()}</td>
                        </tr>
                        <tr onClick={openDialog}>
                            <td>일간베스트</td>
                            <td>제목</td>
                            <td>100</td>
                            <td>ㅇㅇ</td>
                            <td>{new Date().toLocaleDateString()}</td>
                        </tr>
                        <tr onClick={openDialog}>
                            <td>일간베스트</td>
                            <td>제목</td>
                            <td>100</td>
                            <td>ㅇㅇ</td>
                            <td>{new Date().toLocaleDateString()}</td>
                        </tr>
                    </tbody>
                </table>
            </section>
        </>
    );
}

export default Monitor;
