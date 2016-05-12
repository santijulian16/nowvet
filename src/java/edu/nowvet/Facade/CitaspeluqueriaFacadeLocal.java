/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Facade;

import edu.nowvet.Entitys.Citaspeluqueria;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julian
 */
@Local
public interface CitaspeluqueriaFacadeLocal {

    void create(Citaspeluqueria citaspeluqueria);

    void edit(Citaspeluqueria citaspeluqueria);

    void remove(Citaspeluqueria citaspeluqueria);

    Citaspeluqueria find(Object id);

    List<Citaspeluqueria> findAll();

    List<Citaspeluqueria> findRange(int[] range);

    int count();
    
}
