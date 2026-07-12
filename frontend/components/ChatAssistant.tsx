"use client"

import { useEffect, useRef, useState } from "react";
import { MessageCircle, X, Send, Loader2 } from "lucide-react";

// Point this at your FastAPI backend.
// Matches: class ChatRequest(BaseModel): message: str
const CHAT_API_URL = "http://localhost:8000/chat/";

type ChatMessage = {
    role: "user" | "assistant";
    content: string;
};

export default function ChatAssistant() {
    const [open, setOpen] = useState(false);
    const [messages, setMessages] = useState<ChatMessage[]>([
        {
            role: "assistant",
            content: "Hi! I'm your dashboard assistant. Ask me about workload, submissions, or trends.",
        },
    ]);
    const [input, setInput] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const scrollRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        scrollRef.current?.scrollTo({ top: scrollRef.current.scrollHeight, behavior: "smooth" });
    }, [messages, open]);

    async function sendMessage() {
        const text = input.trim();
        if (!text || loading) return;

        setMessages((prev) => [...prev, { role: "user", content: text }]);
        setInput("");
        setLoading(true);
        setError(null);

        try {
            const res = await fetch(CHAT_API_URL, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ message: text }),
            });

            if (!res.ok) {
                throw new Error(`Request failed (${res.status})`);
            }

            const data = await res.json();

            // Adjust this line to match whatever field your FastAPI
            // endpoint returns (e.g. { reply }, { response }, { message }).
            const reply =
                data.reply ?? data.response ?? data.message ?? JSON.stringify(data);

            setMessages((prev) => [...prev, { role: "assistant", content: String(reply) }]);
        } catch (err: any) {
            setError("Couldn't reach the assistant. Is the API running on localhost:8000?");
        } finally {
            setLoading(false);
        }
    }

    function handleKeyDown(e: React.KeyboardEvent<HTMLInputElement>) {
        if (e.key === "Enter" && !e.shiftKey) {
            e.preventDefault();
            sendMessage();
        }
    }

    return (
        <>
            {/* Floating toggle button */}
            <button
                onClick={() => setOpen((v) => !v)}
                aria-label={open ? "Close chat assistant" : "Open chat assistant"}
                className="fixed bottom-6 right-6 z-50 flex h-14 w-14 items-center justify-center rounded-full bg-indigo-600 text-white shadow-lg shadow-indigo-600/30 transition-all hover:bg-indigo-700 hover:scale-105 focus:outline-none focus-visible:ring-2 focus-visible:ring-indigo-400 focus-visible:ring-offset-2"
            >
                {open ? <X size={24} /> : <MessageCircle size={24} />}
            </button>

            {/* Chat panel */}
            {open && (
                <div className="fixed bottom-24 right-6 z-50 flex h-[520px] w-[360px] flex-col overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-2xl">
                    <div className="flex items-center justify-between border-b border-slate-100 bg-slate-900 px-4 py-3">
                        <div>
                            <p className="text-sm font-semibold text-white">Dashboard Assistant</p>
                            <p className="text-xs text-slate-400">Connected to localhost:8000</p>
                        </div>
                        <button
                            onClick={() => setOpen(false)}
                            aria-label="Close chat"
                            className="rounded-md p-1 text-slate-400 hover:bg-slate-800 hover:text-white"
                        >
                            <X size={18} />
                        </button>
                    </div>

                    <div ref={scrollRef} className="flex-1 space-y-3 overflow-y-auto bg-slate-50 px-4 py-4">
                        {messages.map((m, i) => (
                            <div
                                key={i}
                                className={`flex ${m.role === "user" ? "justify-end" : "justify-start"}`}
                            >
                                <div
                                    className={`max-w-[80%] rounded-2xl px-3.5 py-2 text-sm leading-relaxed ${m.role === "user"
                                            ? "rounded-br-sm bg-indigo-600 text-white"
                                            : "rounded-bl-sm border border-slate-200 bg-white text-slate-700"
                                        }`}
                                >
                                    {m.content}
                                </div>
                            </div>
                        ))}
                        {loading && (
                            <div className="flex justify-start">
                                <div className="flex items-center gap-2 rounded-2xl rounded-bl-sm border border-slate-200 bg-white px-3.5 py-2 text-sm text-slate-500">
                                    <Loader2 size={14} className="animate-spin" />
                                    Thinking…
                                </div>
                            </div>
                        )}
                        {error && (
                            <p className="rounded-lg bg-rose-50 px-3 py-2 text-xs text-rose-600">{error}</p>
                        )}
                    </div>

                    <div className="flex items-center gap-2 border-t border-slate-100 bg-white p-3">
                        <input
                            value={input}
                            onChange={(e) => setInput(e.target.value)}
                            onKeyDown={handleKeyDown}
                            placeholder="Ask about your dashboard…"
                            className="flex-1 rounded-full border border-slate-200 bg-slate-50 px-4 py-2 text-sm text-slate-700 outline-none focus:border-indigo-400 focus:ring-2 focus:ring-indigo-100"
                        />
                        <button
                            onClick={sendMessage}
                            disabled={loading || !input.trim()}
                            aria-label="Send message"
                            className="flex h-9 w-9 shrink-0 items-center justify-center rounded-full bg-indigo-600 text-white transition-colors hover:bg-indigo-700 disabled:opacity-40"
                        >
                            <Send size={16} />
                        </button>
                    </div>
                </div>
            )}
        </>
    );
}
