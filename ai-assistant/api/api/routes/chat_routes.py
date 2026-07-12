from fastapi import APIRouter
from api.schemas.chat_request import ChatRequest
from api.schemas.chat_response import ChatResponse
from core.orchestrator import Orchestrator

router = APIRouter()

orchestrator = Orchestrator()

@router.post("", response_model=ChatResponse)
async def chat(request:ChatRequest):
    user_message = request.message

    print(f"### user_message : {user_message}")

    # get response dic from orchestrator
    bot_response = await  orchestrator.get_response(user_message=user_message)

    # get options to provide to user based on confidence vlaue
    print(f"### response : {bot_response['response']}")

    return ChatResponse(response=bot_response["response"])
