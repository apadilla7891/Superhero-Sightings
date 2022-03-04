package com.ap.Superhero.dao;

import com.ap.Superhero.dto.Hero;
import com.ap.Superhero.dto.HeroSighting;
import com.ap.Superhero.dto.Location;
import com.ap.Superhero.dto.Organization;
import com.ap.Superhero.dto.Superpower;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Andy Padilla
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HeroDaoDBTest {
    
    @Autowired
    SuperpowerDao powerTestDao;
    
    @Autowired
    HeroDao heroTestDao;
    
    @Autowired
    OrganizationDao orgTestDao;
    
    @Autowired
    LocationDao localTestDao;
    
    @Autowired
    HeroSightingDao sightTestDao;
    
    public HeroDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Superpower> powers = powerTestDao.getAllPowers();
        for(Superpower power: powers){
            powerTestDao.deletePowerById(power.getId());
        }
        
        List<Hero> heroes = heroTestDao.getAllHeroes();
        for(Hero hero : heroes){
            heroTestDao.deleteHeroById(hero.getId());
        }
        
        List<Organization> orgs = orgTestDao.getAllOrgs();
        for(Organization org : orgs){
            orgTestDao.deleteOrgById(org.getId());
        }
        
        List<Location> locations = localTestDao.getAllLocations();
        for(Location location : locations){
            localTestDao.deleteLocationById(location.getId());
        }
        
        List<HeroSighting> sightings = sightTestDao.getAllSightings();
        for(HeroSighting sight : sightings){
            sightTestDao.deleteSightingById(sight.getId());
        }
    }

    /**
     * Test of addHero and getHeroById method, of class HeroDaoDB.
     */
    @Test
    public void testAddAndGetHero() {
        Superpower testPower = new Superpower();
        testPower.setName("Test Power");
        testPower.setDescription("Test Description");
        testPower = powerTestDao.addPower(testPower);
        
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        
        Hero testHero = new Hero();
        testHero.setName("TestName");
        testHero.setDescription("TestDescription");
        testHero.setIsHero(true);
        testHero.setPowers(powers);
        testHero = heroTestDao.addHero(testHero);
        
        heroTestDao.updateHero(testHero);
        Hero fromDao = heroTestDao.getHeroById(testHero.getId());
        assertEquals(testHero,fromDao);
        
    }

    /**
     * Test of getAllHeroes method, of class HeroDaoDB.
     */
    @Test
    public void testGetAllHeroes() {
        Superpower testPower = new Superpower();
        testPower.setName("Test Power");
        testPower.setDescription("Test Description");
        testPower = powerTestDao.addPower(testPower);
        
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        
        Hero testHero = new Hero();
        testHero.setName("TestName");
        testHero.setDescription("TestDescription");
        testHero.setIsHero(true);
        testHero.setPowers(powers);
        testHero = heroTestDao.addHero(testHero);
        
        Hero testHero2 = new Hero();
        testHero2.setName("TestName");
        testHero2.setDescription("TestDescription");
        testHero2.setIsHero(true);
        testHero2.setPowers(powers);
        testHero2 = heroTestDao.addHero(testHero2);
        
        List<Hero> heroes = heroTestDao.getAllHeroes();
        assertEquals(2,heroes.size());
        assertTrue(heroes.contains(testHero));
        assertTrue(heroes.contains(testHero2));
        
    }
    
    /**
     * Test of updateHero method, of class HeroDaoDB.
     */
    @Test
    public void testUpdateHero() {
        Superpower testPower = new Superpower();
        testPower.setName("Test Power");
        testPower.setDescription("Test Description");
        testPower = powerTestDao.addPower(testPower);
        
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        
        Hero testHero = new Hero();
        testHero.setName("TestName");
        testHero.setDescription("TestDescription");
        testHero.setIsHero(true);
        testHero.setPowers(powers);
        testHero = heroTestDao.addHero(testHero);
        
        Hero fromDao = heroTestDao.getHeroById(testHero.getId());
        assertEquals(testHero,fromDao);
        
        testHero.setName("NewTest");
        testHero.setIsHero(false);
        Superpower testPower2 = new Superpower();
        testPower2.setName("Test Power2");
        testPower2.setDescription("Test Description2");
        testPower2 = powerTestDao.addPower(testPower2);
        powers.add(testPower2);
        testHero.setPowers(powers);
        
        heroTestDao.updateHero(testHero);
        assertNotEquals(testHero,fromDao);
        
        fromDao = heroTestDao.getHeroById(testHero.getId());
        assertEquals(testHero,fromDao);
    }
    
    /**
     * Test of DeleteHeroById method, of class HeroDaoDB.
     */
    @Test
    public void testDeleteHero() {
        Superpower testPower = new Superpower();
        testPower.setName("Test Power");
        testPower.setDescription("Test Description");
        testPower = powerTestDao.addPower(testPower);
        
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        
        Hero testHero = new Hero();
        testHero.setName("TestName");
        testHero.setDescription("TestDescription");
        testHero.setIsHero(true);
        testHero.setPowers(powers);
        testHero = heroTestDao.addHero(testHero);

        Hero fromDao = heroTestDao.getHeroById(testHero.getId());
        assertEquals(testHero,fromDao);
        
        heroTestDao.deleteHeroById(testHero.getId());
        fromDao = heroTestDao.getHeroById(testHero.getId());
        assertNull(fromDao);
    }
    
    /**
     * Test of getHeroesByLocation method of class HeroDaoDB
     */
    @Test
    public void testGetHeroByLocations(){
        Superpower testPower = new Superpower();
        testPower.setName("Test Power");
        testPower.setDescription("Test Description");
        testPower = powerTestDao.addPower(testPower);
        
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        
        Hero testHero = new Hero();
        testHero.setName("TestName");
        testHero.setDescription("TestDescription");
        testHero.setIsHero(true);
        testHero.setPowers(powers);
        testHero = heroTestDao.addHero(testHero);
        
        Superpower testPower2 = new Superpower();
        testPower2.setName("Test Power2");
        testPower2.setDescription("Test Description2");
        testPower2 = powerTestDao.addPower(testPower2);
        
        List<Superpower> powers2 = new ArrayList<>();
        powers2.add(testPower2);
        
        Hero testHero2 = new Hero();
        testHero2.setName("TestName2");
        testHero2.setDescription("TestDescription2");
        testHero2.setIsHero(false);
        testHero2.setPowers(powers2);
        testHero2 = heroTestDao.addHero(testHero2);
        
        Location test = new Location();
        test.setName("testName");
        test.setDescription("testDescription");
        test.setAddress("testAddress");
        test.setLatitude(123.345);
        test.setLongitude(-56.3);
        test = localTestDao.addLocation(test);
        
        HeroSighting testSight = new HeroSighting();
        testSight.setHero(testHero);
        testSight.setLocation(test);
        LocalDate date = LocalDate.parse("2021-12-05");
        testSight.setDate(date);
        testSight = sightTestDao.addSighting(testSight);
        
        HeroSighting testSight2 = new HeroSighting();
        testSight2.setHero(testHero2);
        testSight2.setLocation(test);
        LocalDate date2 = LocalDate.parse("2022-02-05");
        testSight2.setDate(date2);
        testSight2 = sightTestDao.addSighting(testSight2);
        
        List<Hero> heroes = heroTestDao.getHeroesByLocation(test);
        assertEquals(2, heroes.size());
        assertTrue(heroes.contains(testHero));
        assertTrue(heroes.contains(testHero2));
    }
    
    /**
     * Test of getHeroesForOrg method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetHeroesForOrg() {
        Superpower testPower = new Superpower();
        testPower.setName("Test Power");
        testPower.setDescription("Test Description");
        testPower = powerTestDao.addPower(testPower);
        
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        
        Hero testHero = new Hero();
        testHero.setName("TestName");
        testHero.setDescription("TestDescription");
        testHero.setIsHero(true);
        testHero.setPowers(powers);
        testHero = heroTestDao.addHero(testHero);
        
        Superpower testPower2 = new Superpower();
        testPower2.setName("Test Power2");
        testPower2.setDescription("Test Description2");
        testPower2 = powerTestDao.addPower(testPower2);
        
        List<Superpower> powers2 = new ArrayList<>();
        powers2.add(testPower2);
        
        Hero testHero2 = new Hero();
        testHero2.setName("TestName2");
        testHero2.setDescription("TestDescription2");
        testHero2.setIsHero(false);
        testHero2.setPowers(powers2);
        testHero2 = heroTestDao.addHero(testHero2);
        
        List<Hero> heroes = new ArrayList<>();
        heroes.add(testHero);
        heroes.add(testHero2);
        
        Organization testOrg = new Organization();
        testOrg.setName("TestName");
        testOrg.setDescription("TestDescription");
        testOrg.setAddress("TestAddress");
        testOrg.setContact("TestContact");
        testOrg.setIsHero(true);
        testOrg.setHeroes(heroes);
        testOrg = orgTestDao.addOrg(testOrg);

        
        List<Hero> testGroup = heroTestDao.getHeroesByOrg(testOrg);
        assertEquals(2, testGroup.size());
        assertTrue(testGroup.contains(testHero));
        assertTrue(testGroup.contains(testHero2));

    }
}
