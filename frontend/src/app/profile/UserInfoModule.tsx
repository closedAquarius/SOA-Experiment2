// UserInfoModule.tsx
import { useState } from "react";
import { backendUrl } from "@/app/config";
import { Card, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import Cookies from "js-cookie";

// 定义用户信息的类型
interface Profile {
  languagePreference: string;
  favouriteCategoryId: string;
  listOption: boolean;
  bannerOption: boolean;
}

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
  profile?: Profile; // 添加 profile 属性
}

export default function UserInfoModule({
  userInfo,
  setUserInfo,
}: {
  userInfo: UserInfo | null;
  setUserInfo: React.Dispatch<React.SetStateAction<UserInfo | null>>;
}) {
  const [showEditForm, setShowEditForm] = useState(false);
  const [showPasswordForm, setShowPasswordForm] = useState(false);
  const [formData, setFormData] = useState<UserInfo>({
    firstName: userInfo?.firstName || "",
    lastName: userInfo?.lastName || "",
    email: userInfo?.email || "",
    phone: userInfo?.phone || "",
    address1: userInfo?.address1 || "",
    address2: userInfo?.address2 || "",
    city: userInfo?.city || "",
    zip: userInfo?.zip || "",
    state: userInfo?.state || "",
    country: userInfo?.country || "",
    langPref: userInfo?.profile?.languagePreference || "", // 从 profile 中获取
    favCategory: userInfo?.profile?.favouriteCategoryId || "", // 从 profile 中获取
    mylistOpt: userInfo?.profile?.listOption || false, // 从 profile 中获取
    bannerOpt: userInfo?.profile?.bannerOption || false, // 从 profile 中获取
  });
  
  const [passwordData, setPasswordData] = useState({
    oldPassword: "",
    newPassword: "",
    confirmPassword: ""
  });
  //信息更新提交
  const handleEditSubmit = async () => {
    try {
      const token = Cookies.get("token");
      if (!token) {
        alert("Not logged in, please log in first");
        return;
      }
  
      // 构造符合后端期望的数据结构
      const submitData = {
        firstName: formData.firstName,
        lastName: formData.lastName,
        email: formData.email,
        phone: formData.phone,
        address1: formData.address1,
        address2: formData.address2,
        city: formData.city,
        zip: formData.zip,
        state: formData.state,
        country: formData.country,
        languagePreference: formData.langPref,
        favouriteCategoryId: formData.favCategory,
        listOption: formData.mylistOpt,
        bannerOption: formData.bannerOpt
      };
  
      const response = await fetch(`${backendUrl}/api/v1/account/me/info`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
        body: JSON.stringify(submitData),
      });
  
      if (response.ok) {
        const updatedInfo = await response.json();
        setUserInfo(updatedInfo); // 直接使用后端返回的完整数据结构
        setShowEditForm(false);
        alert("😊 Information modified successfully!");
      } else {
        const errorText = await response.text();
        throw new Error(errorText);
      }
    } catch (error) {
      console.error("Error updating user info:", error);
      alert("😔 Information modification failed, please try again later!");
    }
  };
  

  // 密码修改
  const handlePasswordSubmit = async () => {
    if (passwordData.newPassword !== passwordData.confirmPassword) {
      alert("The new passwords entered twice are inconsistent!");
      return;
    }
  
    const currentToken = Cookies.get("token");
    if (!currentToken) {
      alert("Not logged in, please log in first");
      return;
    }
  
    try {
      const response = await fetch(`${backendUrl}/api/v1/auth/resetPsw`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${currentToken}`,
        },
        body: JSON.stringify({
          oldPassword: passwordData.oldPassword,
          newPassword: passwordData.newPassword,
        }),
      });
  
      if (response.ok) {
        setShowPasswordForm(false);
        setPasswordData({
          oldPassword: "",
          newPassword: "",
          confirmPassword: "",
        });
        alert("Password changed successfully!");
      } else if (response.status === 401) {
        alert("Unauthorized: Please check your current password or log in again.");
      } else if (response.status === 400) {
        const errorText = await response.text();
        alert(`Password update failed: ${errorText}`);
      } else {
        const errorText = await response.text();
        alert(`Unexpected error: ${errorText}`);
      }
    } catch (error) {
      if (error instanceof Error) {
        console.error("Password update error:", error);
        alert("Password update failed: " + (error.message || "Please try again later"));
      } else {
        console.error("Unexpected error:", error);
        alert("An unexpected error occurred. Please try again later.");
      }
    }
  };
  

  if (!userInfo) {
    return <p>loading...</p>;
  }

  return (
    <Card>
      <CardContent>
        {!showEditForm && !showPasswordForm ? (
          <>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
              <div>
                <p className="text-sm text-gray-500">Name</p>
                <p className="font-medium">{userInfo.firstName} {userInfo.lastName}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Mail</p>
                <p className="font-medium">{userInfo.email}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Phone</p>
                <p className="font-medium">{userInfo.phone}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Address</p>
                <p className="font-medium">{userInfo.address1}</p>
                {userInfo.address2 && <p className="font-medium">{userInfo.address2}</p>}
              </div>
              <div>
                <p className="text-sm text-gray-500">City</p>
                <p className="font-medium">{userInfo.city}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Zip</p>
                <p className="font-medium">{userInfo.zip}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">State</p>
                <p className="font-medium">{userInfo.state}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Country</p>
                <p className="font-medium">{userInfo.country}</p>
              </div>
              <div>
  <p className="text-sm text-gray-500">Language preference</p>
  <p className="font-medium">
    {userInfo.profile?.languagePreference === "zh_CN"
      ? "Chinese"
      : userInfo.profile?.languagePreference === "english"
      ? "English"
      : userInfo.profile?.languagePreference}
  </p>
</div>
<div>
  <p className="text-sm text-gray-500">Favorite category</p>
  <p className="font-medium">
    {userInfo.profile?.favouriteCategoryId === "FISH"
      ? "Fish"
      : userInfo.profile?.favouriteCategoryId === "DOGS"
      ? "Dogs"
      : userInfo.profile?.favouriteCategoryId === "CATS"
      ? "Cats"
      : userInfo.profile?.favouriteCategoryId === "REPTILES"
      ? "Reptiles"
      : userInfo.profile?.favouriteCategoryId === "BIRDS"
      ? "Birds"
      : userInfo.profile?.favouriteCategoryId}
  </p>
</div>
<div>
  <p className="text-sm text-gray-500">Notification options</p>
  <p className="font-medium">
    {userInfo.profile?.listOption ? "Receive list updates" : "Do not receive list updates"},
    {userInfo.profile?.bannerOption ? "Show banners" : "Do not show banners"}
  </p>
</div>

            </div>
            <div className="flex flex-wrap gap-4">
            <Button
  onClick={() => {
    setFormData({
      firstName: userInfo?.firstName || "",
      lastName: userInfo?.lastName || "",
      email: userInfo?.email || "",
      phone: userInfo?.phone || "",
      address1: userInfo?.address1 || "",
      address2: userInfo?.address2 || "",
      city: userInfo?.city || "",
      zip: userInfo?.zip || "",
      state: userInfo?.state || "",
      country: userInfo?.country || "",
      langPref: userInfo?.profile?.languagePreference || "",
      favCategory: userInfo?.profile?.favouriteCategoryId || "",
      mylistOpt: userInfo?.profile?.listOption || false,
      bannerOpt: userInfo?.profile?.bannerOption || false,
    });
    setShowEditForm(true);
  }}
>
  Modify personal information
</Button>
              <Button
                variant="outline"
                onClick={() => setShowPasswordForm(true)}
              >
                Change password
              </Button>
            </div>
          </>
        ) : showEditForm ? (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div className="space-y-2">
              <Label htmlFor="firstName">firstName</Label>
              <Input
                id="firstName"
                type="text"
                value={formData.firstName}
                onChange={(e) => setFormData({ ...formData, firstName: e.target.value })}
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="lastName">lastName</Label>
              <Input
                id="lastName"
                type="text"
                value={formData.lastName}
                onChange={(e) => setFormData({ ...formData, lastName: e.target.value })}
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="email">email</Label>
              <Input
                id="email"
                type="email"
                value={formData.email}
                onChange={(e) => setFormData({ ...formData, email: e.target.value })}
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="phone">phone</Label>
              <Input
                id="phone"
                type="text"
                value={formData.phone}
                onChange={(e) => setFormData({ ...formData, phone: e.target.value })}
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="address1">address1</Label>
              <Input
                id="address1"
                type="text"
                value={formData.address1}
                onChange={(e) => setFormData({ ...formData, address1: e.target.value })}
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="address2">address2</Label>
              <Input
                id="address2"
                type="text"
                value={formData.address2}
                onChange={(e) => setFormData({ ...formData, address2: e.target.value })}
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="city">city</Label>
              <Input
                id="city"
                type="text"
                value={formData.city}
                onChange={(e) => setFormData({ ...formData, city: e.target.value })}
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="zip">zip</Label>
              <Input
                id="zip"
                type="text"
                value={formData.zip}
                onChange={(e) => setFormData({ ...formData, zip: e.target.value })}
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="country">state</Label>
              <Input
                id="state"
                type="text"
                value={formData.state}
                onChange={(e) => setFormData({ ...formData, state: e.target.value })}
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="country">country</Label>
              <Input
                id="country"
                type="text"
                value={formData.country}
                onChange={(e) => setFormData({ ...formData, country: e.target.value })}
              />
            </div>
            <div className="space-y-2">
  <Label htmlFor="langPref">Language preference</Label>
  <select
    id="langPref"
    className="w-full p-2 border rounded"
    value={formData.langPref}
    onChange={(e) => setFormData({ ...formData, langPref: e.target.value })}
  >
    <option value="">Choose language</option>
    <option value="zh_CN">Chinese</option>
    <option value="english">English</option>
  </select>
</div>
<div className="space-y-2">
  <Label htmlFor="favCategory">Favorite category</Label>
  <select
    id="favCategory"
    className="w-full p-2 border rounded"
    value={formData.favCategory}
    onChange={(e) => setFormData({ ...formData, favCategory: e.target.value })}
  >
    <option value="">Choose category</option>
    <option value="FISH">Fish</option>
    <option value="DOGS">Dogs</option>
    <option value="CATS">Cats</option>
    <option value="REPTILES">Reptiles</option>
    <option value="BIRDS">Birds</option>
  </select>
</div>
<div className="col-span-2 space-y-4 mt-2">
  <div className="flex items-center space-x-2">
    <input 
      type="checkbox" 
      id="mylistOpt" 
      checked={formData.mylistOpt}
      onChange={(e) => setFormData({ ...formData, mylistOpt: e.target.checked })}
    />
    <Label htmlFor="mylistOpt">Receive list updates</Label>
  </div>
  <div className="flex items-center space-x-2">
    <input 
      type="checkbox" 
      id="bannerOpt" 
      checked={formData.bannerOpt}
      onChange={(e) => setFormData({ ...formData, bannerOpt: e.target.checked })}
    />
    <Label htmlFor="bannerOpt">Show banners</Label>
  </div>
</div>
            <div className="col-span-2 flex gap-4 mt-4">
              <Button
                onClick={handleEditSubmit}
              >
                save information
              </Button>
              <Button
                variant="outline"
                onClick={() => setShowEditForm(false)}
              >
                cancel
              </Button>
            </div>
          </div>
        ) : (
          <div className="space-y-4">
            <div className="space-y-2">
              <Label htmlFor="oldPassword">current password</Label>
              <Input
                id="oldPassword"
                type="password"
                value={passwordData.oldPassword}
                onChange={(e) => setPasswordData({ ...passwordData, oldPassword: e.target.value })}
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="newPassword">new password</Label>
              <Input
                id="newPassword"
                type="password"
                value={passwordData.newPassword}
                onChange={(e) => setPasswordData({ ...passwordData, newPassword: e.target.value })}
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="confirmPassword">confirm password</Label>
              <Input
                id="confirmPassword"
                type="password"
                value={passwordData.confirmPassword}
                onChange={(e) => setPasswordData({ ...passwordData, confirmPassword: e.target.value })}
              />
            </div>
            <div className="flex gap-4 mt-4">
              <Button
                onClick={handlePasswordSubmit}
              >
                change password
              </Button>
              <Button
                variant="outline"
                onClick={() => {
                  setShowPasswordForm(false);
                  setPasswordData({
                    oldPassword: "",
                    newPassword: "",
                    confirmPassword: ""
                  });
                }}
              >
                cancel
              </Button>
            </div>
          </div>
        )}
      </CardContent>
    </Card>
  );
}