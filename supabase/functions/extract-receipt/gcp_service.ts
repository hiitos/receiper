import { DocumentProcessorServiceClient } from '@google-cloud/documentai';


const projectId = Deno.env.get('GCP_PROJECT_ID') ?? '';
const location = Deno.env.get('GCP_LOCATION') ?? '';
const processorId = Deno.env.get('PROCESSOR_ID') ?? '';
const name = `projects/${projectId}/locations/${location}/processors/${processorId}`;

// Process a single document
export async function processDocument(encodedImage: string): Promise<void> {
    // Instantiates a client
    const client = new DocumentProcessorServiceClient();

    try {
        const request = {
            name,
            rawDocument: {
                content: encodedImage,
                // mimeType: 'application/pdf',
                mimeType: 'image/jpeg',
            },
        };
  
        const [result] = await client.processDocument(request);
        console.log('Document processing complete.');
        const { document } = result;

        if (document?.text) {
            console.log(`Full document text: ${JSON.stringify(document.text)}`);
            return document.text;
        } else {
            console.log("No text found in document.");
            return null;
        }

    } catch (error) {
        console.error("Failed to process document:", error);
        return null;
    }
}