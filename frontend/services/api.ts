import axios from "axios";

// Central Axios API client

// creates a customized Axios object/reusable client
// no need to type entire url since we set it as baseUrl type needed unique part only
// Default Headers are json format
const api = axios.create({
    baseURL: process.env.NEXT_PUBLIC_API_URL,
    headers: {
        "Content-Type": "application/json"
    },
});

// Attach JWT token automatically
// Interceptor runs before every API request
api.interceptors.request.use(
    (config) => {

        console.log("TOKEN:", localStorage.getItem("token"));
        console.log("URL:", config.url);
        console.log("DATA:", config.data);

        // Get JWT Token
        const token = localStorage.getItem("token");

        if (token) {
            // Auth header adding
            config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
    },

    (error) => {
        return Promise.reject(error);
    }
);

export default api;