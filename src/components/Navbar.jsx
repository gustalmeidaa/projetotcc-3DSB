import React, { useState } from "react";
import { motion } from 'framer-motion'

import { List, X } from 'phosphor-react'
import { Link, animateScroll as scroll, } from 'react-scroll'
import logoImg from '../assets/Try-on.png'

const Navbar = () => {

    const [nav, setNav] = useState(false)
    const handleClick = () => setNav(!nav)
    const handleClose = () => setNav(!nav)
    return (
        <div className='w-screen h-[80px] z-10 bg-brown-500 drop-shadow-lg'>
            <div className="flex justify-around items-center w-full h-full">
                <div className="md:hidden" onClick={handleClick}>
                    {!nav ? <List className="w-[40px] h-[40px]" /> : <X className="w-[40px] h-[40px]" />}
                </div>
                <motion.div className="flex items-center">
                    <h1 className="text-lg font-bold mr-4 sm:text-3xl pl-[100px]"><a href="">Try On</a></h1>
                    <a href=""><img className="w-[90px] h-[63px]" src={logoImg} alt="" /></a>
                    <div className="flex items-center">
                        <ul className="hidden md:flex text-little-black text-lg">
                            <li className="hover:text-black transition"><a href="">vegetais</a></li>
                            <li className="hover:text-black transition"><a href="">carnes</a></li>
                            <li className="hover:text-black transition"><a href="">aves</a></li>
                            <li className="hover:text-black transition"><a href="">bolos</a></li>
                            <li className="hover:text-black transition"><a href="">saladas</a></li>
                            <li className="hover:text-black transition"><a href="">mais</a></li>
                        </ul>
                    </div>
                </motion.div>
                <div className="hidden ex:flex">
                    <form method="GET" className="flex">
                        <div class="relative text-brown-700 focus-within:text-brown-700">
                            <span class="absolute inset-y-0 left-0 flex items-center">
                                <button type="submit" class="p-1 focus:outline-none focus:shadow-outline">
                                    <svg fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" viewBox="0 0 24 24" class="w-6 h-6"><path d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg>
                                </button>
                            </span>
                            <input type="search" name="q" className="py-2 text-sm text-right placeholder-brown-700 text-brown-700 bg-brown-100 rounded-md focus:outline-2 outline-brown-700 focus:text-brown-700" placeholder="procurar receita" autocomplete="off" />
                        </div>
                    </form>
                </div>
            </div>


            <ul className={!nav ? 'hidden' : 'absolute bg-brown-500 w-full px-8'}>
                < li className="border-b-2 border-zinc-600 w-full" > <a href="">carnes</a></li >
                <li className="border-b-2 border-zinc-600 w-full"><a href="">vegetais</a></li>
                <li className="border-b-2 border-zinc-600 w-full"><a href="">aves</a></li>
                <li className="border-b-2 border-zinc-600 w-full"><a href="">bolos</a></li>
                <li className="border-b-2 border-zinc-600 w-full"><a href="">saladas</a></li>
                <li className="border-b-2 border-zinc-600 w-full"><a href="">mais</a></li>
            </ul >


        </div >
    )
}

export default Navbar;