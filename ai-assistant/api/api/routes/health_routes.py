from fastapi import APIRouter

router = APIRouter()

@router.get("/")
def health_check():
    return {
        "status": "ok",
        "message": "Weekly report AI Chatbot API is running"
    }