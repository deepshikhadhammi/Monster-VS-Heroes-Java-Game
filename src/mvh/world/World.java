package mvh.world;

import mvh.Main;
import mvh.Menu;
import mvh.enums.Direction;
import mvh.enums.Symbol;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A World is a 2D grid of entities, null Spots are floor spots
 * @author Jonathan Hudson
 * @version 1.0a
 */
public class World {

    /**
     * World starts ACTIVE, but will turn INACTIVE after a simulation ends with only one type of Entity still ALIVE
     */
    private enum State {
        ACTIVE, INACTIVE
    }

    /**
     * The World starts ACTIVE
     */
    private State state;
    /**
     * The storage of entities in World, floor is null, Dead entities can be moved on top of (deleting them essentially from the map)
     */
    private final Entity[][] world;
    /**
     * We track the order that entities were added (this is used to determine order of actions each turn)
     * Entities remain in this list (Even if DEAD) ,unlike the world Entity[][] where they can be moved on top of causing deletion.
     */
    private final ArrayList<Entity> entities;
    /**
     * We use a HashMap to track entity location in world {row, column}
     * We will update this every time an Entity is shifted in the world Entity[][]
     */
    private final HashMap<Entity, Integer[]> locations;

    /**
     * The local view of world will be 3x3 grid for attacking
     */
    private static final int ATTACK_WORLD_SIZE = 3;
    /**
     * The local view of world will be 5x5 grid for moving
     */
    private static final int MOVE_WORLD_SIZE = 5;

    /**
     * A new world of ROWSxCOLUMNS in size
     *
     * @param rows    The 1D of the 2D world (rows)
     * @param columns The 2D of the 2D world (columns)
     */
    public World(int rows, int columns) {
        this.world = new Entity[rows][columns];
        this.entities = new ArrayList<>();
        this.locations = new HashMap<>();
        //Starts active
        this.state = State.ACTIVE;
    }

    /**
     * Is this simulation still considered ACTIVE
     *
     * @return True if the simulation still active, otherwise False
     */
    public boolean isActive() {
        return state == State.ACTIVE;
    }

    /**
     * End the simulation, (Set in INACTIVE)
     */
    public void endSimulation() {
        this.state = State.INACTIVE;
    }

    /**
     * Advance the simulation one step
     */
    public void advanceSimulation() {
        //Do not advance if simulation is done
        if (state == State.INACTIVE) {
            return;
        }
        //If not done go through all entities (this will be in order read and added from file)
        for (Entity entity : entities) {
            //If entity is something that is ALIVE, we want to give it a turn to ATTACK or MOVE
            if (entity.isAlive()) {
                //Get location of entity (only the world knows this, the entity does not itself)
                Integer[] location = locations.get(entity);
                //Pull out row,column
                int row = location[0];
                int column = location[1];
                //Determine if/where an entity wants to attack
                World attackWorld3X3 = getLocal(ATTACK_WORLD_SIZE, row, column);
                Direction attackWhere = entity.attackWhere(attackWorld3X3);
                //If I don't attack, then I must be moving
                if (attackWhere == null) {
                    //Figure out where entity wants to move
                    World moveWorld5x5 = getLocal(MOVE_WORLD_SIZE, row, column);
                    Direction moveWhere = entity.chooseMove(moveWorld5x5);
                    //Log moving
                    Menu.println(String.format("%s moving %s", entity.shortString(), moveWhere));
                    //If this move is valid, then move it
                    if (canMoveOnTopOf(row, column, moveWhere)) {
                        moveEntity(row, column, moveWhere);
                    } else {
                        //Otherwise, indicate an invalid attempt to move
                        Menu.println(String.format("%s  tried to move somewhere it could not!", entity.shortString()));
                    }
                } else {
                    //If we are here our earlier attack question was not null, and we are attacking a nearby entity
                    //Get the entity we are attacking
                    Entity attacked = getEntity(row, column, attackWhere);
                    Menu.println(String.format("%s attacking %s in direction %s", entity.shortString(), attackWhere, attacked.shortString()));
                    //Can we attack this entity
                    if (canBeAttacked(row, column, attackWhere)) {
                        //Determine damage using RNG
                        int damage = 1 + Main.random.nextInt(entity.weaponStrength());
                        int true_damage = Math.max(0, damage - attacked.armorStrength());
                        Menu.println(String.format("%s attacked %s for %d damage against %d defense for %d", entity.shortString(), attacked.shortString(), damage, attacked.armorStrength(), true_damage));
                        attacked.damage(true_damage);
                        if (!attacked.isAlive()) {
                            locations.remove(attacked);
                            Menu.println(String.format("%s died!", attacked.shortString()));
                        }
                    } else {
                        Menu.println(String.format("%s  tried to attack somewhere it could not!", entity.shortString()));
                    }
                }
            }
        }
        checkActive();
    }

