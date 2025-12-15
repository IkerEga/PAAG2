using WineShop.Models;

namespace WineShop.Services
{
    public interface IArdoaService
    {
        Task<IList<ArdoUpeltegi>> GetMota(int id);
    }
}
