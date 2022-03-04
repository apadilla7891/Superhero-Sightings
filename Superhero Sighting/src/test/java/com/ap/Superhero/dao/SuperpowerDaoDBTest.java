package com.ap.Superhero.dao;

import com.ap.Superhero.dto.Hero;
import com.ap.Superhero.dto.HeroSighting;
import com.ap.Superhero.dto.Location;
import com.ap.Superhero.dto.Organization;
import com.ap.Superhero.dto.Superpower;
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
public class SuperpowerDaoDBTest {
    
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
    
    public SuperpowerDaoDBTest() {
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
     * Test of getPowerById and addPower method, of class SuperpowerDaoDB.
     */
    @Test
    public void testGetAndAddPower() {
        Superpower test = new Superpower();
        test.setName("Test Power");
        test.setDescription("Test Description");
        test = powerTestDao.addPower(test);
        
        Superpower fromDao = powerTestDao.getPowerById(test.getId());
        assertEquals(test,fromDao);
    }
    
    
    /**
     * Test of getAllPowers method, of class SuperpowerDaoDB.
     */
    @Test
    public void testGetAllPowers() {
        Superpower test = new Superpower();
        test.setName("Test Power");
        test.setDescription("Test Description");
        test = powerTestDao.addPower(test);
        
        Superpower test2 = new Superpower();
        test2.setName("Test Power2");
        test2.setDescription("Test Description2");
        test2 = powerTestDao.addPower(test2);
        
        List<Superpower> powers = powerTestDao.getAllPowers();
        
        assertEquals(2, powers.size());
        assertTrue(powers.contains(test));
        assertTrue(powers.contains(test2));
    }
    
    /**
     * Test of updatePower method, of class SuperpowerDaoDB.
     */
    @Test
    public void testUpdatePower() {
        Superpower test = new Superpower();
        test.setName("Test Power");
        test.setDescription("Test Description");
        test = powerTestDao.addPower(test);
        
        Superpower fromDao = powerTestDao.getPowerById(test.getId());
        assertEquals(test,fromDao);
        
        test.setName("New test power");
        powerTestDao.updatePower(test);
        assertNotEquals(test, fromDao);
        
        fromDao = powerTestDao.getPowerById(test.getId());
        assertEquals(test,fromDao);
    }
    
    /**
     * Test of deletePower method, of class SuperpowerDaoDB.
     */
    @Test
    public void testDeletePower() {
        Superpower test = new Superpower();
        test.setName("Test Power");
        test.setDescription("Test Description");
        test = powerTestDao.addPower(test);
        
        Superpower fromDao = powerTestDao.getPowerById(test.getId());
        assertEquals(test,fromDao);
        
        powerTestDao.deletePowerById(test.getId());
        fromDao = powerTestDao.getPowerById(test.getId());
        assertNull(fromDao);
    }
}
