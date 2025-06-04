import { backendUrl } from "./config";
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
};