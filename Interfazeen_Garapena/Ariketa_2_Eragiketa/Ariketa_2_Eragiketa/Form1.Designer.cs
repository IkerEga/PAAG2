namespace Ariketa_2_Eragiketa
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
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
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            txtBox1 = new TextBox();
            btn_Hurrengoa = new Button();
            btn_Gabritu = new Button();
            btn_Irten = new Button();
            labelZenbakia = new Label();
            SuspendLayout();
            // 
            // txtBox1
            // 
            txtBox1.Location = new Point(436, 97);
            txtBox1.Name = "txtBox1";
            txtBox1.Size = new Size(296, 27);
            txtBox1.TabIndex = 0;
            txtBox1.TextChanged += txtBox1_TextChanged;
            // 
            // btn_Hurrengoa
            // 
            btn_Hurrengoa.Location = new Point(133, 257);
            btn_Hurrengoa.Name = "btn_Hurrengoa";
            btn_Hurrengoa.Size = new Size(94, 29);
            btn_Hurrengoa.TabIndex = 1;
            btn_Hurrengoa.Text = "Hurrengoa";
            btn_Hurrengoa.UseVisualStyleBackColor = true;
            btn_Hurrengoa.Click += btn_Hurrengoa_Click;
            // 
            // btn_Gabritu
            // 
            btn_Gabritu.Location = new Point(383, 257);
            btn_Gabritu.Name = "btn_Gabritu";
            btn_Gabritu.Size = new Size(94, 29);
            btn_Gabritu.TabIndex = 2;
            btn_Gabritu.Text = "Garbitu";
            btn_Gabritu.UseVisualStyleBackColor = true;
            btn_Gabritu.Click += btn_Gabritu_Click;
            // 
            // btn_Irten
            // 
            btn_Irten.Location = new Point(638, 257);
            btn_Irten.Name = "btn_Irten";
            btn_Irten.Size = new Size(94, 29);
            btn_Irten.TabIndex = 3;
            btn_Irten.Text = "Irten";
            btn_Irten.UseVisualStyleBackColor = true;
            btn_Irten.Click += btn_Irten_Click;
            // 
            // labelZenbakia
            // 
            labelZenbakia.AutoSize = true;
            labelZenbakia.Location = new Point(177, 104);
            labelZenbakia.Name = "labelZenbakia";
            labelZenbakia.Size = new Size(85, 20);
            labelZenbakia.TabIndex = 4;
            labelZenbakia.Text = "1. Zenbakia";
            labelZenbakia.Click += TxtZenbakia_Click;
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(800, 450);
            Controls.Add(labelZenbakia);
            Controls.Add(btn_Irten);
            Controls.Add(btn_Gabritu);
            Controls.Add(btn_Hurrengoa);
            Controls.Add(txtBox1);
            Name = "Form1";
            Text = "Form1";
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private TextBox txtBox1;
        private Button btn_Hurrengoa;
        private Button btn_Gabritu;
        private Button btn_Irten;
        private Label labelZenbakia;
    }
}
