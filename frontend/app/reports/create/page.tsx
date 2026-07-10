"use client"

import { useState } from "react";
import { createReport } from "@/services/report.service";
import { useRouter } from "next/navigation";
import Link from "next/link";

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

            await createReport(form);


            alert("Report created successfully");


            router.push("/reports");


        } catch (error) {

            console.log(error);

            alert("Failed to create report");

        }

    }



    return (

        <div className="max-w-2xl mx-auto">


            <h1 className="text-2xl font-bold mb-5">
                Create Weekly Report
            </h1>

            <Link
                href="/reports/create"
                className="bg-black text-white px-4 py-2 rounded inline-block mt-4"
            >
                Create Report
            </Link>



            <form
                onSubmit={handleSubmit}
                className="space-y-4"
            >



                <input

                    className="border p-2 w-full"

                    name="weekStart"

                    type="date"

                    value={form.weekStart}

                    onChange={handleChange}

                />



                <input

                    className="border p-2 w-full"

                    name="weekEnd"

                    type="date"

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

                    name="hoursWorked"

                    type="number"

                    placeholder="Hours Worked"

                    value={form.hoursWorked}

                    onChange={handleChange}

                />





                <button

                    className="bg-black text-white px-5 py-2 rounded"

                    type="submit"

                >

                    Create Report

                </button>



            </form>



        </div>

    )


}