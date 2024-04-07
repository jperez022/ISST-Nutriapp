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
    global db, my_cursor, mydb, dia_platos, Dia, Platos_Sugeridos, Plato, Usuarios, Objetivos
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
        objetivos = db.relationship("Objetivos", backref = "usuarios")

    class Dia(db.Model):
        id = db.Column(db.Integer, primary_key = True)
        usuario = db.Column(db.String(500), nullable = False)
        dia = db.Column(db.Integer, nullable = False)
        mes = db.Column(db.Integer, nullable = False)
        platos = db.relationship("Plato", secondary = dia_platos, back_populates = "dias")
    
    class Platos_Sugeridos(db.Model):
        id = db.Column(db.Integer, primary_key = True)
        nombre = db.Column(db.Integer, nullable = False)
        preparacion = db.Column(db.String(500), nullable = False)
        ingredientes = db.Column(db.String(500), nullable = False)
        cantidades = db.Column(db.String(500), nullable = False)
        calorias = db.Column(db.String(500), nullable = False)
        calorias_total = db.Column(db.Integer, nullable = False)

    class Plato(db.Model):
        id = db.Column(db.Integer, primary_key = True)
        nombre = db.Column(db.String(500), nullable = False)
        preparacion = db.Column(db.String(500))
        ingredientes = db.Column(db.String(500), nullable = False)
        descripcion = db.Column(db.String(500))
        calorias = db.Column(db.String(500), nullable = False)
        calorias_total = db.Column(db.Integer, nullable = False)
        dias = db.relationship("Dia", secondary = dia_platos, back_populates = "platos")

    class Objetivos(db.Model):
        id = db.Column(db.Integer, primary_key = True)
        usuario = db.Column(db.Integer, db.ForeignKey("usuarios.id"))
        peso_ini = db.Column(db.String(10), nullable = False)
        peso_act = db.Column(db.String(10), nullable = False)
        peso_obj = db.Column(db.String(10), nullable = False)
        ejercicio_act = db.Column(db.Integer, nullable = False)
        ejercicio_obj = db.Column(db.Integer, nullable = False)
    
    db.create_all()

@app.route('/api/isst/nuevo_usuario/<string:usuario>', methods = ['GET', 'POST'])
def nuevo_usuario(usuario):
    elem = Usuarios.query.filter(Usuarios.usuario == usuario).first()
    if elem == None:
        entry = Usuarios(usuario = usuario)
        db.session.add(entry)
        db.session.commit()
    return Response(None,200)

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

@app.route('/api/isst/calendario/dia/<string:usuario>/<string:mes>/<string:dia>', methods = ['GET', 'POST'])
def get_platos_en_dia(usuario, mes, dia):
    elem = Usuarios.query.filter(Usuarios.usuario == usuario).first()
    if elem == None:
        return Response(None,400)
    elem = Dia.query.filter(Dia.usuario == usuario, Dia.mes == mes, Dia.dia == dia).first()
    if elem == None:
        return Response(None,400)
    else:
        aux = elem.platos
        if (len(aux) == 0):
            resp = {"resp": "vacio"}
        else:
            num = 0
            resp = {}
            for elem in aux:   
                resp[str(num)] = [elem.nombre,elem.preparacion,elem.ingredientes,elem.descripcion,elem.calorias,elem.calorias_total]
                num += 1
        return Response(json.dumps(resp),200)

@app.route('/api/isst/agregar_plato/<string:usuario>/<string:nombre>/<string:preparacion>/<path:ingredientes>/<path:descripcion>/<path:calorias>/<int:calorias_total>/<string:dia_mes>', methods = ['GET', 'POST'])
def agregar_plato(usuario,nombre,preparacion,ingredientes,descripcion,calorias,calorias_total,dia_mes):
    elem = Usuarios.query.filter(Usuarios.usuario == usuario).first()
    if elem == None:
        return Response(None,400)
    nombre = nombre.replace('_',' ')
    preparacion = preparacion.replace('_',' ')
    ingredientes = ingredientes.replace('_',' ').replace('-','/')
    descripcion = descripcion.replace('_',' ').replace('-','/')
    calorias = calorias.replace('_',' ').replace('-','/')
    if preparacion == " " and descripcion == " ":
        entry = Plato(nombre = nombre, ingredientes = ingredientes, calorias = calorias, calorias_total = calorias_total)
    elif preparacion == " ":
        entry = Plato(nombre = nombre, ingredientes = ingredientes, descripcion = descripcion, calorias = calorias, calorias_total = calorias_total)
    elif descripcion == " ":
        entry = Plato(nombre = nombre, preparacion = preparacion, ingredientes = ingredientes, calorias = calorias, calorias_total = calorias_total)
    else:        
        entry = Plato(nombre = nombre, preparacion = preparacion, ingredientes = ingredientes, descripcion = descripcion, calorias = calorias, calorias_total = calorias_total)
    db.session.add(entry)
    db.session.commit()
    dia_mes = dia_mes.split('_')
    elem = Dia.query.filter(Dia.usuario == usuario, Dia.mes == dia_mes[1], Dia.dia == dia_mes[0]).first()
    if preparacion == " " and descripcion == " ":
        elem2 = Plato.query.filter(Plato.nombre == nombre, Plato.ingredientes == ingredientes, Plato.calorias == calorias, Plato.calorias_total == calorias_total).first()
    elif preparacion == " ":
        elem2 = Plato.query.filter(Plato.nombre == nombre, Plato.ingredientes == ingredientes, Plato.descripcion == descripcion, Plato.calorias == calorias, Plato.calorias_total == calorias_total).first()
    elif descripcion == " ":
        elem2 = Plato.query.filter(Plato.nombre == nombre, Plato.preparacion == preparacion, Plato.ingredientes == ingredientes, Plato.calorias == calorias, Plato.calorias_total == calorias_total).first()
    else:
        elem2 = Plato.query.filter(Plato.nombre == nombre, Plato.preparacion == preparacion, Plato.ingredientes == ingredientes, Plato.descripcion == descripcion, Plato.calorias == calorias, Plato.calorias_total == calorias_total).first()
    statement = dia_platos.insert().values(dia_id= elem.id, plato_id= elem2.id)
    db.session.execute(statement)
    db.session.commit()
    return Response(None,200)

