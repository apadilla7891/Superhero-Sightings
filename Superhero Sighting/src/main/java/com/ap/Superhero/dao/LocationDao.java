package com.ap.Superhero.dao;

import com.ap.Superhero.dto.Hero;
import com.ap.Superhero.dto.Location;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public interface LocationDao {
    Location addLocation(Location location);
    Location getLocationById(int id);
    List<Location> getAllLocations();
    void updateLocation(Location location);
    void deleteLocationById(int id);
    
    List<Location> getLocationForHero(Hero hero);
}
