#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <pthread.h>
#include <string.h>

char *STRING_TO_SEARCH;
int s = 1;
int values[2] = {-1, -1};

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

int searchForWord(char *needle, char *haystack)
{
    int out = 0;
    char *temp = haystack;
    while (temp = strstr(temp, needle))
    {
        out++;
        temp++;
    }
    return out;
}

void enterCriticalSection(){
    while (s<=0);
    s--;
}

void exitCriticalSection(){
    s++;
}

void *threadFunction(void *id){

    char *needle;
    int *myID = (int *) id;
    enterCriticalSection(s);
    if (*myID == 1)
    {
        needle = "easy";
        values[0] = searchForWord(needle, STRING_TO_SEARCH);
    }else if (*myID == 2)
    {
        needle = "polar";
        values[1] = searchForWord(needle, STRING_TO_SEARCH);
    }
    exitCriticalSection(s);

    //wait for the values to be populated
    while(values[0] < 0 || values[1] < 0){
       
    };
    enterCriticalSection();
    printf(
        "Thread %d: There are %d occurrances of 'easy' and %d occurrances of 'polar'.\n",
        *myID, values[0], values[1]
    );
    exitCriticalSection();
}

int main(int argc, char const *argv[])
{
    system("wget -O hw3-data.txt http://undcemcs01.und.edu/~ronald.marsh/CLASS/CS451/hw3-data.txt");
    FILE *fp = fopen("hw3-data.txt", "r");

    STRING_TO_SEARCH = fileToArray(fp);
    fclose(fp);
    
    pthread_t thread1, thread2;
    int id1 = 1; int id2 = 2;
    int thread1Status = pthread_create(&thread1, NULL, &threadFunction, &id1);
    int thread2Status = pthread_create(&thread2, NULL, &threadFunction, &id2);

    void *result;
    pthread_join(thread1, &result);
    pthread_join(thread2, &result);
    free(STRING_TO_SEARCH);
    return 0;
}
