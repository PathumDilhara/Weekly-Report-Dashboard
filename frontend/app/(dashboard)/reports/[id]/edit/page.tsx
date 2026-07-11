"use client"

import { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import {
    getReportById,
    updateReport
} from "@/services/report.service";

export default function EditReportPage() {

    const params = useParams();
    const router = useRouter();
    const id = Number(params.id);
    const [form, setForm] = useState({
        projectId: 0,
        weekStart: "",
        weekEnd: "",
        tasksCompleted: "",
        tasksPlanned: "",
        blockers: "",
        hoursWorked: 0
    });

    useEffect(() => {
        loadReport();
    }, []);

    async function loadReport() {
        const report = await getReportById(id);

        setForm({
            projectId: report.projectId,
            weekStart: report.weekStart,
            weekEnd: report.weekEnd,
            tasksCompleted: report.tasksCompleted,
            tasksPlanned: report.tasksPlanned,
            blockers: report.blockers,
            hoursWorked: report.hoursWorked
        });
    }

    function handleChange(
        e: React.ChangeEvent<
            HTMLInputElement |
            HTMLTextAreaElement
        >
    ) {
        const { name, value } = e.target;

        setForm({
            ...form,
            [name]:
                name === "hoursWorked"
                    ?
                    Number(value)
                    :
                    value
        });
    }

    async function handleSubmit(
        e: React.FormEvent
    ) {
        e.preventDefault();
        await updateReport(
            id,
            form
        );

        alert("Report updated");
        router.push(`/reports/${id}`);
    }

    return (

        <div className="max-w-2xl mx-auto">
            <h1 className="text-2xl font-bold mb-5">
                Edit Weekly Report
            </h1>

            <form
                onSubmit={handleSubmit}
                className="space-y-4"
            >
                <input
                    className="border p-2 w-full"
                    type="date"
                    name="weekStart"
                    value={form.weekStart}
                    onChange={handleChange}
                />

                <input
                    className="border p-2 w-full"
                    type="date"
                    name="weekEnd"
                    value={form.weekEnd}
                    onChange={handleChange}
                />

                <textarea
                    className="border p-2 w-full"
                    name="tasksCompleted"
                    placeholder="Tasks Completed"
                    value={form.tasksCompleted}
                    onChange={handleChange}
                />

                <textarea
                    className="border p-2 w-full"
                    name="tasksPlanned"
                    placeholder="Tasks Planned"
                    value={form.tasksPlanned}
                    onChange={handleChange}
                />

                <textarea
                    className="border p-2 w-full"
                    name="blockers"
                    placeholder="Blockers"
                    value={form.blockers}
                    onChange={handleChange}
                />

                <input
                    className="border p-2 w-full"
                    type="number"
                    name="hoursWorked"
                    value={form.hoursWorked}
                    onChange={handleChange}
                />

                <button
                    className="bg-blue-600 text-white px-5 py-2 rounded"
                    type="submit"
                >
                    Update Report
                </button>
            </form>
        </div>

    )

}