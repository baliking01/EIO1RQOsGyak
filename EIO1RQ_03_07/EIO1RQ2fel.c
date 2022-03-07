#include <stdio.h>
#include <stdlib.h>

int main()
{
	char buffer[255];
	do{
		scanf("%s", buffer);
		if(*buffer == 'q')
		{
			return 0;
		}
		system(buffer);
	}while(1);
	return 0;
}
