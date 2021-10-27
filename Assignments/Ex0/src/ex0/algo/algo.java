package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class algo implements ElevatorAlgo {
    final static int UP = 1, DOWN = -1;
    public Building building;
    //public int direction;
    int allo;
    private ArrayList<FloorQueue> E_List;
    private boolean _firstTime;
    private ListOfStates E_States;


    public algo(Building b) {
        this.building = b;
        E_List = new ArrayList<>();
        E_States = new ListOfStates();
        // this.direction = UP;
        allo = 0;
        _firstTime = true;
//        _firstTime = new boolean[building.numberOfElevetors()];
//        for(int i=0;i<_firstTime.length;i++) {_firstTime[i] = true;}
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
        int best_ele = bestElevator(E_States, c.getType(), c);


       /* System.out.println("get time :" + c.getTime(c.getState()));


        System.out.println("src :" + c.getSrc());
        System.out.println("dest :" + c.getDest());
        ;

        int num = this.building.numberOfElevetors();
        double min = timeCalc(0, c);
        int best_ele = 0;

        System.out.println("pos :" + this.building.getElevetor(best_ele).getPos());
        System.out.println("state of elevator " + " :" + this.building.getElevetor(best_ele).getState());
        for (int i = 1; i < num; i++) {
            System.out.println("pos of the elevator " + i + " : " + this.building.getElevetor(i).getPos());
            System.out.println("state of elevator " + i + " :" + this.building.getElevetor(i).getState());
            if (min > timeCalc(i, c)) {
                min = timeCalc(i, c);
                best_ele = i;
            }
        }
        System.out.println("pos :" + this.building.getElevetor(best_ele).getPos());*/

        //  if (c.getSrc()<c.getDest() && this.building.getElevetor(best_ele).getState()== 1)
        //    this.building.getElevetor(best_ele).getState()

//        this.E_List.get(best_ele).setFloor(c.getSrc());
//        this.E_List.get(best_ele).push();
//        System.out.println("the chosen elevator is :" + best_ele);
        this.E_List.get(best_ele).push(c.getSrc());
        this.E_List.get(best_ele).push(c.getDest());
        return best_ele;
    }

    public int bestElevator(ListOfStates l, int state, CallForElevator c) {
        int best_ele = 0;
        if (state == -1 && !l.getDOWN().isEmpty()) {
            double best = timeCalc(l.getDOWN().get(0), c);
            for (int i = 1; i < l.getDOWN().size(); i++) {
                if (timeCalc(l.getDOWN().get(i), c) < best) {
                    best = timeCalc(l.getDOWN().get(i), c);
                    best_ele = i;
                }
            }
        } else if (state == 1 && !l.getUP().isEmpty()) {
            double best = timeCalc(l.getUP().get(0), c);
            for (int i = 1; i < l.getUP().size(); i++) {
                if (timeCalc(l.getUP().get(i), c) < best) {
                    best = timeCalc(l.getUP().get(i), c);
                    best_ele = i;
                }
            }
        }
        if (!l.getLEVEL().isEmpty()) {
            double best = timeCalc(l.getLEVEL().get(0), c);
            for (int i = 1; i < l.getLEVEL().size(); i++) {
                if (timeCalc(l.getLEVEL().get(i), c) < best) {
                    best = timeCalc(l.getLEVEL().get(i), c);
                    best_ele = i;
                }
            }
        }
        return best_ele;

    }


    public double timeCalc(int index, CallForElevator c) {
        Elevator curr = this.getBuilding().getElevetor(index);
        LinkedList<Integer> this_q = E_List.get(index).queue;
        int q_size = this_q.size();
        double floor_time =
                (curr.getStartTime() + curr.getTimeForClose() + curr.getStopTime() + curr.getTimeForOpen());
        int num_to_src = 0;
        int des_index = 0;
        int num_to_des = 0;
        int des_range = Math.abs(c.getDest() - c.getSrc());
        int src_range = Math.abs(curr.getPos() - c.getSrc());
        if (!this_q.isEmpty()) {
            for (int i = 0; i < this_q.size(); i++) { // how many stops in the way to the src.
                if (this_q.get(i) < c.getSrc()) {
                    num_to_src++;
                } else if (this_q.get(i) == c.getSrc()) {
                    num_to_src = i;
                    if (i == this_q.size() - 1) {
                        des_index = i;
                    } else {
                        des_index = i + 1;
                    }
                    break;
                } else {
                    num_to_src = i;
                    des_index = i;
                    break;
                }
            }


            for (int j = des_index; j < q_size; j++) {
                if (this_q.get(j) < c.getDest()) {
                    num_to_des++;
                } else if (this_q.get(j) >= c.getDest()) {
                    num_to_des = j;
                    break;
                }
            }
            des_range = Math.abs(c.getDest() - this_q.get(des_index));
        }
        double total_src_speed = (src_range / curr.getSpeed());
        double total_des_speed = (des_range / curr.getSpeed());
        double total_src = total_src_speed + (num_to_src * floor_time);
        double total_des = total_des_speed + (num_to_des * floor_time);

        return (total_src + total_des);

    }

    public void index() {
        Elevator[] arr = new Elevator[this.building.numberOfElevetors()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = this.building.getElevetor(i);
        }


    }


    @Override
    public void cmdElevator(int elev) {


        if (!_firstTime) {
            Elevator curr = this.building.getElevetor(elev);
            int pos = curr.getPos();
            if (curr.getState() == Elevator.LEVEL) {
                if (!E_List.get(elev).queue.isEmpty()) {
                    int first_F = E_List.get(elev).queue.getFirst();
                    int last_F = E_List.get(elev).queue.getLast();
                    if (E_List.get(elev).queue.size() == 1) {
                        curr.goTo(first_F);
                    } else {
                        E_List.get(elev).queue.removeFirst();
                        curr.goTo(last_F);
                        curr.stop(first_F);
                    }
                }
            } else {
                System.out.println("on swap");
                E_States.swap(elev, ListOfStates.findState(elev), "LEVEL");
            }

        } else {
            _firstTime = false;
            int min = this.building.minFloor(), max = this.building.maxFloor();
            for (int i = 0; i < this.building.numberOfElevetors(); i++) {
                Elevator curr = this.getBuilding().getElevetor(elev);
                int floor = rand(min, max);
                String state = dir(curr.getPos() - floor);
                E_States.add(i, state);
                curr.goTo(floor);
                System.out.println("Down: "+E_States.getDOWN().size()+" UP: "+E_States.getUP().size()+" LEVEL: "+ E_States.getLEVEL().size());
            }
        }
    }

    public static String dir(int range) {
        if (range > 0) {
            return "DOWN";
        } else if (range < 0) {
            return "UP";
        }
        return "LEVEL";
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
