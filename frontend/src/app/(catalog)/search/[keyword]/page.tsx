"use client";
// @ts-nocheck
import Footer from "@/app/common/footer";
import Header from "@/app/common/header";
import { backendUrl } from "@/app/config";
import { parseDescription } from "@/app/utils";
import Link from "next/link";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";
import { ProductDTO, searchProducts } from "../../product-service";

export default function Search() {
  const params = useParams();
  const keyword = params.keyword as string;
  const [productList, setProductList] = useState<ProductDTO[]>([]);

  useEffect(() => {
    if (!keyword) return;
    searchProducts(keyword)
      .then((nextData: ProductDTO[]) => {
        setProductList(nextData);
      })
      .catch((err: unknown) => {
        console.error(err);
      });
  }, [keyword]);

  return (
    <div className="flex flex-col h-screen">
      <Header />
      <div
        className="flex-1 self-center mt-4
             grid md:grid-cols-2 sm:grid-cols-1 gap-2 justify-items-center
             auto-rows-min
             w-fit overflow-y-auto"
      >
        {productList.map((item: ProductDTO) => (
          <div
            key={item.productId}
            className="bg-orange-200 w-fit p-4 border-4 border-rose-400 rounded-2xl
                            flex flex-row gap-2 items-center justify-center
                            h-48"
          >
            <img
              src={`${backendUrl}` + parseDescription(item.description).image}
              alt="img"
              width="60"
            />
            <div className="w-60">
              <p className="text-xl font-bold">{item.name}</p>
              <p className="underline text-rose-500">
                <Link href={`/product/${item.productId}`}>{item.productId}</Link>
              </p>
              <p>{item.categoryId}</p>
              <p className="truncate">
                {parseDescription(item.description).text}
              </p>
            </div>
          </div>
        ))}
      </div>
      <Footer />
    </div>
  );
}