@app.route('/api/isst/modificar_plato/<string:usuario>/<string:nombre>/<string:preparacion>/<path:ingredientes>/<path:calorias>/<int:calorias_total>/<string:dia_mes>', methods = ['GET', 'POST'])
def modificar_plato(usuario,nombre,preparacion,ingredientes,calorias,calorias_total,dia_mes):
    elem = Usuarios.query.filter(Usuarios.usuario == usuario).first()
    if elem == None:
        return Response(None,400)
    nombre = nombre.replace('_',' ')
    ingredientes = ingredientes.replace('_',' ').replace('-','/')
    calorias = calorias.replace('_',' ').replace('-','/')
    dia_mes = dia_mes.split('_')
    elem = Dia.query.filter(Dia.usuario == usuario, Dia.mes == dia_mes[1], Dia.dia == dia_mes[0]).first()
    elem2 = Plato.query.filter(Plato.nombre == nombre, Plato.preparacion == None, Plato.ingredientes == ingredientes, Plato.calorias == calorias, Plato.calorias_total == calorias_total, Plato.dias.any(id = elem.id)).first()    
    if elem2 == None:
        return Response(None,400)
    elem2.preparacion = preparacion
    db.session.commit()
    return Response(None,200)

@app.route('/api/isst/crear_objetivos/<string:usuario>/<path:peso>/<path:ejercicio>', methods = ['GET', 'POST'])
def crear_objetivos(usuario,peso,ejercicio):
    user = Usuarios.query.filter(Usuarios.usuario == usuario).first()
    if user == None:
        return Response(None,400)
    elem = Objetivos.query.filter(Objetivos.usuario == user.id).first()
    if elem != None:
        db.session.delete(elem)
        db.session.commit()
    peso = peso.split('-')
    ejercicio = ejercicio.split('-')
    entry = Objetivos(usuario = user.id, peso_ini = peso[0], peso_act = peso[1], peso_obj = peso[2], ejercicio_act = ejercicio[0], ejercicio_obj = ejercicio[1])
    db.session.add(entry)
    db.session.commit()
    return Response(None,200)

@app.route('/api/isst/obtener_objetivos/<string:usuario>', methods = ['GET', 'POST'])
def obtener_objetivos(usuario):
    user = Usuarios.query.filter(Usuarios.usuario == usuario).first()
    if user == None:
        return Response(None,400)
    elem = Objetivos.query.filter(Objetivos.usuario == user.id).first()
    if elem == None:
        return Response(None,400)
    resp = {"peso_ini": elem.peso_ini, "peso_act": elem.peso_act, "peso_obj": elem.peso_obj, "ejercicio_act": elem.ejercicio_act, "ejercicio_obj": elem.ejercicio_obj}
    return Response(json.dumps(resp),200)

@app.route('/api/isst/agregar_plato_sugerido/<string:nombre>/<string:preparacion>/<path:ingredientes>/<path:cantidad>/<path:calorias>/<int:calorias_total>', methods = ['GET', 'POST'])
def agregar_plato_sugerido(nombre,preparacion,ingredientes,cantidad,calorias,calorias_total):
    nombre = nombre.replace('_',' ')
    preparacion = preparacion.replace('_',' ')
    ingredientes = ingredientes.replace('_',' ').replace('-','/')
    cantidad = cantidad.replace('_',' ').replace('-','/')
    descripcion = descripcion.replace('_',' ').replace('-','/')
    calorias = calorias.replace('_',' ').replace('-','/')
    entry = Platos_Sugeridos(nombre = nombre, preparacion = preparacion, ingredientes = ingredientes, cantidad = cantidad, calorias = calorias, calorias_total = calorias_total)
    db.session.add(entry)
    db.session.commit()
    return Response(None,200)

@app.route('/api/isst/obtener_plato_sugerido/<path:calorias_total>', methods = ['GET', 'POST'])
def obtener_plato_sugerido(calorias_total):
    calorias_total = calorias_total.split('_')
    elems = Platos_Sugeridos.query.filter(Platos_Sugeridos.calorias_total > calorias_total[0], Platos_Sugeridos.calorias_total < calorias_total[1]).all()
    resp = {}
    num = 0
    for elem in elems:
        resp[str(num)] = [elem.nombre,elem.preparacion,elem.ingredientes,elem.cantidad,elem.calorias,elem.calorias_total]
        num += 1
    return Response(json.dumps(resp),200)

@app.route('/api/isst/obtener_plato_usuario/<string:usuario>', methods = ['GET', 'POST'])
def obtener_plato_usuario(usuario):
    elems = Plato.query.filter(Plato.dias.any(usuario = usuario)).all()
    resp = {}
    if (elems == None):
        resp = {"resp": "vacio"}
    num = 0
    for elem in elems:
        resp[str(num)] = [elem.nombre,elem.preparacion,elem.ingredientes,elem.descripcion,elem.calorias,elem.calorias_total]
        num += 1
    return Response(json.dumps(resp),200)

if __name__ == "__main__":
    bbdd_init()
    app.run(host='0.0.0.0',port=port,debug=True)
