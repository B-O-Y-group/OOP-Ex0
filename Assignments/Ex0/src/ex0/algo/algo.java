package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;

import java.util.Arrays;

public class algo implements  ElevatorAlgo{
    final  static  int UP= 1 , DOWN = -1;
    public Building building;
   // public int direction;
    boolean [] floor ;


    public algo(Building b) {
        this.building = b;
        int max = this.building.maxFloor();
        int min = this.building.minFloor();
        floor= new boolean[max-min+1];
     //   this.direction = UP;
        Arrays.fill(floor, false);
    }

    @Override
    public Building getBuilding() {
        return  this.building;
    }

    @Override
    public String algoName() {
        return "algorithm : boy ";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        int ans = 0;
        return ans;
    }

    @Override
    public void cmdElevator(int elev) {

    }
}
