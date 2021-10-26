package ex0.algo;

import ex0.CallForElevator;
import ex0.Elevator;

import java.util.LinkedList;
import java.util.Queue;

public class FloorQueue {
    LinkedList<Integer> queue;
    int floor;
    int pos;
    CallForElevator c;

    public FloorQueue(Elevator e) {
        this.floor = c.getSrc();
        this.pos = e.getPos();
    }


    public static int range(int floor, int pos) {
        return Math.abs(floor - pos);
    }


    public void push() {
        if (!queue.isEmpty()) {
            boolean added = false;
            for (int i = 0; i < queue.size() && !added; i++) {
                if (range(floor, pos) < range(queue.get(i), pos)) {
                    queue.add(i, floor);
                    added = true;
                }
            }
            if (!added) {
                queue.addLast(floor);
            }
        } else {
            queue.addFirst(floor);
        }


    }


}
