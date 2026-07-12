from datetime import date, datetime
from typing import Optional

from pydantic import BaseModel

class AddReportRequest(BaseModel):
    reportId: Optional[int] = None

    projectId: int
    projectName: Optional[str] = None

    userId: Optional[str] = None
    firstName: Optional[str] = None
    lastName: Optional[str] = None
    email: Optional[str] = None
    team: Optional[str] = None

    weekStart: date
    weekEnd: date

    tasksCompleted: str
    tasksPlanned: str
    blockers: Optional[str] = None

    hoursWorked: int

    status: Optional[str] = None

    submittedAt: Optional[datetime] = None
    createdAt: Optional[datetime] = None
    updatedAt: Optional[datetime] = None