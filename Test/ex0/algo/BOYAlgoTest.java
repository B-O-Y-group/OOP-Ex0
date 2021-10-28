package ex0.algo;

import ex0.Building;
import ex0.simulator.Simulator_A;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BOYAlgoTest {
    private Building building;
    BOYAlgo algo;



    public BOYAlgoTest() {
        Simulator_A.initData(0,null);
        this.building = Simulator_A.getBuilding();
        this.algo = new BOYAlgo(this.building);
    }

    @Test
    void getBuilding() {
        assertEquals(this.building, algo.getBuilding());
    }

    @Test
    void algoName() {
        assertEquals("BOY Algo",algo.algoName());
    }

    @Test
    void allocateAnElevator() {
    }

    @Test
    void cmdElevator() {
    }
}