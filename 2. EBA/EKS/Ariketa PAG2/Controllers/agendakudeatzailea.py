import json
from Models.agenda import kontaktuak


class agendakudeatzailea:
    def __init__(self, json_path):
        self.json_path = json_path
        self.kontaktuaklista = []  # Lista hontan gordeko dira

# ------- KONTAKTUA BISTARATZEKO ------

    def kargatu(self):
        
        try:
            with open(self.json_path, 'r') as file:
                data = json.load(file)
                self.kontaktuaklista = [kontaktuak(**entry) for entry in data]  # Listan kargatzen da jsona
        except FileNotFoundError:
            print("Ez da aurkitzen.")  
        except json.JSONDecodeError:
            print("Errorea irakurtzerako orduan.")  

    def erakutsi(self):
        
        if not self.kontaktuaklista:  # Lista utzik dagoen ikusi
            print("Ez da kontakturik kargatu.")
            return
        for banakakontaktuak in self.kontaktuaklista:  # iteratzen doa listan
            print(banakakontaktuak)  

# ------- KONTAKTUA GORDETZEKO ------

    def gorde(self):

        data = []
        for k in self.kontaktuaklista:
            data.append({
                "izena": k.izena,
                "abizena": k.abizena,
                "telefonoa": k.telefonoa,
                "email": k.email
            })

        with open(self.json_path, 'w') as file:
            json.dump(data, file, indent = 4)

    def gehitu_kontaktua (self):

        izena = input("Sartu izena: ")
        abizena = input("Sartu abizena: ")
        telefonoa = input("Sartu telefonoa: ")
        email = input("Sartu email: ")

        kontaktuBerria = kontaktuak(izena, abizena, telefonoa, email)
        self.kontaktuaklista.append(kontaktuBerria)

        self.gorde()
        
# ------- KONTAKTUA EDITATZEKO ------

    def editatu_kontaktua(self):
        if not self.kontaktuaklista:
            print("Ez dago kontakturik editatzeko.")
            return

        print("\nKontaktuak:")
        for i, k in enumerate(self.kontaktuaklista, start=1):
            print(f"{i}. {k.izena} {k.abizena} - {k.telefonoa} - {k.email}")

        aukera = input("Zein kontaktu editatu nahi duzu? (zenbakia): ")
        if not aukera.isdigit():
            print("Zenbakia sartu behar duzu.")
            return

        idx = int(aukera) - 1
        if idx < 0 or idx >= len(self.kontaktuaklista):
            print("Aukera ez da zuzena.")
            return

        kontaktua = self.kontaktuaklista[idx]

        print("\nSartu balio berriak (Enter = berdin utzi):")
        izena = input(f"Izena ({kontaktua.izena}): ")
        abizena = input(f"Abizena ({kontaktua.abizena}): ")
        telefonoa = input(f"Telefonoa ({kontaktua.telefonoa}): ")
        email = input(f"Emaila ({kontaktua.email}): ")

        if izena.strip() != "":
            kontaktua.izena = izena
        if abizena.strip() != "":
            kontaktua.abizena = abizena
        if telefonoa.strip() != "":
            kontaktua.telefonoa = telefonoa
        if email.strip() != "":
            kontaktua.email = email

        self.gorde()
        print("Kontaktua eguneratu da.")


# ------- KONTAKTUA BORRATZEKO ------

    def ezabatu_kontaktua(self):
        
        if not self.kontaktuaklista:
            print("Ez dago kontakturik ezabatzeko.")
            return

        
        print("\nKontaktuak:")
        for i, k in enumerate(self.kontaktuaklista, start=1):
            print(f"{i}. {k.izena} {k.abizena} - {k.telefonoa} - {k.email}")

       
        aukera = input("Zein kontaktu ezabatu nahi duzu? (zenbakia): ")
        if not aukera.isdigit():
            print("Zenbakia sartu behar duzu.")
            return

        idx = int(aukera) - 1
        if idx < 0 or idx >= len(self.kontaktuaklista):
            print("Aukera ez da zuzena.")
            return

        
        kontaktua = self.kontaktuaklista[idx]
        confirm = input(f"Ziur? Ezabatuko da: {kontaktua.izena} {kontaktua.abizena} (bai/ez): ").lower()

        if confirm != "bai":
            print("Ez da ezabatu.")
            return

        
        self.kontaktuaklista.pop(idx)
        self.gorde()
        print("Kontaktua ezabatu da.")
