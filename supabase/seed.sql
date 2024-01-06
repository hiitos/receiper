-- migrationのテストデータを作成する
INSERT INTO 
  "public"."user" ("UserName", "Email", "CreatedAt") 
VALUES
  ('test1', 'test1@test.com', timestamp '2023-12-30 00:00:00')
;