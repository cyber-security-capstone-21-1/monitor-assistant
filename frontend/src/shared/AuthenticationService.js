import axios from 'axios'

class AuthenticationService {
    executeJwtAuthenticationService(email, password) {
        return axios.post('/api/monitor/authenticate', { email, password })
    }

    registerSuccessfulLoginForJwt(email, token) {
        localStorage.setItem('token', token);
        localStorage.setItem('authenticatedUser', username);
        this.setupAxiosInterceptors();
    }

    createJWTToken(token) {
        return `Bearer ${token}`;
    }

    setupAxiosInterceptors() {
        axios.interceptors.request.use(
            config => {
                const token = localStorage.getItem('token');
                if (token) {
                    config.headers['Authorization'] = this.createJWTToken(token);
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

    getLoggedInUsername () {
        let user = localStorage.getItem('authenticatedUser');
        if (user === null) return '';
        return user;
    }
}

export default new AuthenticationService();