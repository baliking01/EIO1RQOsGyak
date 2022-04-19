#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>


#define SIZE 128

int main()
{
    int pid;
    int fds;

    mkfifo("EIO1RQ", 0666);

    pid = fork();

    if (pid == 0) {
        char msg[] = "Nagy Balázs";
        fds = open("EIO1RQ", O_WRONLY);
        write(fds, msg, SIZE+2);
        close(fds);
        wait(NULL);
        return 0;
    }

    char inbuf[SIZE];
    fds = open("EIO1RQ", O_RDONLY);
    read(fds, inbuf, SIZE+2);
    close(fds);
    unlink("EIO1RQ");

    printf("Read text: %s\n", inbuf);

    wait(NULL);
    return 0;
}
