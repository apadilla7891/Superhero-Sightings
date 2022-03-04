package com.ap.Superhero.service;

import com.ap.Superhero.dto.Hero;
import com.ap.Superhero.dto.HeroSighting;
import com.ap.Superhero.dto.Location;
import com.ap.Superhero.dto.Organization;
import com.ap.Superhero.dto.Superpower;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public interface SuperService {
    Hero addHero(Hero hero);
    Hero getHeroById(int id);
    List<Hero> getAllHeroes();
    void updateHero(Hero hero);
    void deleteHeroById(int id);
    List<Hero> getHeroesByOrg(Organization org);
    List<Hero> getHeroesByLocation(Location location);
    HeroSighting addSighting(HeroSighting sight);
    HeroSighting getSightingById(int id);
    List<HeroSighting> getAllSightings();
    void updateSighting(HeroSighting sight);
    void deleteSightingById(int id);
    List<HeroSighting> getSightingsForDate(LocalDate date);
    Location addLocation(Location location);
    Location getLocationById(int id);
    List<Location> getAllLocations();
    void updateLocation(Location location);
    void deleteLocationById(int id);    
    List<Location> getLocationForHero(Hero hero);
    Organization addOrg(Organization org);
    Organization getOrgById(int id);
    List<Organization> getAllOrgs();
    void updateOrg(Organization org);
    void deleteOrgById(int id);    
    List<Organization> getOrgForHero(Hero hero);
    Superpower addPower(Superpower power);
    Superpower getPowerById(int id);
    List<Superpower> getAllPowers();
    void updatePower(Superpower power);
    void deletePowerById(int id);
    public boolean checkLatitude(String latitude);
    public boolean checkLongitude(String longitude);
    public LocalDate validDate(String date);
    public boolean checkDate(String date);
    public LocalDate insertDate(boolean dateCorrect, String sDate);
    public List<Location> removeDupeLocal(List<Location> location);
    public List<Hero> removeDupeHero(List<Hero> hero);
    public List<HeroSighting> getLastTen();
    public List<Superpower> setPowerInfo(String[] powerIds, List<Superpower> powers);
    public List<Hero> setHeroInfo(String[] heroIds, List<Hero> heroes);
    public Location setLocationInfo(Location location, String name, String description, String address, String longitude, String latitude);
}
