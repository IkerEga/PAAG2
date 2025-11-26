using Liburutegia;
using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace Agenda_AzterketaEredukoa
{
    public partial class Form1 : Form
    {
        // Lista en memoria donde guardamos los contactos
        private List<Contacto> _contactos = new List<Contacto>();

        public Form1()
        {
            InitializeComponent();

            // Rellenamos el combo "a mano" (más fácil de entender)
            comboBoxGeneroa.Items.Clear();
            comboBoxGeneroa.Items.Add(Generoa.Ezezaguna);
            comboBoxGeneroa.Items.Add(Generoa.Emakumea);
            comboBoxGeneroa.Items.Add(Generoa.Gizona);
            comboBoxGeneroa.Items.Add(Generoa.Bestelakoa);
            comboBoxGeneroa.SelectedIndex = 0; // valor por defecto
            comboBoxGeneroa.DropDownStyle = ComboBoxStyle.DropDownList; // no permitir escribir

            AdibidezkoKontaktuak();

        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        // GORDE
        private void btnGorde_Click(object sender, EventArgs e)
        {
            //  Leemos lo que hay en la pantalla
            string izena = txtIzena.Text.Trim();
            string abizena = txtAbizena.Text.Trim();
            string prefijoa = txtPrefijoa.Text.Trim();
            string telefonoa = txtTelefonoa.Text.Trim();
            Generoa generoa = (Generoa)comboBoxGeneroa.SelectedItem;

            if (izena == "")
            {
                MessageBox.Show("Izena beharrezkoa da.");
                return;
            }
            if (telefonoa == "")
            {
                MessageBox.Show("Telefonoa beharrezkoa da.");
                return;
            }

            //  Comprobar que no exista ya ese teléfono 
            bool duplicado = false;
            foreach (var repetido in _contactos)
            {
                if (repetido.Telefonoa == telefonoa)
                {
                    duplicado = true;
                    break;
                }
            }
            if (duplicado)
            {
                MessageBox.Show("Telefono hori errepikatuta dago.");
                return;
            }

            // 4) Crear el contacto y guardarlo en la lista
            Contacto kontaktua = new Contacto();
            kontaktua.Izena = izena;
            kontaktua.Abizena = abizena;
            kontaktua.Prefijoa = prefijoa;
            kontaktua.Telefonoa = telefonoa;
            kontaktua.Generoa = generoa;

            _contactos.Add(kontaktua);

            MessageBox.Show("Kontaktua gordeta!");
            LimpiarFormulario();
        }

        private void btnIkusi_Click(object sender, EventArgs e)
        {
            IkusiForm formulario = new IkusiForm(_contactos);
            formulario.ShowDialog(this);
        }

        // BILATU
        private void btnBilatu_Click(object sender, EventArgs e)
        {
            string telefonoBuscado = txtTelefonoa.Text.Trim();
            if (telefonoBuscado == "")
            {
                MessageBox.Show("Idatzi telefono bat bilatzeko.");
                return;
            }

            // Buscar con un bucle
            Contacto aurkitua = null;   //Lo dejamos en null para crear la variable antes del bucle
            foreach (var contacto in _contactos)
            {
                if (contacto.Telefonoa == telefonoBuscado)
                {
                    aurkitua = contacto;
                    break;
                }
            }

            if (aurkitua == null)
            {
                MessageBox.Show("Ez da kontakturik aurkitu telefono honekin.");
                return;
            }

            MessageBox.Show(
                "Izena: " + aurkitua.Izena + "\n" +
                "Abizena: " + aurkitua.Abizena + "\n" +
                "Generoa: " + aurkitua.Generoa + "\n" +
                "Telefonoa: " + aurkitua.Prefijoa + " " + aurkitua.Telefonoa,
                "Kontaktua aurkituta");

            txtIzena.Text = aurkitua.Izena;
            txtAbizena.Text = aurkitua.Abizena;
            txtPrefijoa.Text = aurkitua.Prefijoa;
            txtTelefonoa.Text = aurkitua.Telefonoa;
            comboBoxGeneroa.SelectedItem = aurkitua.Generoa;

            LimpiarFormulario();
        }

        private void LimpiarFormulario()
        {
            txtIzena.Clear();
            txtAbizena.Clear();
            txtTelefonoa.Clear();
            txtPrefijoa.Clear();
            comboBoxGeneroa.SelectedIndex = 0; // Ezezaguna
            txtIzena.Focus();
        }

        private void btnGrafikoak_Click(object sender, EventArgs e)
        {
            var f = new GrafikoakForm(_contactos);
            f.ShowDialog(this);
        }

        private void AdibidezkoKontaktuak()
        { 

            Contacto c1 = new Contacto();
            c1.Izena = "Ane";
            c1.Abizena = "Etxeberria";
            c1.Prefijoa = "+34";
            c1.Telefonoa = "600111111";
            c1.Generoa = Generoa.Emakumea;
            _contactos.Add(c1);

            Contacto c2 = new Contacto();
            c2.Izena = "Jon";
            c2.Abizena = "Gomez";
            c2.Prefijoa = "+34";
            c2.Telefonoa = "600222222";
            c2.Generoa = Generoa.Gizona;
            _contactos.Add(c2);

            Contacto c3 = new Contacto();
            c3.Izena = "Mikel";
            c3.Abizena = "Perez";
            c3.Prefijoa = "+33";
            c3.Telefonoa = "600333333";
            c3.Generoa = Generoa.Gizona;
            _contactos.Add(c3);

            Contacto c4 = new Contacto();
            c4.Izena = "Laura";
            c4.Abizena = "Lopez";
            c4.Prefijoa = "+49";
            c4.Telefonoa = "600444444";
            c4.Generoa = Generoa.Emakumea;
            _contactos.Add(c4);
        }

    }
}





