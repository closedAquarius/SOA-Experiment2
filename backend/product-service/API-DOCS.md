# Product Service API 文档

## 服务信息
- 服务名称: product-service
- 端口: 8081
- Nacos: localhost:8848
- 统一响应格式: `{"code": 200, "message": "success", "data": {...}}`

---

## 商品API (Product)

### 1. 获取所有商品
```
GET /api/products
```
**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "productId": "FI-SW-01",
      "categoryId": "FISH",
      "name": "Angelfish",
      "description": "..."
    }
  ]
}
```

### 2. 获取商品详情
```
GET /api/products/{productId}
```
**参数：**
- `productId`: 商品ID（路径参数）

**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "productId": "FI-SW-01",
    "categoryId": "FISH",
    "name": "Angelfish",
    "description": "...",
    "items": [...]
  }
}
```

### 3. 按分类查询商品
```
GET /api/products/category/{categoryId}
```
**参数：**
- `categoryId`: 分类ID（路径参数）

### 4. 搜索商品
```
GET /api/products/search?keyword=xxx
```
**参数：**
- `keyword`: 搜索关键词（查询参数）

### 5. 创建商品
```
POST /api/products
Content-Type: application/json
```
**请求体：**
```json
{
  "productId": "NEW-01",
  "categoryId": "FISH",
  "name": "New Product",
  "description": "Product description"
}
```

### 6. 更新商品
```
PUT /api/products/{productId}
Content-Type: application/json
```
**请求体：** 同创建商品

### 7. 删除商品
```
DELETE /api/products/{productId}
```

---

## 商品项API (Item/SKU)

### 1. 获取商品项详情
```
GET /api/items/{itemId}
```
**参数：**
- `itemId`: 商品项ID（路径参数）

**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "itemId": "EST-1",
    "productId": "FI-SW-01",
    "productName": "Angelfish",
    "listPrice": 16.50,
    "unitCost": 10.00,
    "quantity": 100
  }
}
```

### 2. 获取商品的所有商品项
```
GET /api/items/product/{productId}
```
**参数：**
- `productId`: 商品ID（路径参数）

### 3. 检查库存
```
POST /api/items/check-stock?itemId=xxx&quantity=10
```
**参数：**
- `itemId`: 商品项ID（查询参数）
- `quantity`: 需要的数量（查询参数）

**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": true
}
```

### 4. 创建商品项
```
POST /api/items
Content-Type: application/json
```
**请求体：**
```json
{
  "itemId": "EST-100",
  "productId": "FI-SW-01",
  "listPrice": 20.00,
  "unitCost": 12.00,
  "supplierId": 1,
  "status": "P",
  "attributes": "Large"
}
```

### 5. 更新商品项
```
PUT /api/items/{itemId}
Content-Type: application/json
```
**请求体：** 同创建商品项

### 6. 删除商品项
```
DELETE /api/items/{itemId}
```

---

## 分类API (Category)

### 1. 获取所有分类
```
GET /api/categories
```
**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "categoryId": "FISH",
      "name": "Fish",
      "description": "..."
    }
  ]
}
```

### 2. 获取分类详情（含商品列表）
```
GET /api/categories/{categoryId}
```
**参数：**
- `categoryId`: 分类ID（路径参数）

**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "categoryId": "FISH",
    "name": "Fish",
    "description": "...",
    "products": [...]
  }
}
```

### 3. 创建分类
```
POST /api/categories
Content-Type: application/json
```
**请求体：**
```json
{
  "categoryId": "NEW",
  "name": "New Category",
  "description": "Category description"
}
```

### 4. 更新分类
```
PUT /api/categories/{categoryId}
Content-Type: application/json
```
**请求体：** 同创建分类

---

## 错误响应格式

```json
{
  "code": 500,
  "message": "错误信息",
  "data": null
}
```

---

## 测试建议

### 使用浏览器测试（GET请求）
```
http://localhost:8081/api/categories
http://localhost:8081/api/products
http://localhost:8081/api/products/FI-SW-01
```

### 使用Postman/Apifox测试（POST/PUT/DELETE）
导入上述API创建测试集合
