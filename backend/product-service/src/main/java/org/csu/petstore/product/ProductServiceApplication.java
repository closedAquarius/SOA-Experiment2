package org.csu.petstore.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 商品服务启动类
 * 
 * @author SOA Team
 * @description 商品微服务 - 负责商品、分类、库存管理
 */
@SpringBootApplication
@EnableDiscoveryClient  // 启用Nacos服务注册与发现
@EnableFeignClients     // 启用OpenFeign客户端
@MapperScan("org.csu.petstore.product.mapper")  // 扫描Mapper接口
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
        System.out.println("========================================");
        System.out.println("   Product Service 启动成功！");
        System.out.println("   服务端口: 8081");
        System.out.println("   Nacos地址: localhost:8848");
        System.out.println("========================================");
    }
}
