# Product Service 测试指南

## 测试前准备

### 1. 启动Nacos
```bash
cd nacos/bin
startup.cmd -m standalone
```
访问: http://localhost:8848/nacos (nacos/nacos)

### 2. 确认数据库
- 数据库: mypetstore-ssm
- 确保有测试数据

### 3. 启动服务
```bash
cd product-service
mvn spring-boot:run
```

## 测试用例

### 测试1: 获取商品详情
```
GET http://localhost:8081/api/products/FI-SW-01
```
预期结果: 返回Angelfish商品信息

### 测试2: 按分类查询
```
GET http://localhost:8081/api/products/category/FISH
```
预期结果: 返回所有鱼类商品

### 测试3: 搜索商品
```
GET http://localhost:8081/api/products/search?keyword=fish
```
预期结果: 返回包含fish的商品

### 测试4: 获取商品项
```
GET http://localhost:8081/api/items/EST-1
```
预期结果: 返回商品项详情及库存

### 测试5: 检查库存
```
POST http://localhost:8081/api/items/check-stock?itemId=EST-1&quantity=5
```
预期结果: 返回true/false

### 测试6: 获取所有分类
```
GET http://localhost:8081/api/categories
```
预期结果: 返回FISH, DOGS, CATS, BIRDS, REPTILES

### 测试7: 获取分类详情
```
GET http://localhost:8081/api/categories/FISH
```
预期结果: 返回分类信息及商品列表

## Nacos验证

1. 访问 http://localhost:8848/nacos
2. 进入"服务管理" -> "服务列表"
3. 确认product-service已注册
4. 查看实例详情，确认健康状态为UP

## 常见问题

### 服务无法启动
- 检查8081端口是否被占用
- 检查Nacos是否启动
- 检查数据库连接配置

### 服务未注册到Nacos
- 检查bootstrap.yml配置
- 检查Nacos地址是否正确
- 查看启动日志

### 数据库连接失败
- 检查MySQL是否启动
- 检查数据库密码
- 确认数据库名称正确
