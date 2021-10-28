package ex0.algo.DataBases;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;

public class CallList {
    public ArrayList<CallForElevator>[] List;
    private Elevator e;

    public CallList(int capacity) {
        this.List = new ArrayList[capacity];
        for (int i = 0; i < capacity; i++) {
            List[i] = new ArrayList<CallForElevator>();
        }
    }






//    public int getSrc(int el, int i) {
//        return List.get(el).get(i).getSrc();
//    }
//
//    public int getDest(int el, int i) {
//        return this.List.get(el).get(i).getDest();
//    }
//
//    public int getState(int el, int i) {
//        return this.List.get(el).get(i).getState();
//    }
//
//    public int getType(int el, int i) {
//        return this.List.get(el).get(i).getType();
//    }
//
//    public double getTime(int el, int i, int state) {
//        return this.List.get(el).get(i).getTime(state);
//    }


}
