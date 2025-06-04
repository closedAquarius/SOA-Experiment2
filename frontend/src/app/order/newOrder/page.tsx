"use client";

import { useState, useEffect } from "react";
import Header from "@/app/common/header";
import Footer from "@/app/common/footer";
import styles from "./order.module.css";
import Cookies from "js-cookie";
import { backendUrl } from "../../config";
import { useRouter } from "next/navigation";

export interface OrderData {
  orderId: number;
  username: string;
  orderDate: string;
  shipAddress1: string;
  shipAddress2: string;
  shipCity: string;
  shipState: string;
  shipZip: string;
  shipCountry: string;
  billAddress1: string;
  billAddress2: string;
  billCity: string;
  billState: string;
  billZip: string;
  billCountry: string;
  courier: string;
  totalPrice: number;
  billToFirstName: string;
  billToLastName: string;
  shipToFirstName: string;
  shipToLastName: string;
  creditCard: string;
  expiryDate: string;
  cardType: string;
}

export interface Address {
  addressId: string;
  firstName: string;
  lastName: string;
  address1: string;
  address2: string;
  city: string;
  state: string;
  zip: string;
  country: string;
}

export default function OrderPage() {
  const [orderData, setOrderData] = useState<OrderData | null>(null);
  const [addresses, setAddresses] = useState<Address[]>([]); // 存储地址数据
  const [shipToDifferentAddress, setShipToDifferentAddress] = useState(false); // 新增状态
  const [hoveredAddress, setHoveredAddress] = useState<Address | null>(null);
  const [tooltipPosition, setTooltipPosition] = useState({ x: 0, y: 0 });
  const [mainAddress, setMainAddress] = useState<string | null>(null);

  const router = useRouter();

  useEffect(() => {
    // 获取订单数据
    const fetchOrderData = async () => {
      try {
        const response = await fetch(`${backendUrl}/carts/orders`, {
          method: "GET",
          headers: { "Authorization": `Bearer ${Cookies.get("token")}` },
        });
        const data = await response.json();
        if (data.status === 0) {
          setOrderData(data.data);
        } else {
          alert(data.message);
        }
      } catch (error) {
        console.error("Error fetching order data:", error);
        alert("Failed to fetch order data");
      }
    };

    // 获取地址数据
    const fetchAddresses = async () => {
      try {
        const response = await fetch(`${backendUrl}/orders/addresses`, {
          method: "GET",
          headers: { "Authorization": `Bearer ${Cookies.get("token")}` },
        });
        const data = await response.json();
        if (data.status === 0) {
          setAddresses(data.data);
        } else {
          alert(data.message);
        }
      } catch (error) {
        console.error("Error fetching addresses:", error);
        alert("Failed to fetch addresses");
      }
    };

    fetchOrderData();
    fetchAddresses();
  }, []);

  // 删除地址
  const handleDelete = async (addressId: string) => {
    try {
      const response = await fetch(`${backendUrl}/addresses/${addressId}`, {
        method: "DELETE",
        headers: { "Authorization": `Bearer ${Cookies.get("token")}` },
      });

      const data = await response.json();
      if (data.status === 0) {
        alert("Address deleted successfully!");
        setAddresses((prevAddresses) => prevAddresses.filter((address) => address.addressId !== addressId));
      } else {
        alert(data.message);
      }
    } catch (error) {
      console.error("Error deleting address:", error);
      alert("Failed to delete address");
    }
  };

  // 设置为主要地址
  const handleSetMain = async (addressId: string) => {
    try {
      const response = await fetch(`${backendUrl}/addresses/${addressId}`, {
        method: "PUT",
        headers: { "Authorization": `Bearer ${Cookies.get("token")}` },
      });

      const data = await response.json();
      if (data.status === 0) {
        alert("Set main address successfully!");
        setMainAddress(addressId);
        setAddresses((prevAddresses) => {
          const updatedAddresses = [...prevAddresses];
          const selectedAddress = updatedAddresses.find((address) => address.addressId === addressId);
          if (selectedAddress) {
            updatedAddresses.unshift(updatedAddresses.splice(updatedAddresses.indexOf(selectedAddress), 1)[0]);
          }
          return updatedAddresses;
        });
      } else {
        alert(data.message);
      }
    } catch (error) {
      console.error("Error setting main address:", error);
      alert("Failed to set main address");
    }
  };

  const handleChoose = (address: Address) => {
    setOrderData((prevOrder) => {
      const defaultOrder: OrderData = {
        orderId: prevOrder?.orderId ?? 0, 
        username: prevOrder?.username ?? "", 
        orderDate: prevOrder?.orderDate ?? "", 
        shipAddress1: prevOrder?.shipAddress1 ?? "",
        shipAddress2: prevOrder?.shipAddress2 ?? "", 
        shipCity: prevOrder?.shipCity ?? "", 
        shipState: prevOrder?.shipState ?? "", 
        shipZip: prevOrder?.shipZip ?? "", 
        shipCountry: prevOrder?.shipCountry ?? "", 
        billAddress1: prevOrder?.billAddress1 ?? "", 
        billAddress2: prevOrder?.billAddress2 ?? "", 
        billCity: prevOrder?.billCity ?? "", 
        billState: prevOrder?.billState ?? "", 
        billZip: prevOrder?.billZip ?? "", 
        billCountry: prevOrder?.billCountry ?? "",
        courier: prevOrder?.courier ?? "",
        totalPrice: prevOrder?.totalPrice ?? 0, 
        billToFirstName: prevOrder?.billToFirstName ?? "", 
        billToLastName: prevOrder?.billToLastName ?? "",
        shipToFirstName: prevOrder?.shipToFirstName ?? "",
        shipToLastName: prevOrder?.shipToLastName ?? "",
        creditCard: prevOrder?.creditCard ?? "", 
        expiryDate: prevOrder?.expiryDate ?? "", 
        cardType: prevOrder?.cardType ?? "" 
      };
  
      //返回更新后的 OrderData
      return {
        ...defaultOrder,
        billToFirstName: address.firstName,
        billToLastName: address.lastName,
        billAddress1: address.address1,
        billAddress2: address.address2,
        billCity: address.city,
        billState: address.state,
        billZip: address.zip,
        billCountry: address.country,
      };
    });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("666")
    console.log(orderData?.billToFirstName);
    
    if (!orderData) {
      alert("Order data is not loaded yet");
      return;
    }

    // 将orderData存入Cookie
    Cookies.set("currentOrder", JSON.stringify(orderData), { expires: 1 }); // 有效期1天

    // 根据复选框状态跳转
    if (shipToDifferentAddress) {
      router.push("/order/adjustAddress");
    } else {
      router.push("/order/confirmOrder");
    }
  };

  return (
    <>
      <Header />
      <div className={styles.orderContainer}>
        {orderData && addresses.length > 0 ? (
          <>
            {/* 订单表单 */}
            <form method="post" className={styles.orderForm} onSubmit={handleSubmit}>
              <input type="hidden" name="newOrderFormSubmitted" value="true" />
              <table>
                <thead>
                  <tr>
                    <th colSpan={2}>Payment Details</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <th>Card Type:</th>
                    <td>
                      <select name="order.cardType" defaultValue={orderData.cardType}>
                        <option value="Visa">Visa</option>
                        <option value="wechat">WeChat</option>
                        <option value="alipay">Alipay</option>
                        <option value="others">Others</option>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <th>Card Number:</th>
                    <td>
                      <input type="text" name="order.creditCard" defaultValue={orderData.creditCard} />
                    </td>
                  </tr>
                  <tr>
                    <th>Expiry Date (MM/YYYY):</th>
                    <td>
                      <input type="text" name="order.expiryDate" defaultValue={orderData.expiryDate} />
                    </td>
                  </tr>
                  <tr>
                    <th colSpan={2}>Billing Address</th>
                  </tr>
                  <tr>
                    <th>First Name:</th>
                    <td>
                      <input
                        type="text"
                        name="order.billToFirstName"
                        value={orderData.billToFirstName}
                        onChange={(e) => setOrderData({ ...orderData, billToFirstName: e.target.value })}
                      />
                    </td>
                  </tr>
                  <tr>
                    <th>Last Name:</th>
                    <td>
                      <input
                        type="text"
                        name="order.billToLastName"
                        value={orderData.billToLastName}
                        onChange={(e) => setOrderData({ ...orderData, billToLastName: e.target.value })}
                      />
                    </td>
                  </tr>
                  <tr>
                    <th>Address 1:</th>
                    <td>
                      <input
                        type="text"
                        name="order.billAddress1"
                        value={orderData.billAddress1}
                        onChange={(e) => setOrderData({ ...orderData, billAddress1: e.target.value })}
                      />
                    </td>
                  </tr>
                  <tr>
                    <th>Address 2:</th>
                    <td>
                      <input
                        type="text"
                        name="order.billAddress2"
                        value={orderData.billAddress2}
                        onChange={(e) => setOrderData({ ...orderData, billAddress2: e.target.value })}
                      />
                    </td>
                  </tr>
                  <tr>
                    <th>City:</th>
                    <td>
                      <input
                        type="text"
                        name="order.billCity"
                        value={orderData.billCity}
                        onChange={(e) => setOrderData({ ...orderData, billCity: e.target.value })}
                      />
                    </td>
                  </tr>
                  <tr>
                    <th>State:</th>
                    <td>
                      <input
                        type="text"
                        name="order.billState"
                        value={orderData.billState}
                        onChange={(e) => setOrderData({ ...orderData, billState: e.target.value })}
                      />
                    </td>
                  </tr>
                  <tr>
                    <th>Zip:</th>
                    <td>
                      <input
                        type="text"
                        name="order.billZip"
                        value={orderData.billZip}
                        onChange={(e) => setOrderData({ ...orderData, billZip: e.target.value })}
                      />
                    </td>
                  </tr>
                  <tr>
                    <th>Country:</th>
                    <td>
                      <input
                        type="text"
                        name="order.billCountry"
                        value={orderData.billCountry}
                        onChange={(e) => setOrderData({ ...orderData, billCountry: e.target.value })}
                      />
                    </td>
                  </tr>
                  <tr>
                    <td colSpan={2}>
                        <input 
                        type="checkbox" 
                        name="shippingAddressRequired" 
                        checked={shipToDifferentAddress}
                        onChange={(e) => setShipToDifferentAddress(e.target.checked)}
                        />
                        Ship to different address...
                    </td>
                  </tr>
                </tbody>
              </table>

              <p>
                <input type="submit" name="newOrder" value="Continue" />
              </p>
            </form>

            {/* 用户地址部分 */}
            <div id={styles.userAddresses}>
              <h3>Historical Addresses</h3>
              {addresses.map((address) => (
                <div key={address.addressId} className={styles.address}
                  onMouseMove={(e) => {
                    // 获取相对于userAddresses容器的位置
                    const container = e.currentTarget.closest(`#${styles.userAddresses}`);
                    if (container) {
                      const rect = container.getBoundingClientRect();
                      setTooltipPosition({
                        x: e.clientX - rect.left + 20,
                        y: e.clientY - rect.top
                      });
                    }
                    setHoveredAddress(address);
                  }}
                  onMouseLeave={() => setHoveredAddress(null)}>
                  <h4>
                    {address.addressId}
                    {address.addressId === mainAddress && <span className={styles.mainBadge}> (Main)</span>}
                  </h4>
                  <span>
                    <p>
                      {address.address1} {address.address2}
                    </p>
                    <button
                      id={styles.chooseButton}
                      onClick={() => handleChoose(address)}
                    >
                      Choose
                    </button>
                    &nbsp;&nbsp;&nbsp;
                    <button
                      id={styles.setMainButton}
                      onClick={() => handleSetMain(address.addressId)}
                    >
                      Set Main
                    </button>
                    &nbsp;&nbsp;&nbsp;
                    <button
                      id={styles.deleteButton}
                      onClick={() => handleDelete(address.addressId)}
                    >
                      Delete
                    </button>
                  </span>
                </div>
              ))}

            {/* 悬浮窗 */}
            {hoveredAddress && (
                <div 
                  className={`${styles.addressTooltip} ${hoveredAddress ? styles.visible : ''}`}
                  style={{
                    left: `${tooltipPosition.x}px`,
                    top: `${tooltipPosition.y}px`
                  }}>
                  <ul>
                    <li>{hoveredAddress.firstName} {hoveredAddress.lastName}</li>
                    <li>{hoveredAddress.address1} | {hoveredAddress.address2}</li>
                    <li>{hoveredAddress.city} | {hoveredAddress.state} | {hoveredAddress.zip} | {hoveredAddress.country}</li>
                  </ul>
                </div>
              )}

            </div>
          </>
        ) : (
          <p>Loading order details...</p>
        )}
      </div>
      <Footer />
    </>
  );
}
