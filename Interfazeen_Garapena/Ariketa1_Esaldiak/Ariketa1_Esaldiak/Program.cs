namespace Ariketa1_Esaldiak
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

    internal class Esaldiak 
    {
        public string esaldia1 {get; set;}
        public string esaldia2 {get; set; }
        public string esaldia3 {get; set; }
        public string esaldia4 {get; set; }
        public string esaldia5 {get; set; }

        public Esaldiak()
        {
            esaldia1 = "";
            esaldia2 = "";
            esaldia3 = "";
            esaldia4 = "";
            esaldia5 = "";
        }

        public string lotuEsaldiak()
        {
            string esaldiOsoa = "";
            esaldiOsoa = esaldia1 + " " + esaldia2 + " " + esaldia3 + " " + esaldia4 + " " + esaldia5;
            return esaldiOsoa;
        }
    }
}