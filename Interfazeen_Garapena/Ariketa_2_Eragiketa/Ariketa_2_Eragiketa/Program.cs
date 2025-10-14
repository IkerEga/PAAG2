namespace Ariketa_2_Eragiketa
{
    internal static class Program
    {
        /// <summary>
        ///  The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            // To customize application configuration such as set high DPI settings or default font,
            // see https://aka.ms/applicationconfiguration.
            ApplicationConfiguration.Initialize();
            Application.Run(new Form1());
        }
    }

    internal class Eragiketa
    {
        public double eragiketa1{ get; set; }
        public double eragiketa2 { get; set; }
        public double eragiketa3 { get; set; }
        public double eragiketa4 { get; set; }

        public Eragiketa ()
        {
            Garbitu();
        }

        public void Garbitu()
        {
            eragiketa1 = 0;
            eragiketa2 = 0;
            eragiketa3 = 0;
            eragiketa4 = 0;
        }

        public bool SetFromString(int erregistroa, string texto)
        {
            if (double.TryParse(texto, out double zenbakia))
            {
                switch (erregistroa) // <-- uso de switch-case para elegir posición
                {
                    case 1:
                        eragiketa1 = zenbakia;
                        return true;
                    case 2:
                        eragiketa2 = zenbakia;
                        return true;
                    case 3:
                        eragiketa3 = zenbakia;
                        return true;
                    case 4:
                        eragiketa4 = zenbakia;
                        return true;
                    default:
                        return false;
                }
            }
            else
            {
                return false;
            }
        }

        public string Formula()
        {
            double emaitza = (eragiketa1 + (2 * eragiketa2) + (3 * eragiketa3) + (4 * eragiketa4)) / 4.0;

            string eragiketaOsoa = $"({eragiketa1} + (2x{eragiketa2}) + (3x{eragiketa3}) + (4x{eragiketa4})) / 4 = {emaitza}";
            return eragiketaOsoa;
        }

    }
}