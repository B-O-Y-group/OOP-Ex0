package ex0.algo;

import ex0.CallForElevator;
import ex0.Elevator;

import java.util.LinkedList;
import java.util.Queue;

public class FloorQueue {
    LinkedList<Integer> queue = new LinkedList<>();
    int floor;
    int pos;

    public FloorQueue(Elevator e) {
        this.pos = e.getPos();
    }

    public void setFloor(int new_src) {
        this.floor = new_src;
    }


    public static int range(int floor, int pos) {
        return Math.abs(floor - pos);
    }


    public void push() {
        System.out.println("first" + queue.toString());
        if (!queue.isEmpty() && !queue.contains(floor)) {
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

        System.out.println(queue.toString());
    }



}
