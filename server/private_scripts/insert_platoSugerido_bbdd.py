import requests, sys

def mensaje():
    print("Este script esta diseñado para introducir articulos a las BBDD.")
    print("Para ejecutar este script escriba 'python3 insert_articulos_bbdd.py *nombre* *preparacion* *ingredientes* *cantidades* *calorias* *calorias_total*'.")

if len(sys.argv) == 7:
    nombre = sys.argv[1]
    preparacion = sys.argv[2]
    ingredientes = sys.argv[3]
    cantidades = sys.argv[4]
    calorias = sys.argv[5]
    calorias_total = sys.argv[6]
    data = {"nombre": nombre, "preparacion": preparacion, "ingredientes": ingredientes, "cantidades": cantidades, "calorias": calorias, "calorias_total": calorias_total}
    requests.post("http://localhost:5000/plato/agregarSugerido", json = data)
else:
    mensaje()