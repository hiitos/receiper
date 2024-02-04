from langchain.output_parsers import ResponseSchema
from pydantic import BaseModel, Field


# Structured Response
def structured_schema(**kwargs):
    response_schemas = []
    for key, value in kwargs.items():
        response_schemas.append(ResponseSchema(name=key, description=value))
    return response_schemas

# Pydantic Schema
class Test(BaseModel):
    test: str = Field(..., title="Test", description="Test")

class SupplierInfo(BaseModel):
    name: str = Field(..., title="Name", description="Name")
    phone: str = Field(..., title="Phone", description="Phone")
    address: str = Field(..., title="Address", description="Address")
    city: str = Field(..., title="City", description="City")

class LineItems(BaseModel):
    description: str = Field(..., title="Description", description="Description")
    price: int = Field(..., title="Price", description="Price")
    tax: int = Field(..., title="Tax", description="Tax")
    barcode: str = Field(..., title="Barcode", description="Barcode")

class ReceiptResponseSchema(BaseModel):
    total_amount: int = Field(..., title="Total Amount", description="Total Amount")
    currency: str = Field(..., title="Currency", description="Currency")
    purchase_time: str = Field(..., title="Purchase Time", description="Purchase Time")
    receipt_date: str = Field(..., title="Receipt Date", description="Receipt Date")
    supplier_info: SupplierInfo = Field(..., title="Supplier Info", description="Supplier Info")
    line_items: list[LineItems] = Field(..., title="Line Items", description="Line Items")

    # Pydantic設定クラスを追加
    class Config:
        arbitrary_types_allowed = True


# Example
'''
{
  "total_amount": 430,
  "currency": "JPY",
  "purchase_time": "12:23",
  "receipt_date": "2023-12-02",
  "supplier_info": {
    "name": "農産物直売所 ファームテラスみのわ",
    "phone": "TEL0265-70-5230",
    "address": "日本\n〒399-4601 長野県上伊那郡箕輪町大出３７３０−１８６",
    "city": "箕輪町"
  },
  "line_items": [
    {
      "description": "惣菜",
      "price": 280,
      "tax": 8,
      "barcode": "P2548000358311"
    },
    {
      "description": "野沢菜",
      "price": 150,
      "tax": 8,
      "barcode": "P2548000367719"
    }
  ]
}
'''
