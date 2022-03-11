package mvh.world;

import mvh.enums.Direction;
import mvh.enums.WeaponType;

/**
 * A Monster is an Entity with a set ARMOR STRENGTH and a user provided WEAPON TYPE
 * @author Jonathan Hudson
 * @version 1.0a
 */
public final class Monster extends Entity {

    /**
     * The set armor strength of a Monster
     */
    private static final int MONSTER_ARMOR_STRENGTH = 2;

    /**
     * The user provided weapon type
     */
    private final WeaponType weaponType;

    /**
     * A Monster has regular health and symbol as well as a weapon type
     *
     * @param health     Health of Monster
     * @param symbol     Symbol for map to show Monster
     * @param weaponType The weapon type of the Monster
     */
    public Monster(int health, char symbol, WeaponType weaponType) {
        super(symbol, health);
        this.weaponType = weaponType;
    }

    /**
     * Gets Monster's weapon type
     * @return The Monster's weapon type
     */
    public WeaponType getWeaponType(){
        return this.weaponType;
    }

    /**
     * The weapon strength of monster is from their weapon type
     * @return The weapon strength of monster is from their weapon type
     */
    @Override
    public int weaponStrength() {
        return weaponType.getWeaponStrength();
    }

    /**
     * The armor strength of monster is from the stored constant
     * @return The armor strength of monster is from the stored constant
     */
    @Override
    public int armorStrength() {
        return MONSTER_ARMOR_STRENGTH;
    }

    /**
     *
     * @param local The local view of the entity
     * @return Direction in which monster can move
     */
    @Override
    public Direction chooseMove(World local) {
        int row_center= 2 ;//finding the center of local where monster is placed
        int col_center= 2 ;
        for(int i=local.getRows()-1;i>=0;i--) //looping through all entities in local from bottom to top and right to left
        {
            for(int j=local.getColumns()-1;j>=0;j--)
            {
                if(local.isHero(i,j) && local.getEntity(i,j).isAlive()) //if monster encounters an alive hero
                {
                       //find three drections
                        Direction[] array=Direction.getDirections(i-row_center,j-col_center);

                        for (int k = 0; k < 3; k++) {  //looping through all directions
                            if(local.canMoveOnTopOf(row_center,col_center,array[k]))  //if monster can move on top of a location
                                return array[k] ;  //return the location
                        }
                    }

                }
            }

        return Direction.getDirection(1,1);   // return SOUTHEAST

    }

    /**
     *
     * @param local The local view of the entity (immediate neighbors 3x3)
     * @return the direction in which monster can attack
     */

    @Override
    public Direction attackWhere(World local) {
        int row_center= 1; //finding the center of local where monster is placed
        int col_center= 1;
        for(int i=local.getRows()-1;i>=0;i--)  //looping through all the entities in local
        {
            for(int j= local.getColumns()-1;j>=0;j--)
            {
                if(local.isHero(i,j) && local.getEntity(i,j).isAlive()) //if monster finds a alive hero
                {
                        int row_change=i-row_center;  //find the difference  of row and column index of hero and monster
                        int col_change=j-col_center;
                        if(local.canBeAttacked(row_center,col_center,Direction.getDirection(row_change,col_change)))//if monster can attack in the location
                           return Direction.getDirection(row_change,col_change);  //return direction
                        }

            }
        }
        return null;  //return null if no direction found

    }

    /**
     * Can only be moved on top of if dad
     * @return isDead()
     */
    @Override
    public boolean canMoveOnTopOf() {
        return isDead();
    }

    /**
     * Can only be attacked if alive
     * @return isAlive()
     */
    @Override
    public boolean canBeAttacked() {
        return isAlive();
    }

    @Override
    public String toString() {
        return super.toString() + "\t" + weaponType;
    }
}
