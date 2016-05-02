/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Facade;

import edu.nowvet.Entitys.Usuarios;
import java.util.Date;
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
public class UsuariosFacade extends AbstractFacade<Usuarios> {

    @PersistenceContext(unitName = "NowVetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }
    
    public Usuarios login(String correo, String contrasena) {
        Usuarios objDatos = new Usuarios();
        objDatos.setNombres("Vacio");
        Query q;
        q = em.createQuery("select d from Usuarios d where d.correo = :nom and d.contrasena =:con");
        q.setParameter("nom", correo);
        q.setParameter("con", contrasena);
        
        List<Usuarios> miLista = q.getResultList();
        if (miLista.isEmpty()) {
            return objDatos;            
        } else {
            return miLista.get(0);
            
        }
    }
    
        
    public List<Usuarios> buscarVeterinario(Date fecha) {
        Query q;
        q = em.createQuery("select u from Usuarios u where u.cedula=u.personalveterinario.dniPersonal and :fecha BETWEEN u.personalveterinario.horarioEntrada and u.personalveterinario.horarioSalida");
        q.setParameter("fecha", fecha);
        return q.getResultList();
    }
}
