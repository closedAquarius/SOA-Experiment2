/*import { backendUrl } from "./config";
import toast from "react-hot-toast";
import Cookies from "js-cookie";

export const handleAddToCart =  (id:string) => {
  fetch(`${backendUrl}/carts/${id}`, {
    method: "POST",
    headers: {
      "Authorization": `Bearer ${Cookies.get("token")}`
    },
  })
  .then(res => res.json())
  .then(data => {
    if (data.status === 0) {
      toast(`😉successfully added ${id} to your cart!`);
    } else {
      toast(`😒we are sorry that ${data.message}`);
    }
  })
  .catch(error => {
    alert("访问令牌失效，请重新登录");
  })
};*/

import { addItemToCart } from "./cart/order-service";

/**
 * 添加商品到购物车
 * 供各页面调用的通用方法
 */
export async function handleAddToCart(itemId: string): Promise<void> {
  try {
    await addItemToCart(itemId);
    alert("Successfully added to cart!");
  } catch (error: any) {
    if (error.message === "item is not in stock") {
      alert("Sorry, this item is not in stock.");
    } else if (error.message === "Please log in first") {
      alert("Please log in first");
      window.location.href = "/login";
    } else {
      console.error("Error adding to cart:", error);
      alert(error.message || "Failed to add item to cart");
    }
  }
}