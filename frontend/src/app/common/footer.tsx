import Link from "next/link";

export default function Footer() {
    return (
        <div className="self-end w-full bg-black h-fit text-white
        text-center p-6">
            <p>2025&copy; All Rights Reserved by <Link href="https://www.csu.edu.cn/">CSU</Link></p>
        </div>
    )
}