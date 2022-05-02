#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <unistd.h>


#define SHM_SIZE 1024

int main()
{
	key_t key;
	int shmid;
	char *buffer, *s;
	int mode;
	char c;

	key = 2345;

	if((shmid = shmget(key, SHM_SIZE, 0644 | IPC_CREAT)) == -1)
	{
		perror("shmget");
		exit(1);
	}

	buffer = shmat(shmid, NULL, 0);
	if(buffer == (char* )(-1))
	{
		perror("shmat");
		exit(1);
	}

	while(*buffer != '*')
	{
		printf("Buffer is empty! Waiting...\n");
		sleep(1);
	}

	for(s = buffer + 1; *s != '@'; s++)
	{
		putchar(*s);
	}
	putchar('\n');

	shmdt(buffer);
	shmctl(shmid, IPC_RMID, NULL);

	return 0;
}
