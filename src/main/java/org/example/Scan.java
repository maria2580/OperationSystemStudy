package org.example;

import java.util.*;

class DiskScheduleThread extends Thread{
    boolean[] job= new boolean[200];
    int point=51;
    int point_adder=-1;
    DiskScheduleThread(int[] initial_job){
        for (int i = 0; i < job.length; i++) {
            job[i]=false;
        }
        for (int i = 0; i < initial_job.length; i++) {
            int point=initial_job[i];
            job[point]=true;
        }
    }
    @Override
    public void run() {
        super.run();
        int all_distance=0;
        int lastPoint=point;
        boolean all_processedflag=false;
        while (true){
            boolean[] job=this.job;
            if (point==0){
                point_adder=1;
                String temp= Arrays.toString(job);
                if (!temp.contains("true")){
                    System.out.println("job 배열이 비어있어 종료합니다");
                    break;
                }
                else {
                    System.out.println("job 배열에 job이 남아있어 계속 동작합니다");
                }
                all_distance+= Math.abs(point-lastPoint);
                lastPoint=point;
            }
            if (point==199){
                point_adder=-1;
                String temp= Arrays.toString(job);
                if (!temp.contains("true")){
                    System.out.println("job 배열이 비어있어 종료합니다");
                    break;
                }
                else {
                    System.out.println("job 배열에 job이 남아있어 계속 동작합니다");
                }
                all_distance+= Math.abs(point-lastPoint);
                lastPoint=point;
            }

            if (job[point]){
                all_distance+= Math.abs(point-lastPoint);
                System.out.println("\t\t\t\t추가된 거리: "+Math.abs(point-lastPoint));
                lastPoint=point;
                job[point]=false;
                System.out.println("\t\t job "+(point+1)+" 처리됨");
            }

            point+=point_adder;
            System.out.println("head "+(point+1)+"로 이동함");
            try{
                Thread.sleep(30);//출력값 보기쉽도록 처리 속도 조절함
            }catch (Exception e){e.printStackTrace();}
        }
        System.out.println("총 이동 거리: "+all_distance);
    }
    synchronized  void addJob(int point){
        job[point]=true;
    }
}


public class Scan {
    public static void main(String[] args) {
        int[] initial_jobs={104,179,39,119,9,124,64,69};
        DiskScheduleThread thread =new DiskScheduleThread(initial_jobs);
        thread.start();
        for (int i = 0; i <10 ; i++) {//도중에 읽기작업 추가하는 코드
            int temp=(int)(Math.random()*200);
            thread.addJob(temp);
            System.out.println("\t\t\t\t\t\t"+(i+1)+" 번째 새로운  잡이 "+(temp+1)+"에 추가되었습니다.");
            try{
                Thread.sleep(900);//0.9초마다 새로운 잡 추가
            }catch (Exception e){e.printStackTrace();}
        }
    }
}