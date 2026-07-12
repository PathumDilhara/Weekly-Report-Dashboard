from langchain_huggingface import HuggingFaceEmbeddings
from dotenv import load_dotenv
import os

load_dotenv()

embedding_model = os.getenv("embedding_model_path")
if embedding_model is None:
    raise ValueError("embedding_model_path not found in .env")
print("Embedding model path:", embedding_model)

class Embedding:
    
    def __init__(self):
        self.model = HuggingFaceEmbeddings(
            model_name=embedding_model,
            model_kwargs={"device":"cpu"},
            encode_kwargs={"normalize_embeddings": True}
        )

    def create_embedding(self, chunks):
        return self.model.embed_documents(
            [
                chunk.page.content
                for chunk in chunks
            ]
        )