package org.example;

import java.util.ArrayList;
import java.util.Objects;

class Page{
    int myIndex;
    boolean isRefferenced;
    Page(int myIndex){
        this.myIndex=myIndex;
        this.isRefferenced=false;
    }
    void defaultJob(){
        isRefferenced=false;
    }
    void setRefferenced(){
        isRefferenced=true;
    }
}
class Memory{
    Page[] pagesInMemory;
    int index;
    int index2;
    Memory(int memSize){
        pagesInMemory= new Page[memSize];
        index=0;
        index2=0;
    }
    boolean isfull(){return pagesInMemory.length==index;}
    void exec(Page execPage){
        System.out.println("찾는 페이지: "+ execPage.myIndex);
        boolean isfound=false;
        for (Page page :pagesInMemory) {
            if (Objects.isNull(page)){continue;}
            if (page.myIndex == execPage.myIndex) {
                page.setRefferenced();
                isfound = true;
            } else {

            }
        }
        if (!isfound) {//찾는 페이지가 없을 시 실행
             if(!isfull()) {
                 System.out.println("페이지 폴트 상태: 빈 공간이 있어 "+index+" 자리에 "+execPage.myIndex+"번 페이지를 삽입합니다.");
                pagesInMemory[index] = execPage;
                 execPage.setRefferenced();
                 index++;
            }else{
                for (int i = 0; i < pagesInMemory.length*2; i++,index2++) {
                    int point = index2% pagesInMemory.length;
                    Page target = pagesInMemory[point];
                    System.out.println("페이지 폴트 상태: 프레임"+(point+1)+" 자리에 교체 요청"+index2);
                    if (Objects.isNull(target)){
                        continue;
                    }
                    if (target.isRefferenced == true) {
                        target.defaultJob();//참조비트가 1이면 0으로 만들고 넘어감
                        System.out.println("페이지 폴트 상태: 프레임" +(point+1)+"자리에 든 페이지"+ target.myIndex + "의 참조비트를 1에서 0으로 수정");
                    } else {
                        System.out.println("페이지 폴트 상태: 프레임" + (point+1) + " 자리에 " + execPage.myIndex + "번 페이지 삽임");
                        pagesInMemory[point] = execPage;
                        execPage.setRefferenced();
                        break;
                    }
                }
            }
        }
        System.out.println("현재 메모리 상태 ");

        for (int i = 0; i < pagesInMemory.length; i++) {
            if (Objects.isNull(pagesInMemory[i])){continue;}
            System.out.println(pagesInMemory[i].myIndex+":\t is refferenced="+pagesInMemory[i].isRefferenced);
        }
        System.out.println("\n\n");
    }
}
public class SecondChance {
    public static void main(String[] args) {
        ArrayList<Page> pagesInHardDisk=new ArrayList<>();
        Memory memory=new Memory(3);//메모리 크기 3
        int[] arr = {8,1,2,3,1,4,1,5,3,4,1,4,3,2,3,1,2,8,1,2};
        for (int i = 0; i <8; i++) {
            pagesInHardDisk.add(new Page(i+1));
        }//하드디스크에 1~8까지 페이지 초기화

        for (int i = 0; i < arr.length; i++) {
            int neededPage=arr[i];
            memory.exec(pagesInHardDisk.get(neededPage-1));
        }
    }
}
