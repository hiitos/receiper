import { create, getNumericDate } from "https://deno.land/x/djwt/mod.ts";


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

export async function getAccessToken() {
  const serviceAccountKeyJson = Deno.env.get('GCP_SERVICE_ACCOUNT_KEY');
  if (!serviceAccountKeyJson) {
    throw new Error('GCP_SERVICE_ACCOUNT_KEY is not set.');
  }
  const serviceAccount = JSON.parse(serviceAccountKeyJson);
  console.log('serviceAccount:', serviceAccount);

  // JWTヘッダー
  const header = {
    alg: "RS256",
    typ: "JWT",
  };

  // JWTクレームセット
  const now = getNumericDate(new Date());
  console.log('now:', now);
  const exp = getNumericDate(new Date().getTime() + 3600 * 1000); // 有効期限を1時間後に設定

  const claims = {
    iss: serviceAccount.client_email,
    scope: "https://www.googleapis.com/auth/cloud-platform",
    aud: "https://oauth2.googleapis.com/token",
    exp,
    iat: now,
  };

  // JWTを生成
  const jwt = await create(header, claims, serviceAccount.private_key);

  // OAuth 2.0トークンエンドポイントにリクエストを送信してアクセストークンを取得
  const response = await fetch("https://oauth2.googleapis.com/token", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: `grant_type=urn:ietf:params:oauth:grant-type:jwt-bearer&assertion=${jwt}`,
  });

  if (!response.ok) {
    throw new Error('Failed to obtain access token.');
  }

  const data = await response.json();
  return data.access_token; // 取得したアクセストークンを返す
}
