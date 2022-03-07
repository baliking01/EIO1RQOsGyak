#include <stdlib.h>
#include <stdio.h>
#include <sys/wait.h>
#include <unistd.h>

int main()
{
	int pid;
	pid = fork();
	if(pid == 0)
	{
		printf("Child_1\n");
		exit(0);
	}
	else{
		printf("Parent_1\n");
		wait(NULL);
	}

	pid = fork();
	if(pid == 0)
        {
                printf("Child_2\n");
                abort;
        }
        else{
                printf("Parent_2\n");
		wait(NULL);
        }

	pid = fork();
	int a;
	if(pid == 0)
        {
                printf("Child_3\n");
                a = 1/0;
        }
        else{
                printf("Parent_3\n");
		wait(NULL);
        }

	return 0;
}
