using System.ComponentModel.DataAnnotations;

namespace WineShop.Models
{
    public class Mota
    {
        [Key]
        public int Id { get; set; }
        public string Izena { get; set; }
        public virtual IList<Ardoa> Ardoak { get; set; }
    }
}
