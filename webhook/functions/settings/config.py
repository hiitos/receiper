# .env ファイルをロードして環境変数へ反映
from dotenv import load_dotenv
load_dotenv()
import os

project_id = os.getenv("GCP_PROJECT_ID")
location = os.getenv("GCP_LOCATION")
processor_id = os.getenv("GCP_PROCESSOR_ID")
