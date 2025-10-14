using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Liburutegia

{
    public enum Generoa { Ezezaguna, Emakumea, Gizona, Bestelakoa }

    public class Contacto
    {
        public Guid Id { get; set; } = Guid.NewGuid();
        public string Izena { get; set; } = "";
        public string Abizena { get; set; } = "";
        public string Prefijoa { get; set; } = "";   // ej: +34
        public string Telefonoa { get; set; } = "";  // ej: 600123123

        public Generoa Generoa { get; set; } = Generoa.Ezezaguna;


    }
}