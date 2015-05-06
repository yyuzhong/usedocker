#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <pthread.h>
#include <queue>

#define M 3
#define K 2
#define N 3

int matrix1[M][K];
int matrix2[K][N];
int results[M][N];

struct arg_struct
{
    int row;
    int col;
};

void *calc(void *args) {
  int row, col;
  struct arg_struct *para = (struct arg_struct *)args;
  row = para->row;
  col = para->col;
  //printf("Compute %d, %d\n",row,col);
  for (int i = 0; i < K;i++) {
      results[row][col] += matrix1[row][i] * matrix2[i][col];
  }
  pthread_exit(NULL);
}

int main(int argc, char *argv[]) {
  int rows, cols, status;
  void *thread_status;

  srand(time(NULL));
  for (rows = 0; rows < M; rows++) {
    for (cols = 0; cols < K; cols++) {
      matrix1[rows][cols] = rand()%10;
      printf(" %d, ",matrix1[rows][cols]);
    }
    printf("\n");
  }
  printf("\n");

  for (rows = 0; rows < K; rows++) {
    for (cols = 0; cols < N; cols++) {
      matrix2[rows][cols] = rand()%10;
      printf(" %d, ",matrix2[rows][cols]);
    }
    printf("\n");
  }
  printf("\n");

  for (rows = 0; rows < M; rows++) {
    for (cols = 0; cols < N; cols++) {
      results[rows][cols] = 0;
      //printf(" %d, ",results[rows][cols]);
    }
    //printf("\n");
  }
  //printf("\n");

  pthread_t threads[M*N];
  struct arg_struct para[M*N];
  int m,n;
  for (m = 0; m < M; m++) {
      for (n = 0; n < N; n++) {
         para[m*N+n].row = m;
         para[m*N+n].col = n;
         //printf("Create thread: %d, %d, num=%d\n",m,n,m*N+n); 
         status = pthread_create(&threads[m*N+n], NULL, calc, (void*)&(para[m*N+n]));
         if (status) {
             perror("Could not create thread: ");
             exit(1);
         } 
      }
  }
  for (m = 0; m < M; m++) {
      for (n = 0; n < N; n++) {
          status = pthread_join(threads[m*N+n], &thread_status);
          if (status) {
              perror("Could not join thread: ");
              exit(1);
          }
      }
  }

  for (rows = 0; rows < M; rows++) {
    for (cols = 0; cols < N; cols++) {
      printf(" %d, ",results[rows][cols]);
    }
    printf("\n");
  }

  pthread_exit(NULL);
  return 0;
}
