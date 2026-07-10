export interface DashboardSummary {

    totalUsers: number;

    totalProjects: number;

    totalReports: number;

    submittedReports: number;

    draftReports: number;
}


export interface Workload {

    userName: string;

    totalHoursWorked: number;

}


export interface SubmissionStatus {

    userName: string;

    projectName: string;

    weekStart: string;

    weekEnd: string;

    status: string;

}


export interface Trend {

    week: string;

    reportCount: number;

}