import { NextRequest } from 'next/server';
import axios from 'axios';
import { redirect } from 'next/navigation'; // 服务端使用redirect, 客户端使用useRouter
import { cookies } from 'next/headers';
import { backendUrl } from '@/app/config';

const clientID = process.env.AUTH_GITEE_ID;
const clientSecret = process.env.AUTH_GITEE_SECRET;
export async function GET(request: NextRequest) {
  //1. 获取code
  const searchParams = request.nextUrl.searchParams;
  console.log('searchParams: ', searchParams);
  const requestToken = searchParams.get('code'); // e.g. `/api/oauth/redirect?code=xxx`
  console.log('requestToken', requestToken);
  
  //2. 获取访问token
  const tokenResponse = await axios({
    method: 'post',
    url: 'https://gitee.com/oauth/token'+
      `?grant_type=authorization_code`+
      `&code=${requestToken}&client_id=${clientID}`+
      `&redirect_uri=http://localhost:3000/api/oauth/redirect`+
      `&client_secret=${clientSecret}`,
    headers: {
      'Content-Type': 'application/json'
    }
  });
  const accessToken = tokenResponse.data.access_token;
  console.log(`access token: ${accessToken}`);
  const openapi = `https://gitee.com/api/v5/user?access_token=${accessToken}`;
  console.log("openapi: ", openapi);
  
  //3. 获取用户数据
  const res = await fetch(openapi);
  const data = await res.json();
  console.log(data);
  const name = data.name;
  const email = data.email;
  let token = '';
  try {
    token = await fetch(`${backendUrl}/api/v1/auth/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        "username": name,
        "password": "gitee-oauth",
      })
    }).then(res => res.text());
  } catch {
    console.log("the user is not in the database");
    token = await fetch(`${backendUrl}/api/v1/account`, {
        method: "POST",
        body: JSON.stringify({
          "username" : name,
          "password" : "gitee-oauth",
          
          "firstName" : "xxx",
          "lastName" : "xxx",
          "email" : email,
          "phone" : "xxx",
          "address1" : "xxx",
          "address2" : "xxx",
          "city" : "xxx",
          "zip" : "xxx",
          "state" : "xxx",
          "country" : "xxx",
          "languagePreference" : "xxx",
          "favouriteCategoryId" : "dogs",
          "listOption" : true,
          "bannerOption" : true,
        }),
        headers: {
            "Content-Type": "application/json",
        }
      })
      .then(res => res.text());
  }
  
  (await cookies()).set('token', token);
  (await cookies()).set('username', name);
  redirect(`/main`)
}