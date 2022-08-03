using FireSharp;
using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using WebsiteTCC.Models;

namespace WebsiteTCC.Controllers
{
    public class HomeController : Controller
    {
        IFirebaseClient cliente;
        public HomeController()
        {
            IFirebaseConfig conf = new FirebaseConfig()
            {
                AuthSecret = "1BMaJSOs6XlJViTkCgN9qikBSWwLKJsrnGNzohYP",
                BasePath = "https://testefirebase-faa05-default-rtdb.firebaseio.com/"
            };

            cliente = new FirebaseClient(conf);
        }

        

        private readonly ILogger<HomeController> _logger;

        public IActionResult Index()
        {
            return View();
        }

        public IActionResult Receitas()
        {
            List<Receitas> listaReceitas = new List<Receitas>();

            FirebaseResponse response = cliente.Get("receitas/1");
            if (response.StatusCode == System.Net.HttpStatusCode.OK)
            {
                Receitas r = JsonConvert.DeserializeObject<Receitas>(response.Body);
                listaReceitas.Add(r);
            }

            return View(listaReceitas);
        }
        public IActionResult Login()
        {
            return View();
        }
        public IActionResult Cadastro()
        {
            return View();
        }

        public IActionResult PesquisarReceita()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}
