const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = (app) => {
  app.use(
    createProxyMiddleware("/v1", {
      target: "https://sc4q7x54gh.execute-api.ap-northeast-2.amazonaws.com",
      changeOrigin: true,
    })
  );

  app.use(
    createProxyMiddleware("/api/monitor", {
      target: "http://127.0.0.1:8080",
      changeOrigin: true,
      // pathRewrite: { "^/api/monitor": "/" },
    })
  );
};
