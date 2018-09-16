#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

int fileSize(FILE *fp)
{
    int out = 0;
    while (fgetc(fp) != EOF)
    {
        out++;
    }
    return out;
}

char* fileToArray(FILE *fp){
    int filesize = fileSize(fp);
    char* out = malloc(filesize * sizeof(char));
    rewind(fp);
    for (int i = 0; i < filesize; i++)
    {
        out[i] = (char) tolower(fgetc(fp));
    }
    return out;
}

int main(int argc, char const *argv[])
{
    // system("wget http://undcemcs01.und.edu/~ronald.marsh/CLASS/CS451/hw3-data.txt");
    FILE *fp = fopen("hw3-data.txt", "r");
    printf("%s", fileToArray(fp));
    return 0;
}

