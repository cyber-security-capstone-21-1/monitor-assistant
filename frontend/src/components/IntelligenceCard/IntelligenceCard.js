import React from 'react';
import { useHistory } from 'react-router-dom';
import Constants from '@/shared/constants';
import Swal from 'sweetalert2';
import axios from 'axios';
import { faTrash, faFileAlt, faFileImage } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import "./IntelligenceCard.scss";

export default function IntelligenceCard ({ item, onRemove }) {

    const history = useHistory();

    const onReadyThumbnail = uid => {
        Swal.fire({
            title: '문서 준비중...',
            html: '문서에 들어갈 스크린샷 이미지 썸네일을 준비하고 있습니다.',
            allowOutsideClick: false,
            didOpen: () => {
              Swal.showLoading();
              axios.get(`${Constants.AWS.STAGE}${Constants.AWS.APIs.RESIZER}${uid}`)
                .then((result) => {
                    if (result.status === 200) {
                        history.push(`/service/intelligence/${uid}`);
                        window.location.reload(true);
                    }
                })
                .catch(console.error)
            }
          });
    }

    const screenshot = uid => {
        Swal.fire({
            title: '미리보기',
            html: `<img src="/archives/${uid}/screenshots/w_1920.png" alt="Screenshot" />`,
        });
    }

    return (
        <article className="card">
            <div className="content">
                <h4>{item.site}</h4>
                <header>
                    <a href={`/archives/${item.uid}/index.html`}>{item.title}</a>
                </header>
                <h5>{item.author}</h5>
                <hr style={{ width: "80%", margin: "15px 0" }}/>
                <p>
                    {item.action_plan}
                </p>
            </div>
            <footer className="footer footer__card">
                <p>
                    <span>{new Date(item.created_at).toLocaleString()}</span>
                </p>
                <div className="footer__prefs">
                    <button
                        className="button button__info"
                        onClick={() => onReadyThumbnail(item.uid)}
                    >
                        <FontAwesomeIcon icon={faFileAlt} />&nbsp;<span>문서 보기</span>
                    </button>
                    <button
                        className="button button__warning button__cursor__pointer"
                        onClick={() => onRemove(item.id)}
                    >
                        <FontAwesomeIcon icon={faTrash} />&nbsp;<span>삭제</span>
                    </button>
                    <button
                        className="button button__warning button__cursor__pointer"
                        onClick={() => screenshot(item.uid)}
                    >
                        <FontAwesomeIcon icon={faFileImage} />&nbsp;<span>스크린샷</span>
                    </button>
                </div>
            </footer>
        </article>
    );
};