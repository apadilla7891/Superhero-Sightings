package com.ap.Superhero.dao;

import com.ap.Superhero.dto.Hero;
import com.ap.Superhero.dto.HeroSighting;
import com.ap.Superhero.dto.Location;
import com.ap.Superhero.dto.Superpower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Andy Padilla
 */
@Repository
public class HeroSightingDaoDB implements HeroSightingDao{
    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    HeroDaoDB heroDaoDB;
    
    //adds a hero sighting to database
    @Override
    @Transactional
    public HeroSighting addSighting(HeroSighting sight) {
        final String INSERT_SIGHTING = "INSERT INTO HeroSighting(HeroId, LocationId, Date) VALUE(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sight.getHero().getId(),
                sight.getLocation().getId(),
                sight.getDate());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sight.setId(newId);
        
        return sight;
    }

    //gets a hero sighting by id if it exists returns null otherwise
    @Override
    public HeroSighting getSightingById(int id) {
        try{
            final String SELECT_Sight_BY_ID ="SELECT * FROM HeroSighting WHERE SightingId = ?";
            HeroSighting sight = jdbc.queryForObject(SELECT_Sight_BY_ID, new SightMapper(), id);
            sight.setHero(getHeroForSighting(id));
            sight.setLocation(getLocationForSighting(id));
            return sight;
        }catch(DataAccessException ex) {
            return null;
        }
    }

    //gets all hero sightings from database
    @Override
    public List<HeroSighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM HeroSighting";
        List<HeroSighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightMapper());
        //adds corresponding location and hero info to sighting
        associateLocationsAndHeroesForSightings(sightings);
        return sightings;
    }
    
    //allows user to update a hero sighting
    @Override
    public void updateSighting(HeroSighting sight) {
        final String UPDATE_SIGHTING ="UPDATE HeroSighting SET HeroId = ?, LocationId = ?, Date = ? WHERE SightingId = ?";
        jdbc.update(UPDATE_SIGHTING,
                sight.getHero().getId(),
                sight.getLocation().getId(),
                sight.getDate(),
                sight.getId());
    }
    
    //deletes a sighting from the database
    @Override
    @Transactional
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM HeroSighting WHERE SightingId = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }
    
    //gets all the sightings for a given date
    @Override
    public List<HeroSighting> getSightingsForDate(LocalDate date) {
        final String SELECT_SIGHTINGS_FOR_DATE = "SELECT * FROM HeroSighting WHERE Date = ?";
        List<HeroSighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_DATE, new SightMapper(), date);
        for(HeroSighting sight : sightings){
            sight.setLocation(getLocationForSighting(sight.getId()));
            sight.setHero(getHeroForSighting(sight.getId()));
        }
        return sightings;
    }
    
    @Override
    public List<HeroSighting> getLastTen(){
        final String GET_LAST_TEN_SIGHTINGS="SELECT * FROM HeroSighting ORDER BY Date DESC, SightingId DESC LIMIT 10";
        List<HeroSighting> sightings = jdbc.query(GET_LAST_TEN_SIGHTINGS, new SightMapper());
        for(HeroSighting sight : sightings){
            sight.setLocation(getLocationForSighting(sight.getId()));
            sight.setHero(getHeroForSighting(sight.getId()));
        }
        return sightings;
    }
    
    //helepr method gets location info for a sighting
    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM Location l "
                + "JOIN HeroSighting hs ON hs.LocationId = l.LocationId WHERE hs.SightingId = ?";
        Location location = jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationDaoDB.LocationMapper(), id);
        return location;
    }
    
    //helper method to add location and hero info to sighting
    private void associateLocationsAndHeroesForSightings(List<HeroSighting> sightings) {
        for(HeroSighting sight : sightings){
            sight.setLocation(getLocationForSighting(sight.getId()));
            sight.setHero(getHeroForSighting(sight.getId()));
        }
    }
    
    //helper method to get hero info for sighting
    private Hero getHeroForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT h.* FROM Hero h "
                + "JOIN HeroSighting hs ON hs.HeroId = h.HeroId WHERE hs.SightingId = ?";
        Hero hero = jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new HeroDaoDB.HeroMapper(), id);
        hero.setPowers(getPowerforHero(hero.getId()));
        return hero;}
    
    //helper method to add powers to hero 
    private List<Superpower> getPowerforHero(int id) {
        final String SELECT_POWERS_FOR_HERO = "SELECT p.* FROM Superpower p "
                + "JOIN HeroSuperpower hs ON hs.PowerId = p.PowerId WHERE hs.HeroId = ?";
        return jdbc.query(SELECT_POWERS_FOR_HERO, new SuperpowerDaoDB.SuperpowerMapper(), id);
    }

    
    //maps hero sighting table entry to object
    public static final class SightMapper implements RowMapper<HeroSighting> {

        @Override
        public HeroSighting mapRow(ResultSet rs, int index) throws SQLException {
            HeroSighting sight = new HeroSighting();
            sight.setId(rs.getInt("SightingId"));
            sight.setDate(rs.getDate("Date").toLocalDate());
            return sight;
        }
    }
}
