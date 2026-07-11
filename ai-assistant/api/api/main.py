from fastapi import FastAPI
from api.routes import health_routes
from api.routes import chat_routes

app = FastAPI(
    title="Weekly report generation AI chat assistant",
    description="Weekly report generation AI chat assistant",
    version="1.0.0"
)

app.include_router(health_routes.router, prefix="/health", tags=["Health"])
app.include_router(chat_routes.router, prefix="/chat", tags=["Chat"])

@app.get('/')
def root():
    return {
        "message": "Server running root is responding"
    }