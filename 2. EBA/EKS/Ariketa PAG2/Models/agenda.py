class kontaktuak:
    def __init__(self, izena, abizena, telefonoa, email):
        self.izena  = izena
        self.abizena = abizena
        self.telefonoa = telefonoa
        self.email = email

    def __str__(self):
        return f"{self.izena} {self.abizena} - {self.telefonoa} - {self.email}"
