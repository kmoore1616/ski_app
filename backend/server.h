int startServer(int port,struct sockaddr_in* address);
int acceptClients(int sock_fd, struct sockaddr_in address, struct sockaddr_in* connection_address);
int sendResult(char result_code, int connection_fd);
int sendDb(char* name, int connection_fd);
int getData(int connection_fd, char* dataRX[2]);

