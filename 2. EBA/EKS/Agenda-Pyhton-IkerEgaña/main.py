import json
from Models.agenda import kontaktuak
from Controllers.agendakudeatzailea import agendakudeatzailea
   
kudeatzailea = agendakudeatzailea('agenda.json')
kudeatzailea.kargatu()

while True:
        print("\nMenu:")
        print("1. Irakurri kontaktuak")
        print("2. Gehitu kontaktua")
        print("3. Editatu kontaktua")
        print("4. Ezabatu kontaktua")
        print("5. Irten")

        aukera = input("sartu aukera: ")

        if not aukera.isdigit():
            print("zenbakia okerra da.")
            continue  

        aukerak = int(aukera)  # zenbaki bilakatu

        
        if aukerak == 1:
            kudeatzailea.erakutsi()  # Erakutsi
        elif aukerak == 2:
            kudeatzailea.gehitu_kontaktua() 
        elif aukerak == 3:
            kudeatzailea.editatu_kontaktua()
        elif aukerak == 4:
            kudeatzailea.ezabatu_kontaktua()

        elif aukerak == 5:
            print("Irten")
            break

        else:
            print("Mesedez, irakurri ondo eta sartu aukera aproposena")


