import { supabaseClient } from "./supabase_service.ts";
import { processDocument, getAccessToken } from "./gcp_service.ts";
import { uint8ArrayToBase64 } from "./utils.ts";


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

  // __________________ supabase Storageから画像を取得する __________________
  const { data: image, error } = await supabaseClient.storage.from('receipts').download(image_path);
  
  // imageがとれているか確認
  if (error) {
    console.error('Error downloading image:', error.message);
    return new Response(JSON.stringify({ error: error.message }), {
      headers: { 'Content-Type': 'application/json' },
      status: 500,
    });
  } else {
    console.log('image:', image);
    console.log('image type:', typeof image);
  }

  // __________________ Base64でエンコードする __________________
  //BlobをUint8Arrayに変換してからBase64エンコード
  const arrayBuffer = await image.arrayBuffer(); // imageはBlob型
  const uint8Array = new Uint8Array(arrayBuffer);
  const base64Image = uint8ArrayToBase64(uint8Array);
  console.log('base64Image:', base64Image);

  // __________________ OCRを実行 __________________
  // GCPのaccessTokenを取得しないといけない
  // try {
  //   const accessToken = await getAccessToken(); // アクセストークンを取得
  //   console.log('accessToken:', accessToken);
  // } catch (error) {
  //   console.error('Failed to get access token:', error);
  //   return new Response(JSON.stringify({ error: error.message }), {
  //     headers: { 'Content-Type': 'application/json' },
  //     status: 500,
  //   });
  // }
  const accessToken = Deno.env.get('GCP_ACCESS_TOKEN');

  // DocumentAIに画像を渡してOCRを実行
  try {
    // GCP Document AIを使用してOCRを実行
    const ocrResult = await processDocument(accessToken, base64Image);
    console.log('OCR result:', ocrResult);

    // OCRの結果をレスポンスとして返す（またはLLMへの入力として使用する）
    return new Response(JSON.stringify({ result: 'OCR processing complete', ocrResult }), {
      headers: { 'Content-Type': 'application/json' },
      status: 200,
    });
  } catch (error) {
    console.error("Failed to process document with OCR:", error);
    return new Response(JSON.stringify({ error: error.message }), {
      headers: { 'Content-Type': 'application/json' },
      status: 500,
    });
  }
  
  // __________________ OCRの結果からLLMを実行 __________________


  // try {
  //   return new Response(JSON.stringify({ result: 'Edge Functions OK' }), {
  //     headers: { 'Content-Type': 'application/json' },
  //     status: 200,
  //   })
  // } catch (error) {
  //   return new Response(JSON.stringify({ error: error.message }), {
  //     headers: { 'Content-Type': 'application/json' },
  //     status: 500,
  //   })
  // }
});