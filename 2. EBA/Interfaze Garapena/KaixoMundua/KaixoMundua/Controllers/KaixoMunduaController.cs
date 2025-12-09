using Microsoft.AspNetCore.Mvc;
using System.Web;

namespace KaixoMundua.Controllers
{
    public class KaixoMunduaController : Controller
    {
        public ActionResult OngiEtorri(string izena, int
        zenbat = 1)
        {
            ViewBag.Mezua = "Hello " + izena;
            ViewBag.Zenbat = zenbat;
            return View();
        }

        //public string OngiEtorri(string izena, int zenbakia = 4)
        //{
        //    return HttpUtility.HtmlEncode("Kaixo " + izena + " , zure zenbakia " + zenbakia + " da: ");
        //}
    }
}
