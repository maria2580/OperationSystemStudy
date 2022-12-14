package org.example;

public class bestfit {
   
    static void bestFit(int blockSize[], int processSize[]) {
        int allocation[] = new int[processSize.length];
        for (int i = 0; i < allocation.length; i++)
            allocation[i] = -1;
     
        for (int i=0; i<processSize.length; i++) {
            int bestIdx = -1;
            for (int j=0; j<blockSize.length; j++) {
                if (blockSize[j] >= processSize[i]) {//블럭 사이즈가 프로세스 크기보다는 커야함
                    if (bestIdx == -1)//처음에는 bestidx가 -1이므로 으로 세팅
                        //이거만 수행하면 최초 적합임
                        bestIdx = j;
                    else if (blockSize[bestIdx] < blockSize[j])// > 이면 최악 적합 < 이면 최적적합
                        bestIdx = j;
                }
            }
            if (bestIdx != -1) {//베스트 인덱스 값이 잘 정의 되면 블록으로 부터 해당 프로세서 사이즈 제고
                allocation[i] = bestIdx;
                blockSize[bestIdx] -= processSize[i];
            }
        }
       
        System.out.println("\nProcess No.\tProcess Size\tBlock no.");
        for (int i = 0; i < processSize.length; i++){
            System.out.print("   " + (i+1) + "\t\t" + processSize[i] + "\t\t");
            if (allocation[i] != -1)
                System.out.print(allocation[i] + 1);
            else
                System.out.print("Not Allocated");
            System.out.println();
        }
    }
      
    // Driver Method
    public static void main(String[] args){
         int blockSize[] = {17, 7, 15, 50};
         int processSize[] = {12,30,5};
         int m = blockSize.length;
         int n = processSize.length;
           
         bestFit(blockSize, processSize);
    }
}