

def check_image_with_local(res, destination = "./example.jpg"):
    # ダウンロードしたファイルのバイトデータを取得
    with open(destination, 'wb') as f:
        f.write(res)  # レスポンスのバイトコンテントをファイルに書き込む
    print(f"File downloaded successfully: {destination}")
