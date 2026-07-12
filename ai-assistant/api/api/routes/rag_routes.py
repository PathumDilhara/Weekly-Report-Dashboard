from fastapi import APIRouter, HTTPException, Request
from api.schemas.add_report_request import AddReportRequest
from api.schemas.add_report_response import AddReportResponse
from core.orchestrator import Orchestrator

router = APIRouter()

orchestrator = Orchestrator()

@router.post("/add-report", response_model=AddReportResponse)
async def add_new_report(request:AddReportRequest):
    print("###", request.email)
    try:
        result = await orchestrator.add_new_report(
            request
        )

        return AddReportResponse(
            response=result
        )

    except Exception as e:

        raise HTTPException(
            status_code=500,
            detail=str(e)
        )

# @router.post("/add-report")
# async def add_new_report(request: Request):

#     body = await request.body()

#     print("========== RAW BODY ==========")
#     print(body)
#     print("==============================")

#     return {
#         "response": "received"
#     }