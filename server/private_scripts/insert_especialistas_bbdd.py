import requests, sys

def mensaje():
    print("Este script esta diseñado para introducir especialistas a las BBDD.")
    print("Para ejecutar este script escriba 'python3 insert_especialistas_bbdd.py *nombre* *movil* *info* *precio*'.")

if len(sys.argv) == 4:
    nombre = sys.argv[1]
    movil = sys.argv[2]
    info = sys.argv[3]
    precio = sys.argv[4]
    data = {"titulo": nombre, "descripcion": movil, "url": info, "precio": precio}
    requests.post("http://localhost:5000/especialista/nuevo", json = data)
else:
    mensaje()