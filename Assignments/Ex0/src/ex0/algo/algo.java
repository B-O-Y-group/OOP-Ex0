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
    private boolean[] _firstTime;


    public algo(Building b) {
        this.building = b;
        E_List = new ArrayList<>();
        this.direction = UP;
        allo = 0;
        _firstTime = new boolean[building.numberOfElevetors()];
        for (int i = 0; i < _firstTime.length; i++) {
            _firstTime[i] = true;
        }

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


        int num = this.building.numberOfElevetors();
        double min = timeToSrc(0, c);
        int best_ele = 0;
        for (int i = 1; i < num; i++) {
            if (min > timeToSrc(i, c)) {
                min = timeToSrc(i, c);
                best_ele = i;
            }
        }

        this.E_List.get(best_ele).setFloor(c.getSrc());
        this.E_List.get(best_ele).push();
        return best_ele;

    }


    public double timeToSrc(int index, CallForElevator c) {
        Elevator curr = this.getBuilding().getElevetor(index);
        int q_size = E_List.get(index).queue.size() + 1;

        double speed = curr.getSpeed();
        double floor_time =
                (curr.getStartTime() + curr.getTimeForClose() + curr.getStopTime() + curr.getTimeForOpen());
        int range = Math.abs(curr.getPos() - c.getSrc());
        return (q_size * floor_time + speed) * range;

    }

    public void index() {
        Elevator[] arr = new Elevator[this.building.numberOfElevetors()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = this.building.getElevetor(i);
        }


    }


    @Override
    public void cmdElevator(int elev) {
        if (!_firstTime[elev] && !E_List.get(elev).queue.isEmpty()) {
            Elevator curr = this.building.getElevetor(elev);
            int pos = curr.getPos();
            int first_F = E_List.get(elev).queue.getFirst();

            if (curr.getState() == UP) {
                if (pos >= first_F) {
                    E_List.get(elev).queue.removeFirst();
                    curr.stop(first_F);
                }
            } else {
                if (pos <= first_F) {
                    E_List.get(elev).queue.removeFirst();
                    curr.stop(first_F);
                }
            }
            if (!E_List.get(elev).queue.isEmpty()) {
                curr.goTo(E_List.get(elev).queue.getFirst());
            }

        } else {
            _firstTime[elev] = false;
            int min = this.building.minFloor(), max = this.building.maxFloor();
            for (int i = 0; i < this.building.numberOfElevetors(); i++) {
                Elevator curr = this.getBuilding().getElevetor(elev);
                int floor = rand(min, max);
                curr.goTo(floor);
            }
        }
    }

    public static int rand(int min, int max) {
        if (max < min) {
            throw new RuntimeException("ERR: wrong values for range max should be >= min");
        }
        int ans = min;
        double dx = max - min;
        double r = Math.random() * dx;
        ans = ans + (int) (r);
        return ans;
    }
}
