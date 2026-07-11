"use client"

import { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";

import {
    getReportById,
    deleteReport,
    submitReport
} from "@/services/report.service";

import { Report } from "@/types/Report";
import Link from "next/link";

export default function ReportDetailsPage() {


    const params = useParams();

    const router = useRouter();


    const id = Number(params.id);



    const [report, setReport] = useState<Report | null>(null);



    useEffect(() => {

        loadReport();

    }, []);



    async function loadReport() {
        const data = await getReportById(id);
        setReport(data);
    }



    async function handleDelete() {

        if (!confirm("Delete this report?"))
            return;

        await deleteReport(id);
        alert("Report deleted");
        router.push("/reports");
    }



    async function handleSubmit() {
        await submitReport(id);
        alert("Report submitted");
        loadReport();
    }



    if (!report)
        return <p>Loading...</p>;

    return (
        <div className="max-w-3xl mx-auto">
            <h1 className="text-3xl font-bold mb-5">
                Weekly Report Details
            </h1>
            <div className="border rounded p-5 space-y-3">
                <p>
                    <b>Project:</b> {report.projectName}
                </p>

                <p>
                    <b>User:</b> {report.userName}
                </p>

                <p>
                    <b>Week:</b>
                    {report.weekStart}
                    &nbsp;to&nbsp;
                    {report.weekEnd}
                </p>

                <p>
                    <b>Status:</b> {report.status}
                </p>

                <p>
                    <b>Hours Worked:</b>
                    {report.hoursWorked}
                </p>

                <div>
                    <b>Tasks Completed</b>
                    <p>
                        {report.tasksCompleted}
                    </p>
                </div>

                <div>
                    <b>Tasks Planned</b>
                    <p>
                        {report.tasksPlanned}
                    </p>
                </div>

                <div>
                    <b>Blockers</b>
                    <p>
                        {report.blockers}
                    </p>
                </div>

            </div>

            <div className="mt-5 flex gap-3">
                <Link
                    href={`/reports/${id}/edit`}
                    className="bg-blue-600 text-white px-4 py-2 rounded"
                >
                    Edit
                </Link>

                <button
                    onClick={handleSubmit}
                    className="bg-green-600 text-white px-4 py-2 rounded"
                >
                    Submit
                </button>

                <button
                    onClick={handleDelete}
                    className="bg-red-600 text-white px-4 py-2 rounded"
                >
                    Delete
                </button>
            </div>
        </div>
    )
}