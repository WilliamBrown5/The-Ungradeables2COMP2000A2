package Model;

public class ThreeFiveTwoFormation implements TeamFormation {

    @Override
    public String getName() {
        return "3-5-2";
    }

    @Override
    public int[][] getPositions() {
        return new int[][] {
                { 120, -120 }, { 120, 0 }, { 120, 120 }, // defenders
                { 280, -160 }, { 280, -80 }, { 280, 0 }, { 280, 80 }, { 280, 160 }, // midfielders
                { 420, -60 }, { 420, 60 } // strikers
        };
    }
}
