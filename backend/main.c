#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <pthread.h>
#include <unistd.h>
#include "./database.h"
#include "./server.h"

int main(int argc, char** argv){
    int return_code;
    int connection_fd;
    char* login_dat[2];
    struct sockaddr_in address;
    struct sockaddr_in connection_address;
    int port = atoi(argv[1]);
    int sockfd = startServer(port, &address);
    char* database_name;

    return_code = createTableIfNotExists();
    if(return_code==-1){
        printf("Error on create table\n");
        return -1;
    }

    login_dat[0] = (char*)malloc(sizeof(char)*50);
    login_dat[1] = (char*)malloc(sizeof(char)*50);

    if(sockfd == -1){
        printf("Server start failed\n");
        return -1;
    }
    printf("Server started on port %d\n", port);
    
    connection_fd = acceptClients(sockfd, address, &connection_address);
    if(connection_fd == -1){
        printf("Accept failed...\n");
        return -1;
    }

    return_code = getData(connection_fd, login_dat);

    if(return_code == -1){
        printf("Error on get login\n");
        sendResult('2', connection_fd);
    }else if(return_code == '0'){
        // Signup
        printf("Signing up!\n");
        return_code = insertUser(login_dat[0], login_dat[1]);
        if(return_code==0){
            sendResult('0', connection_fd);
        }else{
            sendResult('1', connection_fd);
        }
    }else{
        // Login
        
        return_code = checkLogin(login_dat[0], login_dat[1], database_name); 
        printf("%s, %s, %d\n", login_dat[0], login_dat[1], return_code);
        if(return_code == -1){
            printf("Check login failed\n");
            sendResult('2', connection_fd);
        }else if(return_code==-2){
            printf("Login not found\n");
            sendResult('0', connection_fd);
        }else{
            printf("Login id: %d\n", return_code);
            sendResult('1', connection_fd);

            return_code = sendDb(login_dat[0], connection_fd);

            if(return_code == -1){
                printf("Failed on db send\n");
                return -1;
            }
        }
    }
    
    return 0;
}
