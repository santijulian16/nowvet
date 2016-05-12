/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Facade;

import edu.nowvet.Entitys.Propietarios;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julian
 */
@Local
public interface PropietariosFacadeLocal {

    void create(Propietarios propietarios);

    void edit(Propietarios propietarios);

    void remove(Propietarios propietarios);

    Propietarios find(Object id);

    List<Propietarios> findAll();

    List<Propietarios> findRange(int[] range);

    int count();
    
}
