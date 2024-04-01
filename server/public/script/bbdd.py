from flask import Flask, request, Response
from flask_sqlalchemy import SQLAlchemy
import mysql.connector, json

host = 'localhost'
my_user = 'root'
my_password = 'jperez022'
port = 5000

app = Flask(__name__)

my_database = ('isst',)

def bbdd_init():
    global db, my_cursor, mydb, Dia, Platos_Sugeridos, Plato, Usuarios
    mydb = mysql.connector.connect(host = host, user = my_user, password = my_password)
    my_cursor = mydb.cursor()
    my_cursor.execute('SHOW DATABASES')
    databases = []
    for my_db in my_cursor:
        databases.append(my_db)
    if not my_database in databases:
        my_cursor.execute('CREATE DATABASE isst')
    app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
    app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://' + my_user + ':' + my_password + '@' + host + '/isst'

    db = SQLAlchemy(app)
    
    dia_platos = db.Table(
        "dia_platos",
        db.Column("dia_id", db.Integer, db.ForeignKey("dia.id")),
        db.Column("plato_id", db.Integer, db.ForeignKey("plato.id")),
    )

    class Usuarios(db.Model):
        id = db.Column(db.Integer, primary_key = True)
        usuario = db.Column(db.String(500), nullable = False, unique = True)

    class Dia(db.Model):
        id = db.Column(db.Integer, primary_key = True)
        usuario = db.Column(db.String(500), nullable = False, unique = True)
        dia = db.Column(db.Integer, nullable = False)
        mes = db.Column(db.Integer, nullable = False)
        platos = db.relationship("Plato", secondary = dia_platos, back_populates = "dias")
    
    class Platos_Sugeridos(db.Model):
        id = db.Column(db.Integer, primary_key = True)
        nombre = db.Column(db.Integer, nullable = False)
        ingredientes = db.Column(db.String(500), nullable = False)
        cantidades = db.Column(db.String(500), nullable = False)
        calorias_total = db.Column(db.Integer, nullable = False)

    class Plato(db.Model):
        id = db.Column(db.Integer, primary_key = True)
        nombre = db.Column(db.Integer, nullable = False)
        ingredientes = db.Column(db.String(500), nullable = False)
        cantidades = db.Column(db.String(500))
        calorias = db.Column(db.String(500))
        calorias_total = db.Column(db.Integer, nullable = False)
        dias = db.relationship("Dia", secondary = dia_platos, back_populates = "platos")
    
    db.create_all()

@app.route('/api/isst/calendario/crear/<string:usuario>', methods = ['GET', 'POST'])
def crear_calendario(usuario):
    elem = Usuarios.query.filter(Usuarios.usuario == usuario).first()
    if elem == None:
        return Response(None,400)
    elem = Dia.query.filter(Dia.usuario == usuario).first()
    if elem == None:
        for i in range(1,32):
            entry = Dia(usuario = usuario, dia = i ,mes = 1)
            db.session.add(entry)
        for i in range(1,30):
            entry = Dia(usuario = usuario, dia = i ,mes = 2)
            db.session.add(entry)
        for i in range(1,32):
            entry = Dia(usuario = usuario, dia = i ,mes = 3)
            db.session.add(entry)
        for i in range(1,31):
            entry = Dia(usuario = usuario, dia = i ,mes = 4)
            db.session.add(entry)
        for i in range(1,32):
            entry = Dia(usuario = usuario, dia = i ,mes = 5)
            db.session.add(entry)
        for i in range(1,31):
            entry = Dia(usuario = usuario, dia = i ,mes = 6)
            db.session.add(entry)
        for i in range(1,32):
            entry = Dia(usuario = usuario, dia = i ,mes = 7)
            db.session.add(entry)
        for i in range(1,32):
            entry = Dia(usuario = usuario, dia = i ,mes = 8)
            db.session.add(entry)
        for i in range(1,31):
            entry = Dia(usuario = usuario, dia = i ,mes = 9)
            db.session.add(entry)
        for i in range(1,32):
            entry = Dia(usuario = usuario, dia = i ,mes = 10)
            db.session.add(entry)
        for i in range(1,31):
            entry = Dia(usuario = usuario, dia = i ,mes = 11)
            db.session.add(entry)
        for i in range(1,32):
            entry = Dia(usuario = usuario, dia = i ,mes = 12)
            db.session.add(entry)
        db.session.commit()
        return Response(None,200)
    else:
        return Response(None,200)

@app.route('/api/isst/calendario/formar/<string:usuario>/<string:mes>/<string:dia>', methods = ['GET', 'POST'])
def get_platos_en_dia(usuario, mes, dia):
    elem = Usuarios.query.filter(Usuarios.usuario == usuario).first()
    if elem == None:
        return Response(None,400)
    elem = Dia.query.filter(Dia.usuario == usuario, Dia.mes == mes, Dia.dia == dia).first()
    if elem == None:
        return Response(None,400)
    else:
        user = elem.usuario
        day = elem.dia
        month = elem.mes
        resp = {'usuario': user, 'dia': day, 'mes': month}
        return Response(json.dumps(resp),200)

@app.route('/api/isst/nuevo_usuario/<string:usuario>', methods = ['GET', 'POST'])
def nuevo_usuario(usuario):
    elem = Usuarios.query.filter(Usuarios.usuario == usuario).first()
    if elem == None:
        entry = Usuarios(usuario = usuario)
        db.session.add(entry)
        db.session.commit()
    return Response(None,200)

if __name__ == "__main__":
    bbdd_init()
    app.run(host='0.0.0.0',port=port,debug=True)