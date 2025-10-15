namespace Agenda_AzterketaEredukoa
{
    partial class IkusiForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.gridContactos = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.gridContactos)).BeginInit();
            this.SuspendLayout();
            // 
            // gridContactos
            // 
            this.gridContactos.AllowUserToAddRows = false;
            this.gridContactos.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.AllCells;
            this.gridContactos.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.gridContactos.Dock = System.Windows.Forms.DockStyle.Fill;
            this.gridContactos.Location = new System.Drawing.Point(0, 0);
            this.gridContactos.Name = "gridContactos";
            this.gridContactos.ReadOnly = true;
            this.gridContactos.RowHeadersWidth = 51;
            this.gridContactos.RowTemplate.Height = 24;
            this.gridContactos.Size = new System.Drawing.Size(1000, 563);
            this.gridContactos.TabIndex = 0;
            this.gridContactos.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.gridContactos_CellContentClick);
            // 
            // IkusiForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.gridContactos);
            this.Name = "IkusiForm";
            this.Text = "Form2";
            this.Load += new System.EventHandler(this.IkusiForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.gridContactos)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView gridContactos;
    }
}