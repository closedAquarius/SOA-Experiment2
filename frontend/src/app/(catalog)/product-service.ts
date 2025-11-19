import { backendUrl } from "@/app/config";

// 统一的“商品服务”前端调用封装，所有请求通过网关 http://localhost:8080/8090 -> /product-service/**

type Result<T> = {
  code: number;
  message: string;
  data: T;
};

export type CategoryDTO = {
  categoryId: string;
  name: string;
  description: string;
  products: ProductDTO[];
};

export type ProductDTO = {
  productId: string;
  categoryId: string;
  name: string;
  description: string;
  items: ItemDTO[];
};

export type ItemDTO = {
  itemId: string;
  productId: string;
  productName: string;
  listPrice: number;
  unitCost: number;
  status: string;
  attribute1: string;
  quantity: number;
  description: string;
};

async function request<T>(path: string): Promise<T> {
  const res = await fetch(`${backendUrl}/product-service${path}`, {
    cache: "no-store",
  });
  if (!res.ok) {
    throw new Error("网络请求失败");
  }
  const body: Result<T> = await res.json();
  if (body.code !== 200) {
    throw new Error(body.message || "业务处理失败");
  }
  return body.data;
}

// 分类详情（包含商品列表）
export async function fetchCategory(id: string) {
  return request<CategoryDTO>(`/api/categories/${id}`);
}

// 所有分类
export async function fetchAllCategories() {
  return request<CategoryDTO[]>(`/api/categories`);
}

// 商品详情（包含 item 列表）
export async function fetchProduct(id: string) {
  return request<ProductDTO>(`/api/products/${id}`);
}

// 指定商品的所有 item
export async function fetchItemsByProduct(productId: string) {
  return request<ItemDTO[]>(`/api/items/product/${productId}`);
}

// 单个 item 详情
export async function fetchItem(id: string) {
  return request<ItemDTO>(`/api/items/${id}`);
}

// 按关键字搜索商品
export async function searchProducts(keyword: string) {
  return request<ProductDTO[]>(`/api/products/search?keyword=${encodeURIComponent(keyword)}`);
}

