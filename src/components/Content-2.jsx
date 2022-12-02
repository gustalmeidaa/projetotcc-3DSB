import React from "react";
import receita from '../assets/bombom-cereja.jpg'
import receita2 from '../assets/petit.webp'
import receita3 from '../assets/palha-italiana.jpg'
import { initializeApp } from 'firebase/app'
import { collection, Firestore, getDocs, getFirestore, query, where } from 'firebase/firestore'
import { useEffect, useState } from 'react';


const images = [receita, receita2, receita3]
const firebaseApp = initializeApp({
    apiKey: "AIzaSyBVAos5VszvTSW3X8fWdV3xQBHBNvydA84",
    authDomain: "projetotcc-9f4ad.firebaseapp.com",
    projectId: "projetotcc-9f4ad",
});

const Content2 = () => {
    const [bombom, setBombom] = useState([]);
    const [petit, setPetit] = useState([]);
    const [palhaItalia, setPalhaItaliana] = useState([]);

    const db = getFirestore();
    const bomboms = query(collection(db, "receitas"), where("nome", "==", "Bombom de cerejas"));
    const petitG = query(collection(db, "receitas"), where("nome", "==", "Petit Gâteau brasileito"));
    const palha = query(collection(db, "receitas"), where("nome", "==", "Palha Italiana Recheada"));

    useEffect(() => {
        const getBrownie = async () => {
            const data = await getDocs(bomboms)
            setBombom(data.docs.map((doc) => ({ ...doc.data(), id: doc.id })));
        };
        getBrownie();
        const getPetit = async () => {
            const data = await getDocs(petitG)
            setPetit(data.docs.map((doc) => ({ ...doc.data(), id: doc.id })));
        };
        getPetit();
        const getPalha = async () => {
            const data = await getDocs(palha)
            setPalhaItaliana(data.docs.map((doc) => ({ ...doc.data(), id: doc.id })));
        };
        getPalha();
    }, [])

    return (
        <div className="invisible sm:visible">
            <h2 className="text-2xl font-bold text-center sm:text-left pt-[45px] pl-[55px]">receitas com chocolate</h2>
            <div className="flex">
                <a href="">
                    <div className="pt-[20px] p-[3px] sm:p-[2px] ex:p-[40px] justify-items-center">
                        <div className="w-[130px] h-[170px] sm:w-[220px] sm:h-[360px] lg:w-[320px] lg:h-[460px] rounded-2xl content-center px-[30px] pt-[15px] sm:pt-[30px] border-solid border-2 border-black shadow-lg bg-transparent  hover:bg-brown-500 transition duration-500">
                            <img src={receita} className="rounded-full w-[150px] h-[150px] lg:w-[255px] lg:h-[255px] border-solid border-2 border-black shadow-lg hover:scale-105 transition duration-500" />
                            {bombom.map((bombom) => {
                                return (
                                    <p className="text-[14px] sm:text-[20px] text-center pt-[70px] lg:pt-[90px] font-bold">{bombom.nome}</p>
                                )
                            })}
                        </div>
                    </div>
                </a>

                <a href="">
                    <div className="pt-[20px] p-[3px] sm:p-[2px] ex:p-[40px]">
                        <div className="w-[130px] h-[170px] sm:w-[220px] sm:h-[360px] lg:w-[320px] lg:h-[460px] rounded-2xl content-center px-[30px] pt-[15px] sm:pt-[30px] border-solid border-2 border-black shadow-lg bg-transparent  hover:bg-brown-500 transition duration-500">
                            <img src={receita2} className="rounded-full w-[150px] h-[150px] lg:w-[255px] lg:h-[255px] border-solid border-2 border-black shadow-lg hover:scale-105 transition duration-500" />
                            {petit.map((petit) => {
                                return (
                                    <p className="text-[14px] sm:text-[20px] text-center pt-[70px] lg:pt-[90px] font-bold">{petit.nome}</p>
                                )
                            })}
                        </div>
                    </div>
                </a>

                <a href="">
                    <div className="pt-[20px] p-[3px] sm:p-[2px] ex:p-[40px]">
                        <div className="w-[130px] h-[170px] sm:w-[220px] sm:h-[360px] lg:w-[320px] lg:h-[460px] rounded-2xl content-center px-[30px] pt-[15px] sm:pt-[30px] border-solid border-2 border-black shadow-lg bg-transparent  hover:bg-brown-500 transition duration-500">
                            <img src={receita3} className="rounded-full w-[150px] h-[150px] lg:w-[255px] lg:h-[255px] border-solid border-2 border-black shadow-lg hover:scale-105 transition duration-500" />
                            {palhaItalia.map((palhaItalia) => {
                                return (
                                    <p className="text-[14px] sm:text-[20px] text-center pt-[70px] lg:pt-[90px] font-bold">{palhaItalia.nome}</p>
                                )
                            })}
                        </div>
                    </div>
                </a>
            </div>
        </div>
    )
}

export default Content2;