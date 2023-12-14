/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Machine;
import entities.Employe;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;

/**
 *
 * @author Pc
 */
@Stateless
public class MachineFacade extends AbstractFacade<Machine> {
    @PersistenceContext(unitName = "ControleEJBJSFPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MachineFacade() {
        super(Machine.class);
    }

    public List<Machine> findByEmployee(String employeeName) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Machine> criteriaQuery = criteriaBuilder.createQuery(Machine.class);
    Root<Machine> machineRoot = criteriaQuery.from(Machine.class);

    Join<Machine, Employe> employeJoin = machineRoot.join("employe");

    criteriaQuery.select(machineRoot)
            .where(criteriaBuilder.equal(employeJoin.get("nom"), employeeName));

    TypedQuery<Machine> typedQuery = em.createQuery(criteriaQuery);
    return typedQuery.getResultList();
}
    
    
    public List<Object[]> getMachineAcquisitionData() {
        String jpqlQuery = "SELECT FUNCTION('YEAR', m.dateAchat), COUNT(m) FROM Machine m GROUP BY FUNCTION('YEAR', m.dateAchat)";
         Query query = getEntityManager().createQuery(jpqlQuery);
        return query.getResultList();
    }
    
}
