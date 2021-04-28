const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = (app) => {
  app.use(
		'/api/archiver', 
		createProxyMiddleware({ 
			target: 'https://sc4q7x54gh.execute-api.ap-northeast-2.amazonaws.com',
			changeOrigin: true,
            pathRewrite: { '^/api/archiver':'/' }
		}));

  app.use(
		'/api/monitor',
		createProxyMiddleware({
			target: 'http://127.0.0.1:8080',
			changeOrigin: true,
            pathRewrite: { '^/api/monitor':'/' }
		}));
};
