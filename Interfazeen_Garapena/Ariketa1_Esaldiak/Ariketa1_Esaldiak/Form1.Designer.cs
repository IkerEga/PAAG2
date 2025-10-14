namespace Ariketa1_Esaldiak
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
            textBox1 = new TextBox();
            btn_Esaldi1 = new Button();
            btn_Esaldi2 = new Button();
            btn_Esaldi3 = new Button();
            btn_Esaldi4 = new Button();
            btn_Esaldi5 = new Button();
            btn_Lotu = new Button();
            btn_Garbitu = new Button();
            SuspendLayout();
            // 
            // textBox1
            // 
            textBox1.Location = new Point(214, 71);
            textBox1.Multiline = true;
            textBox1.Name = "textBox1";
            textBox1.Size = new Size(341, 86);
            textBox1.TabIndex = 0;
            // 
            // btn_Esaldi1
            // 
            btn_Esaldi1.Location = new Point(88, 202);
            btn_Esaldi1.Name = "btn_Esaldi1";
            btn_Esaldi1.Size = new Size(94, 29);
            btn_Esaldi1.TabIndex = 1;
            btn_Esaldi1.Text = "Esaldi 1";
            btn_Esaldi1.UseVisualStyleBackColor = true;
            btn_Esaldi1.Click += btn_Esaldi1_Click;
            // 
            // btn_Esaldi2
            // 
            btn_Esaldi2.Enabled = false;
            btn_Esaldi2.Location = new Point(369, 202);
            btn_Esaldi2.Name = "btn_Esaldi2";
            btn_Esaldi2.Size = new Size(94, 29);
            btn_Esaldi2.TabIndex = 2;
            btn_Esaldi2.Text = "Esaldi 2";
            btn_Esaldi2.UseVisualStyleBackColor = true;
            btn_Esaldi2.Click += btn_Esaldi2_Click;
            // 
            // btn_Esaldi3
            // 
            btn_Esaldi3.Enabled = false;
            btn_Esaldi3.Location = new Point(660, 202);
            btn_Esaldi3.Name = "btn_Esaldi3";
            btn_Esaldi3.Size = new Size(94, 29);
            btn_Esaldi3.TabIndex = 3;
            btn_Esaldi3.Text = "Esaldi 3";
            btn_Esaldi3.UseVisualStyleBackColor = true;
            btn_Esaldi3.Click += btn_Esaldi3_Click;
            // 
            // btn_Esaldi4
            // 
            btn_Esaldi4.Enabled = false;
            btn_Esaldi4.Location = new Point(88, 303);
            btn_Esaldi4.Name = "btn_Esaldi4";
            btn_Esaldi4.Size = new Size(94, 29);
            btn_Esaldi4.TabIndex = 4;
            btn_Esaldi4.Text = "Esaldi 4";
            btn_Esaldi4.UseVisualStyleBackColor = true;
            btn_Esaldi4.Click += btn_Esaldi4_Click;
            // 
            // btn_Esaldi5
            // 
            btn_Esaldi5.Enabled = false;
            btn_Esaldi5.Location = new Point(369, 303);
            btn_Esaldi5.Name = "btn_Esaldi5";
            btn_Esaldi5.Size = new Size(94, 29);
            btn_Esaldi5.TabIndex = 5;
            btn_Esaldi5.Text = "Esaldi 5";
            btn_Esaldi5.UseVisualStyleBackColor = true;
            btn_Esaldi5.Click += btn_Esaldi5_Click;
            // 
            // btn_Lotu
            // 
            btn_Lotu.Enabled = false;
            btn_Lotu.Location = new Point(660, 303);
            btn_Lotu.Name = "btn_Lotu";
            btn_Lotu.Size = new Size(94, 29);
            btn_Lotu.TabIndex = 6;
            btn_Lotu.Text = "Lotu";
            btn_Lotu.UseVisualStyleBackColor = true;
            btn_Lotu.Click += btn_Lotu_Click;
            // 
            // btn_Garbitu
            // 
            btn_Garbitu.Location = new Point(88, 369);
            btn_Garbitu.Name = "btn_Garbitu";
            btn_Garbitu.Size = new Size(666, 29);
            btn_Garbitu.TabIndex = 7;
            btn_Garbitu.Text = "Garbitu";
            btn_Garbitu.UseVisualStyleBackColor = true;
            btn_Garbitu.Click += btn_Garbitu_Click;
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(800, 450);
            Controls.Add(btn_Garbitu);
            Controls.Add(btn_Lotu);
            Controls.Add(btn_Esaldi5);
            Controls.Add(btn_Esaldi4);
            Controls.Add(btn_Esaldi3);
            Controls.Add(btn_Esaldi2);
            Controls.Add(btn_Esaldi1);
            Controls.Add(textBox1);
            Name = "Form1";
            Text = "Form1";
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private TextBox textBox1;
        private Button btn_Esaldi1;
        private Button btn_Esaldi2;
        private Button btn_Esaldi3;
        private Button btn_Esaldi4;
        private Button btn_Esaldi5;
        private Button btn_Lotu;
        private Button btn_Garbitu;
    }
}
