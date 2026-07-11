"use client"

import { useRouter } from "next/navigation";


export default function Navbar() {

    const router = useRouter();



    function logout() {

        localStorage.removeItem("token");

        localStorage.removeItem("user");

        router.push("/login");

    }



    return (

        <header className="
h-16
border-b
flex
items-center
justify-between
px-6
bg-white
">


            <h2 className="font-semibold">
                Manager Portal
            </h2>


            <button

                onClick={logout}

                className="
border
px-4
py-2
rounded
"

            >

                Logout

            </button>


        </header>

    )

}