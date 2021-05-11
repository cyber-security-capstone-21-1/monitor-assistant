import React from 'react';

import { Link } from 'react-router-dom';
import Swal from 'sweetalert2';
import { faTrash, faFileAlt } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'

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
            confirmButtonText: '삭제',
            confirmButtonAriaLabel: '삭제',
            cancelButtonText: '취소',
            cancelButtonColor: "red",
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
                <header>{props.title}</header>
                <p>
                    {props.action_plan}
                </p>
            </div>
            <footer>
                <p>
                    <span>{props.site}</span>&nbsp;|&nbsp;<span>{props.author}</span>&nbsp;|&nbsp;<span>{new Date(props.created_at).toLocaleString()}</span>
                </p>
                <div className="footer__prefs">
                    <Link className="button button__info" to={`/service/intelligence/${props.uid}`}>
                        <FontAwesomeIcon icon={faFileAlt} />&nbsp;
                        <span>문서 보기</span>
                    </Link>
                    <a className="button button__warning button__cursor__pointer" onClick={onDelete} value={props.uid}>
                        <FontAwesomeIcon icon={faTrash} />&nbsp;
                        <span>삭제</span>
                    </a>
                </div>
            </footer>
        </article>
    );
};