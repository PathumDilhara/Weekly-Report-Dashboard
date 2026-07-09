"use client"

import { useRouter } from "next/navigation"
import { login } from "@/services/auth.service";
import { useState } from "react";

export default function LoginPage() {

    const router = useRouter();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    async function handleLogin(e: React.SubmitEvent<HTMLFormElement>) {
        e.preventDefault();

        try {

            const response = await login({
                email,
                password
            });


            console.log(response);


            localStorage.setItem(
                "token",
                response.token
            );


            // router.push("/dashboard");


        } catch (error) {

            console.log(error);

            alert("Login Failed");

        }
    }

    return (
        <div>
            <h1>Login</h1>

            <form onSubmit={handleLogin}>

                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={
                        e => setEmail(e.target.value)
                    }
                />

                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={
                        e => setPassword(e.target.value)
                    }
                />

                <button type="submit">
                    Login
                </button>

            </form>
        </div >
    )
}