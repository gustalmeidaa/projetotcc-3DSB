import React from "react";
import { Egg, Fish, Brandy } from 'phosphor-react'

const FooterMobile = () => {
    return (
        <div className="flex justify-center">
            <div className="p-[36px]"><a href=""><Egg className="w-[50px] h-[50px]" /></a></div>
            <div className="p-[36px]"><a href=""><Fish className="w-[50px] h-[50px]" /></a></div>
            <div className="p-[36px]"><a href=""><Brandy className="w-[50px] h-[50px]" /></a></div>
        </div>
    )
}

export default FooterMobile;