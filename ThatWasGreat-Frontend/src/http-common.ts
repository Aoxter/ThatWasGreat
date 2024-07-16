import axios from "axios";

const httpClient = axios.create({
    baseURL: "http://localhost:8080",
    headers: {
      "Content-type": "application/json"
    }
});

httpClient.interceptors.request.use(request => {
    console.log('Starting Request', JSON.stringify(request, null, 2))
    return request
})

export default httpClient;