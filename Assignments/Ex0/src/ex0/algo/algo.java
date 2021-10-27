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
        //int best_ele = 0;
        System.out.println("TIMEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE " + c.getTime(0));
        int best_ele = bestElevator(E_States, c.getType(), c);
        this.E_List.get(best_ele).push(c.getSrc());
        this.E_List.get(best_ele).push(c.getDest());
        if (this.building.getElevetor(best_ele).getState() == Elevator.LEVEL) {
            if (c.getType() == UP) {
                E_States.swap(best_ele, "LEVEL", "UP");
            } else {
                E_States.swap(best_ele, "LEVEL", "DOWN");
            }
        }
        return best_ele;
    }

    public int bestElevator(ListOfStates l, int state, CallForElevator c) {
        Building curr = this.getBuilding();
        int best_ele = 0;
        if (state == -1 && !l.getDOWN().isEmpty()) {

            ArrayList<Integer> above_src = new ArrayList<>();
            for (int i = 0; i < l.getDOWN().size(); i++) {
                if (curr.getElevetor(l.getDOWN().get(i)).getPos() >= c.getSrc()) {
                    above_src.add(l.getDOWN().get(i));
                }
            }
            best_ele = getBest_ele(c, best_ele, above_src);
        } else if (state == 1 && !l.getUP().isEmpty()) {
            ArrayList<Integer> under_src = new ArrayList<>();
            for (int i = 0; i < l.getUP().size(); i++) {
                if (curr.getElevetor(l.getUP().get(i)).getPos() <= c.getSrc()) {
                    under_src.add(l.getUP().get(i));
                }
            }
            best_ele = getBest_ele(c, best_ele, under_src);
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

    private int getBest_ele(CallForElevator c, int best_ele, ArrayList<Integer> dir_to_src) {
        if (!dir_to_src.isEmpty()) {
            double best = timeCalc(dir_to_src.get(0), c);

            for (int i = 1; i < dir_to_src.size(); i++) {
                if (timeCalc(dir_to_src.get(i), c) < best) {
                    best = timeCalc(dir_to_src.get(i), c);
                    best_ele = i;
                }
            }
        }
        return best_ele;
    }
//TODO time ,we are using only one elevator , juint ,part one ;

    public double timeCalc(int index, CallForElevator c) {
        Elevator curr = this.getBuilding().getElevetor(index);
        LinkedList<Integer> this_q = E_List.get(index).queue;
        int q_size = this_q.size();
        double floor_time =
                (curr.getStartTime() + curr.getTimeForClose() + curr.getStopTime() + curr.getTimeForOpen());
        int num_to_src = 0;// amount of stop until src (from pos to src)
        int pos_to_des = 0;  //(from src to des)
        int num_to_des = 0;// from src

        int des_range = Math.abs(c.getDest() - c.getSrc());
        int src_range = Math.abs(curr.getPos() - c.getSrc());

        if (!this_q.isEmpty()) {
            for (int i = 0; i < this_q.size(); i++) { // how many stops in the way to the src.
                if (this_q.get(i) < c.getSrc()) {
                    num_to_src++;
                } else if (this_q.get(i) == c.getSrc()) {
                    num_to_src = i;
                    if (i == this_q.size() - 1) {
                        pos_to_des = i;
                    } else {
                        pos_to_des = i + 1;
                    }
                    break;
                } else {
                    num_to_src = i;
                    pos_to_des = i;
                    break;
                }
            }


            for (int j = pos_to_des; j < q_size; j++) {
                if (this_q.get(j) < c.getDest()) {
                    num_to_des++;
                } else if (this_q.get(j) >= c.getDest()) {
                    num_to_des = j;
                    break;
                }
            }
            des_range = Math.abs(c.getDest() - this_q.get(pos_to_des));
        }
        double total_src_speed = (src_range * curr.getSpeed());
        double total_des_speed = (des_range * curr.getSpeed());
        double total_src = total_src_speed + ((num_to_src - 1) * floor_time);
        double total_des = total_des_speed + ((num_to_des - 1) * floor_time);

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
        System.out.println("/-------------------------->");

        if (!_firstTime) {
            Elevator curr = this.building.getElevetor(elev);
            int pos = curr.getPos();
            if (curr.getState() == Elevator.LEVEL) {
                if (E_List.get(elev).queue.size() > 1) {
//                    int first_F = E_List.get(elev).queue.getFirst();

//                    if (E_List.get(elev).queue.size() == 1) {
//                        curr.goTo(first_F);
//                        E_List.get(elev).removeTheFirst();

                    System.out.println("QUEUE " + E_List.get(elev).queue);
                    System.out.println("SIZE " + E_List.get(elev).queue.size());

                    E_List.get(elev).removeTheFirst();

                    System.out.println("SIZE " + E_List.get(elev).queue.size());

                    int first_F = E_List.get(elev).queue.getFirst();
                    int last_F = E_List.get(elev).queue.getLast();
                    System.out.println("pos " + curr.getPos() + " first " + first_F + " last " + last_F);
                    System.out.println("new first " + first_F);
                    System.out.println(E_List.get(elev).queue);

                    curr.goTo(first_F);
//                    curr.stop(first_F);
                } else if (E_List.get(elev).queue.size() == 1) {
                    curr.goTo(E_List.get(elev).queue.getFirst());
                    E_List.get(elev).removeTheFirst();
                }
            }
            if (E_List.get(elev).queue.isEmpty() && curr.getState() == Elevator.LEVEL) {
                System.out.println("NOW ITS EMPTY: " + E_List.get(elev).queue.size());
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
                System.out.println("Down: " + E_States.getDOWN().size() + " UP: " + E_States.getUP().size() + " LEVEL: " + E_States.getLEVEL().size());
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
