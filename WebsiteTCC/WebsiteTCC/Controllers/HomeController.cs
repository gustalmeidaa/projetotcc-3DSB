﻿using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
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
        private readonly ILogger<HomeController> _logger;

        public HomeController(ILogger<HomeController> logger)
        {
            _logger = logger;
        }

        public IActionResult Index()
        {
            return View();
        }

        public IActionResult Receitas()
        {
            return View();
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

        public IActionResult Restricoes()
        {
            return View();
        }

        public IActionResult PerfilUsuario()
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
