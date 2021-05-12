import React from 'react';

import { Link } from 'react-router-dom';
import Swal from 'sweetalert2';
import { faTrash, faFileAlt } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import axios from 'axios';

import "./IntelligenceCard.scss";

export default function IntelligenceCard (props) {
    
    const onDelete = (id) => {
        Swal.fire({
            title: '삭제 확인',
            icon: 'warning',
            html: '삭제하시겠습니까?',
            showCloseButton: false,
            showCancelButton: true,
            focusConfirm: true,
            confirmButtonText: '<svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="trash" class="svg-inline--fa fa-trash fa-w-14 " role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><path fill="currentColor" d="M432 32H312l-9.4-18.7A24 24 0 0 0 281.1 0H166.8a23.72 23.72 0 0 0-21.4 13.3L136 32H16A16 16 0 0 0 0 48v32a16 16 0 0 0 16 16h416a16 16 0 0 0 16-16V48a16 16 0 0 0-16-16zM53.2 467a48 48 0 0 0 47.9 45h245.8a48 48 0 0 0 47.9-45L416 128H32z"></path></svg> 삭제',
            confirmButtonColor: "#25cbcb",
            confirmButtonAriaLabel: '삭제',
            cancelButtonText: '<svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="times" class="svg-inline--fa fa-times fa-w-11 " role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 352 512"><path fill="currentColor" d="M242.72 256l100.07-100.07c12.28-12.28 12.28-32.19 0-44.48l-22.24-22.24c-12.28-12.28-32.19-12.28-44.48 0L176 189.28 75.93 89.21c-12.28-12.28-32.19-12.28-44.48 0L9.21 111.45c-12.28 12.28-12.28 32.19 0 44.48L109.28 256 9.21 356.07c-12.28 12.28-12.28 32.19 0 44.48l22.24 22.24c12.28 12.28 32.2 12.28 44.48 0L176 322.72l100.07 100.07c12.28 12.28 32.2 12.28 44.48 0l22.24-22.24c12.28-12.28 12.28-32.19 0-44.48L242.72 256z"></path></svg> 취소',
            cancelButtonColor: "#e7237f",
            cancelButtonAriaLabel: '취소',
            showLoaderOnConfirm: true,
            preConfirm: () => {
                return axios.delete(`/api/intelligences/${id}`)
            }
        })
            .then((result) => {
                if (result) {
                    console.log(result);
                }
            })
            .catch((error) => {});
    };

    return (
        <article className="card">
            <div className="content">
                <h4>{props.site}</h4>
                <header>
                    <a href={`https://cscapstone-21-1-5-s3.s3.ap-northeast-2.amazonaws.com/${props.uid}/index.html`}>{props.title}</a>
                </header>
                <h5>{props.author}</h5>
                <hr style={{ width: "80%", margin: "15px 0" }}/>
                <p>
                    {props.action_plan}
                </p>
            </div>
            <footer className="footer footer__card">
                <p>
                    <span>{new Date(props.created_at).toLocaleString()}</span>
                </p>
                <div className="footer__prefs">
                    <Link
                        className="button button__info"
                        to={`/service/intelligence/${props.uid}`}
                    >
                        <FontAwesomeIcon icon={faFileAlt} />&nbsp;<span>문서 보기</span>
                    </Link>
                    <button
                        className="button button__warning button__cursor__pointer"
                        onClick={onDelete}
                        value={props.uid}
                    >
                        <FontAwesomeIcon icon={faTrash} />&nbsp;<span>삭제</span>
                    </button>
                </div>
            </footer>
        </article>
    );
};