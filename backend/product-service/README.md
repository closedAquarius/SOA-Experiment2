# Product Service - 商品微服务

## 项目简介
商品微服务，负责商品、分类、商品项、库存管理。

## 技术栈
- Spring Boot 3.2.0
- Spring Cloud Alibaba 2023.0.1.0
- Nacos 服务注册与发现
- OpenFeign 服务间调用
- MyBatis-Plus 3.5.10.1
- MySQL 8.0

## 项目结构
```
product-service/
├── src/main/java/org/csu/petstore/product/
│   ├── ProductServiceApplication.java  # 启动类
│   ├── controller/                      # REST控制器
│   ├── service/                         # 业务逻辑层
│   ├── mapper/                          # 数据访问层
│   ├── entity/                          # 实体类
│   ├── dto/                             # 数据传输对象
│   ├── feign/                           # Feign客户端
│   └── config/                          # 配置类
└── src/main/resources/
    ├── application.yml                  # 主配置
    └── bootstrap.yml                    # Nacos配置
```

## 启动步骤

### 1. 启动Nacos
```bash
# 进入bin目录
cd nacos/bin #改为自己的目录
.\startup.cmd -m standalone

#启动时会提示输入三个密钥，依次输入：
VGhpc0lzTXlDdXN0b21TZWNyZXRLZXkwMTIzNDU2Nzg5
serverIdentity
serverIdentityValue

# 访问控制台
http://localhost:8080
用户名/密码: nacos/nacos
```

### 2. 配置数据库
确保MySQL数据库 mypetstore-ssm 已创建并导入数据

### 3. 修改配置
编辑 application.yml，修改数据库密码等配置

### 4. 启动服务
```bash
# 方式1: Maven
mvn spring-boot:run

# 方式2: IDE
运行 ProductServiceApplication.main()

# 方式3: jar包
mvn clean package
java -jar target/product-service-1.0.0.jar
```

### 5. 验证服务
- 访问Nacos控制台，查看product-service是否注册成功
- 测试API: http://localhost:8081/api/products

## API文档
详见 API-DOCS.md

## 核心功能

### 商品管理
- 商品CRUD
- 按分类查询
- 关键词搜索

### 商品项管理
- 商品项CRUD
- 库存查询
- 库存检查

### 分类管理
- 分类CRUD
- 分类商品列表

## Feign客户端
其他服务可通过ProductFeignClient调用本服务

## 注意事项
1. 确保Nacos已启动
2. 确保数据库连接正常
3. 端口8081不被占用
