"use client"

import { useRef } from "react";
import { springBoot } from "../../config";
import { useRouter } from "next/navigation";

export default function Register() {
    const router = useRouter();
    async function handleSubmit(e: any) {
        e.preventDefault();
        const formData = new FormData(e.currentTarget);
        const data = {
            username: formData.get("username"),
            password: formData.get("password"),
            email: formData.get("email"),
            favouriteCategoryId: formData.get("favouriteCategoryId"),

            "firstName" : "xxx",
            "lastName" : "xxx",
            "phone" : "xxx",
            "address1" : "xxx",
            "address2" : "xxx",
            "city" : "xxx",
            "zip" : "xxx",
            "state" : "xxx",
            "country" : "xxx",
            "languagePreference" : "xxx",
            "listOption" : true,
            "bannerOption" : false
        }

        if (data.password !== formData.get("repeat-password")) {
            window.alert("两次密码不一致");
            return;
        }
        const resp = await fetch(`${springBoot}/api/v1/account`, {
            method: "POST",
            body: JSON.stringify(data),
            headers: {
                "Content-Type": "application/json",
            },
        })
        if (resp.ok) {
            window.alert("注册成功");
            router.push('/login');
        } else {
            window.alert("注册失败");
        }
    }

    const usernameRef = useRef<HTMLInputElement>(null);
    const usernameWarning = useRef<HTMLParagraphElement>(null);
    const emailRef = useRef<HTMLInputElement>(null);
    const emailWarning = useRef<HTMLParagraphElement>(null);
    const passwordRef = useRef<HTMLInputElement>(null);
    const passwordWarning = useRef<HTMLParagraphElement>(null);
    const rePasswordRef = useRef<HTMLInputElement>(null);
    const rePasswordWarning = useRef<HTMLParagraphElement>(null);
    const handleOnKeyUp = (option:number) => {
      switch (option) {
        case 0:
            const username = usernameRef.current?.value as string;
            console.log(username);
            if (usernameWarning.current) {
                if (username?.length < 4) {
                    usernameWarning.current.style.display = 'block';
                } else {
                    usernameWarning.current.style.display = 'none';
                }
            }
            break;
        case 1:
            const email = emailRef.current?.value as string;
            console.log(email);
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (emailWarning.current) {
              if (!emailPattern.test(email)) {
                emailWarning.current.style.display = 'block';
              } else {
                 emailWarning.current.style.display = 'none';
              }
            }
            break;
        case 2:
            const password = passwordRef.current?.value as string;
            console.log(password);
            if (passwordWarning.current) {
                if (password?.length < 8) {
                    passwordWarning.current.style.display = 'block';
                } else {
                    passwordWarning.current.style.display = 'none';
                }
            }
            break;
        case 3:
            const lastPassword = passwordRef.current?.value as string;
            const rePassword = rePasswordRef.current?.value as string;
            console.log(rePassword);
            if (rePasswordWarning.current) {
                if (rePassword !== lastPassword) {
                  rePasswordWarning.current.style.display = 'block';
                } else {
                  rePasswordWarning.current.style.display = 'none';
                }
            }
            break;
        default:
           return;
      }

    }
    return (
        <div className="bg-linear-to-r/srgb from-indigo-500 to-teal-400
            h-screen w-screen flex flex-col justify-center items-center">
            <div className="flex justify-center items-center
            bg-white rounded-lg p-8 w-fit">
                <div>
                    <figure>
                        <img src="/images/signup-image.jpg" alt="sign in image" />
                    </figure>
                    <a href="/login"
                    className="underline">already have an account</a>
                </div>
                <div>
                    <form method="post"
                        onSubmit={handleSubmit}
                        className="flex flex-col gap-4 bg-white p-4 
                        border-2 border-black rounded-md shadow-md w-70">
                        <div>
                            <label htmlFor="username">Username</label>
                            <br />
                            <input type="text" id="username" name="username" required ref={usernameRef}
                                className="p-2 border-2 border-black rounded-md" onKeyUp={()=>handleOnKeyUp(0)}/>
                            <p style={{display:'none', color:'red'}} ref={usernameWarning}>
                              username must be at least 4 characters</p>
                        </div>
                        <div>
                            <label htmlFor="email">Email</label>
                            <br />
                            <input type="email" id="email" name="email" required
                                className="p-2 border-2 border-black rounded-md"
                                ref={emailRef} onKeyUp={()=>handleOnKeyUp(1)}/>
                            <p style={{display:'none', color:'red'}} ref={emailWarning}>
                              please enter valid email</p>
                        </div>
                        <div>
                            <label htmlFor="password">Password</label>
                            <br />
                            <input type="password" id="password" name="password" required
                                className="p-2 border-2 border-black rounded-md"
                                ref={passwordRef} onKeyUp={()=>handleOnKeyUp(2)}/>
                            <p style={{display:'none', color:'red'}} ref={passwordWarning}>
                              password must be at least 8 characters</p>
                        </div>
                        <div>
                            <label htmlFor="repeat-password">Repeat password</label>
                            <br />
                            <input type="password" id="repeat-password" name="repeat-password" required
                                className="p-2 border-2 border-black rounded-md"
                                ref={rePasswordRef} onKeyUp={()=>handleOnKeyUp(3)}
                                />
                            <p style={{display:'none', color:'red'}} ref={rePasswordWarning}>
                              it shoule be exactly the same as the password</p>
                        </div>
                        <div>
                            <label htmlFor="favouriteCategoryId">favourite animals</label>
                            <br />
                            <select name="favouriteCategoryId" id="favouriteCategoryId"
                                className="p-2 border-2 border-black rounded-md" required
                                defaultValue="BIRDS">
                                <option value="BIRDS">birds</option>
                                <option value="CATS">cats</option>
                                <option value="DOGS">dogs</option>
                                <option value="FISH">fish</option>
                                <option value="REPTILES">reptiles</option>
                            </select>
                        </div>
                        <button type="submit"
                            className="bg-black text-white p-4 rounded-md
                            cursor-pointer"
                            >Register</button>
                    </form>
                </div>
            </div>
        </div>
    )
}