"use client";

import { useEffect, useState } from "react";
import { useRouter, useSearchParams } from "next/navigation";
import Cookies from "js-cookie";
import { backendUrl } from "@/app/config";
import styles from "../confirmOrder/confirmOrder.module.css";
import Link from "next/link";
import Header from "@/app/common/header";
import Footer from "@/app/common/footer";

interface LineItem {
  itemId: string;
  quantity: number;
  unitPrice: number;
  item?: {
    attribute1?: string;
    attribute2?: string;
    attribute3?: string;
    attribute4?: string;
    attribute5?: string;
    product?: {
      name: string;
    };
  };
}

interface OrderDetails {
  orderId: number;
  orderDate: string;
  cardType: string;
  creditCard: string;
  expiryDate: string;
  billToFirstName: string;
  billToLastName: string;
  billAddress1: string;
  billAddress2: string;
  billCity: string;
  billState: string;
  billZip: string;
  billCountry: string;
  shipToFirstName: string;
  shipToLastName: string;
  shipAddress1: string;
  shipAddress2: string;
  shipCity: string;
  shipState: string;
  shipZip: string;
  shipCountry: string;
  courier: string;
  status: string;
  totalPrice: number;
  lineItems: LineItem[];
}

export default function SuccessPage() {
    const router = useRouter();
    const [order, setOrder] = useState<OrderDetails | null>(null);
    const [loading, setLoading] = useState(true);
    const orderId = Cookies.get("currentOrderId");
  
    useEffect(() => {
      const fetchOrderDetails = async () => {
        try {
          const response = await fetch(`${backendUrl}/orders/${orderId}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${Cookies.get("token")}`,
            },
          });
  
          const result = await response.json();
          if (result.status === 0) {
            setOrder(result.data);

          } else {
            alert(result.message || "获取订单详情失败");
            router.push("/");
          }
        } catch (error) {
          console.error("获取订单出错:", error);
          alert("网络错误");
          router.push("/");
        } finally {
          setLoading(false);
        }
      };

      fetchOrderDetails();
    }, [router]);

  if (loading) return <div className={styles.loading}>Loading order details...</div>;

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

            {order.lineItems.map((item, index) => (
              <tr key={index}>
                <td className={styles.center}>
                  <Link href={`/product/${item.itemId}`}>{item.itemId}</Link>
                </td>
                <td className={styles.center}>
                  {item.item ? (
                    <>
                      {[
                        item.item.attribute1,
                        item.item.attribute2,
                        item.item.attribute3,
                        item.item.attribute4,
                        item.item.attribute5,
                        item.item.product?.name,
                      ]
                        .filter(Boolean)
                        .join(" ")}
                    </>
                  ) : (
                    <i>description unavailable</i>
                  )}
                </td>
                <td className={styles.center}>{item.quantity}</td>
                <td className={styles.center}>
                  ${item.unitPrice.toFixed(2)}
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
        <br></br>
      </form>
    </div>
    <Footer />
    </>
  );
}