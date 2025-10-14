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

namespace AginteKoadroa
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void label4_Click(object sender, EventArgs e)
        {

        }

        private void Form1_Load(object sender, EventArgs e)
        {
            using (var db = new SalmentaDbContext())
            {
                var bezeroaData = db.Bezeroa
                .Include("Saltzailea")
                .GroupBy(b => b.Saltzailea.Izena)
                .ToDictionary(g => g.Key, g => g.Count());
                if (bezeroaData != null)
                {
                    if (bezeroaData.Count > 0)
                    {
                        chart1.DataSource = bezeroaData;
                        chart1.Series[0].YValueMembers = "Value";
                        chart1.Series[0].XValueMember = "Key";
                        chart1.DataBind();
                    }
                }
                //bigarren grafikoa
                var salmentaData = db.Salmenta
               .Include("Bezeroa")
                .GroupBy(b => b.Bezeroa.Izena)
                .ToDictionary(g => g.Key, g => g.Sum(b => b.Zenbatekoa));
                if (salmentaData != null)
                {
                    if (salmentaData.Count > 0)
                    {
                            chart2.Titles[0].Text = "SALMENTA GEHIENGO BEZEROAK";
                            chart2.DataSource = salmentaData;
                            chart2.Series[0].YValueMembers = "Value";
                            chart2.Series[0].XValueMember = "Key";
                            chart2.DataBind();
                    }
                }
            }
        }

        private void chart2_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            Close();
        }
    }
}
