package org.example;

import java.util.Arrays;

class Resource{
    static int remain_res1=9;
    static int remain_res2=5;
    static int remain_res3=7;
    int res1;
    int res2;
    int res3;
    public Resource(){}

    public Resource(int i, int i1, int i2) {
        res1=i;
        res2=i1;
        res3=i2;
    }

    boolean requestResource(int res1,int res2,int res3){
        if((remain_res1-res1)>0&(remain_res2-res2)>0&(remain_res3-res3)>0){
            remain_res1-=res1;
            remain_res2-=res2;
            remain_res3-=res3;
            this.res1+=res1;
            this.res2+=res2;
            this.res3+=res2;
            return true;
        }
        return false;
    }

    public void releaseResource() {
        remain_res1+=res1;
        remain_res2+=res2;
        remain_res3+=res3;
        System.out.println(res1+" "+res2+" "+res3+" 자원 해제");
        System.out.println(remain_res1+" "+remain_res2+" "+remain_res3+" 남음");

    }
}

class Process{
    Resource holdingResource;
    Resource neededResource;
    boolean hasRequest;
    Process(int initRes1,int initRes2,int initRes3){
        holdingResource=new Resource();
        holdingResource.requestResource(initRes1,initRes2,initRes3);
        hasRequest=true;
    }
    void getMoreResource(){
        Resource n=neededResource;
        hasRequest= holdingResource.requestResource(n.res1,n.res2,n.res3);
        System.out.println(n.res1+" "+n.res2+" "+n.res3+" 자원 요청");
        if (!hasRequest){
            holdingResource.releaseResource();
            System.out.println(n.res1+" "+n.res2+" "+n.res3+" 요청 성공");
            return;
        }
        System.out.println("요청 실패");
    }

    public void setRequest(Resource resource) {
        this.neededResource = resource;
    }
}

public class Bankers extends Thread{
    Process[] processes=new Process[5];
    int[][] initS={{1,1,1},{2,1,0},{3,0,2},{2,1,0},{0,0,1}};
    int[][] requests={{6,4,3},{2,0,2},{4,1,4},{0,0,3},{5,3,1}};
    Bankers(){
        for (int i = 0; i < 5; i++) {
            int[] init=initS[i];
            processes[i]=new Process(init[0],init[1],init[2]);
            processes[i].setRequest(new Resource(requests[i][0],requests[i][1],requests[i][2]));
        }
    }

    @Override
    public void run() {
        super.run();
        boolean check=false;
        while (!check){
            System.out.println(Arrays.toString(processes));
            for (int i = 0; i < processes.length; i++) {
                if (processes[i].hasRequest){
                    check=false;
                    break;
                }
                check=true;
            }

            for (int i = 0; i < processes.length; i++) {
                Process process = processes[i];
                if (process.hasRequest) {
                    process.getMoreResource();
                }
            }
        }
    }
}
class MainBank{
    public static void main(String[] args) {
        Bankers bankers= new Bankers();
        bankers.start();
    }
}
