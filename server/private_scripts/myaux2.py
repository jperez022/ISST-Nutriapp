import mysql.connector

mydb = mysql.connector.connect(host="localhost",user="root",passwd="jperez022")

my_cursor = mydb.cursor()

my_cursor.execute("DROP DATABASE isst2")

my_cursor.execute("SHOW DATABASES")
for db in my_cursor:
    print(db)