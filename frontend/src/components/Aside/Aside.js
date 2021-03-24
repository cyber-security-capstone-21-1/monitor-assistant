import React, { Component } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHome, faSearch, faCog, faSignOutAlt } from "@fortawesome/free-solid-svg-icons";
import "./Aside.scss";

class Aside extends Component {
    render() {
        return (
          <aside>
            <nav>
              <a className="nav__home" href="/"><FontAwesomeIcon icon={faHome} className="nav__home" /></a>
              <ul>
                <li>
                  <a><FontAwesomeIcon icon={faSearch} className="nav__search" /></a>
                </li>
                <li>
                  <a><FontAwesomeIcon icon={faCog} className="nav__preferences" /></a>
                </li>
                <li>
                  <a><FontAwesomeIcon icon={faSignOutAlt} className="nav__sign_out" /></a>
                </li>
              </ul>
            </nav>
          </aside>
        );
    }
  }
  
export default Aside;
  