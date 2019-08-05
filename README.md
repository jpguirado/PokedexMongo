# Pokedex Mongo

Proyecto **Spring Boot** de Pokedex implementada con **Mustache** y base de datos **Mongo DB.**


# Instrucciones de ejecución

## Instalación Mongo DB

El primer paso es instalar la última version de Mongo DB Community Server desde la página oficial https://www.mongodb.com/download-center/community. Si aceptas la instalacíon de Mongo DB Compass podrás ver el contenido de la base de datos de una forma más gráfica

![alt text](https://i.imgur.com/MPBMCrk.jpg)
## Importar el CSV con los datos de los pokemons
1.  Nos vamos a la carpeta de binarios de mongo, por defecto C:\Program Files\MongoDB\Server\4.0\bin
2.  Pegamos ahi el CSV de los pokemons.
3.  Ejecutamos este comando de shell en la carpeta de los binarios C:\Program Files\MongoDB\Server\4.0\bin>mongoimport -d pokemonDB -c pokemonC --type CSV --file pokemon.csv --headerline

Una vez importado, si nos vamos a MongoDB Compass y nos conectamos a localhost:27017 podremos ver nuestra base de datos listada.

![alt text](https://i.imgur.com/JP9453l.jpg) 
## Ejecutar el archivo jar y ejecutar la aplicación en navegador
Una vez tenemos la base de datos lista, nos descargamos el archivo jar de la carpeta pokedexMongo y abrimos una shell en la carpeta donde lo hemos descargado. Ejecutamos el siguiente comando java -jar pokedexMongo-0.0.1-SNAPSHOT.jar

![alt text](https://i.imgur.com/EPTAY51.jpg)

La aplicación arrancará y ya podremos conectarnos usando un navegador web mediante la direccion 127.0.0.1:8080

![alt text](https://i.imgur.com/wxFla6f.jpg)

![alt text](https://i.imgur.com/bI6OE5P.jpg)
