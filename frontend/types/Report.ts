export interface Report {
    id: number;

    projectId: number;
    projectName: string;

    userName: string;

    weekStart: string;
    weekEnd: string;

    tasksCompleted: string;
    tasksPlanned: string;

    blockers: string;

    hoursWorked: number;

    status: string;

    submitted: string;
}