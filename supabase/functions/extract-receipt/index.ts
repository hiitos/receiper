import { supabaseClient } from "./supabase_service.ts";
// import { documentaiClient, projectId, location } from "./gcp_service.ts";


Deno.serve(async (req: Request) => {
  console.log('____ Edge Functions ____');
  // console.log(req);
  
  // _________ リクエストボディからuidとimage_pathを取得する _________
  const data = await req.json();
  // console.log('Received data:', data);
  const uid = data.record.user_id;
  const image_path = data.record.image_path;
  console.log('uid:', uid);
  console.log('image_path:', image_path);

  // _________ supabase Storageから画像を取得する _________
  const { data: image, error } = await supabaseClient.storage.from('receipts').download(image_path);
  
  // imageがとれているか確認
  if (error) {
    console.log('error:', error.message);
  } else {
    console.log('image:', image);
    console.log('image type:', typeof image);
  }

  // Base64でエンコードする
  // BlobをUint8Arrayに変換してからBase64エンコード
  const arrayBuffer = await image.arrayBuffer(); // imageはBlob型
  const uint8Array = new Uint8Array(arrayBuffer);
  const base64Image = uint8ArrayToBase64(uint8Array);
  console.log('base64Image:', base64Image);

  // imageをDocumentAIに渡して、OCRを実行

  
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

// Uint8ArrayデータをBase64文字列に変換する関数
function uint8ArrayToBase64(data: Uint8Array): string {
  let binary = '';
  const len = data.byteLength;
  for (let i = 0; i < len; i++) {
    binary += String.fromCharCode(data[i]);
  }
  return btoa(binary);
}