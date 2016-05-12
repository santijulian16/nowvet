/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Facade;

import edu.nowvet.Entitys.Personalveterinario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Julian
 */
@Stateless
public class PersonalveterinarioFacade extends AbstractFacade<Personalveterinario> implements PersonalveterinarioFacadeLocal {

    @PersistenceContext(unitName = "NowVetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonalveterinarioFacade() {
        super(Personalveterinario.class);
    }
    
}
