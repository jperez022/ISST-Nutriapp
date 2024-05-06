import sys, mysql.connector

mydb = mysql.connector.connect(host="localhost",user="root",passwd="jperez022")

my_cursor = mydb.cursor()

def mensaje():
    print("Este script esta diseñado para crear y destruir la BBDD.")
    print("Escribe 'python3 myaux.py create' para crear la BBDD.")
    print("Escribe 'python3 myaux.py drop' para destruir la BBDD.")

def create():
    my_cursor.execute("CREATE DATABASE isst2")

    my_cursor.execute("SHOW DATABASES")
    for db in my_cursor:
        print(db)

def drop():
    my_cursor.execute("DROP DATABASE isst2")

    my_cursor.execute("SHOW DATABASES")
    for db in my_cursor:
        print(db)

if len(sys.argv) == 2:
    if sys.argv[1] == "create":
        create()
    elif sys.argv[1] == "drop":
        drop()
    else:
        mensaje()
else:
    mensaje()
