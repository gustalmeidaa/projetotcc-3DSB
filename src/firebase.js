import { initializeApp, firebase } from "firebase/app";
import { getFirestore } from "@firebase/firestore";

const firebaseConfig = {
    apiKey: "AIzaSyBVAos5VszvTSW3X8fWdV3xQBHBNvydA84",
    authDomain: "projetotcc-9f4ad.firebaseapp.com",
    projectId: "projetotcc-9f4ad",
    storageBucket: "projetotcc-9f4ad.appspot.com",
    messagingSenderId: "220169292567",
    appId: "1:220169292567:web:bc14bf1fcd2007db6cc552"
};

const app = initializeApp(firebaseConfig);
export const firestore = getFirestore(app);

