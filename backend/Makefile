target: server.o db.o main.o
	gcc -o backend main.o db.o server.o -l sqlite3

main.o: ./main.c
	gcc -c -g -o main.o main.c

server.o: ./server.c
	gcc -c -g -o server.o server.c 

db.o: ./database.c 
	gcc -c -g -o db.o database.c
