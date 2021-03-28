import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faSearch, faCog, faSignOutAlt } from "@fortawesome/free-solid-svg-icons";

import './Aside.scss';

class Aside extends Component {
    render () {
        return (
            <aside>
                <nav>
                <Link className="nav-icon nav__home" to="/" data-tip="처음으로">Home</Link >
                <ul>
                    <li data-tip="검색">
                    <Link className="nav-icon nav__search" to="/classify">
                        <FontAwesomeIcon icon={faSearch} />
                    </Link>
                    </li>
                    <li data-tip="설정">
                    <Link className="nav-icon nav__preferences" to="/viewer">
                        <FontAwesomeIcon icon={faCog} />
                    </Link>
                    </li>
                    <li data-tip="로그아웃">
                    <Link className="nav-icon nav__sign_out" to="#">
                        <FontAwesomeIcon icon={faSignOutAlt} />
                    </Link>
                    </li>
                </ul>
                </nav>
            </aside>
        )
    }
}

export default Aside;