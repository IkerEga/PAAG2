import json
from Models.agenda import kontaktuak
from Controllers.agendakudeatzailea import agendakudeatzailea
   
kudeatzailea = agendakudeatzailea('agenda.json')

while True:
        print("\nMenu:")
        print("1. Irakurri kontaktuak")
        print("2. Irten") 
        aukera = input("sartu aukera: ")

        if not aukera.isdigit():
            print("zenbakia okerra da.")
            continue  

        opcion = int(aukera)  # zenbaki billakatu

        
        if opcion == 1:
            kudeatzailea.kargatu()  # kargatu 
            kudeatzailea.erakutsi()  # Erakutsi
        elif opcion == 2:
            print("Irten")
            break  
        else:
            print("Mesedez, irakurri ondo eta sartu aukera aproposena")


