import axios from 'axios';

class AuthenticationService {
    executeJwtAuthenticationService() {
        return axios.post(`/api/auth/authenticate`);
    }

    getNewAccessTokenWithRefreshToken() {
        let refresh = localStorage.getItem('token')
        return axios.post('/api/auth/refreshAccess', refresh);
    }

    registerSuccessfulLoginForJwt(refresh, access) {
        localStorage.setItem('token', refresh);
        this.setupAxiosInterceptors(access);
    }

    createJWTToken(token) {
        return `Bearer ${token}`;
    }

    setupAxiosInterceptors(access) {
        axios.interceptors.request.use(config => {
                if (access) {
                    let beartok = this.createJWTToken(access);
                    config.headers['Authorization'] = beartok;
                }
                return config;
            },
            error => {
                Promise.reject(error);
            }
        );
    }

    logout () {
        localStorage.removeItem('authenticatedUser');
        localStorage.removeItem('token');
    }

    isUserLoggedIn() {
        const token = localStorage.getItem('token');
        if (token) return true;
        return false;
    }

    getLoggedInEmail () {
        let user = localStorage.getItem('authenticatedUser');
        if (user === null) return '';
        return user;
    }
}

export default new AuthenticationService();