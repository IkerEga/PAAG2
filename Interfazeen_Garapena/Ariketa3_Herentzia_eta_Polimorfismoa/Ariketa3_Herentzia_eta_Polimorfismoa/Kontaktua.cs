using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ariketa3_Herentzia_eta_Polimorfismoa
{
    internal class Kontaktua
    {
        public string NAN { get; set; }
        public string Izena { get; set; }
        public string Abizena { get; set; }
        virtual public string Emaila { get; set; }
        public string izenOsoa { get { return Izena + " " + Abizena; } }

        public Kontaktua(string nan, string izena, string abizena, string emaila)
        {
            NAN = nan;
            Izena = izena;
            Abizena = abizena;
            Emaila = emaila;
        }

        virtual public string Gorde()
        {
            return "Kontaktua ondo gorde da";
        }

    }
}
