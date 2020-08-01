const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = function (app) {
  app.use(
    createProxyMiddleware(["/api", "/actuator"], {
      target: "http://localhost:8080",
      changeOrigin: true,
      secure: false,
    }),
    createProxyMiddleware("/ws", {
      target: "ws://localhost:8080",
      ws: true,
      changeOrigin: true,
      secure: false,
      headers: {
        origin: "http://localhost:8080",
      },
    })
  );
};
