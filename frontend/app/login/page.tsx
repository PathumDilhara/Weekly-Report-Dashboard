"use client"

import { useRouter } from "next/navigation"
import { login as loginApi } from "@/services/auth.service"; // Communicates with Spring Boot backend
import { useState } from "react";
import useAuth from "../hooks/useAuth"; // Stores JWT token in browser

export default function LoginPage() {

    const router = useRouter();
    const { login } = useAuth(); // Stores JWT token in browser


    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    async function handleLogin(e: React.SubmitEvent<HTMLFormElement>) {
        e.preventDefault();

        try {

            const response = await loginApi({
                email,
                password
            });


            console.log(response);
            console.log(response.data);

            const token = response.data.token;


            // localStorage.setItem(
            //     "token",
            //     response.token
            // );

            login(token);

            router.push("/dashboard");


        } catch (error) {

            console.log(error);

            alert("Login Failed");

        }
    }

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100">

            <div className="bg-white p-8 rounded-lg shadow-md w-96">

                <h1 className="text-2xl font-bold text-center mb-6">
                    Login
                </h1>

                <form
                    onSubmit={handleLogin}
                    className="space-y-4"
                >

                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={
                            e => setEmail(e.target.value)
                        }
                        className="w-full border p-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
                    />

                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={
                            e => setPassword(e.target.value)
                        }
                        className="w-full border p-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
                    />

                    <button
                        type="submit"
                        className="w-full bg-blue-600 text-white p-2 rounded hover:bg-blue-700"
                    >
                        Login
                    </button>

                </form>

            </div>

        </div>
    )
}