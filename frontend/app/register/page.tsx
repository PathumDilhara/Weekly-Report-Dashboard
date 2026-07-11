"use client";


import { useState } from "react";
import { register } from "@/services/auth.service";
import { useRouter } from "next/navigation";


export default function RegisterPage() {

    const router = useRouter();
    const [form, setForm] = useState({

        username: "",
        email: "",
        password: "",
        firstName: "",
        lastName: ""

    });

    function handleChange(
        e: React.ChangeEvent<HTMLInputElement>
    ) {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });

    }

    async function handleRegister(
        e: React.FormEvent
    ) {
        e.preventDefault();

        try {
            const response = await register(form);
            alert(
                "Registration successful"
            );

            console.log(response);

            localStorage.setItem(
                "token",
                response.data.token
            );

            router.push("/dashboard");
        } catch (error) {
            console.log(error);
            alert(
                "Registration failed"
            );
        }

    }

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100">

            <div className="bg-white p-8 rounded-lg shadow-md w-96">

                <h1 className="text-2xl font-bold text-center mb-6">
                    Register
                </h1>

                <form
                    onSubmit={handleRegister}
                    className="space-y-4"
                >

                    <input
                        name="email"
                        placeholder="Email"
                        onChange={handleChange}
                        className="w-full border p-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
                    />

                    <input
                        name="firstName"
                        placeholder="First Name"
                        onChange={handleChange}
                        className="w-full border p-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
                    />

                    <input
                        name="lastName"
                        placeholder="Last Name"
                        onChange={handleChange}
                        className="w-full border p-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
                    />

                    <input
                        name="password"
                        type="password"
                        placeholder="Password"
                        onChange={handleChange}
                        className="w-full border p-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
                    />

                    <button
                        className="w-full bg-blue-600 text-white p-2 rounded hover:bg-blue-700"
                    >
                        Register
                    </button>

                </form>

            </div>

        </div>
    )
}