import os
import json
import base64
from supabase import create_client, Client
import func.ocr.document_ai as document_ai
import func.ocr.format_data as format_data
from func.llm import prompt, llm_io, response_schemas as rs


def handle_request(request):
    print(f'Received data: {request.data.decode("utf-8")}')
    
    try:
        # リクエストのボディをデコードしてJSONとしてパース
        req = json.loads(request.data.decode('utf-8'))
        print(f'{req["table"]}の{req["type"]}を検知')
    except json.JSONDecodeError as e:
        # JSONの解析に失敗した場合は、エラーメッセージを出力
        print(f'JSON parsing error: {e}')
        return 'Invalid JSON payload', 400
    
    uid = req["record"]["user_id"]
    image_path = req["record"]["image_path"]
    print(f'uid: {uid}')
    print(f'image_path: {image_path}')

    url: str = os.environ.get("SUPABASE_URL")
    key: str = os.environ.get("SUPABASE_KEY")
    supabase: Client = create_client(url, key)

    # supabaseからImageを取得
    res = supabase.storage.from_('receipts').download(image_path)
    print(type(res))    
    
    # ____________________ Base64にエンコード ____________________
    base64_encoded_data = base64.b64encode(res).decode('utf-8')
    # print(base64_encoded_data)

    # ____________________ Base64にエンコード ____________________
    document = document_ai.process_document(
        image_content=base64_encoded_data,
        mime_type='image/jpeg'
    )
    # print(f"Document processing complete: {document.text}")

    results = format_data.create_json_from_response(document)
    print("Document processing complete.")
    print(results)

    # ____________________ LLM ____________________
    prompt_template = prompt.cleanup_receipt()
    
    output = llm_io.workflow_template(
        response_schemas=rs.ReceiptResponseSchema,
        prompt_template=prompt_template,
        data=results
    )

    return output
