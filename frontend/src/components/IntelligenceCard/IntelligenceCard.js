import React from 'react';

import { Link } from 'react-router-dom';
import { faTrash, faFileAlt } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import "./IntelligenceCard.scss";

export default function IntelligenceCard ({ item, onRemove }) {
    
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
                    <Link
                        className="button button__info"
                        to={`/service/intelligence/${item.uid}`}
                    >
                        <FontAwesomeIcon icon={faFileAlt} />&nbsp;<span>문서 보기</span>
                    </Link>
                    <button
                        className="button button__warning button__cursor__pointer"
                        onClick={() => onRemove(item.id)}
                    >
                        <FontAwesomeIcon icon={faTrash} />&nbsp;<span>삭제</span>
                    </button>
                </div>
            </footer>
        </article>
    );
};