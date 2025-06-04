"use client"

import { springBoot, backendUrl } from "@/app/config";
import { useRouter } from "next/navigation";
import GiteeIcon from "../GiteeIcon";
import Cookies from "js-cookie";
export default function LogIn() {
    const router = useRouter();
    // 在客户端组件只能读取到NEXT_PUBLIC_前缀的环境变量
    const giteeOauthUrl = `https://gitee.com/oauth/authorize`
        +`?client_id=${process.env.NEXT_PUBLIC_AUTH_GITEE_ID}`
        +`&redirect_uri=${process.env.NEXT_PUBLIC_GITEE_REDIRECT_URL}`;
    async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();
        const formData = new FormData(e.currentTarget);
        const username = formData.get("username");
        const password = formData.get("password");
        const data = {
            username: username,
            password: password,
        }
        const resp = await fetch(`${backendUrl}/api/v1/auth/login`, {
            method: "POST",
            body: JSON.stringify(data),
            headers: {
                "Content-Type": "application/json",
            },
        })
        if (resp.ok) {
            const data = await resp.text();
            if (data === 'Invalid username or password') {
              alert('Invalid username or password');
              router.push('/login');
              return;
            }
            Cookies.set("token", data);
            Cookies.set("username",username as string);
            router.push(`/main`);
            return;
        } else {
            window.alert("用户名或密码错误");
        }

    }
    return (
        <div className="bg-linear-to-r/srgb from-indigo-500 to-teal-400
            h-screen w-screen flex flex-col justify-center items-center">
            <div className="flex justify-center items-center
            bg-white rounded-lg p-8 w-fit">
                <div>
                    <figure>
                        <img src="/images/signin-image.jpg" alt="sign in image" />
                    </figure>
                    <a href="/register"
                    className="underline">create an account</a>
                </div>
                <div>
                    
                    <form method="post"
                        onSubmit={handleSubmit}
                        className="flex flex-col gap-4 bg-white p-4 
                        border-2 border-black rounded-md shadow-md w-70">
                        
                        <div>
                            <label htmlFor="username">Username</label>
                            <input type="text" id="username" name="username" required
                                className="p-2 border-2 border-black rounded-md"/>
                        </div>
                        <div>
                            <label htmlFor="password">Password</label>
                            <input type="password" id="password" name="password" required
                                className="p-2 border-2 border-black rounded-md"/>
                        </div>
                        <button type="submit"
                            className="bg-black text-white p-4 rounded-md
                            cursor-pointer"
                            >Login</button>
                    </form>
                    <div className="bg-neutral-100 m-2 p-2 rounded-md">
                      <a href={giteeOauthUrl}>
                        <GiteeIcon width="20" height="20"></GiteeIcon>
                      </a>
                    </div>
                </div>
            </div>
        </div>
    )
}