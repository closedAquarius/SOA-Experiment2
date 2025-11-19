"use client";
// @ts-nocheck
import Footer from "@/app/common/footer";
import Header from "@/app/common/header";
import { backendUrl } from "@/app/config";
import { parseDescription } from "@/app/utils";
import Link from "next/link";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";
import { handleAddToCart } from "@/app/actions";
import { ItemDTO, ProductDTO, fetchProduct } from "../../product-service";

const Product = () => {
    const { id } = useParams();
    const [itemList, setItemList] = useState<ItemDTO[]>([]);
    const [productName, setProductName] = useState<string>('');
    const [image, setImage] = useState<string>('');
    const [text, setText] = useState<string>('');
    useEffect(()=>{
      if (!id) return;
      fetchProduct(String(id))
        .then((data: ProductDTO) => {
          setItemList(data.items || []);
          setProductName(data.name);
          setImage(parseDescription(data.description).image);
          setText(parseDescription(data.description).text);
        })
        .catch(error => {
          console.error('获取数据失败', error);
          alert("Our system is under maintaining now. You can try later.");
        });
    }, [id]);
    return (
        <div className="flex flex-col h-screen">
            <Header />
            <div className="flex-1 flex flex-col justify-center items-center">
                <p className="font-bold mb-5 text-2xl italic">{ productName}</p>
                <table className="border-4 border-zinc-200">
                    <thead>
                        <tr className="bg-lime-200">
                            <th className="border-2 border-gray-300 px-4 py-2">Name</th>
                            <th className="border-2 border-gray-300 px-4 py-2">Item Id</th>
                            <th className="border-2 border-gray-300 px-4 py-2">Price</th>
                            <th className="border-2 border-gray-300 px-4 py-2">Attribute</th>
                            <th className="border-2 border-gray-300 px-4 py-2">Image</th>
                            <th className="border-2 border-gray-300 px-4 py-2">Intro</th>
                            <th className="border-2 border-gray-300 px-4 py-2"></th>
                        </tr>
                    </thead>
                    <tbody>
                        {itemList.map((item: ItemDTO) => (
                            <tr key={item.itemId}>
                                <td className="border-2 p-2 bg-yellow-50">{productName}</td>
                                <td className="underline border-2 p-2 bg-yellow-50"><Link href={`/item/${item.itemId}`}>{item.itemId}</Link></td>
                                <td className="border-2 p-2 bg-yellow-50">{item.listPrice}</td>
                                <td className="border-2 p-2 bg-yellow-50">{item.attribute1}</td>
                                <td className="border-2 p-2 bg-yellow-50">
                                    {image ? (
                                        <div className="relative">
                                            {/* 先用普通img标签测试 */}
                                            <img
                                                src={image}
                                                alt="商品图片"
                                                width={60}
                                                height={60}
                                                className="object-cover rounded"
                                                onError={(e) => {
                                                    console.error('图片加载失败:', image); // 调试日志
                                                    e.currentTarget.style.display = 'none';
                                                    const fallback = e.currentTarget.nextElementSibling as HTMLElement;
                                                    if (fallback) {
                                                        fallback.style.display = 'flex';
                                                    }
                                                }}
                                                onLoad={() => {
                                                    console.log('图片加载成功:', image); // 调试日志
                                                }}
                                            />
                                            <div className="w-15 h-15 bg-gray-200 rounded flex items-center justify-center" style={{display: 'none'}}>
                                                <span className="text-gray-500 text-xs">加载失败</span>
                                            </div>
                                        </div>
                                    ) : (
                                        <div className="w-15 h-15 bg-gray-200 rounded flex items-center justify-center">
                                            <span className="text-gray-500 text-xs">No Image</span>
                                        </div>
                                    )}
                                </td>
                                <td className="border-2 p-2 bg-yellow-50">{text}</td>
                                <td className="border-2 p-2 bg-yellow-50">
                                    <button className="bg-black text-white p-2 rounded-lg font-bold cursor-pointer hover:bg-stone-600"
                                    onClick={() => handleAddToCart(item.itemId)}>Add to Cart</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <Footer />
        </div>
    )
}

export default Product;