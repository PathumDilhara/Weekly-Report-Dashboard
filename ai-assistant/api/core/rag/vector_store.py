from langchain_chroma import Chroma
from dotenv import load_dotenv
import os

load_dotenv()

db_path = os.getenv("db_path")

class VectorStore:

    def __init__(self, embedding):
        self.db =Chroma(
            collection_name="weekly_reports",
            embedding_function=embedding,
            persist_directory=db_path,
            collection_metadata={"hnsw:space" : "cosine"}
        )
        
    async def save(self, chunks):

        # if use aad_doc user await
        await self.db.aadd_documents(
            documents=chunks
        )

    async def retriver(self, query:str):
        
        # define retriever
        retriever = self.db.as_retriever(search_kwargs={"k":3})

        # get relevant docs as a list
        docs = await retriever.ainvoke(query)
        
        return docs
