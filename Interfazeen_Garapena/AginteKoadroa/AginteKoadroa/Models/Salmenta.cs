using System;
using System.ComponentModel.DataAnnotations;
namespace AginteKoadroa_PG.Models
{
    public partial class Salmenta
    {
        [Key]
        public int Id { get; set; }
        public DateTime Data { get; set; }
        public decimal Zenbatekoa { get; set; }
        public string BezeroaId { get; set; }
        public virtual Bezeroa Bezeroa { get; set; }
    }
}