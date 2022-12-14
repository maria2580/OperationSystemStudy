package org.example;

import java.util.ArrayList;
import java.util.Objects;

class Page2{
    int myIndex;
    long usedTime;
    Page2(int myIndex){
        this.myIndex=myIndex;
    }
}
class Memory2 {
    Page2[] pagesInMemory;
    int index;
    int index2;

    Memory2(int memSize) {
        pagesInMemory = new Page2[memSize];
        index = 0;
        index2 = 0;
    }

    boolean isfull() {
        return pagesInMemory.length == index;
    }

    void exec(Page2 execPage) {
        System.out.println("찾는 페이지: " + execPage.myIndex);
        boolean isfound = false;
        for (Page2 page : pagesInMemory) {
            if (Objects.isNull(page)) {
                continue;
            }
            if (page.myIndex == execPage.myIndex) {
                page.usedTime=System.currentTimeMillis();
                isfound = true;
            }
        }
        if (!isfound) {//찾는 페이지가 없을 시 실행
            if (!isfull()) {
                System.out.println("페이지 폴트 상태: 빈 공간이 있어 " + index + " 자리에 " + execPage.myIndex + "번 페이지를 삽입합니다.");
                pagesInMemory[index] = execPage;
                execPage.usedTime=System.currentTimeMillis();
                index++;
            } else {
                int oldJobidx=-1;
                for (int j=0; j<pagesInMemory.length; j++) {
                        if (oldJobidx == -1)//오래된 job을 찾음
                            oldJobidx = j;
                        else if (pagesInMemory[oldJobidx].usedTime > pagesInMemory[j].usedTime)//j의 used타임이 더 작으면 j가 올드잡임
                            oldJobidx = j;
                    }

                System.out.println("페이지 폴트 상태: 메모리" + oldJobidx + " 자리에 " + execPage.myIndex + "번 페이지 삽임");
                pagesInMemory[oldJobidx] = execPage;
                execPage.usedTime=System.currentTimeMillis();
            }
        }
        System.out.println("현재 메모리 상태 ");

        for (int i = 0; i < pagesInMemory.length; i++) {
            if (Objects.isNull(pagesInMemory[i])) {
                continue;
            }
            System.out.println(pagesInMemory[i].myIndex + ":\t used time=" + pagesInMemory[i].usedTime);
        }
        System.out.println("\n\n");
    }

}

public class LRU2 {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Page2> pagesInHardDisk=new ArrayList<>();
        Memory2 memory=new Memory2(3);//메모리 크기 3
        int[] arr = {8,1,2,3,1,4,1,5,3,4,1,4,3,2,3,1,2,8,1,2};
        for (int i = 0; i <8; i++) {
            pagesInHardDisk.add(new Page2(i+1));
        }//하드디스크에 1~8까지 페이지 초기화

        for (int i = 0; i < arr.length; i++) {
            int neededPage=arr[i];
            memory.exec(pagesInHardDisk.get(neededPage-1));
            Thread.sleep(1);
        }
    }
}
