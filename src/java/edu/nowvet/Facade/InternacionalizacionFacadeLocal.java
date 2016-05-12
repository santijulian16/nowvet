/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Facade;

import edu.nowvet.Entitys.Internacionalizacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julian
 */
@Local
public interface InternacionalizacionFacadeLocal {

    void create(Internacionalizacion internacionalizacion);

    void edit(Internacionalizacion internacionalizacion);

    void remove(Internacionalizacion internacionalizacion);

    Internacionalizacion find(Object id);

    List<Internacionalizacion> findAll();

    List<Internacionalizacion> findRange(int[] range);

    int count();
    
}
