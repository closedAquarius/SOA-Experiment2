@echo off
echo ========================================
echo   启动 Product Service
echo ========================================
echo.

echo [1/3] 检查Nacos是否启动...
timeout /t 2 /nobreak >nul

echo [2/3] 编译项目...
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo 编译失败！
    pause
    exit /b 1
)

echo [3/3] 启动服务...
echo.
echo 服务启动中，请稍候...
echo 启动成功后访问: http://localhost:8081/api/products
echo.

java -jar target/product-service-1.0.0.jar

pause
