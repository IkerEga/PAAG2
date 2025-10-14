using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Markup;

namespace Ariketa3_Herentzia_eta_Polimorfismoa
{
    internal class Langile : Bezeroa
    {
        public string Soldata { get; set; }
        public int SegurtasunSoziala { get; set; }
        public override string Emaila
        {
            get => base.Emaila; //Referencia a la propiedad "Emaila" de la clase base (Bezeroa)
            set
            {
                try
                {
                    if (string.IsNullOrWhiteSpace(value) || value.Length < 14)
                    {
                        throw new Exception("Emaila ez duzu ondo jarri");
                    }

                    if (value.Substring(value.Length - 14, 14) != "@iesunibhi.com")
                    {
                        throw new Exception("Emaila ez duzu ondo jarri");
                    }

                    base.Emaila = value;
                }
                catch
                {
                    throw;
                }
            }
        }

        public Langile(string nan, string izena, string abizena, string emaila, string kategoria, string soldata, int segurtasunSoziala) : base(nan, izena, abizena, emaila, kategoria)
        {
            try 
            {
                Emaila = emaila;
            }
            catch (Exception)
            {
                throw;
            }

            Soldata = soldata;
            SegurtasunSoziala = segurtasunSoziala;
        }

        override public string Gorde()
        {
            return "Langilea ondo gorde da";
        }
    }
}
