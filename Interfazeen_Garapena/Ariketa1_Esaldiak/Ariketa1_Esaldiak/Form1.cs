using System;
using System.Windows.Forms;

namespace Ariketa1_Esaldiak
{
    public partial class Form1 : Form
    {
        private Esaldiak esaldia = new Esaldiak();

        public Form1()
        {
            InitializeComponent();
        }

        private void btn_Esaldi1_Click(object sender, EventArgs e)
        {
            esaldia.esaldia1 = textBox1.Text.Trim();
            textBox1.Clear();
            textBox1.Focus();
            btn_Esaldi1.Enabled = false; 
            btn_Esaldi2.Enabled = true;
        }

        private void btn_Esaldi2_Click(object sender, EventArgs e)
        {
            esaldia.esaldia2 = textBox1.Text.Trim();
            textBox1.Clear();
            textBox1.Focus();
            btn_Esaldi2.Enabled = false;
            btn_Esaldi3.Enabled = true;
        }

        private void btn_Esaldi3_Click(object sender, EventArgs e)
        {
            esaldia.esaldia3 = textBox1.Text.Trim();
            textBox1.Clear();
            textBox1.Focus();
            btn_Esaldi3.Enabled = false;
            btn_Esaldi4.Enabled = true;

        }

        private void btn_Esaldi4_Click(object sender, EventArgs e)
        {
            esaldia.esaldia4 = textBox1.Text.Trim();
            textBox1.Clear();
            textBox1.Focus();
            btn_Esaldi4.Enabled = false;
            btn_Esaldi5.Enabled = true;

        }

        private void btn_Esaldi5_Click(object sender, EventArgs e)
        {
            esaldia.esaldia5 = textBox1.Text.Trim();
            textBox1.Clear();
            textBox1.Focus();
            btn_Esaldi5.Enabled = false;
            btn_Lotu.Enabled = true;

        }

        private void btn_Lotu_Click(object sender, EventArgs e)
        {
            var esaldiOsoa = esaldia.lotuEsaldiak();
            MessageBox.Show(esaldiOsoa);
            textBox1.Clear();
            textBox1.Focus();
            //btn_Lotu.Enabled = false;
            btn_Esaldi1.Enabled = true;
        }

        private void btn_Garbitu_Click(object sender, EventArgs e)
        {
            esaldia.esaldia1 = esaldia.esaldia2 = esaldia.esaldia3 = esaldia.esaldia4 = esaldia.esaldia5 = "";
            textBox1.Clear();
            btn_Esaldi5.Enabled = false;
            btn_Esaldi4.Enabled = false;
            btn_Esaldi3.Enabled = false;
            btn_Esaldi2.Enabled = false;
            btn_Lotu.Enabled = false;
            btn_Esaldi1.Enabled = true;
        }
    }
}
