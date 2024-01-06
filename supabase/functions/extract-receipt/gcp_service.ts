import { DocumentaiServiceClient } from '@google-cloud/documentai';


const gcpCredentials = JSON.parse(Deno.env.get("GCP_CREDENTIALS"));
export const projectId = gcpCredentials.project_id;
export const location = 'YOUR_DOCUMENT_AI_LOCATION'; // 例: 'us' or 'europe'
export const documentaiClient = new DocumentaiServiceClient({ projectId, credentials: gcpCredentials });