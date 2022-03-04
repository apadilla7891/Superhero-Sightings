package com.ap.Superhero.dao;

import com.ap.Superhero.dto.Hero;
import com.ap.Superhero.dto.HeroSighting;
import com.ap.Superhero.dto.Location;
import com.ap.Superhero.dto.Organization;
import com.ap.Superhero.dto.Superpower;
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
public class OrganizationDaoDBTest {
    
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
    
    public OrganizationDaoDBTest() {
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
     * Test of addOrg and getOrgById method, of class OrganizationDaoDB.
     */
    @Test
    public void testAddAndGetOrg() {
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
        
        List<Hero> heroes = new ArrayList<>();
        heroes.add(testHero);
        
        Organization testOrg = new Organization();
        testOrg.setName("TestName");
        testOrg.setDescription("TestDescription");
        testOrg.setAddress("TestAddress");
        testOrg.setContact("TestContact");
        testOrg.setIsHero(true);
        testOrg.setHeroes(heroes);
        testOrg = orgTestDao.addOrg(testOrg);
        
        Organization fromDao = orgTestDao.getOrgById(testOrg.getId());
        assertEquals(testOrg, fromDao);
    }

    /**
     * Test of getAllOrgs method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetAllOrg() {
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
        
        List<Hero> heroes = new ArrayList<>();
        heroes.add(testHero);
        
        Organization testOrg = new Organization();
        testOrg.setName("TestName");
        testOrg.setDescription("TestDescription");
        testOrg.setAddress("TestAddress");
        testOrg.setContact("TestContact");
        testOrg.setIsHero(true);
        testOrg.setHeroes(heroes);
        testOrg = orgTestDao.addOrg(testOrg);
        
        
        Organization testOrg2 = new Organization();
        testOrg2.setName("TestName2");
        testOrg2.setDescription("TestDescription2");
        testOrg2.setAddress("TestAddress2");
        testOrg2.setContact("TestContact2");
        testOrg2.setIsHero(true);
        testOrg2.setHeroes(heroes);
        testOrg2 = orgTestDao.addOrg(testOrg2);
        
        List<Organization> orgs = orgTestDao.getAllOrgs();
        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(testOrg));
        assertTrue(orgs.contains(testOrg2));
    }
    
    /**
     * Test of updateOrg method, of class OrganizationDaoDB.
     */
    @Test
    public void testUpdateOrg() {
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
        
        List<Hero> heroes = new ArrayList<>();
        heroes.add(testHero);
        
        Organization testOrg = new Organization();
        testOrg.setName("TestName");
        testOrg.setDescription("TestDescription");
        testOrg.setAddress("TestAddress");
        testOrg.setContact("TestContact");
        testOrg.setIsHero(true);
        testOrg.setHeroes(heroes);
        testOrg = orgTestDao.addOrg(testOrg);
        
        Organization fromDao = orgTestDao.getOrgById(testOrg.getId());
        assertEquals(testOrg, fromDao);
        
        testOrg.setName("TestName2");
        testOrg.setDescription("TestDescription2");
        testOrg.setAddress("TestAddress2");
        testOrg.setContact("TestContact2");
        testOrg.setIsHero(false);
        
        orgTestDao.updateOrg(testOrg);
        assertNotEquals(testOrg,fromDao);
        
        fromDao = orgTestDao.getOrgById(testOrg.getId());
        assertEquals(testOrg, fromDao);
    }
    
    /**
     * Test of deleteOrg method, of class OrganizationDaoDB.
     */
    @Test
    public void testDeleteOrg() {
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
        
        List<Hero> heroes = new ArrayList<>();
        heroes.add(testHero);
        
        Organization testOrg = new Organization();
        testOrg.setName("TestName");
        testOrg.setDescription("TestDescription");
        testOrg.setAddress("TestAddress");
        testOrg.setContact("TestContact");
        testOrg.setIsHero(true);
        testOrg.setHeroes(heroes);
        testOrg = orgTestDao.addOrg(testOrg);
        
        Organization fromDao = orgTestDao.getOrgById(testOrg.getId());
        assertEquals(testOrg, fromDao);
        
        orgTestDao.deleteOrgById(testOrg.getId());
        fromDao = orgTestDao.getOrgById(testOrg.getId());
        assertNull(fromDao);
    }
    
    /**
     * Test of getOrgForHero method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetOrgForHero() {
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
        
        List<Hero> heroes = new ArrayList<>();
        heroes.add(testHero);
        
        Organization testOrg = new Organization();
        testOrg.setName("TestName");
        testOrg.setDescription("TestDescription");
        testOrg.setAddress("TestAddress");
        testOrg.setContact("TestContact");
        testOrg.setIsHero(true);
        testOrg.setHeroes(heroes);
        testOrg = orgTestDao.addOrg(testOrg);
        
        Organization testOrg2 = new Organization();
        testOrg2.setName("TestName2");
        testOrg2.setDescription("TestDescription2");
        testOrg2.setAddress("TestAddress2");
        testOrg2.setContact("TestContact2");
        testOrg2.setIsHero(false);
        testOrg2.setHeroes(heroes);
        testOrg2 = orgTestDao.addOrg(testOrg2);
        
        List<Organization> orgs = orgTestDao.getOrgForHero(testHero);
        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(testOrg));
        assertTrue(orgs.contains(testOrg2));

    }
}
