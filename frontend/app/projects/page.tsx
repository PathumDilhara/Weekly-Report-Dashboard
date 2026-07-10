"use client";

import { useEffect, useState } from "react";
import { getProjects } from "@/services/project.service";
import { Project } from "@/types/Project";
import { ProtectedRoute } from "@/components/ProtectedRoute";

export default function ProjectsPage() {

    const [projects, setProjects] = useState<Project[]>([]);

    useEffect(() => {
        const fetchProjects = async () => {

            try {

                const data = await getProjects();
                console.log("API response:", data);

                setProjects(Array.isArray(data) ? data : []);

            } catch (error) {

                console.error(
                    "Failed to load projects",
                    error
                );

            }

        };


        fetchProjects();
    }, []);



    return (
        <ProtectedRoute>
            <div>
                <h1>
                    Projects
                </h1>
                {
                    projects.map(project => (
                        <div key={project.id}>
                            <h2>
                                {project.name}
                            </h2>

                            <p>
                                {project.description}
                            </p>
                        </div>
                    ))
                }
            </div>
        </ProtectedRoute>
    )
}