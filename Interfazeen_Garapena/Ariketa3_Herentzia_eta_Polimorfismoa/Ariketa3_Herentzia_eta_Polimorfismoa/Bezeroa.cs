using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ariketa3_Herentzia_eta_Polimorfismoa
{
    internal class Bezeroa : Kontaktua
    {
        public string Kategoria { get; set; }

        public Bezeroa(string nan, string izena, string abizena, string emaila, string kategoria) : base(nan, izena, abizena, emaila)
        {
            Kategoria = kategoria;
        }

        override public string Gorde()
        {
            return "Bezeroa ondo gorde da";
        }
    }
}
