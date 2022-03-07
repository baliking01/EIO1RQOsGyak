#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main()
{
	int pid = fork();
	char command[] = "ls";
	char arg1[] = "-la";

	if(pid == 0)
	{
		printf("Child process\n");
		execlp(command, arg1, NULL, NULL);
	}
	else
	{
		printf("Parent process\n");
		wait(NULL);
	}
	return 0;
}
