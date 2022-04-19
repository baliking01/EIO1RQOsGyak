#include <fcntl.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <errno.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/file.h>
#include <string.h>

extern int errno;

int main()
{
	int end, fd;
	fd = open("EIO1RQ.txt", O_RDWR);
	if(fd < 0)
	{
		printf("Error number %d\n", errno);
		perror("Program");
	}

	char* buffer = (char*) calloc(100, sizeof(char));

	end = read(fd, buffer, 50);
	buffer[end] = '\0';
	printf("The following was read: %s, number of bytes: %d\n", buffer,
	 end);

	int result = lseek(fd, 0, SEEK_SET);
	printf("New cursor position: %d\n", result);

	char text[] = "extra text";
	write(fd, text, strlen(text));
	close(fd);

	return 0;
}
