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
    int ele_capacity;
    int allo;
    private ArrayList<FloorQueue> E_List;
    private boolean _firstTime;
    private ListOfStates E_States;


    public algo(Building b) {
        this.building = b;
        this.ele_capacity = 3;
        E_List = new ArrayList<>();
        E_States = new ListOfStates();
        _firstTime = true;
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
       // if (!l.getLEVEL().isEmpty()) {
        System.out.println(" WE re here");
            double best = timeCalc(l.getLEVEL().get(0), c);
            for (int i = 1; i < l.getLEVEL().size(); i++) {
                System.out.println("TESTTTTTT " + l.getLEVEL().get(i));
                if (timeCalc(l.getLEVEL().get(i), c) < best && E_List.get(l.getLEVEL().get(i)).queue.size() <= ele_capacity) {
                    best = timeCalc(l.getLEVEL().get(i), c);
                    best_ele = i;
                }
            }
       // }

        if (state == -1 && !l.getDOWN().isEmpty()) {

            ArrayList<Integer> above_src = new ArrayList<>();
            for (int i = 0; i < l.getDOWN().size(); i++) {
                if (curr.getElevetor(l.getDOWN().get(i)).getPos() >= c.getSrc() && c.getDest() > curr.minFloor() && E_List.get(l.getDOWN().get(i)).queue.size() <= ele_capacity) {
                    above_src.add(l.getDOWN().get(i));
                }
            }
            best_ele = getBest_ele(c, best_ele, above_src);
        } else if (state == 1 && !l.getUP().isEmpty()) {
            ArrayList<Integer> under_src = new ArrayList<>();
            for (int i = 0; i < l.getUP().size(); i++) {
                if (curr.getElevetor(l.getUP().get(i)).getPos() <= c.getSrc() && c.getDest() < curr.maxFloor() && E_List.get(l.getUP().get(i)).queue.size() <= ele_capacity) {
                    under_src.add(l.getUP().get(i));
                }
            }
            best_ele = getBest_ele(c, best_ele, under_src);
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
        int des = c.getDest(), src = c.getSrc();
        double ele_speed = this.building.getElevetor(index).getSpeed();
        double stops_time = (this.building.getElevetor(index).getStartTime() + this.building.getElevetor(index).getStopTime() +
                this.building.getElevetor(index).getTimeForClose() + this.building.getElevetor(index).getTimeForOpen());
        boolean src_q = false, des_q = false;
        if (E_List.get(index).queue.contains(src)) {
            src_q = true;
        } else {
            E_List.get(index).push(src);
        }
        if (E_List.get(index).queue.contains(des)) {
            des_q = true;
        } else {
            E_List.get(index).push(des);
        }
        double ans = E_List.get(index).queue.size() * stops_time + Math.abs(E_List.get(index).queue.getLast() - E_List.get(index).queue.getFirst()) / ele_speed;
        if (!src_q) {
            E_List.get(index).removeObject(src);
        }
        if (!des_q) {
            E_List.get(index).removeObject(des);
        }
        return ans;

    }

    private static double total_travel(double stops_time, double ele_speed, int src, int des, LinkedList<Integer> E) {
        int stops_to_src = 0;
        boolean flag = true;
        if (!E.contains(src)) {
            stops_to_src++;
            flag = false;
        }
        for (int i = 0; i < E.size(); i++) {
            if (E.get(i) < src) {
                stops_to_src++;
            } else {
                break;
            }
        }
        int index_des = stops_to_src;
        if (flag) {
            index_des++;
        }
        double src_travel = (stops_to_src * stops_time) + Math.abs(E.get(stops_to_src) - E.getFirst()) * ele_speed;
        double des_travel = total_des(stops_time, ele_speed, index_des, des, E);
        return (src_travel + des_travel);
    }

    public static double total_des(double stops_time, double ele_speed, int from_src, int des, LinkedList<Integer> E) {
        int stops_to_des = 0;
        if (des > E.getLast()) {
            return (E.size() - from_src) * stops_time + Math.abs(E.getLast() - E.get(from_src)) * ele_speed;
        }
        for (int i = 0; i < E.size(); i++) {
            if (E.get(i) < des) {
                stops_to_des++;
            }
        }
        return (stops_to_des * stops_time) + Math.abs((E.get(from_src) + stops_to_des) - E.get(from_src)) * ele_speed;
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
//                    curr.goTo(first_F);
//                    curr.goTo(last_F);
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
