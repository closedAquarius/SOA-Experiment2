import Header from "@/app/common/header";
import Footer from "@/app/common/footer";
import Link from "next/link";
import { backendUrl } from "@/app/config";
export default async function Category({ params }:
    {
        params: Promise<{ id: string }>
    }) {
    const id = (await params).id;
    const data = await getData(id);
    const category = data;
    const productList = data.productList;
    return (
        <div className="flex flex-col h-screen">
            <Header />
            <div className="flex-1 flex flex-col justify-center items-center">
                <p className="font-bold mb-5 text-2xl italic">{category.categoryName}</p>
                <table className="border-4 border-zinc-200">
                    <thead>
                        <tr className="bg-lime-200">
                            <th className="border-2 border-gray-300 px-4 py-2">Name</th>
                            <th className="border-2 border-gray-300 px-4 py-2">Product Id</th>
                            <th className="border-2 border-gray-300 px-4 py-2">Category Id</th>
                        </tr>
                    </thead>
                    <tbody>
                        {productList.map((product:Product) => (
                            <tr key={product.productId}>
                                <td className="border-2 p-2 bg-yellow-50">{product.name}</td>
                                <td className="underline border-2 p-2 bg-yellow-50"><Link href={`/product/${product.productId}`}>{product.productId}</Link></td>
                                <td className="border-2 p-2 bg-yellow-50">{product.categoryId}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <Footer />
        </div>
        

    )
}

type Product = {
    productId: string,
    categoryId: string,
    name: string,
    description:string,
}


async function getData(id: string) {
    const res = await fetch(`${backendUrl}/catalog/category/${id}`);
    if (!res.ok) throw new Error("获取数据失败")
    return res.json();
}