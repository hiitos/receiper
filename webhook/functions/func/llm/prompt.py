

def cleanup_receipt():
    template = """
    使用するデータ: {data}

    このデータはレシートから読み取ったものです。対応が取れているようなjsonを出して欲しい。

    {format_instructions}
    """
    return template