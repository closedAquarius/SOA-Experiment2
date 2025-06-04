"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import Cookies from "js-cookie";
import { OrderData } from "../newOrder/page";
import styles from "../newOrder/order.module.css"; 
import Header from "@/app/common/header";
import Footer from "@/app/common/footer";

export default function AdjustAddress() {
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
        alert("Invalid order data, please create a new order");
        router.push("/order");
      }
    } else {
      alert("No order data found, please create a new order");
      router.push("/order");
    }
  }, [router]);

  // 处理表单字段更新
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setOrderData((prev) => {
      if (!prev) return null;
      return {
        ...prev,
        [name.split(".")[1]]: value, //转换order.shipToFirstName为shipToFirstName
      };
    });
  };

  // 处理表单提交
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!orderData) return;

    // 更新Cookie数据
    Cookies.set("currentOrder", JSON.stringify(orderData), { expires: 1 });
    
    // 跳转到确认页面
    router.push("/order/confirmOrder");
  };

  if (!orderData) return <div>Loading shipping address...</div>;

  return (
    <>
    <Header />
    <div className={styles.catalog}>
      <form onSubmit={handleSubmit} className={styles.orderForm}>
        <input type="hidden" name="shipAddressSubmitted" value="true" />
        
        <table>
          <thead>
            <tr>
              <th colSpan={2}>Shipping Address</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <th>First name:</th>
              <td>
                <input
                  type="text"
                  name="order.shipToFirstName"
                  value={orderData.shipToFirstName || ""}
                  onChange={handleInputChange}
                />
              </td>
            </tr>
            <tr>
              <th>Last name:</th>
              <td>
                <input
                  type="text"
                  name="order.shipToLastName"
                  value={orderData.shipToLastName || ""}
                  onChange={handleInputChange}
                />
              </td>
            </tr>
            <tr>
              <th>Address 1:</th>
              <td>
                <input
                  type="text"
                  name="order.shipAddress1"
                  value={orderData.shipAddress1 || ""}
                  onChange={handleInputChange}
                  size={40}
                />
              </td>
            </tr>
            <tr>
              <th>Address 2:</th>
              <td>
                <input
                  type="text"
                  name="order.shipAddress2"
                  value={orderData.shipAddress2 || ""}
                  onChange={handleInputChange}
                  size={40}
                />
              </td>
            </tr>
            <tr>
              <th>City:</th>
              <td>
                <input
                  type="text"
                  name="order.shipCity"
                  value={orderData.shipCity || ""}
                  onChange={handleInputChange}
                />
              </td>
            </tr>
            <tr>
              <th>State:</th>
              <td>
                <input
                  type="text"
                  name="order.shipState"
                  value={orderData.shipState || ""}
                  onChange={handleInputChange}
                  size={4}
                />
              </td>
            </tr>
            <tr>
              <th>Zip:</th>
              <td>
                <input
                  type="text"
                  name="order.shipZip"
                  value={orderData.shipZip || ""}
                  onChange={handleInputChange}
                  size={10}
                />
              </td>
            </tr>
            <tr>
              <th>Country:</th>
              <td>
                <input
                  type="text"
                  name="order.shipCountry"
                  value={orderData.shipCountry || ""}
                  onChange={handleInputChange}
                  size={15}
                />
              </td>
            </tr>
          </tbody>
        </table>

        <p>
          <br></br>
          <input type="submit" name="newOrder" value="Continue" />
        </p>
      </form>
    </div>
    <Footer />
    </>
  );
}