


export async function processDocument(accessToken: string, encodedImage: string): Promise<void> {
  const endpoint = Deno.env.get('GCP_ENDPOINT') ?? '';
  // エンドポイントが設定されていることを確認
  if (!endpoint) {
    console.error('GCP_ENDPOINT is not set.');
    return null;
  } else {
    console.log('GCP_ENDPOINT:', endpoint);
  }

  const response = await fetch(endpoint, {
    method: 'POST',
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      document: {
        content: encodedImage,
        mimeType: 'image/jpeg'
      }
    }),
  });

  if (response.ok) {
    const data = await response.json();
    console.log('Document processing complete:', data);
    // 処理結果を利用
    return data;
  } else {
    const error = await response.text();
    console.error('Failed to process document:', error);
    return null;
  }

}