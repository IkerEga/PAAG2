namespace AginteKoadroa.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Inicial : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "public.Bezeroa",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        Izena = c.String(),
                        Helbidea = c.String(),
                        Telf = c.String(),
                        Emaila = c.String(),
                        SaltzaileaId = c.String(maxLength: 128),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("public.Saltzailea", t => t.SaltzaileaId)
                .Index(t => t.SaltzaileaId);
            
            CreateTable(
                "public.Salmenta",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Data = c.DateTime(nullable: false),
                        Zenbatekoa = c.Decimal(nullable: false, precision: 18, scale: 2),
                        BezeroaId = c.String(maxLength: 128),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("public.Bezeroa", t => t.BezeroaId)
                .Index(t => t.BezeroaId);
            
            CreateTable(
                "public.Saltzailea",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        Izena = c.String(),
                        Taldea = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
        }
        
        public override void Down()
        {
            DropForeignKey("public.Bezeroa", "SaltzaileaId", "public.Saltzailea");
            DropForeignKey("public.Salmenta", "BezeroaId", "public.Bezeroa");
            DropIndex("public.Salmenta", new[] { "BezeroaId" });
            DropIndex("public.Bezeroa", new[] { "SaltzaileaId" });
            DropTable("public.Saltzailea");
            DropTable("public.Salmenta");
            DropTable("public.Bezeroa");
        }
    }
}
