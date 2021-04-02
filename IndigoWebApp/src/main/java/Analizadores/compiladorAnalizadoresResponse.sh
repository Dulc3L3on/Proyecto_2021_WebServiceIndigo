#! /bin/bash
echo "INICIANDO LECTURA JFLEX"
java -jar /home/phily/Documentos/Carpeta_estudios/2021/5toSemestre/Compi_1/Laboratorio/tareas/pra-proy/Proyecto1/Proyecto_2021_WebServiceIndigo/jflex-1.8.2/lib/jflex-full-1.8.2.jar LexerResponse.jflex

echo "\nINICIANDO LECTURA CUP"
java -jar /home/phily/Documentos/Carpeta_estudios/2021/5toSemestre/Compi_1/Laboratorio/tareas/pra-proy/Proyecto1/Proyecto_2021_WebServiceIndigo/jflex-1.8.2/lib/java-cup-11b.jar -parser Parser -symbols sym ParserResponse.cup
echo "\nCOMPILANDO SYM"
javac ParserResponseSym.java
