import os
# from langchain.llms import OpenAI
from langchain_community.llms import OpenAI


os.environ["OPENAI_API_KEY"] = os.environ.get('OPEN_AI_API')
MODEL_NAME=os.environ.get('MODEL_NAME')

LLM_MODEL = OpenAI(
    model_name=MODEL_NAME,
    temperature=0,
)