// page.tsx
"use client";

import { useState, useEffect } from "react";
import { backendUrl } from "@/app/config";
import Cookies from "js-cookie";
import Header from "@/app/common/header";
import Footer from "@/app/common/footer";
import UserInfoModule from "./UserInfoModule";
import OrdersModule from "./OrderModule";
import LogsModule from "./LogsModule";

// Define the type for user information
interface UserInfo {
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  address1?: string;
  address2?: string;
  city?: string;
  zip?: string;
  state?: string;
  country?: string;
  langPref?: string;
  favCategory?: string;
  mylistOpt?: boolean;
  bannerOpt?: boolean;
}

export default function Profile() {
  const [activeModule, setActiveModule] = useState("info");
  // Simulate user information data
  const [userInfo, setUserInfo] = useState<UserInfo | null>({
    firstName: "",
    lastName: "",
    email: "",
    phone: "",
    address1: "",
    address2: "",
    city: "",
    zip: "",
    state: "",
    country: "",
    langPref: "",
    favCategory: "",
    mylistOpt: false,
    bannerOpt: false
  });

  // API call to display user information
useEffect(() => {
  const fetchUserInfo = async () => {
    const token = Cookies.get("token");
    if (!token) {
      alert("请先登录");
      return;
    }

    fetch(`${backendUrl}/api/v1/account/me`, {
      headers: {
        "Authorization": `Bearer ${token}`
      }
    })
    .then(res => {
      if (res.ok) {
        return res.json();
      } else {
        throw new Error("获取用户信息失败");
      }
    })
    .then(data => {
      setUserInfo(data);
    })
    .catch(error => {
      console.error("获取用户信息失败", error);
      alert("获取用户信息失败，请稍后重试");
    });
  };
  
  fetchUserInfo();
}, []);

  const renderModule = () => {
    switch (activeModule) {
      case "info":
        return <UserInfoModule userInfo={userInfo} setUserInfo={setUserInfo} />;
      case "orders":
        return <OrdersModule />;
      case "logs":
        return <LogsModule />;
      default:
        return <UserInfoModule userInfo={userInfo} setUserInfo={setUserInfo} />;
    }
  };

 
  return (
    <div className="flex flex-col h-screen">
      <Header />
      <div className="flex-grow flex flex-col items-center py-12 bg-gray-100">
        <div className="p-8 bg-white shadow-md rounded-md w-full max-w-4xl">
          <div className="flex justify-center gap-4 mb-8">
            <button
              className={`px-4 py-2 rounded ${
                activeModule === "info"
                  ? "bg-green-500 text-white"
                  : "bg-gray-200 text-gray-700"
              }`}
              onClick={() => setActiveModule("info")}
            >
              Profile
            </button>
            <button
              className={`px-4 py-2 rounded ${
                activeModule === "orders"
                  ? "bg-green-500 text-white"
                  : "bg-gray-200 text-gray-700"
              }`}
              onClick={() => setActiveModule("orders")}
            >
              Orders
            </button>
            <button
              className={`px-4 py-2 rounded ${
                activeModule === "logs"
                  ? "bg-green-500 text-white"
                  : "bg-gray-200 text-gray-700"
              }`}
              onClick={() => setActiveModule("logs")}
            >
              Logs
            </button>
          </div>
          <div>{renderModule()}</div>
        </div>
      </div>
      <Footer />
    </div>
  );
}