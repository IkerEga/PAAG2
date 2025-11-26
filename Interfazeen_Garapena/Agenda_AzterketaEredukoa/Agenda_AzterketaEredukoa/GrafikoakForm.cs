using Liburutegia;
using System;
using System.Collections.Generic;
using System.Windows.Forms;
using System.Windows.Forms.DataVisualization.Charting;

namespace Agenda_AzterketaEredukoa
{
    public partial class GrafikoakForm : Form
    {
        public GrafikoakForm(List<Contacto> kontaktuak)
        {
            InitializeComponent();
            this.Text = "Grafikoak";

            KargatuGrafikoaGeneroarenArabera(kontaktuak);
            KargatuGrafikoaPrefijoarenArabera(kontaktuak);
        }

        // ===============================
        // 1) GRÁFICO DE GÉNERO (TARTA)
        // ===============================
        private void KargatuGrafikoaGeneroarenArabera(List<Contacto> kontaktuak)
        {
            // Dejamos la serie limpia
            chartGeneroa.Series.Clear();
            chartGeneroa.Titles.Clear();

            // Creamos una serie de tipo "tarta"
            Series serie = new Series("Pertsonak");
            serie.ChartType = SeriesChartType.Pie;
            serie.IsValueShownAsLabel = true; // que muestre el número

            // Contadores muy simples
            int kontEzezaguna = 0;
            int kontEmakumea = 0;
            int kontGizona = 0;
            int kontBestelakoa = 0;

            foreach (var c in kontaktuak)
            {
                if (c.Generoa == Generoa.Ezezaguna) kontEzezaguna++;
                else if (c.Generoa == Generoa.Emakumea) kontEmakumea++;
                else if (c.Generoa == Generoa.Gizona) kontGizona++;
                else if (c.Generoa == Generoa.Bestelakoa) kontBestelakoa++;
            }

            // Añadimos los trozos de la tarta
            serie.Points.AddXY("Ezezaguna", kontEzezaguna);
            serie.Points.AddXY("Emakumea", kontEmakumea);
            serie.Points.AddXY("Gizona", kontGizona);
            serie.Points.AddXY("Bestelakoa", kontBestelakoa);

            chartGeneroa.Series.Add(serie);
            chartGeneroa.Titles.Add("Generoaren arabera");
        }

        // =================================
        // 2) GRÁFICO DE PREFIJO (BARRAS)
        // =================================
        private void KargatuGrafikoaPrefijoarenArabera(List<Contacto> kontaktuak)
        {
            // Dejamos la serie limpia
            chartPrefijoa.Series.Clear();
            chartPrefijoa.Titles.Clear();

            // Creamos una serie de tipo "barras verticales"
            Series serie = new Series("Pertsonak");
            serie.ChartType = SeriesChartType.Column;
            serie.IsValueShownAsLabel = true;

            // Queremos contar cuántos contactos hay por cada prefijo.
            // Lo haremos con dos listas paralelas:
            // - una para los prefijos (texto)
            // - otra para sus cantidades (números)
            List<string> prefijos = new List<string>();
            List<int> cantidades = new List<int>();

            foreach (var c in kontaktuak)
            {
                // Si no tiene prefijo, lo marcamos como "(gabe)" (sin)
                string pref = c.Prefijoa;
                if (string.IsNullOrWhiteSpace(pref))
                    pref = "(gabe)";

                // Buscar si ya tenemos ese prefijo guardado
                int indiceEncontrado = -1;
                for (int i = 0; i < prefijos.Count; i++)
                {
                    if (string.Equals(prefijos[i], pref, StringComparison.OrdinalIgnoreCase))
                    {
                        indiceEncontrado = i;
                        break;
                    }
                }

                // Si no existe, lo añadimos con cantidad 1
                if (indiceEncontrado == -1)
                {
                    prefijos.Add(pref);
                    cantidades.Add(1);
                }
                else
                {
                    // Si ya existe, sumamos 1 a su cantidad
                    cantidades[indiceEncontrado] = cantidades[indiceEncontrado] + 1;
                }
            }

            // Ahora añadimos cada prefijo como una barra
            for (int i = 0; i < prefijos.Count; i++)
            {
                string etiqueta = prefijos[i];
                int valor = cantidades[i];
                serie.Points.AddXY(etiqueta, valor);
            }

            chartPrefijoa.Series.Add(serie);
            chartPrefijoa.Titles.Add("Prefijoaren arabera");
        }
    }
}
