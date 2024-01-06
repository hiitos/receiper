import { supabaseClient } from "./supabase_service.ts";
import { documentaiClient, projectId, location } from "./gcp_service.ts";

// Supabase Realtimeのセットアップ
const receiptSubscription = supabaseClient
  .from('receipt')
  .on('INSERT', async (payload) => {
    console.log('New receipt added:', payload.new);

    const imagePath = payload.new.image_path;

    // Supabaseから画像を取得
    const { data: imageBlob, error: supabaseError } = await supabaseClient
      .storage
      .from('your-bucket-name')
      .download(imagePath);
    if (supabaseError) {
      throw supabaseError;
    }

    // 画像データをバッファに変換
    const imageBuffer = await imageBlob.arrayBuffer();

    // 画像をDocument AIに送信
    const [result] = await documentaiClient.processDocument({
      parent: `projects/${projectId}/locations/${location}`,
      document: {
        content: imageBuffer,
        mimeType: 'image/jpeg' // 画像の形式に応じて変更
      }
    });

    console.log('Document AI Result:', result);
  })
  .subscribe();