import React, { Component } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faSearch, faCog, faSignOutAlt } from "@fortawesome/free-solid-svg-icons";
import "./Aside.scss";

class Aside extends Component {
    render() {
        return (
          <aside>
            <nav>
              <a className="nav-icon nav__home" href="/" data-tip="처음으로">Home</a>
              <ul>
                <li data-tip="검색">
                  <a className="nav-icon nav__search" href="#">
                    <FontAwesomeIcon icon={faSearch} />
                  </a>
                </li>
                <li data-tip="설정">
                  <a className="nav-icon nav__preferences" href="#">
                    <FontAwesomeIcon icon={faCog} />
                  </a>
                </li>
                <li data-tip="로그아웃">
                  <a className="nav-icon nav__sign_out" href="#">
                    <FontAwesomeIcon icon={faSignOutAlt} />
                  </a>
                </li>
              </ul>
            </nav>
          </aside>
        );
    }
  }
  
export default Aside;
  