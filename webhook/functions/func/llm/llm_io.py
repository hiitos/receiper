from langchain.prompts import PromptTemplate
from langchain.output_parsers import StructuredOutputParser, PydanticOutputParser, OutputFixingParser, RetryWithErrorOutputParser
from func.llm import exec, parse
import inspect
from typing import Type


def workflow_template(response_schemas, prompt_template, **kwargs):

    output_parser, format_instructions = pydantic_format(response_schemas)
    # output_parser, format_instructions = structured_format(response_schemas)
    
    format, format_prompt = return_prompt_text(prompt_template, format_instructions, **kwargs)

    llm_output = exec.call_llm(format)
    output = parse.parse_llm_output(llm_output, output_parser, format_prompt)
    return output


def structured_format(response_schema):
    output_parser = StructuredOutputParser.from_response_schema(response_schema)
    format_instructions = output_parser.get_format_instructions()
    return output_parser, format_instructions

def pydantic_format(response_schema):
    output_parser = PydanticOutputParser(pydantic_object=response_schema)
    format_instructions = output_parser.get_format_instructions()
    return output_parser, format_instructions

def return_prompt_text(template, format_instructions, **kwargs):
    variables = [var for var in kwargs.keys()]
    values = {var: kwargs[var] for var in variables}

    prompt = PromptTemplate(
        template=template,
        input_variables=variables,
        partial_variables={"format_instructions": format_instructions},
    )
    return prompt.format(**values), prompt.format_prompt(**values)
