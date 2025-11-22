import { backendUrl } from "@/app/config";
import Cookies from "js-cookie";

// 统一的"订单服务"前端调用封装，所有请求通过网关 -> /order-service/**

// 通用响应类型
type CommonResponse<T> = {
  status: number;
  message: string;
  data: T;
};

// 商品项VO
export type ItemVO = {
  itemId: string;
  productId: string;
  productName: string;
  listPrice: number;
  description: string;
  attribute1: string | null;
  attribute2: string | null;
  attribute3: string | null;
  attribute4: string | null;
  attribute5: string | null;
  quantity: number;
};

// 购物车商品项
export type CartItemVO = {
  item: ItemVO;
  quantity: number;
  inStock: boolean;
  total: number;
};

// 购物车VO
export type CartVO = {
  username: string;
  itemList: CartItemVO[];
  numberOfItems: number;
  subTotal: number;
};

// 喜爱商品
export type FavoriteProduct = {
  productId: string;
  categoryId: string;
  name: string;
  description: string;
};

// 更新数量返回的数据
export type UpdateQuantityResult = {
  cartItemTotal: number;
  subTotal: number;
  isOk: number | null;
};

// 获取认证头
function getAuthHeader(): Record<string, string> {
  const token = Cookies.get("token");
  if (token) {
    return { Authorization: `Bearer ${token}` };
  }
  return {};
}

// 通用请求方法
async function request<T>(
  path: string,
  options: RequestInit = {}
): Promise<CommonResponse<T>> {
  const headers: Record<string, string> = {
    "Content-Type": "application/json",
    ...getAuthHeader(),
    ...(options.headers as Record<string, string>),
  };

  const res = await fetch(`${backendUrl}/order-service${path}`, {
    ...options,
    headers,
    cache: "no-store",
  });

  if (!res.ok) {
    throw new Error("网络请求失败");
  }

  return res.json();
}

/**
 * 查看购物车
 * GET /api/carts
 */
export async function fetchCart(): Promise<CartVO> {
  const response = await request<CartVO>("/api/carts", { method: "GET" });
  if (response.status !== 0) {
    throw new Error(response.message || "获取购物车失败");
  }
  return response.data;
}

/**
 * 添加商品到购物车
 * POST /api/carts/{itemId}
 */
export async function addItemToCart(itemId: string): Promise<string> {
  const response = await request<string>(`/api/carts/${itemId}`, {
    method: "POST",
  });
  if (response.status === 2) {
    throw new Error("item is not in stock");
  }
  if (response.status !== 0) {
    throw new Error(response.message || "添加商品失败");
  }
  return response.data;
}

/**
 * 从购物车删除商品
 * DELETE /api/carts/{itemId}
 */
export async function removeItemFromCart(itemId: string): Promise<string> {
  const response = await request<string>(`/api/carts/${itemId}`, {
    method: "DELETE",
  });
  if (response.status !== 0) {
    throw new Error(response.message || "删除商品失败");
  }
  return response.data;
}

/**
 * 更新购物车商品数量
 * PUT /api/carts/{itemId}?quantity=xxx
 */
export async function updateItemQuantity(
  itemId: string,
  quantity: number
): Promise<CommonResponse<UpdateQuantityResult>> {
  const response = await request<UpdateQuantityResult>(
    `/api/carts/${itemId}?quantity=${quantity}`,
    { method: "PUT" }
  );
  return response;
}

/**
 * 获取喜爱列表
 * GET /api/favouriteList
 */
export async function fetchFavoriteList(): Promise<FavoriteProduct[]> {
  const response = await request<FavoriteProduct[]>("/api/favouriteList", {
    method: "GET",
  });
  if (response.status !== 0) {
    // 喜爱列表功能未开启时返回空数组
    return [];
  }
  return response.data;
}
