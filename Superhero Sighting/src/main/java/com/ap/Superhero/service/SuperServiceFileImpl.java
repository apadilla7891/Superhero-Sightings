package com.ap.Superhero.service;

import com.ap.Superhero.dao.HeroDao;
import com.ap.Superhero.dao.HeroSightingDao;
import com.ap.Superhero.dao.LocationDao;
import com.ap.Superhero.dao.OrganizationDao;
import com.ap.Superhero.dao.SuperpowerDao;
import com.ap.Superhero.dto.Hero;
import com.ap.Superhero.dto.HeroSighting;
import com.ap.Superhero.dto.Location;
import com.ap.Superhero.dto.Organization;
import com.ap.Superhero.dto.Superpower;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Andy Padilla
 */
@Service
public class SuperServiceFileImpl implements SuperService{
    
    @Autowired
    SuperpowerDao powerDao;
    
    @Autowired
    HeroDao heroDao;
    
    @Autowired
    OrganizationDao orgDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    HeroSightingDao sightingDao;
    
    @Override
    public Hero addHero(Hero hero) {
        return heroDao.addHero(hero);
    }

    @Override
    public Hero getHeroById(int id) {
        return heroDao.getHeroById(id);
    }

    @Override
    public List<Hero> getAllHeroes() {
        return heroDao.getAllHeroes();
    }

    @Override
    public void updateHero(Hero hero) {
        heroDao.updateHero(hero);
    }

    @Override
    public void deleteHeroById(int id) {
        heroDao.deleteHeroById(id);
    }

    @Override
    public List<Hero> getHeroesByOrg(Organization org) {
        return heroDao.getHeroesByOrg(org);
    }

    @Override
    public List<Hero> getHeroesByLocation(Location location) {
        return heroDao.getHeroesByLocation(location);
    }

    @Override
    public HeroSighting addSighting(HeroSighting sight) {
        return sightingDao.addSighting(sight);
    }

    @Override
    public HeroSighting getSightingById(int id) {
        return sightingDao.getSightingById(id);
    }

    @Override
    public List<HeroSighting> getAllSightings() {
        return sightingDao.getAllSightings();
    }

    @Override
    public void updateSighting(HeroSighting sight) {
        sightingDao.updateSighting(sight);
    }

    @Override
    public void deleteSightingById(int id) {
        sightingDao.deleteSightingById(id);
    }

    @Override
    public List<HeroSighting> getSightingsForDate(LocalDate date) {
        return sightingDao.getSightingsForDate(date);
    }

    @Override
    public Location addLocation(Location location) {
        return locationDao.addLocation(location);
    }

    @Override
    public Location getLocationById(int id) {
        return locationDao.getLocationById(id);
    }

    @Override
    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    @Override
    public void updateLocation(Location location) {
        locationDao.updateLocation(location);
    }

    @Override
    public void deleteLocationById(int id) {
        locationDao.deleteLocationById(id);
    }

    @Override
    public List<Location> getLocationForHero(Hero hero) {
        return locationDao.getLocationForHero(hero);
    }

    @Override
    public Organization addOrg(Organization org) {
        return orgDao.addOrg(org);
    }

    @Override
    public Organization getOrgById(int id) {
        return orgDao.getOrgById(id);
    }

    @Override
    public List<Organization> getAllOrgs() {
        return orgDao.getAllOrgs();
    }

    @Override
    public void updateOrg(Organization org) {
        orgDao.updateOrg(org);
    }

    @Override
    public void deleteOrgById(int id) {
        orgDao.deleteOrgById(id);
    }

    @Override
    public List<Organization> getOrgForHero(Hero hero) {
        return orgDao.getOrgForHero(hero);
    }

    @Override
    public Superpower addPower(Superpower power) {
        return powerDao.addPower(power);
    }

    @Override
    public Superpower getPowerById(int id) {
        return powerDao.getPowerById(id);
    }

    @Override
    public List<Superpower> getAllPowers() {
        return powerDao.getAllPowers();
    }

    @Override
    public void updatePower(Superpower power) {
        powerDao.updatePower(power);
    }

    @Override
    public void deletePowerById(int id) {
        powerDao.deletePowerById(id);
    }

    @Override
    public boolean checkLatitude(String latitude) {
         try{
            double value = Double.parseDouble(latitude);
            if(value<-90 || value>90){
                return false;
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean checkLongitude(String longitude) {
        try{
            double value = Double.parseDouble(longitude);
            if(value<-180 || value>180){
                return false;
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }
    @Override
    public LocalDate validDate(String date){
        try{
            LocalDate ld = LocalDate.parse(date);
            return ld;
        }catch(Exception ex){
            return null;
        }
    }
    
    @Override
    public boolean checkDate(String date){
        try{
            LocalDate ld = LocalDate.parse(date);
            return true;
        }catch(Exception ex){
            return false;
        }
    }
    @Override
    public LocalDate insertDate(boolean dateCorrect, String sDate){
        LocalDate date = null;
        if(dateCorrect==true){
            return date = validDate(sDate);
        }else{
            String wDate = "2500-12-12";
            return date = validDate(wDate);
        }
    }

    @Override
    public List<Location> removeDupeLocal(List<Location> location) {
        location = location.stream().distinct().collect(Collectors.toList());
        return location;
    }
    
    @Override
    public List<Hero> removeDupeHero(List<Hero> hero) {
        hero = hero.stream().distinct().collect(Collectors.toList());
        return hero;
    }

    @Override
    public List<HeroSighting> getLastTen() {
        return sightingDao.getLastTen();
    }
    
    @Override
    public List<Superpower> setPowerInfo(String[] powerIds, List<Superpower> powers){
        if(powerIds !=null){
            for(String powerId : powerIds){
                powers.add(powerDao.getPowerById(Integer.parseInt(powerId)));
            }
        }
        return powers;
    }
    
    @Override
    public List<Hero> setHeroInfo(String[] heroIds, List<Hero> heroes){
        if(heroIds != null){
            for(String superId : heroIds){
                heroes.add(heroDao.getHeroById(Integer.parseInt(superId)));
            }
        }
        return heroes;
    }
    
    @Override
    public Location setLocationInfo(Location location, String name, String description, String address, String longitude, String latitude){
        location.setName(name);
        location.setDescription(description);
        location.setAddress(address);
        
        boolean validLong = checkLongitude(longitude);
        boolean validLat = checkLatitude(latitude);
                
        if(validLong){
            location.setLongitude(Double.parseDouble(longitude));           
        }else if (!longitude.isEmpty()){
            location.setLongitude(999999999);     
        }
        
        if(validLat){
            location.setLatitude(Double.parseDouble(latitude));            
        }else if(!latitude.isEmpty()){
            location.setLatitude(999999999);     
        }
        return location;
    }
}
