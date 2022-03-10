package mvh.world;

import mvh.enums.Direction;

/**
 * A Monster is an Entity with a user provide WEAPON STRENGTH and ARMOR STRENGTH
 * @author Jonathan Hudson
 * @version 1.0
 */
public final class Hero extends Entity{

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
     * @param health Health of hero
     * @param symbol Symbol for map to show hero
     * @param weaponStrength The weapon strength of the hero
     * @param armorStrength The armor strength of the hero
     */
    public Hero(int health, char symbol, int weaponStrength, int armorStrength) {
        super(symbol, health);
        this.weaponStrength = weaponStrength;
        this.armorStrength = armorStrength;
    }

    /**
     * The weapon strength of monster is from user value
     * @return The weapon strength of monster is from user value
     */
    @Override
    public int weaponStrength() {
        return weaponStrength;
    }

    /**
     * The armor strength of monster is from user value
     * @return The armor strength of monster is from user value
     */
    @Override
    public int armorStrength() {
        return armorStrength;
    }

    @Override
    public Direction chooseMove(World local) {
        int row_center= local.getRows()/2;
        int col_center= local.getColumns()/2;

        for(int i=0;i< local.getRows();i++)
        {
            for(int j=0;j< local.getColumns();j++)
            {
                if(local.isMonster(i,j))
                {
                    if((local.getEntity(i,j)).isAlive())
                    {

                        Direction[] array=Direction.getDirections(i-row_center,j-row_center);
                        System.out.println(array[0]+" "+array[1]+" "+array[2]);
                        for (Direction direction : array) {
                            if (((local.canMoveOnTopOf(row_center,col_center, direction)))) {
                                return direction;
                            }
                        }
                        return Direction.getRandomDirection();
                    }
                }
            }
        }
        return Direction.getDirection(-1,-1);

    }

    @Override
    public Direction attackWhere(World local) {
        int row_center= local.getRows()/2;
        int col_center=local.getColumns()/2;
        for(int i=0;i< local.getRows();i++)
        {
            for(int j=0;j<local.getColumns();j++)
            {
                if((local.isMonster(i,j)))
                {
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
    public String toString(){
        return super.toString()+"\t"+weaponStrength+"\t"+armorStrength;
    }
}