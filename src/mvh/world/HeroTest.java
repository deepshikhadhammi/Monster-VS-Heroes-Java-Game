package mvh.world;

import mvh.enums.Direction;
import mvh.enums.WeaponType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {

    @Test
    void chooseMove_test1() {
        World world=new World(5,5);  //creating a world object
        Monster monster=new Monster(3,'M', WeaponType.AXE);  //creating a monster object
        Hero hero=new Hero(3,'H',10,5); //creating a hero object
        world.addEntity(2,0,monster); //adding monster to world
        world.addEntity(2,2,hero);  //adding hero to world
        assertEquals(Direction.getDirection(0,-2),hero.chooseMove(world)); //check if expected and actual output matches
    }
    @Test
    void chooseMove_test2() {
        World world=new World(5,5); //creating a world object
        Monster monster=new Monster(3,'M', WeaponType.AXE);  //creating a monster object
        Hero hero=new Hero(3,'H',10,5); //creating a hero object
        world.addEntity(2,2,hero);//adding hero to world
        world.addEntity(4,2,monster); //adding monster to world
        assertEquals(Direction.getDirection(2,0),hero.chooseMove(world)); //check if expected and actual output matches
    }

    @Test
    void attackWhere_test1() {
        World world=new World(3,3); //creating a world object
        Monster monster=new Monster(3,'M', WeaponType.AXE);  //creating a monster object
        Hero hero=new Hero(3,'H',10,5); //creating a hero object
        world.addEntity(0,2,monster); //adding monster to world
        world.addEntity(1,1,hero); //adding hero to world
        assertEquals(Direction.getDirection(-1,1),hero.attackWhere(world)); //check if expected and actual output matches

    }
    @Test
    void attackWhere_test2() {
        World world=new World(3,3); //creating a world object
        Monster monster=new Monster(3,'M', WeaponType.AXE);  //creating a monster object
        Hero hero=new Hero(3,'H',10,5); //creating a hero object
        world.addEntity(1,0,monster); //adding monster to world
        world.addEntity(1,1,hero); //adding hero to world
        assertEquals(Direction.getDirection(0,-1),hero.attackWhere(world)); //check if expected and actual output matches

    }
}