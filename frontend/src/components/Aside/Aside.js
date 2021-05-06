import React, {memo} from 'react';
import { Link, NavLink } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faSearch, faCog, faSignInAlt, faSignOutAlt, faListAlt } from "@fortawesome/free-solid-svg-icons";
import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';

import './Aside.scss';
import AuthenticationService from '@/shared/AuthenticationService';

const Aside = memo((props) => {
    const authenticated = AuthenticationService.isUserLoggedIn();
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
                //로그아웃 진행
                AuthenticationService.logout();
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
                    { authenticated ?
                        (<li activeClassName="" data-tip="로그아웃" onClick={signOut}>
                            <NavLink className="nav-icon nav__sign_out" to="#">
                                <FontAwesomeIcon icon={faSignOutAlt} />
                            </NavLink>
                        </li>)
                        :
                        (<li activeClassName="" data-tip="로그인">
                            <NavLink className="nav-icon nav__sign_in" to="/auth/login">
                                <FontAwesomeIcon icon={faSignInAlt} />
                            </NavLink>
                        </li>)
                    }
                    
                </ul>
            </nav>
        </aside>
    )
});

export default Aside;
