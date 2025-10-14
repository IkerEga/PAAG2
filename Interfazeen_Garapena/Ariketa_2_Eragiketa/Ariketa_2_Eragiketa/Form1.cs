namespace Ariketa_2_Eragiketa
{
    public partial class Form1 : Form
    {

        private Eragiketa eragiketa = new Eragiketa();
        private int kontadorea; //Nos va a servir para saber en que registro vamos (1, 2, 3, 4...)
        public Form1()
        {
            InitializeComponent();
            kontadorea = 1;
            labelZenbakia.Text = "1. Zenbakia";
        }

        private void TxtZenbakia_Click(object sender, EventArgs e)
        {
            
        }

        private void txtBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void btn_Hurrengoa_Click(object sender, EventArgs e)
        {
            //Para saber si no ha metido un numero
            if(!eragiketa.SetFromString(kontadorea, txtBox1.Text))
            {
                MessageBox.Show("Zenbaki bat sartu behar duzu");
                return;
            }

            switch(kontadorea)
            {
                case 1:
                    kontadorea++;
                    labelZenbakia.Text = "2. Zenbakia";
                    txtBox1.Clear();
                    txtBox1.Focus();
                    break;
                case 2:
                    kontadorea++;
                    labelZenbakia.Text = "3. Zenbakia";
                    txtBox1.Clear();
                    txtBox1.Focus();
                    break;
                case 3:
                    kontadorea++;
                    labelZenbakia.Text = "4. Zenbakia";
                    txtBox1.Clear();
                    txtBox1.Focus();
                    break;
                case 4:
                    labelZenbakia.Text = "Emaitza";
                    txtBox1.Text = eragiketa.Formula();
                    btn_Hurrengoa.Enabled = false;  //Desactivamos para no poder meter mas numeros
                    break;
            }
        }

        private void btn_Gabritu_Click(object sender, EventArgs e)
        {
            eragiketa.Garbitu();
            kontadorea = 1;
            labelZenbakia.Text = "1. Zenbakia";
            txtBox1.Clear();
            btn_Hurrengoa.Enabled = true; //Volvemos a activar el boton para empezar a meter numeros desde 0
            txtBox1.Focus();
        }

        private void btn_Irten_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
