import React from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faSearch, faCog, faSignOutAlt, faListAlt } from "@fortawesome/free-solid-svg-icons";
import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';

import './Aside.scss';

function Aside (props) {
    
    const signOut = () => {
        const MySwal = withReactContent(Swal);

        Swal.fire({
            title: '로그아웃하시겠습니까?',
            showDenyButton: true,
            showCancelButton: false,
            confirmButtonText: `예`,
            denyButtonText: `아니오`,
          }).then((result) => {
            /* Read more about isConfirmed, isDenied below */
            if (result.isConfirmed) {
                // 로그아웃
            } else if (result.isDenied) {
                // 실패
            }
          });
    };
    
    return (
        <aside>
            <nav>
                <Link className="nav-icon nav__home" to="/" data-tip="처음으로">Home</Link >
                <ul>
                    <li data-tip="첩보 목록">
                        <Link className="nav-icon nav__list" to="/list">
                            <FontAwesomeIcon icon={faListAlt} />
                        </Link>
                    </li>
                    <li data-tip="검색">
                        <Link className="nav-icon nav__search" to="/monitor">
                            <FontAwesomeIcon icon={faSearch} />
                        </Link>
                    </li>
                    <li data-tip="설정">
                        <Link className="nav-icon nav__preferences" to="/viewer">
                            <FontAwesomeIcon icon={faCog} />
                        </Link>
                    </li>
                    <li data-tip="로그아웃" onClick={signOut}>
                        <Link className="nav-icon nav__sign_out" to="#">
                            <FontAwesomeIcon icon={faSignOutAlt} />
                        </Link>
                    </li>
                </ul>
            </nav>
        </aside>
    )
}

export default Aside;
