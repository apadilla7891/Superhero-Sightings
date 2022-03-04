package com.ap.Superhero.dao;

import com.ap.Superhero.dao.HeroDaoDB.HeroMapper;
import com.ap.Superhero.dto.Hero;
import com.ap.Superhero.dto.Organization;
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
public class OrganizationDaoDB implements OrganizationDao{
    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    HeroDaoDB heroDaoDB;
    
    //adds a organization to the databse
    @Override
    @Transactional
    public Organization addOrg(Organization org) {
        final String INSERT_ORG ="INSERT INTO Organization(Name, Description, Address, Contact, IsHero) VALUE(?,?,?,?,?)";
        jdbc.update(INSERT_ORG,
                org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getContact(),
                org.isIsHero());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setId(newId);
        //makes the bridge table entries needed
        insertHeroOrganization(org);
        return org;
    }
    
    //retrieves a organization based on id if it exists otherwise returns null
    @Override
    public Organization getOrgById(int id) {
        try{
            final String SELECT_ORG_BY_ID ="SELECT * FROM Organization WHERE OrganizationId = ?";
            Organization org = jdbc.queryForObject(SELECT_ORG_BY_ID, new OrganizationMapper(), id);
            //adds members to org object
            org.setHeroes(getHeroesForOrg(id));
            return org;
        }catch(DataAccessException ex) {
            return null;
        }
    }
    
    //gets all organizations
    @Override
    public List<Organization> getAllOrgs() {
        final String SELECT_ALL_ORGS = "SELECT * FROM Organization";
        List<Organization> orgs = jdbc.query(SELECT_ALL_ORGS, new OrganizationMapper());
        //adds members to org object
        associateHeroes(orgs);
        return orgs;
    }
    
    //allows user to update an organization
    @Override
    public void updateOrg(Organization org) {
        final String UPDATE_ORG ="UPDATE Organization SET Name = ?, Description = ?, Address = ?, Contact = ?, IsHero = ? WHERE OrganizationId = ?";
        jdbc.update(UPDATE_ORG,
                org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getContact(),
                org.isIsHero(),
                org.getId());
        //deletes all bridge table entries and rebuilds them to avoid duplicates
        final String DELETE_HERO_ORG = "DELETE FROM HeroOrganization WHERE OrganizationId = ?";
        jdbc.update(DELETE_HERO_ORG, org.getId());
        insertHeroOrganization(org);
    }
    
    //deletes an organization from all corresponding databases
    @Override
    @Transactional
    public void deleteOrgById(int id) {
        final String DELETE_HERO_ORG = "DELETE FROM HeroOrganization WHERE OrganizationId = ?";
        jdbc.update(DELETE_HERO_ORG, id);
        
        final String DELETE_ORG = "DELETE FROM Organization WHERE OrganizationId = ?";
        jdbc.update(DELETE_ORG, id);
    }
    
    //helper method to build bridge table entries
    private void insertHeroOrganization(Organization org) {
        final String INSERT_HERO_ORG ="INSERT INTO HeroOrganization(HeroId, OrganizationId) VALUES(?,?)";
        for(Hero hero : org.getHeroes()){
            jdbc.update(INSERT_HERO_ORG,
                    hero.getId(),
                    org.getId());
        }
    }
    
    //helper method to get all heroes for a corresponding organization
    private List<Hero> getHeroesForOrg(int id) {
        final String SELECT_HEROES_FOR_ORG ="Select h.* FROM Hero h "
                +"JOIN HeroOrganization ho ON ho.HeroId = h.HeroId WHERE ho.OrganizationId = ?";
        List<Hero> heroes = jdbc.query(SELECT_HEROES_FOR_ORG, new HeroMapper(), id);
        heroDaoDB.associatePower(heroes);
        return heroes;
    }
    
    //helper method to get all heroes for multiple organizations
    private void associateHeroes(List<Organization> orgs) {
        for(Organization org : orgs){
            org.setHeroes(getHeroesForOrg(org.getId()));
        }
    }
    
    //gets all organizations a hero belongs to
    @Override
    public List<Organization> getOrgForHero(Hero hero) {
        final String SELECT_ORGS_FOR_HERO ="Select o.* FROM Organization o "
                +"JOIN HeroOrganization ho ON ho.OrganizationId = o.OrganizationId WHERE ho.HeroId = ?";
        List<Organization> orgs = jdbc.query(SELECT_ORGS_FOR_HERO, new OrganizationMapper(), hero.getId());
        associateHeroes(orgs);
        return orgs;
    }
    
    //maps org table entry to object
    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization org = new Organization();
            org.setId(rs.getInt("OrganizationId"));
            org.setName(rs.getString("Name"));
            org.setDescription(rs.getString("Description"));
            org.setIsHero(rs.getBoolean("IsHero"));
            org.setAddress(rs.getString("Address"));
            org.setContact(rs.getString("Contact"));
            
            return org;
        }
    }
}
