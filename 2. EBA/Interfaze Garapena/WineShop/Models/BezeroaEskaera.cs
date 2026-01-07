using System.ComponentModel.DataAnnotations;

namespace WineShop.Models
{
    public class BezeroaEskaera
    {
        [Key]
        [ScaffoldColumn(false)]
        public int Id { get; set; }
        [Required(ErrorMessage = "Izena derrigorrezkoa da")]
        public string Izena { get; set; }
        [Required(ErrorMessage = "Abizena derrigorrezkoa da")]
        public string Abizena { get; set; }
        [Required(ErrorMessage = "Helbidea derrigorrezkoa da")]
        public string Helbidea { get; set; }
        [Required(ErrorMessage = "Hiria derrigorrezkoa da")]
        public string Hiria { get; set; }
        [Required(ErrorMessage = "Herrialdea derrigorrezkoa da")]
        public string Herrialdea { get; set; }
        [Required(ErrorMessage = "Postakodea derrigorrezkoa da")]
        public string Postakodea { get; set; }
        [Required(ErrorMessage = "Telefonoa derrigorrezkoa da")]
        public string Telefonoa { get; set; }
        [ScaffoldColumn(false)]
        public System.DateTime Data { get; set; }
        [ScaffoldColumn(false)]
        public string? Erabiltzailea { get; set; }
    }
}
