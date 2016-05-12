/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Facade;

import edu.nowvet.Entitys.Auditoria;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julian
 */
@Local
public interface AuditoriaFacadeLocal {

    void create(Auditoria auditoria);

    void edit(Auditoria auditoria);

    void remove(Auditoria auditoria);

    Auditoria find(Object id);

    List<Auditoria> findAll();

    List<Auditoria> findRange(int[] range);

    int count();
    
}
