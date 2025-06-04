"use client";

import Header from "@/app/common/header";
import Footer from "@/app/common/footer";
import { useState, useEffect, useRef } from "react";
import Link from "next/link";
import styles from "./cart.module.css"; 
import Cookies from "js-cookie";
import { backendUrl } from "../config";
import { useRouter } from "next/navigation";

interface Item
{
  itemId: string;
  productId: string;
  productName: string;
  listPrice: number;
  attribute1: string | null;
  attribute2: string | null;
  attribute3: string | null;
  attribute4: string | null;
  attribute5: string | null;
  quantity: number;
  total: number;
  inStock: boolean;
}

interface CartItem {
  item: Item;
  quantity: number;
  inStock: boolean;
  total: number;
}

interface CartData {
    username: string;
    itemList: CartItem[];
    numberOfItems: number;
    subTotal: number;
}

interface FavoriteItem {
    productId: string;
    categoryId: string;
    name: string;
    description: string;
}

export default function Cart() {
    const [cartData, setCartData] = useState<CartData | null>(null);
    const [favoriteList, setFavoriteList] = useState<FavoriteItem[]>([]);  // 新增状态来存储喜爱商品
    const inputRef = useRef<HTMLInputElement>(null);
    const timeoutRef = useRef<NodeJS.Timeout | null>(null);
    const router = useRouter();

    // 获取购物车数据
    const fetchCartData = async () => {
        if(!Cookies.get("token"))
        {
          router.push("/login");
        } 
        else
        {try { 
          const response = await fetch(`${backendUrl}/carts`,{
              method: "GET",
              headers:{"Authorization": `Bearer ${Cookies.get("token")}`},}
            );
            const data = await response.json();
            if (data.status === 0) {
                setCartData(data.data);
            } else {
                alert(data.message);
                router.push("/login");
            }
        } catch (error) {
            console.error("Error fetching cart data", error);
        }}
    };

    // 获取喜爱商品数据
    const fetchFavoriteList = async () => {
      if(!Cookies.get("token"))
        {
          router.push("/login");
        }
        else
        {
        try {
            const response = await fetch(`${backendUrl}/favouriteList`,{
              method: "GET",
              headers:{"Authorization": `Bearer ${Cookies.get("token")}`},}
            );
            const data = await response.json();
            if (data.status === 0) {
                setFavoriteList(data.data);  // 设置喜爱商品列表
            } else {
                alert(data.message);
            }
        } catch (error) {
            console.error("Error fetching favorite list", error);
        }}
    };

    useEffect(() => {
        fetchCartData();       // 获取购物车数据
        fetchFavoriteList();   // 获取喜爱商品数据
    }, []);

    const handleQuantityChange = async (itemId: string, newQuantity: number) => {
      try {
        console.log("newQuantity");  
        console.log(newQuantity);  
        
        // 发送 PUT 请求更新商品数量
          const response = await fetch(`${backendUrl}/carts/${itemId}?quantity=${newQuantity}`,{
            method: "PUT",
            headers:{"Authorization": `Bearer ${Cookies.get("token")}`},}
          );

          const data = await response.json();
          if (data.status === 0) {
            console.log("success");
            fetchCartData();
          } 
          else if (data.status === 2) 
            {
              // 库存不足
              alert("Not Enough Stock");
              fetchCartData();
          } 
          else 
          {
              alert(data.message);
          }
      } catch (error) {
          console.error("Error updating quantity", error);
          alert('Error updating quantity');
      }
    };

    const handleRemoveItem = async (itemId: string) => {
      try {
          const response = await fetch(`${backendUrl}/carts/${itemId}`,{
            method: "DELETE",
            headers:{"Authorization": `Bearer ${Cookies.get("token")}`},}
          );

          const data = await response.json();
          if (data.status === 0) {
              alert('Item successfully removed from the cart.');
              fetchCartData();
          } else {
              alert('Failed to remove item: ' + data.message);
          }
      } catch (error) {
          console.error('Error removing item from cart', error);
          alert('Error removing item from cart');
      }
    };
  
    return (
        <>
            <Header />
            <div className={styles.cartContainer}>
                <div id="Catalog">
                    <div id="Cart">
                        <h2 className={styles.title}>Shopping Cart</h2>
                        {cartData?.numberOfItems === 0 ? (
                            <p className={styles.empty}>Your cart is empty.</p>
                        ) : (
                            <table id="table" className={styles.show}>
                                <thead>
                                    <tr>
                                        <th><b>Item ID</b></th>
                                        <th><b>Product ID</b></th>
                                        <th><b>Description</b></th>
                                        <th><b>In Stock?</b></th>
                                        <th><b>Quantity</b></th>
                                        <th><b>List Price</b></th>
                                        <th><b>Total Cost</b></th>
                                        <th>&nbsp;</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {cartData?.itemList.map((cartItem) => (
                                        <tr key={cartItem.item.itemId+ Math.random()}>
                                            <td>
                                                <Link href={`/item/${cartItem.item.itemId}`}>
                                                    {cartItem.item.itemId}
                                                </Link>
                                            </td>
                                            <td>{cartItem.item.productId}</td>
                                            <td>
                                                {cartItem.item.attribute1 && <span>{cartItem.item.attribute1}</span>}
                                                {cartItem.item.attribute2 && <span>{cartItem.item.attribute2}</span>}
                                                {cartItem.item.attribute3 && <span>{cartItem.item.attribute3}</span>}
                                                {cartItem.item.attribute4 && <span>{cartItem.item.attribute4}</span>}
                                                {cartItem.item.attribute5 && <span>{cartItem.item.attribute5}</span>}
                                                <span>{cartItem.item.productName}</span>
                                            </td>
                                            <td>{cartItem.inStock ? "Yes" : "No"}</td>
                                            <td>
                                              <input
                                                    type="number"
                                                    ref={inputRef}
                                                    value={cartItem.quantity}
                                                    onChange={(e) => handleQuantityChange(cartItem.item.itemId, Number(e.target.value))}
                                                />
                                            </td>
                                            <td>{cartItem.item.listPrice ? cartItem.item.listPrice.toFixed(2) : "0.00"}</td>
                                            <td>
                                                <div className="total">{cartItem.total ? cartItem.total.toFixed(2): "0.00"}</div>
                                            </td>
                                            <td>
                                                <button
                                                    type="button"
                                                    onClick={() => handleRemoveItem(cartItem.item.itemId)}
                                                    className={styles.removeButton}
                                                >
                                                    Remove
                                                </button>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <td colSpan={8}>
                                            <p id="subTotal" style={{ fontWeight: "bold", textAlign: "center" }}>
                                                Sub Total: <span>{cartData?.subTotal.toFixed(2)}</span>
                                            </p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colSpan={8} className={styles.cartButton}>
                                            <p style={{ textAlign: "center" }}>
                                                <button className={styles.updateButton}>Update Cart</button>
                                                {cartData && cartData?.numberOfItems > 0 && (
                                                    <span>
                                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                                        <Link href="/order/newOrder">
                                                            <button type="button" className={styles.Button}>
                                                                Proceed to Checkout
                                                            </button>
                                                        </Link>
                                                    </span>
                                                )}
                                            </p>
                                        </td>
                                    </tr>
                                </tfoot>
                            </table>
                        )}

                         {/* 喜爱商品列表 */}
                         {favoriteList.length > 0 && (
                            <div className={styles.MyList}>
                                <h2 className={styles.title}>Pet Favorites</h2>
                                <p>Shop for more of your favorite pets here.</p>
                                <ul>
                                    {favoriteList.map((favItem) => (
                                        <li key={favItem.productId}>
                                            <Link href={`/product/${favItem.productId}`}>
                                                <span className={styles.favItemName}>{favItem.name}</span>
                                                <span> ({favItem.productId})</span>
                                            </Link>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        )}
                    </div>
                </div>
            </div>
            <Footer />
        </>
    );
}

