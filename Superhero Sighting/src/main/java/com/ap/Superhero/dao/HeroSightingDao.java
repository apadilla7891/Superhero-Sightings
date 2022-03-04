package com.ap.Superhero.dao;

import com.ap.Superhero.dto.HeroSighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public interface HeroSightingDao {
    HeroSighting addSighting(HeroSighting sight);
    HeroSighting getSightingById(int id);
    List<HeroSighting> getAllSightings();
    void updateSighting(HeroSighting sight);
    void deleteSightingById(int id);
    
    List<HeroSighting> getSightingsForDate(LocalDate date);
    public List<HeroSighting> getLastTen();
}
