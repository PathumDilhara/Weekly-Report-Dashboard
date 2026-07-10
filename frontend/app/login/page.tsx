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