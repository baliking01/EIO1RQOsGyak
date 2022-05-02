#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <unistd.h>
#include <math.h>

#define SHM_SIZE 1024
#define SHM_KEY 2345

int isValidTriangle(int a, int b, int c)
{
	if(a + b <= c || a + c <= b || c + b <= a)
	{
		return 0;
	}
	return 1;
}

double getArea(int a, int b, int c)
{
	double k = ((double)a + b + c) / 2;
	return sqrt(k*(k - a)*(k - b)*(k - c));
}

int main()
{
	int side_a, side_b, side_c;
	FILE* fp = fopen("triangle.txt", "r");
	FILE* fp2;
	char *buffer, *s, *token;
	char sides[6];
	int shmid;
	char c;

	if(fp == NULL)	//Check if the file exists
	{
		printf("Error! File not found!\n");
		exit(-1);
	}

	// Select and attach to memory segment

	shmid = shmget(SHM_KEY, SHM_SIZE, 0644);  // Get SHM ID
	if(shmid == -1)
	{
		perror("shmget");
		exit(1);
	}

	buffer = shmat(shmid, NULL, 0);
	if(buffer == (char*) -1)
	{
		perror("shmat");
		exit(1);
	}

	s = buffer;
	s++;
	/*for(c = 'a'; c <= 'z'; c++)
	{
		*s++ = c;
	}*/

	while(!feof(fp))
	{
		c = fgetc(fp);
		if(c == '\n')
		{
			break;
		}
		*s++ = c; // Writing to SHM
	}
	fclose(fp);	//Close file
	*s = '@';	//Ending character
	*buffer = '*';	//Wrtiing to SHM complete

	memcpy(sides, &buffer[1], sizeof(buffer) - 2);
	sides[5] = '\n';

	token = strtok(sides, " ");
	side_a = atoi(token);

	token = strtok(NULL, " ");
	side_b = atoi(token);

	token = strtok(NULL, " ");
	side_c = atoi(token);

	printf("if:\n");
	fp2 = fopen("result.txt", "w");

	if(isValidTriangle(side_a, side_b, side_c))
	{
		fprintf(fp2, "Sides: %d, %d, %d Area: %.2lf Perimeter: %d\n",
		side_a, side_b, side_c,	// Sides
		getArea(side_a, side_b, side_c), //Heron Formula
		side_a + side_b + side_c);	//Perimeter
	}
	else
	{
		fprintf(fp2, "Sides: %d, %d, %d Area: -1 Perimeter -1\n",
		side_a, side_b, side_c);
	}

	fclose(fp2);
	return 0;
}
