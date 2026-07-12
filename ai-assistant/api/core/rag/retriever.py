from langchain_chroma import Chroma
from langchain_huggingface import HuggingFaceEmbeddings
from dotenv import load_dotenv
import os

from core.rag.embedding import Embedding
from core.rag.vector_store import VectorStore

load_dotenv()

db_path = os.getenv("db_path")
embedding_model_path = os.getenv("embedding_model_path")

class Retriever:
    def __init__(self):

        self.embedding = Embedding()

        self.vector_store = VectorStore(
            self.embedding.model
        )

    async def get_relevant_docs(self, query:str):
        docs = await self.vector_store.retriver(query=query)
        return docs # list of Documents
