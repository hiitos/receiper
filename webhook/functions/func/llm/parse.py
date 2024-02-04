from langchain.output_parsers import StructuredOutputParser, PydanticOutputParser, OutputFixingParser, RetryWithErrorOutputParser
from settings.llm_model_setting import LLM_MODEL


def parse_llm_output(llm_output, output_parser, prompt_format):
    
    try:
        output = output_parser.parse(llm_output)
    except Exception as e:
        print(f"Error parsing LLM output: {e}")
        # パースに失敗した場合、別のLLMで再度試行
        try:
            output = parse_retry(output_parser, prompt_format, llm_output)
        except Exception as e:
            print(f"Error parsing LLM output: {e}")
            # パースに失敗した場合、parse_auto
            try:
                output = parse_auto(output_parser, llm_output)
            except Exception as e:
                print(f"Error parsing LLM output: {e}")
                output = "Error parsing LLM output"
    
    return output


def parse_auto(output_parser, llm_output):
    output_fix_parser = OutputFixingParser.from_llm(
        parser=output_parser, llm=LLM_MODEL
    )
    output = output_fix_parser.parse(llm_output)
    return output

def parse_retry(output_parser, prompt_format, llm_output):
    retry_parser = RetryWithErrorOutputParser.from_llm(
        parser=output_parser, llm=LLM_MODEL
    )
    output = retry_parser.parse_with_prompt(
        llm_output, prompt_format
    )
    return output