package org.example;

import java.util.*;

class C_SCAN {
    static int size;
    static int disk_size = 200;
    public static void CSCAN(int arr[], int head) {
        int seek_count = 0;
        int distance, cur_track;

        Vector<Integer> left = new Vector<Integer>();
        Vector<Integer> right = new Vector<Integer>();
        Vector<Integer> seek_sequence = new Vector<Integer>();

        left.add(0);
        right.add(disk_size - 1);

        for (int i = 0; i < size; i++) {
            if (arr[i] < head)
                left.add(arr[i]);
            if (arr[i] > head)
                right.add(arr[i]);
        }

        Collections.sort(left);
        Collections.sort(right);

        for (int i = 0; i < right.size(); i++) {
            cur_track = right.get(i);
            seek_sequence.add(cur_track);
            distance = Math.abs(cur_track - head);
            seek_count += distance;
            head = cur_track;
        }
        head = 0;
        seek_count += (disk_size - 1);
        for (int i = 0; i < left.size(); i++) {
            cur_track = left.get(i);
            seek_sequence.add(cur_track);
            distance = Math.abs(cur_track - head);
            seek_count += distance;
            head = cur_track;
        }

        System.out.println("총 헤드 이동거리 = " + seek_count);
        System.out.print("탐색 순서 : ");
        for (int i = 0; i < seek_sequence.size(); i++) {
            System.out.print(seek_sequence.get(i)+" ");
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        int head = 50; //초기 헤드의 위치
        System.out.print("작업 개수 : ");
        size = sc.nextInt();
        int arr[] = new int[size]; //작업 개수
        for(int i = 0; i < size; i++) {
            System.out.print("읽을 위치 : ");
            arr[i]= sc.nextInt();
        }
        System.out.println("시작 헤드 위치: "+ head);

        CSCAN(arr, head);
    }
}
  