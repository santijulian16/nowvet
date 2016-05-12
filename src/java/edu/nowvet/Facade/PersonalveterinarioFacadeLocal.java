/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Facade;

import edu.nowvet.Entitys.Personalveterinario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julian
 */
@Local
public interface PersonalveterinarioFacadeLocal {

    void create(Personalveterinario personalveterinario);

    void edit(Personalveterinario personalveterinario);

    void remove(Personalveterinario personalveterinario);

    Personalveterinario find(Object id);

    List<Personalveterinario> findAll();

    List<Personalveterinario> findRange(int[] range);

    int count();
    
}
