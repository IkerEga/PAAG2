using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
namespace AginteKoadroa_PG.Models
{
    public partial class Saltzailea
    {
        [Key]
        public string Id { get; set; }
        public string Izena { get; set; }
        public string Taldea { get; set; }
        public virtual List<Bezeroa> Bezeroa { get; set; }
    }
}
