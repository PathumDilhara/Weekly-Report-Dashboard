import api from './api'

export async function testBackend() {
    const response = await api.get("/projects");

    console.log(response.data);
}