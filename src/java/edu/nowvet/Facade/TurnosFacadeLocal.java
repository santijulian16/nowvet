/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Facade;

import edu.nowvet.Entitys.Turnos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julian
 */
@Local
public interface TurnosFacadeLocal {

    void create(Turnos turnos);

    void edit(Turnos turnos);

    void remove(Turnos turnos);

    Turnos find(Object id);

    List<Turnos> findAll();

    List<Turnos> findRange(int[] range);

    int count();
    
}
