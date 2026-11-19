package Model;

public class FourFiveOneFormation implements TeamFormation {
    @Override
    public String getName() {
        return "4-5-1";
    }

    @Override
    public int[][] getPositions() {
        return new int[][] {
                { 120, -150 }, { 120, -50 }, { 120, 50 }, { 120, 150 }, // defenders
                { 280, -160 }, { 280, -80 }, { 280, 0 }, { 280, 80 }, { 280, 160 }, // midfielders
                { 420, 0 } // strikers
                                          
        };
    }
}
