'use client'
import Icon from "@mdi/react";
import { mdiCart, mdiPaw } from '@mdi/js';
import { SearchDialog } from '@/app/main/searchDialog';
import Link from "next/link";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import Cookies from 'js-cookie';
import { useEffect, useState } from "react";
import { backendUrl } from "../config";

export default function Header() {

    const router = useRouter();
    const [username, setUsername] = useState<String | undefined>(undefined);
    useEffect(()=>{
      const username = Cookies.get('username');
      setUsername(username);
    }, []);

    const handleSignOut = async () => {
      await fetch(`${backendUrl}/api/v1/auth/tokens/current`, {
        method: "DELETE",
        headers: {
          "Authorization": `Bearer ${Cookies.get('token')}`,
        },
      }).then(res => res.text()).then(data => {console.log(data)}).catch(error => {
        console.error(error ,"服务器发生错误");
        alert('服务器错误');
      });
      Cookies.remove('username');
      Cookies.remove('token');
      console.log('removed the username from cookie: ', Cookies.get('username'));
      
      setUsername(undefined);
      router.push('/login');
    }

    return (
        <div className="bg-black flex justify-between items-center h-fit">
            <div className="ml-12">
                <Link href="/main"><Icon className="text-white" path={ mdiPaw} size={5}></Icon></Link>
            </div>
            <div className="flex gap-10 justify-center items-center text-white">
                <Link href="/cart"><Icon path={ mdiCart} size={1}></Icon></Link>
                {
                username !== undefined  ? (
                    <>
                        <Button variant="ghost" onClick={handleSignOut}>Sign Out</Button>
                        <Button variant="ghost">
                            <Link href="/profile">My Profile</Link>
                        </Button>
                        <div 
                            className="rounded-full w-fit aspect-square
                            bg-linear-to-r from-sky-500 to-emerald-500 
                            text-white p-1 flex items-center font-bold"
                        >{username}</div>
                    </>
                ) :( <Link href="/login">Sign In</Link>)
                }
            </div>
            <div className="mr-12">
                <SearchDialog />
            </div>
        </div>
    )
}