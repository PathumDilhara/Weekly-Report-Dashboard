import api from "./api";


export async function getSummary() {

    const response = await api.get(
        "/dashboard/summary"
    );

    return response.data.data;

}



export async function getWorkload() {

    const response = await api.get(
        "/dashboard/workload"
    );

    return response.data.data;

}



export async function getSubmissionStatus() {

    const response = await api.get(
        "/dashboard/submission-status"
    );

    return response.data.data;

}



export async function getTrends() {

    const response = await api.get(
        "/dashboard/trends"
    );

    return response.data.data;

}