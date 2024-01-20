import { supabaseClient } from "./supabase_service.ts";
import { documentaiClient, projectId, location } from "./gcp_service.ts";


// functions
Deno.serve(async (req: Request) => {
  console.log('____ Edge Functions ____');
  // console.log(req);
  
  // リクエストボディの解析
  const data = await req.json();
  // console.log('Received data:', data);
  // リクエストの中からimage_pathを取得
  const image_path = data.record.image_path;
  console.log('image_path:', image_path);
  
  // Supabase Storageのバケット一覧を取得
  const { data: listBuckets, listBuckets_e} = await supabaseClient.storage.listBuckets()
  console.log('listBuckets:', listBuckets);

  const uid = data.record.user_id;
  const { data: getBuckets, getBuckets_error } = await supabaseClient.storage.getBucket('receipts');
  console.log('getBuckets:', getBuckets);

  // supabase Storageの画像を取得
  const { data: image, error } = await supabaseClient.storage.from('receipts').download(image_path);
  
  console.log('image:', image);
  // imageがとれているか確認
  if (error) {
    console.log('error:', error);
    console.log('error:', error.message);
  }

  // imageをDocumentAIに渡して、OCRを実行
  const [result] = await documentaiClient.processDocument({
    name: `projects/${projectId}/locations/${location}/processors/xxxxx`,
    document: {
      content: image,
      mimeType: 'image/jpeg',
    },
  });
  console.log('result:', result);
  
  // OCRの結果からLLMを実行
  


  try {
    return new Response(JSON.stringify({ result: 'Edge Functions OK' }), {
      headers: { 'Content-Type': 'application/json' },
      status: 200,
    })
  } catch (error) {
    return new Response(JSON.stringify({ error: error.message }), {
      headers: { 'Content-Type': 'application/json' },
      status: 500,
    })
  }
})