package Model;

public class HoldFormationStrategy implements PlayerMovementStrategy {
    @Override
    public void move(Match match, Player player, int targetX, int targetY) {
        match.moveIntoShape(player, targetX, targetY);
    }
}