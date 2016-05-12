/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Facade;

import edu.nowvet.Entitys.Citasclinicas;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julian
 */
@Local
public interface CitasclinicasFacadeLocal {

    void create(Citasclinicas citasclinicas);

    void edit(Citasclinicas citasclinicas);

    void remove(Citasclinicas citasclinicas);

    Citasclinicas find(Object id);

    List<Citasclinicas> findAll();

    List<Citasclinicas> findRange(int[] range);

    int count();
    
}
