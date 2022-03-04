package com.ap.Superhero.dao;

import com.ap.Superhero.dao.SuperpowerDaoDB.SuperpowerMapper;
import com.ap.Superhero.dto.Hero;
import com.ap.Superhero.dto.Location;
import com.ap.Superhero.dto.Organization;
import com.ap.Superhero.dto.Superpower;
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
public class HeroDaoDB implements HeroDao{
    @Autowired
    JdbcTemplate jdbc;
    
    //adds hero to database
    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        final String INSERT_HERO ="INSERT INTO Hero(Name, Description, IsHero) VALUES(?,?,?)";
        jdbc.update(INSERT_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.isIsHero());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setId(newId);
        //adds powers to hero
        insertHeroSuperpower(hero);
        return hero;
    }
    
    //retieves hero by id if it exists otherwise returns null
    @Override
    public Hero getHeroById(int id) {
        try{
            final String SELECT_HERO_BY_ID ="SELECT * FROM Hero WHERE HeroId = ?";
            Hero hero = jdbc.queryForObject(SELECT_HERO_BY_ID, new HeroMapper(), id);
            hero.setPowers(getPowersForHero(id));
            return hero;
        }catch(DataAccessException ex) {
            return null;
        }
    }
    
    //gets all heroes in database
    @Override
    public List<Hero> getAllHeroes() {
        final String SELECT_ALL_HEROES = "SELECT * FROM Hero";
        List<Hero> heroes = jdbc.query(SELECT_ALL_HEROES, new HeroMapper());
        associatePower(heroes);
        return heroes;
    }
    
    //allows user to update hero
    @Override
    public void updateHero(Hero hero) {
        final String UPDATE_HERO = "UPDATE Hero SET Name = ?, Description = ?, IsHero = ? WHERE HeroId = ?";
        jdbc.update(UPDATE_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.isIsHero(),
                hero.getId());
        //deletes entries from hero superpower bridge tbale and rebuilds
        final String DELETE_HERO_SUPERPOWER = "DELETE FROM HeroSuperpower WHERE HeroId = ?";
        jdbc.update(DELETE_HERO_SUPERPOWER, hero.getId());
        insertHeroSuperpower(hero);
    }
    
    //deletes a hero from database
    @Override
    @Transactional
    public void deleteHeroById(int id) {
        final String DELETE_HERO_SUPERPOWER = "DELETE FROM HeroSuperpower WHERE HeroId = ?";
        jdbc.update(DELETE_HERO_SUPERPOWER, id);
        
        final String DELETE_HERO_SIGHTING = "DELETE FROM HeroSighting where HeroId = ?";
        jdbc.update(DELETE_HERO_SIGHTING, id);
        
        final String DELETE_HERO_ORG = "DELETE FROM HeroOrganization where HeroId = ?";
        jdbc.update(DELETE_HERO_ORG, id);
        
        final String DELETE_HERO = "DELETE FROM Hero where HeroId = ?";
        jdbc.update(DELETE_HERO, id);
    }
    
    //gets all the heros in an organization
    @Override
    public List<Hero> getHeroesByOrg(Organization org) {
        final String SELECT_HEROES_FOR_ORG ="Select h.* FROM Hero h "
                +"JOIN HeroOrganization ho ON ho.HeroId = h.HeroId WHERE ho.OrganizationId = ?";
        List<Hero> heroes = jdbc.query(SELECT_HEROES_FOR_ORG, new HeroMapper(), org.getId());
        associatePower(heroes);
        return heroes;
    }
    
    //gets all the heros spotted at a location
    @Override
    public List<Hero> getHeroesByLocation(Location location) {
        final String SELECT_HERO_FOR_LOCATIONS = "Select h.* FROM Hero h "
                + "JOIN HeroSighting hs ON hs.HeroId = h.HeroId WHERE hs.LocationId = ?";
        List<Hero> heroes = jdbc.query(SELECT_HERO_FOR_LOCATIONS, new HeroMapper(), location.getId());
        associatePower(heroes);
        return heroes;
    }
    
    //helper method to make a her superpower bridge table entry
    private void insertHeroSuperpower(Hero hero) {
        final String INSERT_HERO_SUPERPOWER = "INSERT INTO HeroSuperpower(HeroId,PowerId) VALUES(?,?)";
        for(Superpower power : hero.getPowers()){
            jdbc.update(INSERT_HERO_SUPERPOWER,
                    hero.getId(),
                    power.getId());
        }
    }
    
    //helper method to get all superpowers for a hero
    private List<Superpower> getPowersForHero(int id){
        final String SELECT_POWERS_FOR_HERO = "SELECT p.* FROM Superpower p "
                + "JOIN HeroSuperpower hs ON hs.PowerId = p.PowerId WHERE hs.HeroId = ?";
        return jdbc.query(SELECT_POWERS_FOR_HERO, new SuperpowerMapper(), id);
    }
    
    //helper method to add powers to heroes
    public void associatePower(List<Hero> heroes) {
        for(Hero hero : heroes){
            hero.setPowers(getPowersForHero(hero.getId()));
        }
    }
    
    //maps hero table entry to object
    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setId(rs.getInt("HeroId"));
            hero.setName(rs.getString("Name"));
            hero.setDescription(rs.getString("Description"));
            hero.setIsHero(rs.getBoolean("IsHero"));

            return hero;
        }
    }
}
