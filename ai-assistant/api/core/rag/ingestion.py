from langchain_text_splitters import RecursiveCharacterTextSplitter
from langchain_core.documents import Document
from core.rag.embedding import Embedding
from core.rag.vector_store import VectorStore
from api.schemas.add_report_request import AddReportRequest

class Ingestion:

    def __init__(self):
        self.text_splitter = RecursiveCharacterTextSplitter(
            chunk_size=500,
            chunk_overlap=100
        )
        self.embedding = Embedding()
        self.vector_store = VectorStore(
            self.embedding.model
        )

        
        
    async def add_new_report(self, report:AddReportRequest)->str:

        # create Document
        document = self.create_document(report)

        # spliting document into chunks
        chunks = self.text_splitter.split_documents(documents=[document])

        # save chunks in vector db
        await  self.vector_store.save(chunks)

        return {
            "success":True,
            "message": "Report saving in vector db succesfuly",
            "data": f"Chunk len : {len(chunks)}"
        }
    


     # json report to str
    def _create_report_text(self, report:AddReportRequest):

         return f"""
            Project: {report.projectName}
            User: {report.firstName+ report.lastName}

            Week:
            {report.weekStart} to {report.weekEnd}

            Tasks Completed:
            {report.tasksCompleted}

            Tasks Planned:
            {report.tasksPlanned}

            Blockers:
            {report.blockers}

            Hours Worked:
            {report.hoursWorked}

            Status:
            {report.status}
        """


    # create document obj using report
    def create_document(self, report: AddReportRequest):
        
        report_text = self._create_report_text(report)

        return Document(
                page_content=report_text,
                metadata={
                    "report_id": report.reportId,
                    "user_id": report.userId,
                    "project_id": report.projectId,
                }
            )