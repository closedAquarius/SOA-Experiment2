import { useEffect, useState } from "react";
import { backendUrl } from "@/app/config";
import Cookies from "js-cookie";
import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

interface LineItemVO {
  id: string;
  name: string;
  quantity: number;
  price: number;
}

interface OrderItem {
  orderId: string;
  username: string;
  orderDate: string;
  totalPrice: number;
  status: string;
  shipToFirstName: string;
  shipToLastName: string;
  shipAddress1: string;
  shipCity: string;
  shipCountry: string;
  billToFirstName: string;
  billToLastName: string;
  billAddress1: string;
  billCity: string;
  billCountry: string;
  creditCard: string;
  expiryDate: string;
  cardType: string;
  lineItems: LineItemVO[];
}

export default function OrdersModule() {
  const [orders, setOrders] = useState<OrderItem[]>([]);
  const [expandedOrderId, setExpandedOrderId] = useState<string | null>(null);
  const [returnForm, setReturnForm] = useState({
    orderId: "",
    reason: "",
    description: "",
    imageFile: null as File | null
  });

  const fetchOrders = async () => {
    try {
      const token = Cookies.get("token");
      console.log("Fetching orders with token:", token);  // 打印 token
  
      const response = await fetch(`${backendUrl}/api/v1/account/me/myOrders`, {
        headers: { Authorization: `Bearer ${token}` },
      });
  
      console.log("Response status:", response.status);  // 打印响应状态码
  
      const data: OrderItem[] = await response.json();  // 👈 这里加类型
  
      console.log("Fetched orders data:", data);
      data.forEach((order, idx) => {
        console.log(`Order #${idx + 1}:`, order);
        console.log(`Line Items:`, order.lineItems);
      });
  
      setOrders(data);
    } catch (err) {
      console.error("Failed to fetch orders:", err);  // 捕获错误并打印
    }
  };
  
  

  useEffect(() => {
    console.log("Fetching orders...");  // 确保这行代码被执行
    fetchOrders();
  }, []);

  const handleSubmitReturn = async () => {
    if (!returnForm.imageFile) {
      alert("Please upload an image.");
      return;
    }

    const formData = new FormData();
    formData.append("orderId", returnForm.orderId);
    formData.append("reason", returnForm.reason);
    formData.append("description", returnForm.description);
    formData.append("image", returnForm.imageFile);

    const token = Cookies.get("token");
    const res = await fetch(`${backendUrl}/api/v1/account/myOrders/cancel`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`
      },
      body: formData
    });

    const msg = await res.text();
    if (res.ok) {
      alert("Return request submitted!");
      fetchOrders(); // refresh order list
    } else {
      alert("Failed: " + msg);
    }
  };

  const getStatusStyle = (status: string) => {
    switch (status.toLowerCase()) {
      case "completed":
        return "text-green-600 bg-green-100 px-2 py-1 rounded";
      case "pending":
        return "text-yellow-600 bg-yellow-100 px-2 py-1 rounded";
      default:
        return "text-gray-600 bg-gray-100 px-2 py-1 rounded";
    }
  };

  return (
    <div className="space-y-4">
      {orders.map((order) => (
        <Card key={order.orderId}>
          <CardContent className="p-4">
            <div className="flex justify-between items-center">
              <div>
                <h3 className="font-bold">Order #{order.orderId}</h3>
                <p>{new Date(order.orderDate).toLocaleString()}</p>
              </div>
              <span className={getStatusStyle(order.status)}>{order.status}</span>
            </div>

            <Button
              className="mt-2"
              variant="outline"
              onClick={() =>
                setExpandedOrderId(expandedOrderId === order.orderId ? null : order.orderId)
              }
            >
              {expandedOrderId === order.orderId ? "Hide Details" : "View Details"}
            </Button>

            {expandedOrderId === order.orderId && (
              <div className="mt-4 space-y-2 border-t pt-4 text-sm">
                <p><strong>Ship To:</strong> {order.shipToFirstName} {order.shipToLastName}, {order.shipAddress1}, {order.shipCity}, {order.shipCountry}</p>
                <p><strong>Bill To:</strong> {order.billToFirstName} {order.billToLastName}, {order.billAddress1}, {order.billCity}, {order.billCountry}</p>
                <p><strong>Payment:</strong> {order.cardType} {order.creditCard}, Exp: {order.expiryDate}</p>
                

                <p className="font-bold">Total: ¥{order.totalPrice.toFixed(2)}</p>

                {order.status.toLowerCase() === "f" && (
                  <div className="mt-4 space-y-2 bg-gray-50 p-4 rounded border">
                    <h4 className="font-medium">Return Request</h4>
                    <Input
                      placeholder="Reason"
                      value={returnForm.reason}
                      onChange={(e) =>
                        setReturnForm({ ...returnForm, reason: e.target.value, orderId: order.orderId })
                      }
                    />
                    <textarea
                      placeholder="Description"
                      className="w-full border rounded p-2"
                      rows={3}
                      value={returnForm.description}
                      onChange={(e) =>
                        setReturnForm({ ...returnForm, description: e.target.value, orderId: order.orderId })
                      }
                    />
                    <Input
                      type="file"
                      accept="image/*"
                      onChange={(e) =>
                        setReturnForm({
                          ...returnForm,
                          imageFile: e.target.files ? e.target.files[0] : null,
                          orderId: order.orderId
                        })
                      }
                    />
                    <Button onClick={handleSubmitReturn}>Submit Return Request</Button>
                  </div>
                )}
              </div>
            )}
          </CardContent>
        </Card>
      ))}
    </div>
  );
}
