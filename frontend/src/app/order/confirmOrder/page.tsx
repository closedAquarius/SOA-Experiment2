"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import Cookies from "js-cookie";
import { OrderData } from "../newOrder/page"; 
import styles from "./confirmOrder.module.css";
import { backendUrl } from "../../config";
import Header from "@/app/common/header";
import Footer from "@/app/common/footer";

export default function ConfirmOrder() {
  const router = useRouter();
  const [orderData, setOrderData] = useState<OrderData | null>(null);

  // 从Cookie加载订单数据
  useEffect(() => {
    const savedOrder = Cookies.get("currentOrder");
    if (savedOrder) {
      try {
        const parsedData: OrderData = JSON.parse(savedOrder);
        setOrderData(parsedData);
      } catch (error) {
        console.error("Error parsing order data:", error);
        alert("订单数据异常，请重新创建订单");
        router.push("/order");
      }
    } else {
      alert("未找到订单数据，请重新创建订单");
      router.push("/order");
    }
  }, [router]);

  // 处理订单确认提交
  const handleConfirm = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!orderData) return;

    try {
      const response = await fetch(`${backendUrl}/orders`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${Cookies.get("token")}`
        },
        body: JSON.stringify(orderData)
      });

      const result = await response.json();
      if (result.status === 0) {
        Cookies.set("currentOrderId", result.data.orderId, { expires: 1/288 });
        router.push(`/order/success`);

        Cookies.remove("currentOrder"); // 清除临时数据
        router.push("/order/success"); // 跳转到成功页面
      } else {
        alert(result.message || "确认订单失败");
      }
    } catch (error) {
      console.error("确认订单出错:", error);
      alert("网络错误，请重试");
    }
  };

  if (!orderData) return <div>加载订单数据中...</div>;

  return (
    <>
    <Header />
    <div className={styles.catalog}>
      <p className={styles.confirmTips}>Please confirm the information below and then press continue...</p>

      <form onSubmit={handleConfirm} className={styles.confirmOrderForm}>
        <input type="hidden" name="confirmed" value="true" />

        <table>
          <thead>
            <tr>
              <th colSpan={4} className={styles.orderHeader}>
                Order - {new Date(orderData.orderDate).toLocaleString()}
              </th>
            </tr>
          </thead>

          <tbody>
            {/* 账单地址 */}
            <tr>
              <th colSpan={4}>Billing Address</th>
            </tr>
            <tr>
              <th>First name:</th>
              <td>{orderData.billToFirstName}</td>
              <th>City:</th>
              <td>{orderData.billCity}</td>
            </tr>
            <tr>
              <th>Last name:</th>
              <td>{orderData.billToLastName}</td>
              <th>State:</th>
              <td>{orderData.billState}</td>
            </tr>
            <tr>
              <th>Address 1:</th>
              <td>{orderData.billAddress1}</td>
              <th>Zip:</th>
              <td>{orderData.billZip}</td>
            </tr>
            <tr>
              <th>Address 2:</th>
              <td>{orderData.billAddress2 || "-"}</td>
              <th>Country:</th>
              <td>{orderData.billCountry}</td>
            </tr>

            {/* 配送地址 */}
            <tr>
              <th colSpan={4}>Shipping Address</th>
            </tr>
            <tr>
              <th>First name:</th>
              <td>{orderData.shipToFirstName}</td>
              <th>City:</th>
              <td>{orderData.shipCity}</td>
            </tr>
            <tr>
              <th>Last name:</th>
              <td>{orderData.shipToLastName}</td>
              <th>State:</th>
              <td>{orderData.shipState}</td>
            </tr>
            <tr>
              <th>Address 1:</th>
              <td>{orderData.shipAddress1}</td>
              <th>Zip:</th>
              <td>{orderData.shipZip}</td>
            </tr>
            <tr>
              <th>Address 2:</th>
              <td>{orderData.shipAddress2 || "-"}</td>
              <th>Country:</th>
              <td>{orderData.shipCountry}</td>
            </tr>
          </tbody>
        </table>

        <div className={styles.submitContainer}>
          <button type="submit" className={styles.confirmButton}>
            Confirm
          </button>
        </div>
      </form>
    </div>

    <Footer />
    </>
  );
}