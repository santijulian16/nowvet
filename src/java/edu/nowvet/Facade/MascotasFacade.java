/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Facade;

import edu.nowvet.Entitys.Mascotas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Movimiento Rastafari
 */
@Stateless
public class MascotasFacade extends AbstractFacade<Mascotas> {

    @PersistenceContext(unitName = "NowVetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MascotasFacade() {
        super(Mascotas.class);
    }
    
    public List mostarMascotas(int idper){
        Query q;
        q=this.em.createQuery("select m from Mascotas m where m.codigoPropietario.dniPropietario=:id");
        q.setParameter("id", idper);
        List<Mascotas> mas=q.getResultList();
        return mas;
    }
    
}