    /**
     *
     * @param attackWorldSize that contains the size of row X  column can be any odd integer
     * @param row  that contains the row index of an entity to be placed on centre
     * @param column that contains the column index of an entity to be placed on center
     * @return  subview of entity
     */
    public World getLocal(int attackWorldSize, int row, int column) {
        World subview=new World(attackWorldSize,attackWorldSize);
        int row_start=row-(attackWorldSize/2);    //finding the starting location of row from where entities should be added from world to subview
        int col_start=column-(attackWorldSize/2); //finding the starting location of column from where entities should be added from world to subview
        for(int i=0,p=row_start;i<attackWorldSize;i++,p++)
        {
            for(int j=0,q=col_start;j<attackWorldSize;j++,q++)
            {
                if(p>=getRows()||q>=getColumns()||p<0||q<0)   // if there is no entity at a particular row and column index add wall to subview
                {
                    subview.addEntity(i,j,Wall.getWall());
                }
                else if(isHero(p,q))   //if Entity is a hero add it to subview
                {

                    subview.addEntity(i, j, world[p][q]);
                }
                else if(isMonster(p,q))   //if Entity is a monster add it to subview
                {
                    subview.addEntity(i, j, world[p][q]);
                }

            }
        }
        return subview;   //return the subview
    }

    /**
     * Check if simulation has now ended (only one of two versus Entity types is alive
     */
    private void checkActive() {
        boolean hero_alive = false;
        boolean monster_alive = false;
        for (Entity entity : entities) {
            if (entity.isAlive()) {
                if (entity instanceof Monster) {
                    monster_alive = true;
                } else if (entity instanceof Hero) {
                    hero_alive = true;
                }
            }
        }
        if (!(hero_alive && monster_alive)) {
            state = State.INACTIVE;
        }
    }

    /**
     * Move an existing entity
     *
     * @param row    The  row location of existing entity
     * @param column The  column location of existing entity
     * @param d      The direction to move the entity in
     */
    public void moveEntity(int row, int column, Direction d) {
        Entity entity = getEntity(row, column);
        int moveRow = row + d.getRowChange();
        int moveColumn = column + d.getColumnChange();
        this.world[moveRow][moveColumn] = entity;
        this.world[row][column] = null;
        this.locations.put(entity, new Integer[]{moveRow, moveColumn});
    }

    /**
     * Add a new entity
     *
     * @param row    The  row location of new entity
     * @param column The  column location of new entity
     * @param entity The entity to add
     */
    public void addEntity(int row, int column, Entity entity) {
        this.world[row][column] = entity;
        this.entities.add(entity);
        locations.put(entity, new Integer[]{row, column});
    }

    /**
     * Get entity at a location
     *
     * @param row    The row of the entity
     * @param column The column of the entity
     * @return The Entity at the given row, column
     */
    public Entity getEntity(int row, int column) {
        return this.world[row][column];
    }

    /**
     * Get entity at a location
     *
     * @param row    The row of the entity
     * @param column The column of the entity
     * @param d      The direction adjust look up towards
     * @return The Entity at the given row, column
     */
    public Entity getEntity(int row, int column, Direction d) {
        return getEntity(row + d.getRowChange(), column + d.getColumnChange());
    }

