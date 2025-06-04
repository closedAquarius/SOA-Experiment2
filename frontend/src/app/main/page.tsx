'use client'
import Header from "@/app/common/header";
import Hero from "./hero";
import Footer from "@/app/common/footer";
import toast, { Toaster } from 'react-hot-toast';
import { useEffect, useRef } from "react";
import Cookies from "js-cookie";
export default function Main() {
    const didMountRef = useRef(false);
    
    // 确保是在客户端执行
    useEffect(() => {
        const usernameCookie = Cookies.get('username');
        if (!didMountRef.current) {
            didMountRef.current = true;
            if (usernameCookie !== undefined) {
                toast(`👋Welcome ${usernameCookie}`)
            } else {
                toast("😉You are only a guest now, try to sign in or sign up");
            }
        }
        console.log('execute when user = ', usernameCookie);    
    },[]);
    return (
        <div className="flex flex-col h-screen">
            <Toaster />
            <Header />
            <Hero />
            <Footer />
        </div>
    )
}