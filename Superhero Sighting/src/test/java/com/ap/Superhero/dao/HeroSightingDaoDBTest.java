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
public class HeroSightingDaoDBTest {
    
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
    
    public HeroSightingDaoDBTest() {
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
     * Test of addSighting and getById method, of class HeroSightingDaoDB.
     */
    @Test
    public void testAddAndGetSighting() {
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
        
        HeroSighting fromDao = sightTestDao.getSightingById(testSight.getId());
        assertEquals(testSight,fromDao);
    }
    
    /**
     * Test of getAllSightings method, of class HeroSightingDaoDB.
     */
    @Test
    public void testGetAllSighting() {
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
        
        Location test2 = new Location();
        test2.setName("testName2");
        test2.setDescription("testDescription2");
        test2.setAddress("testAddress2");
        test2.setLatitude(-23.345);
        test2.setLongitude(56.3);
        test2 = localTestDao.addLocation(test2);
        
        HeroSighting testSight2 = new HeroSighting();
        testSight2.setHero(testHero2);
        testSight2.setLocation(test2);
        LocalDate date2 = LocalDate.parse("2022-01-15");
        testSight2.setDate(date2);
        testSight2 = sightTestDao.addSighting(testSight2);
        
        List<HeroSighting> sightings = sightTestDao.getAllSightings();
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(testSight));
        assertTrue(sightings.contains(testSight2));
    }
    
    /**
     * Test of getSightingsByDate method, of class HeroSightingDaoDB.
     */
    @Test
    public void testGetByDateSighting() {
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
        
        Location test2 = new Location();
        test2.setName("testName2");
        test2.setDescription("testDescription2");
        test2.setAddress("testAddress2");
        test2.setLatitude(-23.345);
        test2.setLongitude(56.3);
        test2 = localTestDao.addLocation(test2);
        
        HeroSighting testSight2 = new HeroSighting();
        testSight2.setHero(testHero2);
        testSight2.setLocation(test2);
        LocalDate date2 = LocalDate.parse("2021-12-05");
        testSight2.setDate(date2);
        testSight2 = sightTestDao.addSighting(testSight2);
        
        HeroSighting testSight3 = new HeroSighting();
        testSight3.setHero(testHero2);
        testSight3.setLocation(test2);
        LocalDate date3 = LocalDate.parse("2022-01-15");
        testSight3.setDate(date3);
        testSight3 = sightTestDao.addSighting(testSight3);
        
        List<HeroSighting> sightings = sightTestDao.getSightingsForDate(date);
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(testSight));
        assertTrue(sightings.contains(testSight2));
        assertFalse(sightings.contains(testSight3));
    }
    
    /**
     * Test of updateSighting method, of class HeroSightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {
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
        
        HeroSighting fromDao = sightTestDao.getSightingById(testSight.getId());
        assertEquals(testSight,fromDao);
        
        date = LocalDate.parse("2015-01-05");
        testSight.setDate(date);
        
        sightTestDao.updateSighting(testSight);
        assertNotEquals(testSight, fromDao);
        
        fromDao = sightTestDao.getSightingById(testSight.getId());
        assertEquals(testSight,fromDao);
    }
    
    /**
     * Test of deleteSighting method, of class HeroSightingDaoDB.
     */
    @Test
    public void testDeleteSighting() {
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
        
        HeroSighting fromDao = sightTestDao.getSightingById(testSight.getId());
        assertEquals(testSight,fromDao);
        
        sightTestDao.deleteSightingById(testSight.getId());
        fromDao = sightTestDao.getSightingById(testSight.getId());
        assertNull(fromDao);
    }
}
