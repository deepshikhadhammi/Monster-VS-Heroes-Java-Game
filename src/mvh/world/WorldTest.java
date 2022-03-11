package mvh.world;

import mvh.enums.WeaponType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    @org.junit.jupiter.api.Test
    void gameString() {
        World world= new World(5,4);  //creating a world object
        Monster monster1=new Monster(10,'A', WeaponType.AXE);  //creating monster object
        Monster monster2=new Monster(9,'B',WeaponType.SWORD); //creating monster object
        Monster monster3=new Monster(8,'C',WeaponType.CLUB); //creating monster object
        Hero hero1=new Hero(8,'D',5,2);  //creating a hero object
        Hero hero2=new Hero(7,'E',4,1); //creating a hero object
        Hero hero3=new Hero(6,'F',3,1); //creating a hero object
        world.addEntity(0,0,monster1);  //adding hero and monster entities to world
        world.addEntity(0,2,monster2);
        world.addEntity(1,2,monster3);
        world.addEntity(4,1,hero1);
        world.addEntity(4,2,hero2);
        world.addEntity(4,3,hero3);
         String expected="######\n" +       //expected output
            "#A.B.#\n" +
            "#..C.#\n" +
            "#....#\n" +
            "#....#\n" +
            "#.DEF#\n" +
            "######\n" +
            "NAME\tS\tH\tSTATE\tINFO\n" +
            "Mons(1)\tA\t10\tALIVE\tAXE\n" +
            "Mons(2)\tB\t9\tALIVE\tSWORD\n" +
            "Mons(3)\tC\t8\tALIVE\tCLUB\n" +
            "Hero(4)\tD\t8\tALIVE\t5\t2\n" +
            "Hero(5)\tE\t7\tALIVE\t4\t1\n" +
            "Hero(6)\tF\t6\tALIVE\t3\t1";
    assertEquals(expected,world.gameString());  //check if expected and actual output matches

    }

    @org.junit.jupiter.api.Test
    void worldString() {
        World world= new World(5,4); //creating a world object
        Monster monster1=new Monster(10,'A', WeaponType.AXE); //creating monster object
        Monster monster2=new Monster(9,'B',WeaponType.SWORD);
        Monster monster3=new Monster(8,'C',WeaponType.CLUB);
        Hero hero1=new Hero(8,'D',5,2); //creating a hero object
        Hero hero2=new Hero(7,'E',4,1);
        Hero hero3=new Hero(6,'F',3,1);
        world.addEntity(0,0,monster1);
        world.addEntity(0,2,monster2);
        world.addEntity(1,2,monster3);
        world.addEntity(4,1,hero1);
        world.addEntity(4,2,hero2);
        world.addEntity(4,3,hero3);
        String expected="######\n" +
                "#A.B.#\n" +
                "#..C.#\n" +
                "#....#\n" +
                "#....#\n" +
                "#.DEF#\n" +
                "######" ;
        assertEquals(expected,world.worldString()); //check if expected and actual output matches
    }
    @Test
    void getLocal_test1()
    {
        World world=new World(3,3); //creating a world object
        Hero hero=new Hero(10,'H',7,8); //creating a hero object
        world.addEntity(2,2,hero);
        World subview=new World(3,3);   //create  a new world object
        //adding entities
        subview.addEntity(0,2,Wall.getWall());
        subview.addEntity(1,1,hero);
        subview.addEntity(1,2,Wall.getWall());
        subview.addEntity(2,0,Wall.getWall());
        subview.addEntity(2,1,Wall.getWall());
        subview.addEntity(2,2,Wall.getWall());
        assertEquals(subview.toString(),world.getLocal(3,2,2).toString()); //check if expected and actual output matches
    }


    @Test
    void getLocal_test2()
    {
        World world=new World(3,3); //creating a world object
        Monster monster=new Monster(8,'M',WeaponType.AXE); //creating monster object
        Hero hero=new Hero(10,'H',7,8); //creating a hero object
        world.addEntity(1,0,monster);  //adding entities
        world.addEntity(2,0,hero);
        World subview=new World(3,3);  //create  a new world object
        //adding entities
        subview.addEntity(0,0,Wall.getWall());
        subview.addEntity(0,1,monster);
        subview.addEntity(1,0,Wall.getWall());
        subview.addEntity(1,1,hero);
        subview.addEntity(2,0,Wall.getWall());
        subview.addEntity(2,1,Wall.getWall());
        subview.addEntity(2,2,Wall.getWall());
        assertEquals(subview.toString(),world.getLocal(3,2,0).toString()); //check if expected and actual output matches
    }
    @Test
    void getLocalTest3(){
        World world = new World(11,11) ; //creating a world object
        Hero h = new Hero(6, 'H' , 3 , 2) ; //creating a hero object
        Monster m=new Monster(5,'M',WeaponType.AXE); //creating monster object
        world.addEntity(5,5, h);  //adding entities
        world.addEntity(4,6,m);  //adding entities
        World subview = new World(3,3) ; //create  a new world object
        subview.addEntity(0,2,m); //adding entities
        subview.addEntity(1,1,h);  //adding entities

        assertEquals(subview.toString() , world.getLocal(3,5,5).toString()); //check if expected and actual output matches
    }
}