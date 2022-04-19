#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>

void doNothing();
void handleSignals(int sig);

int main()
{
	printf("PID: %d\n", getpid());

	signal(SIGINT, handleSignals);
	signal(SIGQUIT, handleSignals);
	signal(SIGALRM, doNothing);

	unsigned time = 10;
	while(1)
	{
		alarm(time);
		printf("Waiting...\n");
		pause();
	}

	return 0;
}

void doNothing() {}

void handleSignals(int sig)
{
	if(sig == SIGQUIT)
	{
		printf("User gave 'SIGQUIT' - %d\n", sig);
	}
	else if(sig == SIGINT)
	{
		printf("User gave 'SIGINT' - %d\n", sig);
		signal(SIGINT, SIG_DFL);
	}
}
