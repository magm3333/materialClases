#!/bin/sh


# by Magm 2005

TMP_CLASSPATH=$CLASSPATH

DIR=`dirname $0`
cd $DIR


for i in `find $DIR/drivers -type f -name "*.jar"`
do
   TMP_CLASSPATH=$TMP_CLASSPATH:$i
done

java -classpath $TMP_CLASSPATH:./practico.jar ar.com.magm.j2se.jdbc.practico.CrearPoblarBDPractico scripts


