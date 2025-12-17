using WineShop.Models;

namespace WineShop.Services
{
    public interface ISaskiaService
    {
        Task SaskiaGehitu(int ardoaId, string saskiaId);
        Task<List<SaskiaAlea>> SaskiaLortuAleak(string saskiaId);
    }
}
