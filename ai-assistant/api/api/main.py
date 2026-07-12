from fastapi import FastAPI
from api.routes import health_routes, chat_routes, rag_routes
from fastapi.middleware.cors import CORSMiddleware
 
app = FastAPI(
    title="Weekly report generation AI chat assistant",
    description="Weekly report generation AI chat assistant",
    version="1.0.0"
)

app.include_router(health_routes.router, prefix="/health", tags=["Health"])
app.include_router(chat_routes.router, prefix="/chat", tags=["Chat"])
app.include_router(rag_routes.router, prefix="/rag", tags=["Rag"])

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # restrict this to your frontend's origin in production
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get('/')
def root():
    return {
        "message": "Server running root is responding"
    }



