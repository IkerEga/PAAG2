import json
from Models.agenda import kontaktuak
from Controllers.agendakudeatzailea import agendakudeatzailea
   
kudeatzailea = agendakudeatzailea('agenda.json')

while True:
        print("\nMenu:")
        print("1. Irakurri kontaktuak")
        print("2. Gehitu kontaktu berria")
        print("3. Irten") 
        aukera = input("sartu aukera: ")

        if not aukera.isdigit():
            print("zenbakia okerra da.")
            continue  

        opcion = int(aukera)  # zenbaki billakatu

        
        if opcion == 1:
            kudeatzailea.kargatu()
            kudeatzailea.erakutsi()  # Erakutsi
        elif opcion == 2:
            kudeatzailea.gehitu_kontaktuBerria() 
        elif opcion == 3:
            print("SESIOA AMAITUTA !")
            break  
        else:
            print("Mesedez, irakurri ondo eta sartu aukera aproposena")


