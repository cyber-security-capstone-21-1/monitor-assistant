import React from 'react';
import axios from 'axios';
import Swal from 'sweetalert2';

function login () {

    const onLogin = (email, password) => {
        const data = { email, password };
        axios.post('/api/monitor/api/auth/login', data)
            .then(response => {
                const { accessToken } = response.data;
                axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
            })
            .catch(error => {
                Swal.fire({
                    title: "로그인 실패",
                    icon: "error"
                });
                console.error(error);
            });
    }

    return (
        <>
            <form>
                <header>로그인</header>
                <input type="email" name="email" />
                <input type="password" name="password" />
                <input type="submit" onClick={onLogin}/>
            </form>
        </>
    );
}

export default login;