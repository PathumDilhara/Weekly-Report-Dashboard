import api from "./api";
import { Report } from "@/types/Report";


export interface CreateReportRequest {

    projectId: number;

    weekStart: string;
    weekEnd: string;

    tasksCompleted: string;
    tasksPlanned: string;

    blockers: string;

    hoursWorked: number;
}



export async function createReport(
    data: CreateReportRequest
) {

    const response = await api.post(
        "/report",
        data
    );

    return response.data.data;
}



export async function getMyReports() {
    const response = await api.get(
        "/report/my"
    );

    return response.data.data;
}



export async function getReportById(
    id: number
) {

    const response = await api.get(
        `/report/${id}`
    );

    return response.data.data;
}



export async function updateReport(
    id: number,
    data: CreateReportRequest
) {

    const response = await api.put(
        `/report/${id}`,
        data
    );

    return response.data.data;
}



export async function deleteReport(
    id: number
) {

    const response = await api.delete(
        `/report/${id}`
    );

    return response.data.data;
}



export async function submitReport(
    id: number
) {

    const response = await api.post(
        `/report/${id}/submit`
    );

    return response.data.data;
}