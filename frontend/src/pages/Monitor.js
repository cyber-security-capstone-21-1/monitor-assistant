import React from 'react';
import PageHeader from '@/components/PageHeader/PageHeader';

import Swal from 'sweetalert2';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faSearch } from "@fortawesome/free-solid-svg-icons";

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
                      toast.addEventListener('mouseenter', Swal.stopTimer)
                      toast.addEventListener('mouseleave', Swal.resumeTimer)
                    }
                  })
                  
                  Toast.fire({
                    icon: 'success',
                    title: '100개가 검색되었습니다.'
                  })
            }
        }).then((result) => {
            
            /* Read more about handling dismissals below */
            if (result.dismiss === Swal.DismissReason.timer) {
                console.log('I was closed by the timer')
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

    const openDialog = () => {
        Swal.fire({
            title: '<strong>HTML <u>example</u></strong>',
            icon: 'info',
            html:
              'You can use <b>bold text</b>, ' +
              '<a href="//sweetalert2.github.io">links</a> ' +
              'and other HTML tags',
            showCloseButton: true,
            showCancelButton: true,
            focusConfirm: false,
            confirmButtonText:
              '<i class="fa fa-thumbs-up"></i> Great!',
            confirmButtonAriaLabel: 'Thumbs up, great!',
            cancelButtonText:
              '<i class="fa fa-thumbs-down"></i>',
            cancelButtonAriaLabel: 'Thumbs down'
          })
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
