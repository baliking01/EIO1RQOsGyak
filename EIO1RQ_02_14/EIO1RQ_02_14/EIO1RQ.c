#include <stdio.h>
#include <stdlib.h>
#include <conio.h>

int main()
{
	FILE *f;
	FILE *fr;
	f = fopen("C:/Users/HP/Desktop/EIO1RQ.txt", "w");
	fprintf(f, "Nagy Balázs - Programtervező Informatikus Bsc - EIO1RQ");
	fclose(f);

	fr = fopen("C:/Users/HP/Desktop/EIO1RQ.txt", "r");
	char line[256];

    while (fgets(line, sizeof(line), fr)) 
    {
        printf("%s", line); 
    }

    getch();
	return 0;
}