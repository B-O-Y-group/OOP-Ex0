package ex0.algo;

import java.util.ArrayList;

public class ListOfStates {

    private static ArrayList<Integer> UP, DOWN, LEVEL, ERROR;


    public ListOfStates() {
        UP = new ArrayList<>();
        DOWN = new ArrayList<>();
        LEVEL = new ArrayList<>();
        ERROR = new ArrayList<>();
    }

    public ArrayList<Integer> dictionary(String state) {
        switch (state) {
            case "UP" -> {
                return UP;
            }
            case "DOWN" -> {
                return DOWN;
            }
            case "LEVEL" -> {
                return LEVEL;
            }
            case "ERROR" -> {
                return ERROR;
            }
            default -> {
                System.out.println("dictionary - Not valid state");
            }
        }
        return ERROR;
    }

    public void add(int elev, String state) {
        if (!inList(elev))
            switch (state) {
                case "UP" -> {
                    UP.add(elev);
                }
                case "DOWN" -> {
                    DOWN.add(elev);
                }
                case "LEVEL" -> {
                    LEVEL.add(elev);
                }
                case "ERROR" -> {
                    ERROR.add(elev);
                }
                default -> {
                    System.out.println("Not valid state");
                }
            }
        else {
            System.out.println("Elevator is already located in a stateList");
        }

    }

    public void swap(int elev, String state1, String state2) {
        if (!dictionary(state1).contains(elev)) {
            System.err.println("Wrong State");
        } else {
            dictionary(state1).remove(dictionary(state1).indexOf(elev));
            dictionary(state2).add(elev);
        }
    }

    public static boolean inList(int elev) {
        return UP.contains(elev) || DOWN.contains(elev) || LEVEL.contains(elev) || ERROR.contains(elev);
    }

    public static String findState(int elev) {
        if (!inList(elev)) {
            return "wrong state";
        }
        if (UP.contains(elev)) {
            return "UP";
        }
        if (DOWN.contains(elev)) {
            return "DOWN";
        }
        return "LEVEL";
    }

    public ArrayList<Integer> getUP() {
        return UP;
    }

    public ArrayList<Integer> getDOWN() {
        return DOWN;
    }

    public ArrayList<Integer> getLEVEL() {
        return LEVEL;
    }

    public ArrayList<Integer> getERROR() {
        return ERROR;
    }

}
