"use client"

import { useEffect, useState } from "react";
import { getMyReports } from "@/services/report.service";
import { Report } from "@/types/Report";
import Link from "next/link";
import ReportCard from "@/components/ReportCard";

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
        <div className="min-h-screen bg-gray-100 p-6">

            <div className="max-w-4xl mx-auto">
                <div className="flex justify-between items-center mb-6">
                    <h1 className="text-2xl font-bold">
                        My Weekly Reports
                    </h1>

                    <Link
                        href="/reports/create"
                        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
                    >
                        Create Report
                    </Link>
                </div>

                <div className="space-y-4">
                    {
                        reports.length > 0 ? (
                            reports.map((report) => (

                                <ReportCard
                                    key={report.id}
                                    report={report}
                                />
                            ))
                        ) : (
                            <p className="text-gray-500">
                                No reports found
                            </p>
                        )
                    }
                </div>
            </div>
        </div>
    )
}