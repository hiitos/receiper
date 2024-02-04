from settings.llm_model_setting import LLM_MODEL


def call_llm(prompt: str) -> str:
    """
    Call LLM model and get response
    """
    response = LLM_MODEL(prompt)
    return response