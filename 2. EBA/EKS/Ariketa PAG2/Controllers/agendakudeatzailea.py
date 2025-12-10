import json
from Models.agenda import kontaktuak


class agendakudeatzailea:
    def __init__(self, json_path):
        self.json_path = json_path
        self.kontaktuaklista = []  # Lista hontan gordeko dira

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


        