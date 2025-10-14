using Liburutegia;
using System;
using System.Collections.Generic;
using System.Data;

using System.Linq;

using System.Windows.Forms;

namespace Agenda_AzterketaEredukoa
{
    public partial class Form1 : Form
    {
        private readonly List<Contacto> _contactos = new List<Contacto>();

        public Form1()
        {
            InitializeComponent();

            comboBoxGeneroa.DataSource = Enum.GetValues(typeof(Generoa));
            comboBoxGeneroa.SelectedItem = Generoa.Ezezaguna;
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void btnGorde_Click(object sender, EventArgs e)
        {
            var kontaktua = new Contacto
            {
                Izena = txtIzena.Text.Trim(),
                Abizena = txtAbizena.Text.Trim(),
                Prefijoa = txtPrefijoa.Text.Trim(),
                Telefonoa = txtTelefonoa.Text.Trim(),
                Generoa = (Generoa)comboBoxGeneroa.SelectedItem
            };

            //Para que el nombre sea obligatorio
            if (string.IsNullOrWhiteSpace(kontaktua.Izena))
            { MessageBox.Show("Izena beharrezkoa da."); return; }

            //Para que el telefono sea obligatorio
            if (string.IsNullOrWhiteSpace(kontaktua.Telefonoa))
            { MessageBox.Show("Telefonoa beharrezkoa da."); return; }

            //Para duplicados de numero de telefono
            string Limpia(string t) => new string((t ?? "").Where(char.IsDigit).ToArray());
            if (_contactos.Any(c => Limpia(c.Telefonoa) == Limpia(kontaktua.Telefonoa)))
            { MessageBox.Show("Telefono hori errepikatuta dago."); return; }

            _contactos.Add(kontaktua);
            MessageBox.Show("Kontaktua gordeta! ");
            LimpiarFormulario();
        }


        private void LimpiarFormulario()
        {
            txtIzena.Clear();
            txtAbizena.Clear();
            txtTelefonoa.Clear();
            txtPrefijoa.Clear();
            comboBoxGeneroa.SelectedItem = Generoa.Ezezaguna;
            txtIzena.Focus();
        }
    }
}
