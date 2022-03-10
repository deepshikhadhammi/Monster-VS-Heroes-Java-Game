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

    @Override
    public Direction chooseMove(World local) {
        int row_center= local.getRows()/2;
        int col_center=local.getColumns()/2;
        for(int i=0;i< local.getRows();i++)
        {
            for(int j=0;j< local.getColumns();j++)
            {
                if(local.isHero(i,j))
                {
                    if((local.getEntity(i,j)).isAlive())
                    {

                        Direction[] array=Direction.getDirections(i-row_center,j-col_center);

                        System.out.println(array[0]+"  "+array[1]+" "+array[2]);
                        for (Direction direction : array) {
                            System.out.println(direction);
                            if (((local.canMoveOnTopOf(row_center, col_center, direction)))) {
                                return direction;
                            }
                        }
                        return Direction.getRandomDirection();
                    }
                }
            }
        }
        return Direction.getDirection(1,-1);

    }

    @Override
    public Direction attackWhere(World local) {
        int row_center= local.getRows()/2;
        int col_center= local.getColumns()/2;
        for(int i=local.getRows()-1;i>=0;i--)
        {
            for(int j= local.getColumns()-1;j>0;j--)
            {
                if((local.isHero(i,j)))
                {
                    System.out.println("True");
                    if((local.getEntity(i,j)).isAlive())
                    {
                        int row_change=i-row_center;
                        int col_change=j-col_center;
                        System.out.println(row_change+"  "+col_change);
                        return Direction.getDirection(row_change,col_change);

                    }
                }

            }
        }
        return null;

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
