"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Dialog, DialogContent, DialogHeader, DialogTitle , DialogTrigger} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { springBoot, backendUrl } from "../config";
import { useRouter } from "next/navigation";

export function SearchDialog() {
  const [searchTerm, setSearchTerm] = useState("");
  const [suggestion, setSuggestion] = useState([]);
  const router = useRouter();

  const handleSearch = async(keyword:string) => {
    const resp = await fetch(`${backendUrl}/catalog/search/${keyword}`);
    let data;
    if (resp.ok) {
      data = await resp.json();
    }
    const nextSuggestion = data;
    setSuggestion(nextSuggestion);
  };
  const checkSearchPage = (keyword:string) => {
    if (keyword && keyword.length > 0) {
      router.push(`/search/${encodeURIComponent(keyword)}`);
    }
  }
  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      checkSearchPage(searchTerm);
    }
  }
  function showSuggestion(suggestion: Product[]) { 
    if (searchTerm.length > 0 && suggestion && suggestion.length > 0) {
      return suggestion.map((item: Product) => (
                    <li
                       className="cursor-pointer hover:bg-yellow-300 p-2"
                       key={item.productId}
                      onClick={() => { 
                        router.push(`/product/${item.productId}`)
                      }}>{item.name}</li>
                  ))
    } else {
      return <div>无搜索结果</div>
    }
  }

  return (
        <Dialog>
            <DialogTrigger asChild>
                <Button variant="outline">search</Button>        
            </DialogTrigger>
            <DialogContent className="sm:max-w-[425px]">
            <DialogHeader>
                <DialogTitle>搜索内容</DialogTitle>
            </DialogHeader>
            
            <div className="grid gap-4 py-4">
              <Input
                placeholder="输入关键词..."
                onChange={(e) => {
                  console.log(e.target.value)
                  setSearchTerm(e.target.value)
                  handleSearch(e.target.value);
                }}
                onKeyDown={handleKeyDown} />
                <Button onClick={() => checkSearchPage(searchTerm)}>
                    开始搜索
                </Button>
                <div className="grid gap-4 py-4">
                    <ol>
                      {
                        showSuggestion(suggestion)
                      }
                    </ol>
                </div>
            </div>
            </DialogContent>
        </Dialog>
  );
}



type Product = {
  productId: string,
  categoryId: string,
  name: string,
  description: string,
}