using Microsoft.AspNetCore.Mvc;
using WineShop.Services;

namespace WineShop.Controllers
{
    public class SaskiaController : Controller
    {
        private readonly ISaskiaService _saskiaService;
        public SaskiaController(ISaskiaService saskiaService)
        {
            _saskiaService = saskiaService;
        }
        public async Task<IActionResult> Index()
        {
            return View(); // momentuz bista hutsa sortuko dugu (empty txantiloia)
        }
        public async Task<IActionResult> SaskiaGehitu(int id)
        {
            var cart = Saskia.SaskiaLortu(this.HttpContext); //aurretik sortu dugun Saskia klasea erabiliz
            await _saskiaService.SaskiaGehitu(id, cart.SaskiaId); //zerbitzu berrian karritoan gehitzeko
            return RedirectToAction("Index", new { id = cart.SaskiaId });
        }
    }

}