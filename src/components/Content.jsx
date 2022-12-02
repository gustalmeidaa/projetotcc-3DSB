import React, { useRef } from "react";
import receita from '../assets/brownie.webp'
import receita2 from '../assets/alfajor.jpg'
import receita3 from '../assets/missoshiro.jfif'
import { initializeApp } from 'firebase/app'
import { collection, Firestore, getDocs, getFirestore, query, where } from 'firebase/firestore'
import { useEffect, useState } from 'react';


const images = [receita, receita2, receita3, receita, receita2, receita3, receita, receita2, receita3]

const firebaseApp = initializeApp({
    apiKey: "AIzaSyBVAos5VszvTSW3X8fWdV3xQBHBNvydA84",
    authDomain: "projetotcc-9f4ad.firebaseapp.com",
    projectId: "projetotcc-9f4ad",
});

const Content = () => {
    const [browniee, setBrowniee] = useState([]);
    const [alfajor, setAlfajor] = useState([]);
    const [misso, setMisso] = useState([]);

    const db = getFirestore();
    const brownie = query(collection(db, "receitas"), where("nome", "==", "Bolo Chifon de limão"));
    const alfajors = query(collection(db, "receitas"), where("nome", "==", "Alfajor de leite"));
    const missos = query(collection(db, "receitas"), where("nome", "==", "Missoshiro"));

    useEffect(() => {
        const getBrownie = async () => {
            const data = await getDocs(brownie)
            setBrowniee(data.docs.map((doc) => ({ ...doc.data(), id: doc.id })));
        };
        getBrownie();
        const getAlfajor = async () => {
            const data = await getDocs(alfajors)
            setAlfajor(data.docs.map((doc) => ({ ...doc.data(), id: doc.id })));
        };
        getAlfajor();
        const getMisso = async () => {
            const data = await getDocs(missos)
            setMisso(data.docs.map((doc) => ({ ...doc.data(), id: doc.id })));
        };
        getMisso();
    }, [])

    return (
        <div>
            <h2 className="text-2xl font-bold text-center sm:text-left pt-[45px] pl-[22px]">recomendados da semana</h2>
            <div className="flex">
                <a href="">
                    <div className="pt-[20px] p-[3px] sm:p-[2px] ex:p-[10px]">
                        <div className="w-[130px] h-[170px] sm:w-[210px] sm:h-[280px] rounded-2xl place-content-center px-[30px] pt-[15px] sm:pt-[30px] shadow-2xl bg-brown-300  hover:bg-brown-500 transition duration-500">
                            <img src={receita} className="rounded-full w-[70px] h-[70px] sm:w-[150px] sm:h-[150px] shadow-lg hover:scale-105 transition duration-500" />
                            {browniee.map((browniee) => {
                                return (
                                    <p className="text-[14px] sm:text-[18px] text-center pt-[10px]">{browniee.nome}</p>
                                )
                            })}
                        </div>
                    </div>
                </a>
                <a href="">
                    <div className="pt-[20px] p-[3px] sm:p-[2px] ex:p-[10px]">
                        <div className="w-[130px] h-[170px] sm:w-[210px] sm:h-[280px] rounded-2xl place-content-center px-[30px] pt-[15px] sm:pt-[30px] shadow-2xl bg-brown-300 hover:bg-brown-500 transition duration-500">
                            <img src={receita2} className="rounded-full w-[70px] h-[70px] sm:w-[150px] sm:h-[150px] shadow-lg hover:scale-105 transition duration-500" />
                            {alfajor.map((alfajor) => {
                                return (
                                    <p className="text-[14px] sm:text-[18px] text-center pt-[10px]">{alfajor.nome}</p>
                                )
                            })}
                        </div>
                    </div>
                </a>
                <a href="">
                    <div className="pt-[20px] p-[3px] sm:p-[2px] ex:p-[10px]">
                        <div className="w-[130px] h-[170px] sm:w-[210px] sm:h-[280px] rounded-2xl place-content-center px-[30px] pt-[15px] sm:pt-[30px] shadow-2xl bg-brown-300 hover:bg-brown-500 transition duration-500">
                            <img src={receita3} className="rounded-full w-[70px] h-[70px] sm:w-[150px] sm:h-[150px] shadow-lg hover:scale-105 transition duration-500" />
                            {misso.map((misso) => {
                                return (
                                    <p className="text-[14px] sm:text-[18px] text-center pt-[10px]">{misso.nome}</p>
                                )
                            })}
                        </div>
                    </div>
                </a>

            </div>
        </div >
    )
}

export default Content;