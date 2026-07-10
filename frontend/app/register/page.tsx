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
        <div>
            <h1>Register</h1>

            <form onSubmit={handleRegister}>

                <input
                    name="email"
                    placeholder="Email"
                    onChange={handleChange}
                />

                <input
                    name="firstName"
                    placeholder="First Name"
                    onChange={handleChange}
                />

                <input
                    name="lastName"
                    placeholder="Last Name"
                    onChange={handleChange}
                />

                <input
                    name="password"
                    type="password"
                    placeholder="Password"
                    onChange={handleChange}
                />

                <button>
                    Register
                </button>

            </form>
        </div>
    )
}