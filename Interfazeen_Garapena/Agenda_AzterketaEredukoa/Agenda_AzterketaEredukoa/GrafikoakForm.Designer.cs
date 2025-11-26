namespace Agenda_AzterketaEredukoa
{
    partial class GrafikoakForm
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
            System.Windows.Forms.DataVisualization.Charting.ChartArea chartArea1 = new System.Windows.Forms.DataVisualization.Charting.ChartArea();
            System.Windows.Forms.DataVisualization.Charting.Legend legend1 = new System.Windows.Forms.DataVisualization.Charting.Legend();
            System.Windows.Forms.DataVisualization.Charting.Series series1 = new System.Windows.Forms.DataVisualization.Charting.Series();
            System.Windows.Forms.DataVisualization.Charting.ChartArea chartArea2 = new System.Windows.Forms.DataVisualization.Charting.ChartArea();
            System.Windows.Forms.DataVisualization.Charting.Legend legend2 = new System.Windows.Forms.DataVisualization.Charting.Legend();
            System.Windows.Forms.DataVisualization.Charting.Series series2 = new System.Windows.Forms.DataVisualization.Charting.Series();
            this.chartGeneroa = new System.Windows.Forms.DataVisualization.Charting.Chart();
            this.chartPrefijoa = new System.Windows.Forms.DataVisualization.Charting.Chart();
            ((System.ComponentModel.ISupportInitialize)(this.chartGeneroa)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.chartPrefijoa)).BeginInit();
            this.SuspendLayout();
            // 
            // chartGeneroa
            // 
            chartArea1.Name = "ChartArea1";
            this.chartGeneroa.ChartAreas.Add(chartArea1);
            this.chartGeneroa.Dock = System.Windows.Forms.DockStyle.Top;
            legend1.Name = "Legend1";
            this.chartGeneroa.Legends.Add(legend1);
            this.chartGeneroa.Location = new System.Drawing.Point(0, 0);
            this.chartGeneroa.Name = "chartGeneroa";
            series1.ChartArea = "ChartArea1";
            series1.Legend = "Legend1";
            series1.Name = "Series1";
            this.chartGeneroa.Series.Add(series1);
            this.chartGeneroa.Size = new System.Drawing.Size(1387, 375);
            this.chartGeneroa.TabIndex = 0;
            this.chartGeneroa.Text = "chart1";
            // 
            // chartPrefijoa
            // 
            chartArea2.Name = "ChartArea1";
            this.chartPrefijoa.ChartAreas.Add(chartArea2);
            this.chartPrefijoa.Dock = System.Windows.Forms.DockStyle.Fill;
            legend2.Name = "Legend1";
            this.chartPrefijoa.Legends.Add(legend2);
            this.chartPrefijoa.Location = new System.Drawing.Point(0, 375);
            this.chartPrefijoa.Name = "chartPrefijoa";
            series2.ChartArea = "ChartArea1";
            series2.Legend = "Legend1";
            series2.Name = "Series1";
            this.chartPrefijoa.Series.Add(series2);
            this.chartPrefijoa.Size = new System.Drawing.Size(1387, 740);
            this.chartPrefijoa.TabIndex = 1;
            this.chartPrefijoa.Text = "chart1";
            // 
            // GrafikoakForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1387, 1115);
            this.Controls.Add(this.chartPrefijoa);
            this.Controls.Add(this.chartGeneroa);
            this.Name = "GrafikoakForm";
            this.Text = "Grafikoak";
            ((System.ComponentModel.ISupportInitialize)(this.chartGeneroa)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.chartPrefijoa)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataVisualization.Charting.Chart chartGeneroa;
        private System.Windows.Forms.DataVisualization.Charting.Chart chartPrefijoa;
    }
}