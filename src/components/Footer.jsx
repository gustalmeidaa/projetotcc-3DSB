import React, { useState } from "react";
import { motion } from 'framer-motion'

import { List, X } from 'phosphor-react'
import { Link, animateScroll as scroll, } from 'react-scroll'
import logoImg from '../assets/Try-on.png'
import twitter from '../assets/twitter-logo-4.png'
import youtube from '../assets/youtube-logo-5-2.png'
import insta from '../assets/insta.png'

const Footer = () => {
    const [nav, setNav] = useState(false)
    const handleClick = () => setNav(!nav)
    const handleClose = () => setNav(!nav)
    return (
        <div className='w-screen h-[140px] z-10 bg-brown-100 drop-shadow-lg'>
            <div className="flex justify-around items-center w-full h-full">
                <div className="md:hidden" onClick={handleClick}>
                    {!nav ? <List className="w-[40px] h-[40px]" /> : <X className="w-[40px] h-[40px]" />}
                </div>
                <motion.div className="flex items-center">
                    <h1 className="text-lg font-bold mr-4 sm:text-3xl pl-[100px]"><a href="">Try On</a></h1>
                    <a href=""><img className="w-[90px] h-[63px]" src={logoImg} alt="" /></a>
                    <div className="">
                        <h3 className="underline text-lg">
                            contato@example.com

                        </h3>
                        <h3 className="text-lg">
                            (99) 99999-9999
                        </h3>
                    </div>
                </motion.div>
                <div className="flex">
                    <div className="w-[80px] h-[80px] rounded-full bg-brown-500 place-items-center">
                        <div className="">
                            <img src={twitter} className="w-[40px] h-[40px]" />
                        </div>
                    </div>
                    <div className="w-[80px] h-[80px] rounded-full bg-brown-500">
                        <img src={youtube} className="w-[40px] h-[40px]" />
                    </div>
                    <div className="w-[80px] h-[80px] rounded-full bg-brown-500">
                        <img src={insta} className="w-[40px] h-[40px]" />
                    </div>
                </div>
            </div>


            <ul className={!nav ? 'hidden' : 'absolute bg-brown-500 w-full px-8'}>
                <li className="border-b-2 border-zinc-600 w-full" > <a href="">carnes</a></li >
                <li className="border-b-2 border-zinc-600 w-full"><a href="">vegetais</a></li>
                <li className="border-b-2 border-zinc-600 w-full"><a href="">aves</a></li>
                <li className="border-b-2 border-zinc-600 w-full"><a href="">bolos</a></li>
                <li className="border-b-2 border-zinc-600 w-full"><a href="">saladas</a></li>
                <li className="border-b-2 border-zinc-600 w-full"><a href="">mais</a></li>
            </ul >


        </div >
    )
}

export default Footer;