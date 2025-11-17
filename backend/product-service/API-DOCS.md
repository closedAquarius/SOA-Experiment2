# Product Service API 文档

## 服务信息
- 服务名称: product-service
- 端口: 8081
- Nacos: localhost:8848

## 商品API

### 获取商品详情
GET /api/products/{productId}

### 按分类查询
GET /api/products/category/{categoryId}

### 搜索商品
GET /api/products/search?keyword=xxx

### 创建/更新/删除商品
POST/PUT/DELETE /api/products

## 商品项API

### 获取商品项
GET /api/items/{itemId}

### 检查库存
POST /api/items/check-stock

## 分类API

### 获取所有分类
GET /api/categories
