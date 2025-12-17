using Microsoft.AspNetCore.Mvc;
using WineShop.Services;
using WineShop.ViewModels;

public class SaskiaController : Controller
{
    private readonly ISaskiaService _saskiaService;
    private readonly IArdoaService _ardoaService;
    public SaskiaController(ISaskiaService saskiaService, IArdoaService ardoaService)
    {
        _saskiaService = saskiaService;
        _ardoaService = ardoaService;
    }
    public async Task<IActionResult> Index(string id)
    {
        var saskiaAleaList = await _saskiaService.SaskiaLortuAleak(id);
        var saskiaAleaVMList = new List<SaskiaAleaViewModel>();
        decimal guztira = 0; // Saskiaren totala
        foreach (var saskiaAlea in saskiaAleaList)
        {
            var ardoa = await _ardoaService.GetArdoa(saskiaAlea.ArdoaId);
            var saskiaAleaViewModel = new SaskiaAleaViewModel()
            {
                ArdoaId = ardoa.Id,
                Irudia = ardoa.Irudia,
                Izena = ardoa.Izena,
                Kantitatea = saskiaAlea.Kantitatea,
                Salneurria = ardoa.Salneurria
            };
            saskiaAleaVMList.Add(saskiaAleaViewModel);
            // Totala gehitu
            guztira += saskiaAlea.Kantitatea * ardoa.Salneurria;
        }
        // ViewModela sortu bistarako
        var saskiaViewModel = new SaskiaViewModel()
        {
            SaskiaAleaVMList = saskiaAleaVMList,
            Guztira = guztira // <-- aquí incluimos el total
        };
        return View(saskiaViewModel);
    }

    public async Task<IActionResult> SaskiaGehitu(int id)
    {
        var cart = Saskia.SaskiaLortu(this.HttpContext); //aurretik sortu dugun Saskia klasea erabiliz
        await _saskiaService.SaskiaGehitu(id, cart.SaskiaId); //zerbitzu berrian karritoan gehitzeko
        return RedirectToAction("Index", new { id = cart.SaskiaId });
    }

    public async Task<IActionResult> SaskiaGehituAjax(int id)
    {
        var cart = Saskia.SaskiaLortu(this.HttpContext);
        await _saskiaService.SaskiaGehitu(id, cart.SaskiaId);
        var saskiaItems = await _saskiaService.SaskiaLortuAleak(cart.SaskiaId);
        var itemAdded = saskiaItems.FirstOrDefault(x => x.ArdoaId == id);
        var ardoa = await _ardoaService.GetArdoa(id);
        decimal lineSubtotal = (itemAdded?.Kantitatea ?? 0) * ardoa.Salneurria;
        decimal guztira = 0;
        foreach (var item in saskiaItems)
        {
            var a = await _ardoaService.GetArdoa(item.ArdoaId);
            guztira += item.Kantitatea * a.Salneurria;
        }
        return Json(new
        {
            kantitatea = itemAdded?.Kantitatea ?? 0,
            lineSubtotal,
            guztira,
            mezua = "Zure saskia eguneratu da"
        });
    }



}