from langchain_core.messages import HumanMessage, AIMessage
from collections import deque



class ChatMemory:
    def __init__(self, max_turns:int=5):
        self.history = deque(maxlen=max_turns) # act as 5 elts sliding window

    def add(self, user_message:str, bot_response:str):
        self.history.append(HumanMessage(content=user_message))
        self.history.append(AIMessage(content=bot_response))

    def clear(self):
        self.history.clear()
