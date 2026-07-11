"use client"

import Link from "next/link";
import useAuth from "@/app/hooks/useAuth";


export default function Sidebar() {

    const { user } = useAuth();


    return (

        <aside className="
        w-64
        min-h-screen
        border-r
        p-5
        bg-white
        ">


            <h1 className="
        text-xl
        font-bold
        mb-8
        ">
                Weekly Report
            </h1>



            <nav className="space-y-4">


                {
                    user?.role === "MANAGER" && (

                        <Link
                            href="/dashboard"
                            className="block"
                        >
                            Dashboard
                        </Link>

                    )
                }



                <Link
                    href="/reports"
                    className="block"
                >
                    Reports
                </Link>




                {
                    user?.role === "MANAGER" && (

                        <Link
                            href="/projects"
                            className="block"
                        >
                            Projects
                        </Link>

                    )
                }



            </nav>


        </aside>

    )

}