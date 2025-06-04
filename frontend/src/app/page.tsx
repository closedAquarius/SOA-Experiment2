'use client'
import Image from "next/image";
import Link from "next/link";
import styles from "./style.module.css";
export default function Home() {
  return (
    <div className={styles.background}>
      <nav>
        <div className={ styles.header}>
          <Image src="/images/logo.png" alt="logo" width={100} height={100}
             className="rounded-xl"></Image>
          <div className="flex gap-4 items-center p-12 mr-12">
            <a href="/main" className={styles.link}>Home</a>
            <Link href="/" className={styles.link}>About</Link>
            <Link href="/" className={styles.link}>Contact</Link>
            <Link href="/login" className={styles.link}>Sign In</Link>
            <Link href="/register" className={styles.link}>Sign UP</Link>
          </div>
        </div>
      </nav>
      <div className={ styles.shell}>
        <div className={styles.box}>
            <img src="images/index-bird.jpg" />
            <span>bird</span>
        </div>
        <div className={styles.box}>
            <img src="images/index-cat.jpg" />
            <span>cat</span>
        </div>
        <div className={styles.box}>
            <img src="images/index-dog.jpg" />
            <span>dog</span>
        </div>
        <div className={styles.box}>
            <img src="images/index-fish.jpg" />
            <span>fish</span>
        </div>
        <div className={styles.box}>
            <img src="images/index-reptiles.jpg" />
            <span>reptiles</span>
        </div>
      </div>
    </div>
  );
}
