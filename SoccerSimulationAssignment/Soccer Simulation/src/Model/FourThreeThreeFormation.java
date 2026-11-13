package Model;

public class FourThreeThreeFormation implements TeamFormation {

    @Override
    public String getName() {
        return "4-3-3";
    }

    @Override
    public int[][] getPositions() {
        return new int[][] {
                { 120, -150 }, { 120, -50 }, { 120, 50 }, { 120, 150 }, // defenders
                { 280, -100 }, { 280, 0 }, { 280, 100 }, // midfeilders
                { 420, -120 }, { 420, 0 }, { 420, 120 } //  strikers
        };
    }
}
