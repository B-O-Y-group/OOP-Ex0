package ex0.algo;

import ex0.CallForElevator;

import java.util.LinkedList;
import java.util.Queue;

public class FloorQueue {
    LinkedList<Integer> queue;
    CallForElevator c;


    public FloorQueue() {
    }


    public int range(int src, int pos) {
        return Math.abs(src - pos);
    }


    public void push(int floor) {
        // int src =   c.getSrc();


        if (!queue.isEmpty()) {
            for (int i = 0; i < queue.size(); i++) {
                if (queue.)
                    queue.add(floor);
            }

        }


    }


}
