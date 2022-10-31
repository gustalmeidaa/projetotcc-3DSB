import React from "react";
import receita from '../assets/receita.jpeg'
import receita2 from '../assets/receita2.jpeg'
import receita3 from '../assets/receita3.webp'

const images = [receita, receita2, receita3, receita, receita2, receita3, receita, receita2, receita3]
const Content = () => {
    return (
        <div>
            <h2 className="text-2xl font-bold text-center sm:text-left pt-[45px] pl-[22px]">recomendados da semana</h2>
            <div className="flex">
                <div className="pt-[20px] p-[3px] sm:p-[2px] ex:p-[10px]">
                    <div className="w-[130px] h-[170px] sm:w-[210px] sm:h-[280px] rounded-2xl place-content-center px-[30px] pt-[15px] sm:pt-[30px] shadow-2xl bg-brown-300">
                        <img src={receita} className="rounded-full w-[70px] h-[70px] sm:w-[150px] sm:h-[150px] shadow-lg" />
                        <p className="text-[14px] sm:text-[20px] text-center pt-[10px]">Receita de chocolate</p>
                    </div>
                </div>
                <div className="pt-[20px] p-[3px] sm:p-[2px] ex:p-[10px]">
                    <div className="w-[130px] h-[170px] sm:w-[210px] sm:h-[280px] rounded-2xl place-content-center px-[30px] pt-[15px] sm:pt-[30px] shadow-2xl bg-brown-300">
                        <img src={receita2} className="rounded-full w-[70px] h-[70px] sm:w-[150px] sm:h-[150px] shadow-lg" />
                        <p className="text-[14px] sm:text-[20px] text-center pt-[10px]">Receita de ?</p>
                    </div>
                </div>
                <div className="pt-[20px] p-[3px] sm:p-[2px] ex:p-[10px]">
                    <div className="w-[130px] h-[170px] sm:w-[210px] sm:h-[280px] rounded-2xl place-content-center px-[30px] pt-[15px] sm:pt-[30px] shadow-2xl bg-brown-300">
                        <img src={receita3} className="rounded-full w-[70px] h-[70px] sm:w-[150px] sm:h-[150px] shadow-lg" />
                        <p className="text-[14px] sm:text-[20px] text-center pt-[10px]">Receita de banana</p>
                    </div>
                </div>

            </div>
        </div >
    )
}

export default Content;