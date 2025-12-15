#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include "./sqlite3.h"

static int callback(void *NotUsed, int argc, char **argv, char **azColName) {
   int i;
   for(i = 0; i<argc; i++) {
      printf("%s = %s\n", azColName[i], argv[i] ? argv[i] : "NULL");
   }
   printf("\n");
   return 0;
}

int queryUsername(char* user){
    sqlite3 *userdb;
    char *zErrMsg = 0;
    int rc;
    char *sql;

    /* Open database */
    rc = sqlite3_open("users.db", &userdb);
    if( rc ) {
        fprintf(stderr, "Can't open database: %s\n", sqlite3_errmsg(userdb));
        return(-1);
    }

    char* queryCMD = "SELECT id FROM USERS WHERE USERNAME=?;";
    sqlite3_stmt* stmt;
    rc = sqlite3_prepare_v2(userdb, queryCMD, -1, &stmt, NULL);
    if (rc != SQLITE_OK) {
        fprintf(stderr, "Prepare error: %s\n", sqlite3_errmsg(userdb));
        sqlite3_close(userdb);
        return -1;
    }
    sqlite3_bind_text(stmt, 1, user, -1, SQLITE_STATIC);
    rc = sqlite3_step(stmt);
    if(rc == SQLITE_ROW){
        printf("Login found!\n");
        return 1;
    }else{
        return 0;
    }

    sqlite3_finalize(stmt);
    sqlite3_close(userdb);
}


int createTableIfNotExists(){
    sqlite3 *userdb;
    char *zErrMsg = 0;
    int rc;
    char* sql;

    rc = sqlite3_open("users.db", &userdb);
    if(rc){
        perror("Error opening database...");
        return -1;
    }

    printf("db created!\n");

   /* Create SQL statement */
   sql = "CREATE TABLE USERS("  \
      "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," \
      "USERNAME           TEXT    NOT NULL," \
      "PASSWORD           TEXT    NOT NULL," \
      "DATABASE           TEXT    NOT NULL);";

   /* Execute SQL statement */
   rc = sqlite3_exec(userdb, sql, callback, 0, &zErrMsg);
   
   if( rc != SQLITE_OK ){
      fprintf(stderr, "SQL error: %s\n", zErrMsg);
      sqlite3_free(zErrMsg);
   } else {
      fprintf(stdout, "Table created successfully\n");
   }
   sqlite3_close(userdb);

    return 0;
}

int insertUser(char* user, char* pass){
    sqlite3 *userdb;
    char *zErrMsg = 0;
    int rc;
    char *sql;
    char buffer[50];

    if(queryUsername(user)==1){
        printf("User already exists...\n");
        return 1;
    }
    
    sprintf(buffer, "%s.db", user);
    
    rc = sqlite3_open("users.db", &userdb);
    if( rc ) {
        fprintf(stderr, "Can't open database: %s\n", sqlite3_errmsg(userdb));
        return(-1);
    } 

    char* insertCMD = "INSERT INTO USERS (USERNAME, PASSWORD, DATABASE) VALUES (?, ?, ?)";
    sqlite3_stmt* stmt;
    rc = sqlite3_prepare_v2(userdb, insertCMD, -1, &stmt, NULL);
    if (rc != SQLITE_OK) {
        fprintf(stderr, "Prepare error: %s\n", sqlite3_errmsg(userdb));
        sqlite3_close(userdb);
        return -1;
    }
    sqlite3_bind_text(stmt, 1, user, -1, SQLITE_STATIC);
    sqlite3_bind_text(stmt, 2, pass, -1, SQLITE_STATIC);
    sqlite3_bind_text(stmt, 3, buffer, -1, SQLITE_STATIC);
    rc = sqlite3_step(stmt);
    if (rc != SQLITE_DONE) {
        fprintf(stderr, "Execution error: %s\n", sqlite3_errmsg(userdb));
    } else {
        printf("User %s inserted successfully!\n", user);
    }

    // 5. Finalize and Close
    sqlite3_finalize(stmt);
    sqlite3_close(userdb);

    return 0;
}

// Assuming database_ptr is a global or passed-in char*
int checkLogin(char* user, char* pass, char* database_ptr) {
    sqlite3 *userdb;
    sqlite3_stmt* stmt;
    int result = -1; // Default to error/not found

    if (sqlite3_open("users.db", &userdb) != SQLITE_OK) return -1;

    char* queryCMD = "SELECT ID, DATABASE FROM USERS WHERE USERNAME=? AND PASSWORD=?;";
    
    if (sqlite3_prepare_v2(userdb, queryCMD, -1, &stmt, NULL) != SQLITE_OK) {
        sqlite3_close(userdb);
        return -1;
    }

    sqlite3_bind_text(stmt, 1, user, -1, SQLITE_STATIC);
    sqlite3_bind_text(stmt, 2, pass, -1, SQLITE_STATIC);

    if (sqlite3_step(stmt) == SQLITE_ROW) {
        printf("Login found!\n");
        
        // 1. Get the ID (Column 0)
        result = sqlite3_column_int(stmt, 0);

        // 2. Safely copy the string (Column 1)
        const char* db_name = (const char*)sqlite3_column_text(stmt, 1);
        if (db_name) {
            // Use strdup or malloc + strcpy to make a permanent copy
            database_ptr = strdup(db_name); 
        }
    } else {
        printf("Login failed.\n");
        result = -2; // Or whatever value represents "not found"
    }

    sqlite3_finalize(stmt);
    sqlite3_close(userdb);
    return result;
}
