package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;
import java.util.List;

public class BOYAlgo implements ElevatorAlgo {
    public Building building;
    public int num_E;
    private ArrayList<CallForElevator>[] E_Call;

    public BOYAlgo(Building b) {
        this.building = b;
        this.num_E = this.building.numberOfElevetors();
        this.E_Call = new ArrayList[num_E];
        for (int i = 0; i < num_E; i++) {
            this.E_Call[i] = new ArrayList<>();
        }
        System.out.println(this.E_Call.length);
    }


    @Override
    public Building getBuilding() {
        return this.building;
    }

    @Override
    public String algoName() {
        return "BOY Algo";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        int ans = 0;
        int type = c.getType();
        if (type == 1) {
            ArrayList<Integer> UP = new ArrayList<>();
            for (int i = 0; i < num_E; i++) {
                if (building.getElevetor(i).getState() == 1 && this.building.getElevetor(i).getPos() < c.getSrc()) {
                    if (E_Call[this.building.getElevetor(i).getID()].get(0).getDest() < c.getSrc())
                        UP.add(this.building.getElevetor(i).getID());

                }
            }
            if (UP.size() > 0) {
                ans = UP.get(0);
                for (int i = 1; i < UP.size(); i++) {
                    if (timeCalc(ans, c) < timeCalc(UP.get(i), c)) {
                        ans = UP.get(i);
                    }


                }
            }
        }
        if (type == -1) {
            ArrayList<Integer> DOWN = new ArrayList<>();
            boolean flag = true;
            for (int i = 0; i < num_E; i++) {
                if (building.getElevetor(i).getState() == -1 && this.building.getElevetor(i).getPos() > c.getSrc()) {
                    if (E_Call[this.building.getElevetor(i).getID()].get(0).getDest() > c.getSrc())
                        DOWN.add(this.building.getElevetor(i).getID());
                }
            }
            if (DOWN.size() > 0) {
                ans = DOWN.get(0);
                for (int i = 1; i < DOWN.size(); i++) {
                    if (timeCalc(ans, c) < timeCalc(DOWN.get(i), c)) {
                        ans = DOWN.get(i);
                    }


                }
            }
        }

        ArrayList<Integer> LEVEL = new ArrayList<>();
        for (int i = 0; i < num_E; i++) {
            if (building.getElevetor(i).getState() == 0) {
                LEVEL.add(this.building.getElevetor(i).getID());
            }

        }
        if (!LEVEL.isEmpty()) {
            for (int i = 0; i < LEVEL.size(); i++) {
                if (timeCalc(ans, c) < timeCalc(LEVEL.get(i), c)) {
                    ans = LEVEL.get(i);
                }

            }
        }

        E_Call[ans].add(c);
        return ans;
    }

    private double timeCalc(int ans, CallForElevator c) {
        double time_to_src = 0;

        Elevator curr = this.building.getElevetor(ans);
        double speed = curr.getSpeed();
        double floorTime = speed + curr.getStopTime() + curr.getStartTime() + curr.getTimeForOpen() + curr.getTimeForClose();
        int range = Math.abs(curr.getPos() - c.getSrc());
        if (!E_Call[ans].isEmpty()) {
            time_to_src = c.getTime(E_Call[ans].get(E_Call[ans].size() - 1).getState());

        }
        return (range / speed) + floorTime + time_to_src;
    }


    @Override
    public void cmdElevator(int elev) {
        Elevator curr = this.building.getElevetor(elev);
        if (!E_Call[elev].isEmpty()) {
            if (E_Call[elev].get(0).getState() == 1 && curr.getState() == Elevator.LEVEL) {
                curr.goTo(E_Call[elev].get(0).getSrc());
            }
            if (E_Call[elev].get(0).getState() == 2 && curr.getState() == Elevator.LEVEL) {
                curr.goTo(E_Call[elev].get(0).getDest());
            }
            if (E_Call[elev].get(0).getState() == 3 && curr.getState() == Elevator.LEVEL) {
                E_Call[elev].remove(0);
                if (E_Call[elev].size() > 0) {
                    curr.goTo(E_Call[elev].get(0).getSrc());
                }
            }
        }
    }
}
