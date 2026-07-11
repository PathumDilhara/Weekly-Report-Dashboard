"use client"

import { useState } from "react";
import { createReport } from "@/services/report.service";
import { useRouter } from "next/navigation";
import toast from "react-hot-toast";

export default function CreateReportPage() {

    const router = useRouter();
    const [form, setForm] = useState({
        projectId: 3,
        weekStart: "",
        weekEnd: "",
        tasksCompleted: "",
        tasksPlanned: "",
        blockers: "",
        hoursWorked: 0
    });

    function handleChange(
        e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
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
        try {
            e.preventDefault();

            if (!form.weekStart ||
                !form.weekEnd ||
                !form.tasksCompleted) {
                toast.error(
                    "Please fill required fields"
                );
                return;
            }
            await createReport(form);
            alert("Report created successfully");
            router.push("/reports");
        } catch (error) {
            console.log(error);
            alert("Failed to create report");
        }

    }

    return (
        <div className="min-h-screen bg-gray-100 p-6">
            <div className="max-w-2xl mx-auto bg-white p-8 rounded-lg shadow-md">
                <h1 className="text-2xl font-bold mb-6">
                    Create Weekly Report
                </h1>
                <form
                    onSubmit={handleSubmit}
                    className="space-y-4"
                >
                    <input
                        className="border p-2 w-full rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
                        name="weekStart"
                        type="date"
                        value={form.weekStart}
                        onChange={handleChange}
                    />
                    <input
                        className="border p-2 w-full rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
                        name="weekEnd"
                        type="date"
                        value={form.weekEnd}
                        onChange={handleChange}
                    />

                    <textarea
                        className="border p-2 w-full rounded h-28 focus:outline-none focus:ring-2 focus:ring-blue-400"
                        name="tasksCompleted"
                        placeholder="Tasks Completed"
                        value={form.tasksCompleted}
                        onChange={handleChange}
                    />

                    <textarea
                        className="border p-2 w-full rounded h-28 focus:outline-none focus:ring-2 focus:ring-blue-400"
                        name="tasksPlanned"
                        placeholder="Tasks Planned"
                        value={form.tasksPlanned}
                        onChange={handleChange}
                    />

                    <textarea
                        className="border p-2 w-full rounded h-24 focus:outline-none focus:ring-2 focus:ring-blue-400"
                        name="blockers"
                        placeholder="Blockers"
                        value={form.blockers}
                        onChange={handleChange}
                    />

                    <input
                        className="border p-2 w-full rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
                        name="hoursWorked"
                        type="number"
                        placeholder="Hours Worked"
                        value={form.hoursWorked}
                        onChange={handleChange}
                    />

                    <button
                        className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
                        type="submit"
                    >
                        Create Report
                    </button>
                </form>
            </div>
        </div>
    )

}