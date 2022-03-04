package com.ap.Superhero.dao;

import com.ap.Superhero.dto.Superpower;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public interface SuperpowerDao {
    Superpower addPower(Superpower power);
    Superpower getPowerById(int id);
    List<Superpower> getAllPowers();
    void updatePower(Superpower power);
    void deletePowerById(int id);
}
