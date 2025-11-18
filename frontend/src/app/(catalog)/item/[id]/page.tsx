"use client";
import { handleAddToCart } from "@/app/actions";
import Footer from "@/app/common/footer";
import Header from "@/app/common/header";
import { backendUrl } from "@/app/config";
import { parseDescription } from "@/app/utils";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";
import { Toaster } from "react-hot-toast";
import { ItemDTO, fetchItem } from "../../product-service";


export default function Item() {
    const {id} = useParams();
    const [productName, setProductName] = useState<string>();
    const [itemId, setItemId] = useState('');
    const [attribute1, setAttribute1] = useState<string>();
    const [quantity, setQuantity] = useState<number>();
    const [listPrice, setListPrice] = useState<number>();
    const [image, setImage] = useState('');
    const [text, setText] = useState('');
    useEffect(() => {
      if (!id) return;
      fetchItem(String(id))
        .then((data: ItemDTO) => {
          const {productName, itemId, attribute1, quantity, description, listPrice} = data;
          setProductName(productName);
          setItemId(itemId);
          setAttribute1(attribute1);
          setQuantity(quantity);
          setListPrice(Number(listPrice));
          setImage(parseDescription(description).image);
          setText(parseDescription(description).text);
        });
    }, [id]);
    return (
        <div className="flex flex-col h-screen">
            <Toaster />
            <Header></Header>
            <div className="flex-1 flex flex-col items-center justify-center">
                <ul className="border-5 border-rose-400 rounded-lg p-6 bg-orange-200">
                    <li><img src={`${backendUrl}`+ image} alt="商品图片" width="60"/></li>
                    <li className="italic text-rose-500 underline">{text}</li>
                    <li>{attribute1}</li>
                    <li>{productName}</li>
                    <li>{itemId}</li>
                    <li className="text-rose-500 font-bold">{"$" + listPrice}</li>
                    <li>{quantity + " in stock"}</li>
                    <li className="mt-6 bg-black w-fit text-white font-bold p-2 rounded-lg hover:bg-neutral-700 cursor-pointer"
                    onClick={() => handleAddToCart(itemId)}>Add to Chart</li>
                 </ul>
            </div>
            <Footer></Footer>
        </div>
    )
}