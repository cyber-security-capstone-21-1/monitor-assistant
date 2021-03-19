import React, { Component } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHome, faSearch, faCog, faSignOutAlt } from "@fortawesome/free-solid-svg-icons";
import "./Aside.scss";

class Aside extends Component {
    render() {
        return (
          <aside>
            <nav>
              <ul>
                <li>
                  <FontAwesomeIcon icon={faHome} className="nav__home" />
                </li>
                <li>
                  <FontAwesomeIcon icon={faSearch} className="nav__search" />
                </li>
                <li>
                  <FontAwesomeIcon icon={faCog} className="nav__preferences" />
                </li>
                <li>
                  <FontAwesomeIcon icon={faSignOutAlt} className="nav__sign_out" />
                </li>
              </ul>
            </nav>
          </aside>
        );
    }
  }
  
export default Aside;
  