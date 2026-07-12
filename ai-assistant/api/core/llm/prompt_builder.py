from langchain_core.messages import SystemMessage, HumanMessage

class PromptBuilder:
    def build_prompt(self, user_message:str, context:str, chat_history:list):
        
        msg= f"""
            Context:
            {context}

            User message:
            {user_message}
        """

        prompt= [
                SystemMessage(content="""
                    You are a helpful weekly report dash board insight getting assistant that answer questions based on provided documents, conversation history.
                    If conversation hostory is empty just ignore them.
                    In the response don't mention about documents or sources such as 'according to the provided documents...'.
                    Answer should be less than 200 chars.
                """),
        ] + list(chat_history) + [
                HumanMessage(content= msg)
        ]
            
        return prompt # list
    
    def build_question_update_prompt(self, user_message:str, chat_history:list):

        prompt = [
            SystemMessage(content="""
                Given the conversation history and the latest user message,
                rewrite the latest message into a complete standalone search query.
                Preserve the original meaning.
                Do not answer the question.
                Return only the rewritten query.
            """)
        ] + list(chat_history) + [
            HumanMessage(content=f"New question : {user_message}")
        ]

        return prompt # list
        