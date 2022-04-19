#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

#define SIZE 20

int main()
{
	char inBuffer[SIZE];

	int p[2], nbytes , pid;

	if(pipe(p) < 0){
		perror("Pipe error");
		exit(1);
	}


	/*Child*/
	pid = fork();

	if (pid == 0)
	{
		printf("Write to Child \n");
		write(p[1], "writing...", SIZE);
		printf("Written to: Child\n");

	}

	/*Parent*/

	else if (pid > 0)
	{
		wait(NULL);
		read(p[0], inBuffer, SIZE);
		printf("Read from Parent %s \n", inBuffer);
	}

	return 0;
}
