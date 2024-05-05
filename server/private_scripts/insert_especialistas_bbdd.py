import requests, sys

def mensaje():
    print("Este script esta diseñado para introducir especialistas a las BBDD.")
    print("Para ejecutar este script escriba 'python3 insert_especialistas_bbdd.py *nombre* *usuario* *movil* *info* *precio*'.")

if len(sys.argv) == 6:
    nombre = sys.argv[1]
    usuario = sys.argv[2]
    movil = sys.argv[3]
    info = sys.argv[4]
    precio = sys.argv[5]
    data = {"nombre": nombre, "usuario": usuario, "movil": movil, "info": info, "precio": precio}
    requests.post("http://localhost:5000/especialista/nuevo", json = data)
else:
    mensaje()