    /**
     * See if we can move to location
     *
     * @param row    The row to check
     * @param column The column to check
     * @return True if we can move to that location
     */
    public boolean canMoveOnTopOf(int row, int column) {
        Entity entity = getEntity(row, column);
        if (entity == null) {
            return true;
        }
        return entity.canMoveOnTopOf();
    }

    /**
     * See if we can move to location
     *
     * @param row    The row to check
     * @param column The column to check
     * @param d      The direction adjust look up towards
     * @return True if we can move to that location
     */
    public boolean canMoveOnTopOf(int row, int column, Direction d) {
        return canMoveOnTopOf(row + d.getRowChange(), column + d.getColumnChange());
    }

    /**
     * See if we can attack entity at a location
     *
     * @param row    The row to check
     * @param column The column to check
     * @return True if we can attack entity at that location
     */
    public boolean canBeAttacked(int row, int column) {
        Entity entity = getEntity(row, column);
        if (entity == null) {
            return false;
        }
        return entity.canBeAttacked();

    }

    /**
     * See if we can attack entity at a location
     *
     * @param row    The row to check
     * @param column The column to check
     * @param d      The direction adjust look up towards
     * @return True if we can attack entity at that location
     */
    public boolean canBeAttacked(int row, int column, Direction d) {
        return canBeAttacked(row + d.getRowChange(), column + d.getColumnChange());

    }

    /**
     * See if entity is hero at this location
     *
     * @param row    The row to check
     * @param column The column to check
     * @return True if entity is a hero at that location
     */
    public boolean isHero(int row, int column) {
        Entity entity = getEntity(row, column);
        if (entity == null) {
            return false;
        }
        return entity instanceof Hero;
    }


    /**
     * See if entity is monster at this location
     *
     * @param row    The row to check
     * @param column The column to check
     * @return True if entity is a monster at that location
     */
    public boolean isMonster(int row, int column) {
        Entity entity = getEntity(row, column);
        if (entity == null) {
            return false;
        }
        return entity instanceof Monster;
    }

    @Override
    public String toString() {
        return gameString();
    }

    /**creating a string that calls worldstring() and adds information about the entities
     * @return string that is a combination of map and information about entities
     */

   public String gameString() {
        String str="";                   //empty string
        str=worldString();
        str = str + "\nNAME\tS\tH\tSTATE\tINFO";    //Adding a header line to the string
        for(Entity entity :entities) {             //looping through all the entities
            str = str + "\n" + entity ;            //adding information about the entity to the string
        }

        return str;                   //return the string
    }

    /** creating a map consisting of entities,walls and floors
     * @return string that contains a map
     */

    public String worldString()
    {
        String str = "";
        String str1 = "";
        for (int k = 0; k <= getColumns() + 1; k++) {     //loop to add walls before first and after last row
            str1 = str1 + Symbol.WALL.getSymbol();
        }
        str = str + str1 + "\n";
        for (int i = 0; i < getRows(); i++) {             //looping through entities
            for (int j = 0; j < getColumns(); j++) {
                if (j == 0) {                              //adding a wall before the first column in every row
                    str = str + Symbol.WALL.getSymbol();
                }
                if (getEntity(i, j) == null) {
                    str = str + Symbol.FLOOR.getSymbol();   //if entity null add floor
                } else if (getEntity(i, j) != null) {     //if entity is not null
                    if(getEntity(i,j).isAlive())         //if entity alive
                     str = str + world[i][j].getSymbol();    //Add symbol of entity
                    else
                        str=str+Symbol.DEAD.getSymbol();    //If entity dead add dead symbol
                }
                if ((j + 1) == getColumns()) {
                    str = str + Symbol.WALL.getSymbol();     //adding wall at the end of every column
                }
            }
            str = str + "\n";
        }
        str = str + str1;
        return str;              //return the string that contains worldstring() and information about entities
    }
    /**
     * The rows of the world
     * @return The rows of the world
     */
    public int getRows(){
        return world.length;
    }

    /**
     * The columns of the world
     * @return The columns of the world
     */
    public int getColumns(){
        return world[0].length;
    }

}
