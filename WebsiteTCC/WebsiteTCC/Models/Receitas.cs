using FireSharp.Config;
using FireSharp.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebsiteTCC.Models
{
    public class Receitas
    {
        public string nome;
        //public string secao;
        //public string conteudo;
        public string[] ingredientes;
        public string[] modoPreparo;


        public string Nome
        {
            get => nome;
            set => nome = value;
        }
        //public string Secao
        //{
        //    get => secao;
        //    set => secao = value;
        //}
        //public string Conteudo
        //{
        //    get => conteudo;
        //    set => conteudo = value;
        //}
        public string[] Ingredientes
        {
            get => ingredientes;
            set => ingredientes = value;
        }
        public string[] ModoPreparo
        {
            get => modoPreparo;
            set => modoPreparo = value;
        }

    }
}
