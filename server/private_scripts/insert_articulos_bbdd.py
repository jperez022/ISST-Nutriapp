import requests, sys

def mensaje():
    print("Este script esta diseñado para introducir articulos a las BBDD.")
    print("Para ejecutar este script escriba 'python3 insert_articulos_bbdd.py *titulo* *descripcion* *url*'.")

if len(sys.argv) == 4:
    titulo = sys.argv[1]
    descripcion = sys.argv[2]
    url = sys.argv[3]
    data = {"titulo": titulo, "descripcion": descripcion, "url": url}
    requests.post("http://localhost:5000/articulo/nuevo", json = data)
else:
    mensaje()