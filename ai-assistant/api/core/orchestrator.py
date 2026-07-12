from core.llm.llama_client import LlamaClient
from core.llm.prompt_builder import PromptBuilder
from core.memory.chat_history import ChatMemory
from core.rag.ingestion import Ingestion
from core.rag.retriever import Retriever



class Orchestrator:

    def __init__(self):
            self.ingestion = Ingestion()
            self.llama = LlamaClient()
            self.prompt = PromptBuilder()
            self.retriever = Retriever()
            self.chat_memory = ChatMemory()

    async def add_new_report(self, report):
          
          res = await self.ingestion.add_new_report(report)

          return res
    
    async def get_response(self, user_message:str)-> str:
        
        if self.chat_memory.history:

            # build the user message updating prompt according to the chat history
            msg_update_prompt= self.prompt.build_question_update_prompt(
                user_message=user_message, 
                chat_history=self.chat_memory.history
                )
            updated_message= self.llama.generate(prompt=msg_update_prompt)
            
        else:
            updated_message=user_message
        print(f"### Updated message : {updated_message}")

        relevant_docs = await  self.retriever.get_relevant_docs(query= updated_message)

        # Convert docs_list -> text
        combined_docs = "\n".join([f" - {doc.page_content}" for doc in relevant_docs])

        # Build the final answer prompt 
        prompt = self.prompt.build_prompt(
            user_message=updated_message, 
            context =combined_docs, 
            chat_history=self.chat_memory.history
            )
        
        response = self.llama.generate(prompt=prompt)

        #  update chat history
        self.chat_memory.add(user_message=updated_message, bot_response=response)
        print(f"### Current history len : {len(self.chat_memory.history)}")
        
        #  Return final response
        return {
            "response": response
        }
        
        