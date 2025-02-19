package com.example.finalproject.models;

public class Banker {
    // Java program to illustrate Banker's Algorithm
        // Number of processes
        // Number of resources
         private int R=4 ;
         private int P;
         private int n;

         private int[] safeSeq;
         private int[][] work_array;
        public Banker(){

        }
         public Banker(int P,int n, int[][] work,int[] safeSeq){
             this.P=P;
             this.n=n;
             this.work_array=work;
             this.safeSeq=safeSeq;

         }

    public int[] getSafeSeq() {
        return safeSeq;
    }

    public void setSafeSeq(int[] safeSeq) {
        this.safeSeq = safeSeq;
    }

    public int[][] getWork_array() {
        return work_array;
    }

    public void setWork_array(int[][] work_array) {
        this.work_array = work_array;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setP(int p) {
        this.P = p;
    }

    public int getP() {
        return P;
    }
    // Function to find the need of each process

        public int [][] set_allot(int [][] allot,int[]req){
            if (n >= 0 && n < allot.length && req != null && req.length == allot[n].length) {
                for (int i = 0; i < req.length; i++) {
                    allot[n][i] += req[i];
                }
            } else {
                // Xử lý lỗi nếu có
                System.out.println("Invalid index or request array");
            }
            return allot;

        }
        public int[][] calculateNeed(int need[][], int maxm[][],
                                  int allot[][])
        {
            // Calculating Need of each P
            for (int i = 0 ; i < P ; i++)
                for (int j = 0 ; j < R ; j++)

                    // Need of instance = maxm instance -
                    //				 allocated instance
                    need[i][j] = maxm[i][j] - allot[i][j];
            return need;
        }
        public int[][] printWork(int[][] work_array,int[]work,int row){
            System.out.println("test");
            System.out.println(row);
            for(int w=0;w< work.length;w++){
                System.out.print(work[w]+" ");
            }
            for(int i=0;i< work.length;i++){
                 work_array[row][i]=work[i];
                 }
             return work_array;

        }

        // Function to find the system is in safe state or not
         public boolean isSafe(int avail[], int maxm[][],
                              int allot[][],int req[])
        {
            allot=set_allot(allot,req);
            int [][]need = new int[P][R];

            int[][] work_arr=new int[P][R];

            // Function to calculate need matrix
            calculateNeed(need, maxm, allot);

            // Mark all processes as infinish
            boolean []finish = new boolean[P];

            // To store safe sequence
            safeSeq = new int[P];

            // Make a copy of available resources
            int []work = new int[R];
            for (int i = 0; i < R ; i++) {
                work[i] = avail[i]-req[i];

            }
            // While all processes are not finished
            // or system is not in safe state.
            int count = 0;
            while (count < P)
            {
                // Find a process which is not finish and
                // whose needs can be satisfied with current
                // work[] resources.
                boolean found = false;
                for (int p = 0; p < P; p++)
                {
                    // First check if a process is finished,
                    // if no, go for next condition
                    if (finish[p] == false)
                    {
                        // Check if for all resources of
                        // current P need is less
                        // than work
                        int j;
                        for (j = 0; j < R; j++)
                            if (need[p][j] > work[j])
                                break;

                        // If all needs of p were satisfied.
                        if (j == R)
                        {
                            // Add the allocated resources of
                            // current P to the available/work
                            // resources i.e.free the resources
                            for (int k = 0 ; k < R ; k++)
                                work[k] += allot[p][k];

                            // Add this process to safe sequence.
                            safeSeq[count++] = p+1;

                            // Mark this p as finished
                            finish[p] = true;
                            work_array=printWork(work_arr,work,count-1);
                            found = true;
                            System.out.println("---------------");
                            for(int i=0;i<P;i++) {
                                for (int x = 0; x < R; x++) {
                                    System.out.print(work_array[i][x]);
                                    System.out.print(",");
                                }
                                System.out.println(" ");
                            }

                        }
                    }
                }

                // If we could not find a next process in safe
                // sequence.
                if (found == false)
                {
                    System.out.print("System is not in safe state");
                    return false;
                }
            }

            System.out.print("System is in safe state.\nSafe"
                    +" sequence is: ");
            for (int i = 0; i < P ; i++)
                System.out.print(safeSeq[i] + " ");

            return true;
        }



}

        // Driver code





