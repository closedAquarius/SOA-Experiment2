"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import Cookies from "js-cookie";
import styles from "../confirmOrder/confirmOrder.module.css";
import Link from "next/link";
import Header from "@/app/common/header";
import Footer from "@/app/common/footer";
import { OrderVO, LineItemVO, fetchOrder } from "../order-service";

export default function SuccessPage() {
  const router = useRouter();
  const [order, setOrder] = useState<OrderVO | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchOrderDetails = async () => {
      // 从 Cookie 获取当前订单ID
      const orderIdStr = Cookies.get("currentOrderId");
      if (!orderIdStr) {
        alert("订单ID不存在");

        // 李欣心还没有写好前面的，让我编一个数据
        // router.push("/");

        Cookies.set("currentOrderId", "1002", { expires: 1/288 });
        return;
      }

      const orderId = parseInt(orderIdStr, 10);

      // 检查登录状态
      if (!Cookies.get("token")) {
        alert("Please log in first");
        router.push("/login");
        return;
      }

      try {
        const orderData = await fetchOrder(orderId);
        setOrder(orderData);
      } catch (error: any) {
        console.error("获取订单出错:", error);
        alert(error.message || "网络错误");
        router.push("/");
      } finally {
        setLoading(false);
      }
    };

    fetchOrderDetails();
  }, [router]);

  if (loading)
    return <div className={styles.loading}>Loading order details...</div>;

  if (!order) return <div className={styles.error}>Order not found</div>;

  return (
    <>
      <Header />
      <div className={styles.catalog}>
        <form className={styles.confirmOrderForm}>
          <table>
            <tbody>
              <tr>
                <td colSpan={4} className={styles.successInfo}>
                  <p>Thank you, your order has been submitted.</p>
                </td>
              </tr>

              <tr>
                <th colSpan={4} className={styles.orderHeader}>
                  Order #{order.orderId}
                  <span className={styles.orderDate}>
                    {new Date(order.orderDate).toLocaleString()}
                  </span>
                </th>
              </tr>

              {/* Payment Details */}
              <tr>
                <th colSpan={4}>Payment Details</th>
              </tr>
              <tr>
                <th colSpan={2}>Card Type:</th>
                <td colSpan={2}>{order.cardType}</td>
              </tr>
              <tr>
                <th colSpan={2}>Card Number:</th>
                <td colSpan={2}>{order.creditCard}</td>
              </tr>
              <tr>
                <th colSpan={2}>Expiry Date (MM/YYYY):</th>
                <td colSpan={2}>{order.expiryDate}</td>
              </tr>

              {/* Billing Address */}
              <tr>
                <th colSpan={4}>Billing Address</th>
              </tr>
              <tr>
                <th>First name:</th>
                <td>{order.billToFirstName}</td>
                <th>City:</th>
                <td>{order.billCity}</td>
              </tr>
              <tr>
                <th>Last name:</th>
                <td>{order.billToLastName}</td>
                <th>State:</th>
                <td>{order.billState}</td>
              </tr>
              <tr>
                <th>Address 1:</th>
                <td>{order.billAddress1}</td>
                <th>Zip:</th>
                <td>{order.billZip}</td>
              </tr>
              <tr>
                <th>Address 2:</th>
                <td>{order.billAddress2 || "-"}</td>
                <th>Country:</th>
                <td>{order.billCountry}</td>
              </tr>

              {/* Shipping Address */}
              <tr>
                <th colSpan={4}>Shipping Address</th>
              </tr>
              <tr>
                <th>First name:</th>
                <td>{order.shipToFirstName}</td>
                <th>City:</th>
                <td>{order.shipCity}</td>
              </tr>
              <tr>
                <th>Last name:</th>
                <td>{order.shipToLastName}</td>
                <th>State:</th>
                <td>{order.shipState}</td>
              </tr>
              <tr>
                <th>Address 1:</th>
                <td>{order.shipAddress1}</td>
                <th>Zip:</th>
                <td>{order.shipZip}</td>
              </tr>
              <tr>
                <th>Address 2:</th>
                <td>{order.shipAddress2 || "-"}</td>
                <th>Country:</th>
                <td>{order.shipCountry}</td>
              </tr>

              {/* Courier and Status */}
              <tr>
                <th>Courier:</th>
                <td>{order.courier}</td>
                <th>Status:</th>
                <td>{order.status}</td>
              </tr>

              {/* Items List */}
              <tr>
                <th colSpan={4}>Purchased Items</th>
              </tr>
              <tr className={styles.itemHeader}>
                <th>Item ID</th>
                <th>Description</th>
                <th>Quantity</th>
                <th>Price</th>
              </tr>

              {order.lineItems.map((lineItem: LineItemVO, index: number) => (
                <tr key={index}>
                  <td className={styles.center}>
                    <Link href={`/item/${lineItem.itemId}`}>
                      {lineItem.itemId}
                    </Link>
                  </td>
                  <td className={styles.center}>
                    {lineItem.item ? (
                      <>
                        {[
                          lineItem.item.attribute1,
                          lineItem.item.attribute2,
                          lineItem.item.attribute3,
                          lineItem.item.attribute4,
                          lineItem.item.attribute5,
                          lineItem.item.productName,
                        ]
                          .filter(Boolean)
                          .join(" ")}
                      </>
                    ) : (
                      <i>description unavailable</i>
                    )}
                  </td>
                  <td className={styles.center}>{lineItem.quantity}</td>
                  <td className={styles.center}>
                    ${lineItem.unitPrice.toFixed(2)}
                  </td>
                </tr>
              ))}

              {/* Total Price */}
              <tr>
                <th colSpan={4} className={styles.totalPrice}>
                  Total: ${order.totalPrice.toFixed(2)}
                </th>
              </tr>
            </tbody>
          </table>
          <br />
        </form>
      </div>
      <Footer />
    </>
  );
}
