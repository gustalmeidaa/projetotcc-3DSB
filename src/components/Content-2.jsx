import React from "react";
import receita from '../assets/receita.jpeg'
import receita2 from '../assets/receita2.jpeg'
import receita3 from '../assets/receita3.webp'

const images = [receita, receita2, receita3, receita, receita2, receita3, receita, receita2, receita3]

const Content2 = () => {
    return (
        <div className="invisible sm:visible">
            <h2 className="text-2xl font-bold text-center sm:text-left pt-[45px] pl-[55px]">receitas com chocolate</h2>
            <div className="flex">
                <div className="pt-[20px] p-[3px] sm:p-[2px] ex:p-[40px] justify-items-center">
                    <div className="w-[130px] h-[170px] sm:w-[320px] sm:h-[460px] rounded-2xl content-center px-[30px] pt-[15px] sm:pt-[30px] border-solid border-2 border-black shadow-lg bg-transparent">
                        <img src={receita} className="rounded-full w-[70px] h-[70px] sm:w-[255px] sm:h-[255px] border-solid border-2 border-black shadow-lg" />
                        <p className="text-[14px] sm:text-[20px] text-center pt-[110px] font-bold">Receita de chocolate</p>
                    </div>
                </div>
                <div className="pt-[20px] p-[3px] sm:p-[2px] ex:p-[40px]">
                    <div className="w-[130px] h-[170px] sm:w-[320px] sm:h-[460px] rounded-2xl content-center px-[30px] pt-[15px] sm:pt-[30px] border-solid border-2 border-black shadow-lg bg-transparent">
                        <img src={receita2} className="rounded-full w-[70px] h-[70px] sm:w-[255px] sm:h-[255px] border-solid border-2 border-black shadow-lg" />
                        <p className="text-[14px] sm:text-[20px] text-center pt-[110px] font-bold">Receita de chocolate</p>
                    </div>
                </div>
                <div className="pt-[20px] p-[3px] sm:p-[2px] ex:p-[40px]">
                    <div className="w-[130px] h-[170px] sm:w-[320px] sm:h-[460px] rounded-2xl content-center px-[30px] pt-[15px] sm:pt-[30px] border-solid border-2 border-black shadow-lg bg-transparent">
                        <img src={receita3} className="rounded-full w-[70px] h-[70px] sm:w-[255px] sm:h-[255px] border-solid border-2 border-black shadow-lg" />
                        <p className="text-[14px] sm:text-[20px] text-center pt-[110px] font-bold">Receita de chocolate</p>
                    </div>
                </div>

            </div>
        </div >
    )
}

export default Content2;