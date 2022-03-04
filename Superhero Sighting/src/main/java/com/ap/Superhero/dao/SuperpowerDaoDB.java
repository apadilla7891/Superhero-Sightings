package com.ap.Superhero.dao;


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
public class SuperpowerDaoDB implements SuperpowerDao{
    
    @Autowired
    JdbcTemplate jdbc;
    
    //Adds a power to the database
    @Override
    @Transactional
    public Superpower addPower(Superpower power) {
        final String INSERT_POWER = "INSERT INTO Superpower(PowerName, Description) VALUES(?,?)";
        jdbc.update(INSERT_POWER,
                power.getName(),
                power.getDescription());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setId(newId);
        return power;
    }
    
    //using an id retrieves corresponding power if it exists otherwise returns null
    @Override
    public Superpower getPowerById(int id) {
        try{
            final String SELECT_POWER_BY_ID = "SELECT * FROM Superpower WHERE PowerId=?";
            return jdbc.queryForObject(SELECT_POWER_BY_ID, new SuperpowerMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        } 
    }
    
    //gets all powers from the data base
    @Override
    public List<Superpower> getAllPowers() {
        final String SELECT_ALL_POWERS ="SELECT * FROM Superpower"; 
        return jdbc.query(SELECT_ALL_POWERS, new SuperpowerMapper());
    }

    //allows user to update a power that is already in the database
    @Override
    public void updatePower(Superpower power) {
        final String UPDATE_POWER ="UPDATE Superpower SET PowerName=?, Description=? WHERE PowerId=?";
        jdbc.update(UPDATE_POWER,
                power.getName(),
                power.getDescription(),
                power.getId());
    }
    
    //using a given id deletes a power from all databases
    @Override
    @Transactional
    public void deletePowerById(int id) {
        final String DELETE_HERO_SUPERPOWER="DELETE FROM HeroSuperpower WHERE PowerId = ?";
        jdbc.update(DELETE_HERO_SUPERPOWER, id);
        
        final String DELETE_SUPERPOWER="DELETE FROM Superpower WHERE PowerId = ?";
        jdbc.update(DELETE_SUPERPOWER, id);
    }
    
    //maps a table entry to a superpower object
    public static final class SuperpowerMapper implements RowMapper<Superpower> {

        @Override
        public Superpower mapRow(ResultSet rs, int index) throws SQLException {
            Superpower superpower = new Superpower();
            superpower.setId(rs.getInt("PowerId"));
            superpower.setName(rs.getString("PowerName"));
            superpower.setDescription(rs.getString("Description"));

            return superpower;
        }
    }
}
