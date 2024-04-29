import sys, os

def mensaje():
    print("Este script esta diseñado para modificar los ficheros de views")
    print("Para ejecutar este script escriba 'python3 views.py *NEW_IP* *OLD_IP*'.")
    print("NEW_IP representa la IP donde se va a ejecutanco el servidor.")
    print("OLD_IP representa la IP que anteriormente alojaba el servicio (si anteriormente nunca ha modificado script.js deje este argumento en blanco).")

ruta_py = sys.argv[0]
ruta = ruta_py.replace('.py','.ejs')
ruta_calc = ruta.replace('viewss','calc')
ruta_calc2 = ruta.replace('viewss','calc2')
ruta_dia = ruta.replace('viewss','dia')
ruta_seg = ruta.replace('viewss','seg')
ruta_segmodif = ruta.replace('viewss','segmodif')
ruta_segini = ruta.replace('viewss','segini')
ruta_platogen = ruta.replace('viewss','platogen')

if len(sys.argv) >= 4:
    mensaje()
    sys.exit()
if len(sys.argv) == 2:
    old_ip = "localhost"
    new_ip = sys.argv[1]
    if (new_ip == 'help'):
        mensaje()
        sys.exit()

    my_file = open(ruta_calc,'r')
    my_data = my_file.read()
    my_data = my_data.replace(old_ip,new_ip)
    my_file.close()
    my_file = open(ruta_calc,'w')
    my_file.write(my_data)
    my_file.close()

    my_file = open(ruta_calc2,'r')
    my_data = my_file.read()
    my_data = my_data.replace(old_ip,new_ip)
    my_file.close()
    my_file = open(ruta_calc2,'w')
    my_file.write(my_data)
    my_file.close()

    my_file = open(ruta_dia,'r')
    my_data = my_file.read()
    my_data = my_data.replace(old_ip,new_ip)
    my_file.close()
    my_file = open(ruta_dia,'w')
    my_file.write(my_data)
    my_file.close()

    my_file = open(ruta_seg,'r')
    my_data = my_file.read()
    my_data = my_data.replace(old_ip,new_ip)
    my_file.close()
    my_file = open(ruta_seg,'w')
    my_file.write(my_data)
    my_file.close()

    my_file = open(ruta_segmodif,'r')
    my_data = my_file.read()
    my_data = my_data.replace(old_ip,new_ip)
    my_file.close()
    my_file = open(ruta_segmodif,'w')
    my_file.write(my_data)
    my_file.close()

    my_file = open(ruta_segini,'r')
    my_data = my_file.read()
    my_data = my_data.replace(old_ip,new_ip)
    my_file.close()
    my_file = open(ruta_segini,'w')
    my_file.write(my_data)
    my_file.close()

    my_file = open(ruta_platogen,'r')
    my_data = my_file.read()
    my_data = my_data.replace(old_ip,new_ip)
    my_file.close()
    my_file = open(ruta_platogen,'w')
    my_file.write(my_data)
    my_file.close()
    print("localhost cambiado por " + new_ip + " en los ficheros views")
else:
    mensaje()