# Receiper

```bash
docker compose up --build
```

```bash
curl -X POST http://localhost:5001 -H "Content-Type: application/json" -d '{
  "data": {"type": "INSERT", "table": "receipt_dev", "record": {"id": 31, "user_id": 5021, "created_at": "2024-02-04T03:37:08.86978+00:00", "image_path": "5021/41F68EB3-FF01-488E-9816-4AEB6614271A.jpg"}, "schema": "public", "old_record": None}
}'
```