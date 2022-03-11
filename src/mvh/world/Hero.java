package mvh.world;

import mvh.enums.Direction;

/**
 * A Monster is an Entity with a user provide WEAPON STRENGTH and ARMOR STRENGTH
 *
 * @author Jonathan Hudson
 * @version 1.0
 */
public final class Hero extends Entity {

    /**
     * The user provided weapon strength
     */
    private final int weaponStrength;

    /**
     * The user provided armor strength
     */
    private final int armorStrength;

    /**
     * A Hero has regular health and symbol as well as a weapon strength and armor strength
     *
     * @param health         Health of hero
     * @param symbol         Symbol for map to show hero
     * @param weaponStrength The weapon strength of the hero
     * @param armorStrength  The armor strength of the hero
     */
    public Hero(int health, char symbol, int weaponStrength, int armorStrength) {
        super(symbol, health);
        this.weaponStrength = weaponStrength;
        this.armorStrength = armorStrength;
    }

    /**
     * The weapon strength of monster is from user value
     *
     * @return The weapon strength of monster is from user value
     */
    @Override
    public int weaponStrength() {
        return weaponStrength;
    }

    /**
     * The armor strength of monster is from user value
     *
     * @return The armor strength of monster is from user value
     */
    @Override
    public int armorStrength() {
        return armorStrength;
    }

    /**
     *
     * @param local The local view of the entity
     * @return Direction in which hero should move
     */

    @Override
    public Direction chooseMove(World local) {
        int row_center = 2; //find the center position of row and column in local where hero is placed
        int col_center = 2;

        for (int i = 0; i < local.getRows(); i++) { //looping through entities in local
            for (int j = 0; j < local.getColumns(); j++) {
                if (local.isMonster(i, j) && local.getEntity(i, j).isAlive()) {//if entity is a monster and alive

                    Direction[] array = Direction.getDirections(i - row_center, j - row_center); //get three directions
                    for (int k = 0; k < 3; k++) { //loop through every direction
                        if (local.canMoveOnTopOf(row_center, col_center, array[k])) //check if hero can move on top of that direction
                            return array[k];  //return direction
                    }

                    return Direction.getRandomDirection() ; //return random if no direction found
                    }
                }
            }

        return Direction.getDirection(-1, -1);  //return NORTHWEST
    }

    /**
     *
     * @param local The local view of the entity (immediate neighbors 3x3)
     * @return the direction in which hero can attack
     */

    @Override
    public Direction attackWhere(World local) {
        int row_center = 1;     //find the center position of row and column in local where hero is placed
        int col_center = 1;
        for (int i = 0; i < local.getRows(); i++) {   //looping through entities in local
            for (int j = 0; j < local.getColumns(); j++) {
                if (local.isMonster(i, j) && local.getEntity(i, j).isAlive()) {   //if entity is a monster and alive

                    int row_change = i - row_center;  //row_change (difference of row index of hero and monster)
                    int col_change = j - col_center;//column_change (difference of column index of hero and monster)
                    if (local.canBeAttacked(row_center, col_center, Direction.getDirection(row_change, col_change)))
                        return Direction.getDirection(row_change, col_change);  //return the direction if hero can attack in that particular direction

                   // return Direction.getRandomDirection() ;
                }
            }
        }
        return null;   //return null if no direction found

    }

    /**
     * Can only be moved on top of if dad
     *
     * @return isDead()
     */
    @Override
    public boolean canMoveOnTopOf() {
        return isDead();
    }

    /**
     * Can only be attacked if alive
     *
     * @return isAlive()
     */
    @Override
    public boolean canBeAttacked() {
        return isAlive();
    }

    @Override
    public String toString() {
        return super.toString() + "\t" + weaponStrength + "\t" + armorStrength;
    }
}
