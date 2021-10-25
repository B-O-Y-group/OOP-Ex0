package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;

public class algo implements  ElevatorAlgo{
    final  static  int UP= 1 , DOWN = -1;
    public Building building;
   // public int direction;


    public algo(Building b) {
        this.building = b;
     //   this.direction = UP;
    }

    @Override
    public Building getBuilding() {
        return  this.building;
    }

    @Override
    public String algoName() {
        return "Oron the gay ;";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        return 0;
    }

    @Override
    public void cmdElevator(int elev) {

    }
}
