#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <arpa/inet.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>

int startServer(int port, int sock_fd, struct sockaddr_in address){
    char* host= "127.0.0.1";

    address.sin_family = AF_INET;
    address.sin_port = htons(port);
    address.sin_addr.s_addr = inet_addr(host);

    sock_fd = socket(AF_INET, SOCK_STREAM, 0);
    if(sock_fd == -1){
        perror("Unable to create socket");
        return -1;
    }
    if(bind(sock_fd, (struct sockaddr*)&address, sizeof(address))==-1){
        perror("Failure to bind");
        return -1;
    }
    if(listen(sock_fd, 1) == -1){
        perror("Error on listen");
        return -1;
    }
    return sock_fd;
}

int acceptClients(int sock_fd, struct sockaddr_in address, struct sockaddr_in connection_address){
    printf("Accepting clients\n");
    socklen_t struct_size = sizeof(connection_address);
    int connection_fd = accept(sock_fd, (struct sockaddr*)&address, &struct_size);
    if(connection_fd == -1){
        perror("Error on accept");
        return -1;
    }
    printf("Connection established!\n");

    return connection_fd;
}

int sendResult(char result_code, int connection_fd){
    if(write(connection_fd, &result_code, 1) == -1){
        perror("Bad write on result");
        return -1;
    }
    return 0;
}

int sendDb(char* name, int connection_fd){
    int zip_fd;
    int file_size; 
    char zipCMD[512];
    char buffer[50];
    char* zip_buffer;

    int temp_fd = open("test.txt", O_RDWR | O_CREAT | O_APPEND);
    printf("Opened test.txt\n");
    

    sprintf(zipCMD, "zip zippedDB.zip ./databases/%s.db", name);
    if(system(zipCMD) !=0){ 
        printf("Zip failed...\n");
        return -1;
    }

    zip_fd = open("zippedDB.zip", O_RDONLY);
    if(zip_fd == -1){
        perror("Error opening zip");
    }

    struct stat buf;
    if(fstat(zip_fd, &buf) == -1){
        close(zip_fd);
        perror("Error stat zip...");
        return -1;
    }

    file_size = buf.st_size;
    printf("%d\n", file_size);
    sprintf(buffer, "%d|", file_size);
    
    zip_buffer = (char*)malloc(sizeof(char)*(file_size+strlen(buffer)));
    int header_size = strlen(buffer);

    memcpy(zip_buffer, buffer, header_size);

    int i = 0;
    while(i<file_size){
        int bytes_read = read(zip_fd, zip_buffer+i+header_size, file_size-i);
        if(bytes_read <= 0)
            break;
        i+=bytes_read;
    }

    if(write(connection_fd, zip_buffer, header_size+file_size) == -1)
        perror("Error on TX");


    free(zip_buffer);
    close(zip_fd);
    close(temp_fd);

    return 0;
}



int getData(int connection_fd, char* dataRX[2]){
    char buffer[50];
    int read_count;
    char ret_type;
   
    read(connection_fd, &ret_type, 1);

    read_count = read(connection_fd, buffer, 50);
    if(read_count == -1){
        perror("Error on read...");
        return -1;
    }
    buffer[read_count] = '\0'; // Null terminate
                               
    char* token = strtok(buffer, ",");
    if(token != NULL) strcpy(dataRX[0], token); // Copy string to main's memory

    token = strtok(NULL, ",");
    if(token != NULL) strcpy(dataRX[1], token); //
                                                //
    unlink("zippedDB.zip");
    return ret_type;
}
