import api from "./api";

interface loginRequest {
    email: string;
    password: string;
}

interface RegisterRequest {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
}

export const login = async (data: loginRequest) => {
    const response = await api.post(
        "/auth/login",
        data
    );

    return response.data;
}


export const register = async (data: RegisterRequest) => {
    const response = await api.post(
        "/auth",
        data
    );

    return response.data;
}