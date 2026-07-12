"use client"

import { useEffect, useState } from "react";
import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    Tooltip,
    CartesianGrid,
    PieChart,
    Pie,
    Cell,
    LineChart,
    Line,
    ResponsiveContainer,
} from "recharts";
import DashboardCard from "@/components/DashboardCard";

import {
    getSummary,
    getWorkload,
    getSubmissionStatus,
    getTrends,
} from "@/services/dashboard.service";
import Loading from "@/components/Loading";
import ChatAssistant from "@/components/ChatAssistant";

// One consistent palette used across every chart
const CHART_COLORS = ["#4f46e5", "#22c55e", "#f59e0b", "#f43f5e", "#0ea5e9"];

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

        const submission = await getSubmissionStatus();

        const statusCount = Object.entries(
            submission.reduce((acc: any, item: any) => {
                acc[item.status] = (acc[item.status] || 0) + 1;
                return acc;
            }, {})
        ).map(([status, count]) => ({ status, count }));

        setStatus(statusCount);
        setTrends(await getTrends());
    }

    if (!summary) return <Loading />;

    return (
        <div className="min-h-screen bg-slate-50">
            {/* Header */}
            <div className="border-b border-slate-200 bg-white">
                <div className="mx-auto max-w-7xl px-6 py-8">
                    <p className="text-sm font-medium text-indigo-600">Overview</p>
                    <h1 className="mt-1 text-3xl font-bold tracking-tight text-slate-900">
                        Manager Dashboard
                    </h1>
                    <p className="mt-1 text-sm text-slate-500">
                        A snapshot of your team&apos;s workload, submissions, and reporting trends.
                    </p>
                </div>
            </div>

            <div className="mx-auto max-w-7xl space-y-8 px-6 py-8">
                {/* Summary cards */}
                <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-5">
                    <DashboardCard title="Users" value={summary.totalUsers} />
                    <DashboardCard title="Projects" value={summary.totalProjects} />
                    <DashboardCard title="Reports" value={summary.totalReports} />
                    <DashboardCard title="Submitted" value={summary.submittedReports} />
                    <DashboardCard title="Draft" value={summary.draftReports} />
                </div>

                {/* Charts */}
                <div className="grid grid-cols-1 gap-6 lg:grid-cols-2">
                    <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
                        <div className="mb-5">
                            <h2 className="text-base font-semibold text-slate-900">Workload</h2>
                            <p className="text-xs text-slate-500">Hours logged per team member</p>
                        </div>
                        <ResponsiveContainer width="100%" height={280}>
                            <BarChart data={workload}>
                                <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#e2e8f0" />
                                <XAxis
                                    dataKey="userName"
                                    tick={{ fontSize: 12, fill: "#64748b" }}
                                    axisLine={{ stroke: "#e2e8f0" }}
                                    tickLine={false}
                                />
                                <YAxis
                                    tick={{ fontSize: 12, fill: "#64748b" }}
                                    axisLine={false}
                                    tickLine={false}
                                />
                                <Tooltip
                                    contentStyle={{
                                        borderRadius: 12,
                                        border: "1px solid #e2e8f0",
                                        fontSize: 13,
                                    }}
                                />
                                <Bar
                                    dataKey="totalHoursWorked"
                                    fill={CHART_COLORS[0]}
                                    radius={[6, 6, 0, 0]}
                                />
                            </BarChart>
                        </ResponsiveContainer>
                    </div>

                    <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
                        <div className="mb-5">
                            <h2 className="text-base font-semibold text-slate-900">Submission Status</h2>
                            <p className="text-xs text-slate-500">Breakdown of report statuses</p>
                        </div>
                        <ResponsiveContainer width="100%" height={280}>
                            <PieChart>
                                <Pie
                                    data={status}
                                    dataKey="count"
                                    nameKey="status"
                                    innerRadius={60}
                                    outerRadius={100}
                                    paddingAngle={2}
                                >
                                    {status.map((entry, index) => (
                                        <Cell
                                            key={entry.status ?? index}
                                            fill={CHART_COLORS[index % CHART_COLORS.length]}
                                        />
                                    ))}
                                </Pie>
                                <Tooltip
                                    contentStyle={{
                                        borderRadius: 12,
                                        border: "1px solid #e2e8f0",
                                        fontSize: 13,
                                    }}
                                />
                            </PieChart>
                        </ResponsiveContainer>
                    </div>

                    <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm lg:col-span-2">
                        <div className="mb-5">
                            <h2 className="text-base font-semibold text-slate-900">Report Trend</h2>
                            <p className="text-xs text-slate-500">Reports submitted per week</p>
                        </div>
                        <ResponsiveContainer width="100%" height={280}>
                            <LineChart data={trends}>
                                <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#e2e8f0" />
                                <XAxis
                                    dataKey="week"
                                    tick={{ fontSize: 12, fill: "#64748b" }}
                                    axisLine={{ stroke: "#e2e8f0" }}
                                    tickLine={false}
                                />
                                <YAxis
                                    tick={{ fontSize: 12, fill: "#64748b" }}
                                    axisLine={false}
                                    tickLine={false}
                                />
                                <Tooltip
                                    contentStyle={{
                                        borderRadius: 12,
                                        border: "1px solid #e2e8f0",
                                        fontSize: 13,
                                    }}
                                />
                                <Line
                                    type="monotone"
                                    dataKey="reportCount"
                                    stroke={CHART_COLORS[0]}
                                    strokeWidth={2.5}
                                    dot={{ r: 4, fill: CHART_COLORS[0] }}
                                />
                            </LineChart>
                        </ResponsiveContainer>
                    </div>
                </div>
            </div>

            {/* Floating chat assistant, wired to the FastAPI backend */}
            <ChatAssistant />
        </div>
    );
}
