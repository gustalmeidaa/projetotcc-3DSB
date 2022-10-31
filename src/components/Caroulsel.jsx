import React, { useState, useEffect, useRef } from "react";

import receita from '../assets/receita.jpeg'
import receita2 from '../assets/receita2.jpeg'
import receita3 from '../assets/receita3.webp'
import { motion } from 'framer-motion'

const images = [receita, receita2, receita3, receita, receita2, receita3, receita, receita2, receita3]

function Carousel() {
    const carrossel = useRef();
    const [width, setWidth] = useState(0)

    useEffect(() => {
        console.log(carrossel.current?.scrollWidth, carrossel.current?.offsetWidth)
        setWidth(carrossel.current?.scrollWidth - carrossel.current?.offsetWidth)
    }, [])

    return (
        <motion.div ref={carrossel} className="flex justify-center cursor-grab overflow-hidden pt-[40px]" whileTap={{ cursor: "grabbing" }}>
            <motion.div className="flex"
                drag="x"
                dragConstraints={{ right: 0, left: -width }}
                initial={{ x: 100 }}
                animate={{ x: 0 }}
                transition={{ duration: 1 }}
            >
                {images.map(image => (
                    <motion.div className="h-[320px] w-[220px] sm:min-h-[320px] sm:min-w-[400px] p-[14px]" key={image}>
                        <img src={image} alt="imagens" className="w-[220px] h-[300px] sm:w-full sm:h-[240px] rounded-xl pointer-events-none shadow-xl" />
                    </motion.div>
                ))}
            </motion.div>
        </motion.div>
    )
}

export default Carousel;