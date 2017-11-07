
/* udpclient.c */ 

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <stdlib.h>

int main()
{
int sock;
struct sockaddr_in server_addr;
struct hostent *host;
char send_data[1024];

host= (struct hostent *) gethostbyname((char *)"127.0.0.1");

/*
The hostent structure: describes IP, hostname pairs
I gethostbyname: hostent of a specified machine
I htons, htonl, ntohs, ntohl: byte ordering
I inet pton, inet ntop: conversion of IP numbers between
presentation and strings
*/

/*
structhostent{
char *h-name; //official(canonical)name ofthe host
char **h-aliases; //null terminated array of alternative hostnames
int h-addrtype; //host address type AFINET or AFINET6
int h-length;//4 or 16 bytes
char **h-addrlist; //IPv4 or IPv6 list of addresses
}
Error is return through h error which can be:
I HOST NOT FOUND
I TRY AGAIN
I NO RECOVERY
I NO DATA



*/
if ((sock = socket(AF_INET, SOCK_DGRAM, 0)) == -1)
{
perror("socket");
exit(1);
}

server_addr.sin_family = AF_INET;
server_addr.sin_port = htons(5000);
server_addr.sin_addr = *((struct in_addr *)host->h_addr);
bzero(&(server_addr.sin_zero),8);

   while (1)
   {

    printf("Type Something (q or Q to quit):");
    gets(send_data);

    if ((strcmp(send_data , "q") == 0) || strcmp(send_data , "Q") == 0)
       break;

    else
       sendto(sock, send_data, strlen(send_data), 0,
              (struct sockaddr *)&server_addr, sizeof(struct sockaddr));
     
   }

}

