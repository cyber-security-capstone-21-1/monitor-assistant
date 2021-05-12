import React, { memo } from 'react';
import { Link, NavLink } from 'react-router-dom';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSearch, faSignOutAlt, faListAlt } from "@fortawesome/free-solid-svg-icons";
import Swal from 'sweetalert2';

import AuthenticationService from '@/shared/AuthenticationService';
import './Aside.scss';

const Aside = memo((props) => {
    const signOut = () => {
        Swal.fire({
            title: '로그아웃하시겠습니까?',
            icon: 'info',
            showDenyButton: true,
            showCancelButton: false,
            confirmButtonText: `<svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="check-circle" class="svg-inline--fa fa-check-circle fa-w-16 " role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path fill="currentColor" d="M504 256c0 136.967-111.033 248-248 248S8 392.967 8 256 119.033 8 256 8s248 111.033 248 248zM227.314 387.314l184-184c6.248-6.248 6.248-16.379 0-22.627l-22.627-22.627c-6.248-6.249-16.379-6.249-22.628 0L216 308.118l-70.059-70.059c-6.248-6.248-16.379-6.248-22.628 0l-22.627 22.627c-6.248 6.248-6.248 16.379 0 22.627l104 104c6.249 6.249 16.379 6.249 22.628.001z"></path></svg> 로그아웃`,
            denyButtonText: `<svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="times" class="svg-inline--fa fa-times fa-w-11 " role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 352 512"><path fill="currentColor" d="M242.72 256l100.07-100.07c12.28-12.28 12.28-32.19 0-44.48l-22.24-22.24c-12.28-12.28-32.19-12.28-44.48 0L176 189.28 75.93 89.21c-12.28-12.28-32.19-12.28-44.48 0L9.21 111.45c-12.28 12.28-12.28 32.19 0 44.48L109.28 256 9.21 356.07c-12.28 12.28-12.28 32.19 0 44.48l22.24 22.24c12.28 12.28 32.2 12.28 44.48 0L176 322.72l100.07 100.07c12.28 12.28 32.2 12.28 44.48 0l22.24-22.24c12.28-12.28 12.28-32.19 0-44.48L242.72 256z"></path></svg> 취소`,
            confirmButtonColor: "#25cbcb",
            denyButtonColor: "#e7237f"
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.showLoading();
                //로그아웃 진행
                AuthenticationService.logout();
                Swal.close();
                //다른 방법 고려
                window.location.href = '/';
            }
        });
    };
    
    return (
        <aside>
            <nav>
                <Link className="nav-icon nav__home" to="/" data-tip="처음으로">Home</Link>
                <ul>
                    <li data-tip="첩보 목록">
                        <NavLink activeClassName="active" className="nav-icon nav__list" to="/service/list">
                            <FontAwesomeIcon icon={faListAlt} />
                        </NavLink>
                    </li>
                    <li data-tip="검색">
                        <NavLink activeClassName="active" className="nav-icon nav__search" to="/service/monitor">
                            <FontAwesomeIcon icon={faSearch} />
                        </NavLink>
                    </li>
                    {/* 추후 추가 */}
                    {/* <li data-tip="설정">
                        <NavLink activeClassName="active" className="nav-icon nav__preferences" to="/viewer">
                            <FontAwesomeIcon icon={faCog} />
                        </NavLink>
                    </li> */}
                    <li data-tip="로그아웃" onClick={signOut}>
                        <button className="nav-icon nav__sign_out">
                            <FontAwesomeIcon icon={faSignOutAlt} />
                        </button>
                    </li>
                </ul>
            </nav>
        </aside>
    )
});

export default Aside;
