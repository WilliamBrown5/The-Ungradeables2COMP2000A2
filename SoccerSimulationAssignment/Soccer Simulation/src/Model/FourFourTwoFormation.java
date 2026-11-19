package Model;

public class FourFourTwoFormation implements TeamFormation {
   
    @Override
    public String getName() {
        return "4-4-2";
    }

    @Override 
    public int[][] getPositions() {
        return new int[][] {
                { 120, -150 }, { 120, -50 }, { 120, 50 }, { 120, 150 }, // defenders
                { 280, -150 }, { 280, -50 }, { 280, 50 }, { 280, 150 }, // midfielders
                { 420, -60 }, { 420, 60 }                               // strikers
        };
    }
}
