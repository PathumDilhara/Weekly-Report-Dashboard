import api from "./api";

import { Project } from "@/types/Project";



export const getProjects = async () => {

    const response = await api.get<{ success: boolean; message: string; data: Project[] }>(
        "/project"
    );

    return response.data.data;

};



export const createProject = async (
    data: Project
) => {

    const response = await api.post(
        "/project",
        data
    );

    return response.data;

};



export const updateProject = async (
    id: number,
    data: Project
) => {

    const response = await api.put(
        `/project/${id}`,
        data
    );

    return response.data;

};



export const deleteProject = async (
    id: number
) => {

    const response = await api.delete(
        `/project/${id}`
    );

    return response.data;

};