using Liburutegia;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Agenda_AzterketaEredukoa
{
    public partial class IkusiForm : Form
    {
        public IkusiForm(List<Contacto> contactos)
        {
            InitializeComponent();

            gridContactos.AutoGenerateColumns = true;
            gridContactos.DataSource = contactos;
        }

        private void IkusiForm_Load(object sender, EventArgs e)
        {

        }

        private void gridContactos_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }
    }
}
