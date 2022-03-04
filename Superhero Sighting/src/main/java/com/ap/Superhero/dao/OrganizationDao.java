package com.ap.Superhero.dao;

import com.ap.Superhero.dto.Hero;
import com.ap.Superhero.dto.Organization;
import java.util.List;

/**
 *
 * @author Andy Padilla
 */
public interface OrganizationDao {
    Organization addOrg(Organization org);
    Organization getOrgById(int id);
    List<Organization> getAllOrgs();
    void updateOrg(Organization org);
    void deleteOrgById(int id);
    
    List<Organization> getOrgForHero(Hero hero);
}
