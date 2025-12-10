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

    def gehitu_kontaktuBerria (self):

        izena = input("Sartu izena: ")
        abizena = input("Sartu abizena: ")
        telefonoa = input("Sartu telefonoa: ")
        email = input("Sartu email: ")

        kontaktuBerria = kontaktuak(izena, abizena, telefonoa, email)
        self.kontaktuaklista.append(kontaktuBerria)

        self.gorde()
        
# ------- KONTAKTUA EDITATZEKO ------


# ------- KONTAKTUA BORRATZEKO ------