/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ap.Superhero.dao;

import com.ap.Superhero.dto.Hero;
import com.ap.Superhero.dto.Location;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class LocationDaoDB implements LocationDao{
    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    HeroDaoDB heroDaoDB;
    
    //adds a new location to database
    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION ="INSERT INTO Location(Name, Description, Address, Longitude, Latitude) VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLongitude(),
                location.getLatitude());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    //retrieves a location based on id if it exists returns null otherwise
    @Override
    public Location getLocationById(int id) {
        try{
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM Location WHERE LocationId= ?";
            Location location = jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), id);
            
            return location;
        }catch(DataAccessException ex) {
            return null;
        }
    
    }
    
    //gets all locations from database
    @Override
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM LOCATION";
        List<Location> locations = jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
        return locations;
    }
    
    //allows user to update a location
    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION= "UPDATE Location SET Name = ?, Description =?, Address =?, Longitude =?, Latitude =? WHERE LocationId =?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLongitude(),
                location.getLatitude(),
                location.getId());
    }
    
    //deletes a location from database
    @Override
    public void deleteLocationById(int id) {
        final String DELETE_LOCATION_SIGHTING = "DELETE FROM HeroSighting WHERE LocationId =?";
        jdbc.update(DELETE_LOCATION_SIGHTING,id);
        
        final String DELETE_LOCATION = "DELETE FROM Location WHERE LocationId =?";
        jdbc.update(DELETE_LOCATION,id);
    }
    
    //gets all the locations a hero was spotted at
    @Override
    public List<Location> getLocationForHero(Hero hero) {
        final String SELECT_LOCATIONS_FOR_HERO = "Select l.* FROM Location l "
                + "JOIN HeroSighting hs ON hs.LocationId = l.LocationId WHERE hs.HeroId = ?";
        List<Location> locations = jdbc.query(SELECT_LOCATIONS_FOR_HERO, new LocationMapper(), hero.getId());
        return locations;
    }
    
    //maps a location table entry to object
    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("LocationId"));
            location.setName(rs.getString("Name"));
            location.setDescription(rs.getString("Description"));
            location.setAddress(rs.getString("Address"));
            location.setLongitude(rs.getDouble("Longitude"));
            location.setLatitude(rs.getDouble("Latitude"));
            
            return location;
        }
    }
}
