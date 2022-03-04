package com.ap.Superhero.dao;

import com.ap.Superhero.dto.Hero;
import com.ap.Superhero.dto.Location;
import com.ap.Superhero.dto.Organization;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public interface HeroDao {
    Hero addHero(Hero hero);
    Hero getHeroById(int id);
    List<Hero> getAllHeroes();
    void updateHero(Hero hero);
    void deleteHeroById(int id);
    
    List<Hero> getHeroesByOrg(Organization org);
    List<Hero> getHeroesByLocation(Location location);
}
