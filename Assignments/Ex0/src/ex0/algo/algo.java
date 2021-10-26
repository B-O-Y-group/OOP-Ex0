package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;
import java.util.Arrays;

public class algo implements ElevatorAlgo {
    final static int UP = 1, DOWN = -1;
    public Building building;
    public int direction;
    int allo;
    ArrayList<FloorQueue> E_List;


    public algo(Building b) {
        this.building = b;
        E_List = new ArrayList<>();
        this.direction = UP;
        allo = 0;

        for (int i = 0; i < b.numberOfElevetors(); i++) {
            FloorQueue e = new FloorQueue(this.building.getElevetor(i));
            E_List.add(e);
        }
    }

    @Override
    public Building getBuilding() {
        return this.building;
    }

    @Override
    public String algoName() {
        return "algorithm : boy ";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        int ans = 1;
        this.E_List.get(ans).setFloor(c.getSrc());
        return ans;
    }
    private double dist(int src, int elev) {
        double ans = -1;
        Elevator thisElev = this.building.getElevetor(elev);
        int pos = thisElev.getPos();
        double speed = thisElev.getSpeed();
        int min = this.building.minFloor(), max = this.building.maxFloor();
        double up2down = (max-min)*speed;
        double floorTime = speed+thisElev.getStopTime()+thisElev.getStartTime()+thisElev.getTimeForOpen()+thisElev.getTimeForClose();
        if(elev%2==1) { // up
            if(pos<=src) {ans = (src-pos)*floorTime;}
            else {
                ans = ((max-pos) + (pos-min))*floorTime + up2down;
            }
        }
        else {
            if(pos>=src) {ans = (pos-src)*floorTime;}
            else {
                ans = ((max-pos) + (pos-min))*floorTime + up2down;
            }
        }
        return ans;
    }

    public void index() {
        Elevator[] arr = new Elevator[this.building.numberOfElevetors()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = this.building.getElevetor(i);
        }


    }


    @Override
    public void cmdElevator(int elev) {
        Elevator curr = this.building.getElevetor(elev);
        int pos = curr.getPos();
        int first_F = E_List.get(elev).queue.getFirst();

        if (curr.getState() == UP)
        if (pos >= first_F) {
            E_List.get(elev).queue.removeFirst();
            curr.stop(first_F);
        }
        curr.goTo(E_List.get(elev).queue.getFirst());

    }
}
