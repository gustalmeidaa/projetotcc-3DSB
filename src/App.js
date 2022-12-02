import Caroulsel from './components/Caroulsel';
import Navbar from './components/Navbar'
import Content from './components/Content';
import Content2 from './components/Content-2';
import Footer from './components/Footer';
import FooterMobile from './components/FooterMobile';

function App() {
  return (
    <div className="w-screen h-screen bg-brown-300 overflow-x-hidden">
      <header>
        <Navbar />
      </header>
      <Caroulsel />
      <div className="h-[350px] sm:h-[430px] flex justify-center bg-brown-100 drop-shadow-lg">
        <Content />
      </div>
      <div className="h-[730px]  justify-center hidden sm:flex">
        <Content2 />
      </div>
      <div className="h-[120px] justify-center flex sm:hidden">
        <FooterMobile />
      </div>
      <footer>
        <Footer />
      </footer>
    </div>

  );
}

export default App;
