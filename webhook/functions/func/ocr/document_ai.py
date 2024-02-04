import settings.config as config
from google.cloud import documentai_v1 as documentai


def process_document(image_content: str, mime_type: str) -> documentai.Document:
    """
    Processes a document using the Document AI API.
    """

    project_id, location, processor_id = config.project_id, config.location, config.processor_id
 
    # Instantiates a client
    documentai_client = documentai.DocumentProcessorServiceClient()
 
    # The full resource name of the processor, e.g.:
    # projects/project-id/locations/location/processor/processor-id
    resource_name = documentai_client.processor_path(
        project_id, location, processor_id)

    # Load Binary Data into Document AI RawDocument Object
    raw_document = documentai.RawDocument(
        content=image_content, mime_type=mime_type)

    # Configure the process request
    request = documentai.ProcessRequest(
        name=resource_name, raw_document=raw_document)

    # Use the Document AI client to process the sample form
    result = documentai_client.process_document(request=request)

    return result.document