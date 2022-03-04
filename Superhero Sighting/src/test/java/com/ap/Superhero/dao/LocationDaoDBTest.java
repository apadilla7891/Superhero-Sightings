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
public class LocationDaoDBTest {
    
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
    
    public LocationDaoDBTest() {
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
     * Test of addLocation and getLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testAddAndGetLocation() {
        Location test = new Location();
        test.setName("testName");
        test.setDescription("testDescription");
        test.setAddress("testAddress");
        test.setLatitude(123.345);
        test.setLongitude(-56.3);
        test = localTestDao.addLocation(test);
        
        Location fromDao = localTestDao.getLocationById(test.getId());
        assertEquals(test, fromDao);
        
    }
    
    /**
     * Test of getAllLocation method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocation() {
        Location test = new Location();
        test.setName("testName");
        test.setDescription("testDescription");
        test.setAddress("testAddress");
        test.setLatitude(123.345);
        test.setLongitude(-56.3);
        test = localTestDao.addLocation(test);
        
        Location test2 = new Location();
        test2.setName("testName2");
        test2.setDescription("testDescription2");
        test2.setAddress("testAddress2");
        test2.setLatitude(-36.345);
        test2.setLongitude(96.2);
        test2 = localTestDao.addLocation(test2);

        List<Location> locations = localTestDao.getAllLocations();
        assertEquals(2, locations.size());
        assertTrue(locations.contains(test));
        assertTrue(locations.contains(test2));
    }
    
    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */
    @Test
    public void testUpdateLocation() {
        Location test = new Location();
        test.setName("testName");
        test.setDescription("testDescription");
        test.setAddress("testAddress");
        test.setLatitude(123.345);
        test.setLongitude(-56.3);
        test = localTestDao.addLocation(test);
        
        Location fromDao = localTestDao.getLocationById(test.getId());
        assertEquals(test, fromDao);
        
        test.setName("testName2");
        test.setDescription("testDescription2");
        test.setAddress("testAddress2");
        test.setLatitude(-36.345);
        test.setLongitude(96.2);
        
        localTestDao.updateLocation(test);
        assertNotEquals(test,fromDao);
        
        fromDao = localTestDao.getLocationById(test.getId());
        assertEquals(test, fromDao);
    }
    
    /**
     * Test of deleteLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocation() {
        Location test = new Location();
        test.setName("testName");
        test.setDescription("testDescription");
        test.setAddress("testAddress");
        test.setLatitude(123.345);
        test.setLongitude(-56.3);
        test = localTestDao.addLocation(test);
        
        Location fromDao = localTestDao.getLocationById(test.getId());
        assertEquals(test, fromDao);
        
        localTestDao.deleteLocationById(test.getId());
        fromDao = localTestDao.getLocationById(test.getId());
        assertNull(fromDao);
    }
    
    /**
     * Test of getLocationsByHero method of class LocationDaoDB
     */
    @Test
    public void testGetLocationsByHero(){
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
        
        Location test2 = new Location();
        test2.setName("testName2");
        test2.setDescription("testDescription2");
        test2.setAddress("testAddress2");
        test2.setLatitude(23.345);
        test2.setLongitude(56.3);
        test2 = localTestDao.addLocation(test2);
        
        HeroSighting testSight2 = new HeroSighting();
        testSight2.setHero(testHero);
        testSight2.setLocation(test2);
        LocalDate date2 = LocalDate.parse("2022-02-05");
        testSight2.setDate(date2);
        testSight2 = sightTestDao.addSighting(testSight2);
        
        List<Location> locations = localTestDao.getLocationForHero(testHero);
        assertEquals(2, locations.size());
        assertTrue(locations.contains(test));
        assertTrue(locations.contains(test2));
    }
}
