using AginteKoadroa_PG.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AginteKoadroaBerregiten
{
    public partial class AginteKoadroa : Form
    {
        public AginteKoadroa()
        {
            InitializeComponent();
        }

        private void label4_Click(object sender, EventArgs e)
        {

        }

        private void label7_Click(object sender, EventArgs e)
        {

        }

        private void AginteKoadroa_Load(object sender, EventArgs e)
        {
            using (var db = new SalmentaDbContext())
            {
                var bezeroaData = db.Bezeroa
                .Include("Saltzailea")
                .GroupBy(b => b.Saltzailea.Izena)
                .ToDictionary(g => g.Key, g => g.Count());


                //bigarren grafikoa
                var salmentaData = db.Salmenta
                .Include("Bezeroa")
                .GroupBy(b => b.Bezeroa.Izena)
                .ToDictionary(g => g.Key, g => g.Sum(b => b.Zenbatekoa));


                if (salmentaData != null)
                {
                    if (salmentaData.Count > 0)
                    {
                        var kontrolak = chart2.Controls.OfType<System.Windows.Forms.DataVisualization.Charting.Chart>();

                        chart2.Titles[0].Text = "SALMENTA GEHIENGO BEZEROAK";
                        chart2.DataSource = salmentaData;
                        chart2.Series[0].YValueMembers = "Value";
                        chart2.Series[0].XValueMember = "Key";
                        chart2.DataBind();
                    }
                }
            }
        }
    }
}
