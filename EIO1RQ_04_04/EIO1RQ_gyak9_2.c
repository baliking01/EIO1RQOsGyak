#include <stdio.h>
#include <sys/types.h>
#include <signal.h>
#include <unistd.h>


void kezelo(int i){
    printf("Signal handler: %d\n", i);
}

int
main (void){

    printf("PID = %d\n", getpid());
    printf("Signal handler passed: %p\n", signal(SIGTERM, &kezelo));

    while(1){
        printf("step\n");
        sleep(3);
    }
}
