using System.Diagnostics;
using Futbol.Models;
using Microsoft.AspNetCore.Mvc;

namespace Futbol.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;

        public HomeController(ILogger<HomeController> logger)
        {
            _logger = logger;
        }

        public ActionResult OngiEtorri(string izena, int zenbat = 1)
        {
            ViewBag.Mezua = "Hello " + izena;
            ViewBag.Zenbat = zenbat;
            return View();
        }

        public IActionResult Index()
        {
            return View();
        }

        public IActionResult Privacy()
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
