"use client"

import { useEffect, useState } from "react";
import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    Tooltip,
    PieChart,
    Pie,
    Cell,
    LineChart,
    Line
} from "recharts";
import DashboardCard from "@/components/DashboardCard";
import {
    getSummary,
    getWorkload,
    getSubmissionStatus,
    getTrends
} from "@/services/dashboard.service";
import Loading from "@/components/Loading";

export default function DashboardPage() {
    const [summary, setSummary] = useState<any>(null);
    const [workload, setWorkload] = useState<any[]>([]);
    const [status, setStatus] = useState<any[]>([]);
    const [trends, setTrends] = useState<any[]>([]);

    useEffect(() => {
        loadDashboard();
    }, []);


    async function loadDashboard() {

        setSummary(await getSummary());

        setWorkload(await getWorkload());

        const submission =
            await getSubmissionStatus();

        const statusCount =
            Object.values(
                submission.reduce(
                    (acc: any, item: any) => {
                        acc[item.status] =
                            (acc[item.status] || 0) + 1;
                        return acc;
                    }, {})
            )
                .map((item: any) => item);

        setStatus(statusCount);
        setTrends(await getTrends());

    }

    if (!summary)
        return <Loading />
    return (
        <div className="min-h-screen bg-gray-100 p-6">
            <div className="max-w-7xl mx-auto space-y-8">
                <h1 className="text-3xl font-bold">
                    Manager Dashboard
                </h1>

                <div className="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-5 gap-4">
                    <DashboardCard
                        title="Users"
                        value={summary.totalUsers}
                    />
                    <DashboardCard
                        title="Projects"
                        value={summary.totalProjects}
                    />
                    <DashboardCard
                        title="Reports"
                        value={summary.totalReports}
                    />
                    <DashboardCard
                        title="Submitted"
                        value={summary.submittedReports}
                    />
                    <DashboardCard
                        title="Draft"
                        value={summary.draftReports}
                    />
                </div>

                <div className="bg-white border rounded-lg p-5 shadow-sm">
                    <h2 className="font-bold text-lg mb-4">
                        Workload
                    </h2>
                    <BarChart
                        width={500}
                        height={300}
                        data={workload}
                    >
                        <XAxis dataKey="userName" />
                        <YAxis />
                        <Tooltip />
                        <Bar
                            dataKey="totalHoursWorked"
                        />
                    </BarChart>
                </div>

                <div className="bg-white border rounded-lg p-5 shadow-sm">
                    <h2 className="font-bold text-lg mb-4">
                        Submission Status
                    </h2>
                    <PieChart
                        width={400}
                        height={300}
                    >
                        <Pie
                            data={status}
                            dataKey="count"
                            nameKey="status"
                            outerRadius={100}
                        >
                            {
                                status.map(
                                    (entry, index) => (

                                        <Cell
                                            key={index}
                                        />

                                    )
                                )
                            }
                        </Pie>
                        <Tooltip />
                    </PieChart>
                </div>

                <div className="bg-white border rounded-lg p-5 shadow-sm">
                    <h2 className="font-bold text-lg mb-4">
                        Report Trend
                    </h2>
                    <LineChart
                        width={500}
                        height={300}
                        data={trends}
                    >
                        <XAxis dataKey="week" />
                        <YAxis />
                        <Tooltip />
                        <Line
                            type="monotone"
                            dataKey="reportCount"
                        />
                    </LineChart>
                </div>
            </div>
        </div>
    )


}