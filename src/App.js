import Caroulsel from './components/Caroulsel';
import Navbar from './components/Navbar'
import Content from './components/Content';
import Content2 from './components/Content-2';
import Footer from './components/Footer';

function App() {
  return (
    <div className="w-screen h-full bg-brown-300">
      <header>
        <Navbar />
      </header>
      <Caroulsel />
      <div className="h-[430px] w-screen flex justify-center bg-brown-100">
        <Content />
      </div>
      <div className="h-[730px] w-screen flex justify-center bg-brown-300">
        <Content2 />
      </div>
      <footer>
        <Footer />
      </footer>
    </div>

  );
}

export default App;
