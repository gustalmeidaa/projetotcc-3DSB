import React, { useState } from "react";
import { motion } from 'framer-motion'

import { List, X } from 'phosphor-react'
import { Link, animateScroll as scroll, } from 'react-scroll'
import logoImg from '../assets/Try-on.png'
import twitter from '../assets/twitter-logo-4.png'
import youtube from '../assets/youtube-logo-5-2.png'
import insta from '../assets/insta.png'
import playstore from '../assets/playstore.png'

const Footer = () => {
    const [nav, setNav] = useState(false)
    const handleClick = () => setNav(!nav)
    const handleClose = () => setNav(!nav)
    return (
        <div className='w-screen h-[140px] z-10 bg-brown-100 drop-shadow-lg'>
            <div className="flex justify-around items-center w-full h-full">
                <div className="hidden sm:flex items-center">
                    <h1 className="text-lg text-little-black font-bold mr-4 sm:text-3xl pl-[100px] hover:text-black"><a href="">Try On</a></h1>
                    <a href=""><img className="w-[90px] h-[63px]" src={logoImg} alt="" /></a>
                    <div className="">
                        <h3 className="underline text-lg text-little-black hover:text-black">
                            contato@example.com

                        </h3>
                        <h3 className="text-lg text-little-black hover:text-black">
                            (99) 99999-9999
                        </h3>
                    </div>
                </div>
                <div className="hidden sm:flex">
                    <a href="">
                        <div className="w-[80px] h-[80px] rounded-full bg-brown-500 hover:shadow-lg transition duration-500">
                            <div className="p-[20px]">
                                <img src={twitter} className="w-[40px] h-[40px]" />
                            </div>
                        </div>
                    </a>

                    <a href="">
                        <div className="w-[80px] h-[80px] rounded-full bg-brown-500 hover:shadow-lg transition duration-500">
                            <div className="p-[20px]">
                                <img src={youtube} className="w-screen h-[40px]" />
                            </div>
                        </div>
                    </a>

                    <a href="">
                        <div className="w-[80px] h-[80px] rounded-full bg-brown-500 hover:shadow-lg transition duration-500">
                            <div className="p-[20px]">
                                <img src={insta} className="w-[40px] h-[40px]" />
                            </div>
                        </div>
                    </a>
                </div>
                <a href="">
                    <div className="flex items-center w-[320px] h-[88px] border-solid border-2 border-black rounded-xl hover:bg-brown-300 transition duration-500 shadow-lg">
                        <img src={playstore} className="h-[80px] w-[80px]" />
                        <h1 className="text-center text-[20px] pl-[20px]">Baixe nosso app</h1>
                    </div>
                </a>
            </div >
        </div >
    )
}

export default Footer;