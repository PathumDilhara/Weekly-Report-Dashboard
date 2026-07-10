"use client"

import { useEffect, useState } from "react";
import { getMyReports } from "@/services/report.service";
import { Report } from "@/types/Report";
import Link from "next/link";

export default function ReportsPage() {

    const [reports, setReports] = useState<Report[]>([]);

    useEffect(() => {
        async function loadReports() {
            const data = await getMyReports();
            setReports(data);
        }

        loadReports();
    }, []);

    return (
        <div>
            <h1 className="text-2xl font-bold">
                My Weekly Reports
            </h1>
            {
                reports.map((report) => (

                    <Link
                        href={`/reports/${report.id}`}

                        key={report.id}

                        className="border p-4 mt-4 rounded block"

                    >
                        <h2>
                            {report.projectName}
                        </h2>
                        <p>
                            Week:
                            {report.weekStart}
                            -
                            {report.weekEnd}
                        </p>
                        <p>
                            Status:
                            {report.status}
                        </p>
                        <p>
                            Hours:
                            {report.hoursWorked}
                        </p>
                    </Link>
                ))
            }
        </div>
    )
}