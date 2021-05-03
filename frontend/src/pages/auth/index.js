import React from 'react';
import { Route, Redirect } from 'react-router-dom';

import Login from './Login';
import SignUp from './SignUp';

export default function AuthRouter ({ match }) {

    const loggedIn = false; // 수정 필요

    return (
        <>
            <Route path={`${match.path}/login`} component={Login} />
            <Route exact path={`${match.path}/signup`} component={SignUp} />
            { !loggedIn && <Redirect to={`${match.path}/login`} /> }
        </>
    );
}