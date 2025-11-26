using Microsoft.AspNetCore.Mvc;
using System.Web;

namespace KaixoMundua.Controllers
{
    public class KaixoMunduaController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }

        public string OngiEtorri(string izena, int zenbakia = 4)
        {
            return HttpUtility.HtmlEncode("Kaixo " + izena + " , zure zenbakia " + zenbakia + " da: ");
        }
    }
}
