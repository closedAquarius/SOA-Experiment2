"use client"
import { CarouselDemo } from "@/app/main/carousel"
import Link from "next/link"
import { useEffect } from "react";
import tippy from 'tippy.js';
import 'tippy.js/dist/tippy.css';
import 'tippy.js/themes/light.css';
import 'tippy.js/animations/scale.css';
export default function Hero() {
    useEffect(()=>{
        tippy('[data-tippy-content]', {
          theme: 'light',
          animation: 'scale',
        });
    }, []);
    return (
        <div className="flex-1 flex gap-30 p-10 m-10">
            <div className="flex-1 flex flex-col gap-5 bg-emerald-200 p-10 rounded-e-full items-start shadow-2xl">
                <div>
                    <Link href="category/fish"><img src="/images/fish_icon.gif"/></Link>
                    <span className="underline decoration-sky-500">Saltwater, Freshwater</span>
                </div>
                <div>
                    <Link href="category/dogs"><img src="/images/dogs_icon.gif" /></Link>
                    <span className="underline decoration-sky-500">Various Breeds</span>
                </div>
                <div>
                    <Link href="category/cats"><img src="/images/cats_icon.gif" /></Link>
                    <span className="underline decoration-sky-500">Various Breeds, Exotic Varieties</span>
                </div>
                <div>
                    <Link href="category/reptiles"><img src="/images/reptiles_icon.gif" /></Link>
                    <span className="underline decoration-sky-500">Lizards, Turtles, Snakes</span>
                </div>
                <div>
                    <Link href="category/birds"><img src="/images/birds_icon.gif" /></Link>
                    <span className="underline decoration-sky-500">Exotic Varieties</span>
                </div>
            </div>
            <div className="flex-1 self-center">
                <map id="estoremap" name="estoremap">
                    <Link href="/category/birds">
                        <area alt="Birds" coords="72,2,280,250"
                            shape="RECT" data-tippy-content="Birds" />
                    </Link>
                    <Link href="/category/fish">
                        <area alt="Fish" coords="2,180,72,250"
                            shape="RECT" data-tippy-content="Fish"/>
                    </Link>
                    <Link href="/category/dogs">
                        <area alt="Dogs" coords="60,250,130,320"
                            shape="RECT" data-tippy-content="Dogs" />
                    </Link>
                    <Link href="/category/reptiles">
                        <area alt="Reptiles" coords="140,270,210,340"
                            shape="RECT" data-tippy-content="Reptiles"/>
                    </Link>
                    <Link href="/category/cats">
                        <area alt="Cats" coords="225,240,295,310"
                            shape="RECT" data-tippy-content="Cats"/>
                    </Link>
                    <Link href="/category/birds">
                        <area alt="Birds" coords="280,180,350,250"
                        shape="RECT" data-tippy-content="Birds"/>
                    </Link>
                </map>
                <img height={355} src="/images/splash.gif" useMap="#estoremap" width={350} />
            </div>
            <div className="flex-1 self-center">
                <CarouselDemo />
            </div>
        </div>
    )
}