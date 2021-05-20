const Constants = {
    ENDPOINT: "https://monitor-assistant.com",
    AWS: {
        STAGE: "/api",
        APIs: {
            ARCHIVER: "/archive",
            SCREENSHOOTER: "/screenshot",
            RESIZER: "/screenshot/resize?uid=",
            SCREENSHOTPREVIEW:"/screesshot/preview?url="
        }
    },
    SPRING_BACKEND: {
        APIs: {
            MONITOR: "/api/monitor",
            INTLIST: "/api/intelligences"
        }
    } 
};

export default Constants;
