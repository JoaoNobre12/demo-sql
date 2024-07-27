package model.entities.dao.impl;

import model.entities.Department;
import model.entities.Seller;
import model.entities.dao.SellerDao;

import java.util.List;

public class SellerDaoJDBC implements SellerDao {
    /**
     * @param obj seller implementation
     */
    @Override
    public void insert(Seller obj) {

    }

    /**
     * @param obj seller implementation
     */
    @Override
    public void update(Seller obj) {

    }

    /**
     * @param id id of seller
     */
    @Override
    public void deleteById(Integer id) {

    }

    /**
     * @param id id of seller
     * @return found objects
     */
    @Override
    public Seller findById(Integer id) {
        return null;
    }

    /**
     * @return list of found sales
     */
    @Override
    public List<Seller> findAll() {
        return List.of();
    }

    /**
     * @param department department implementation
     * @return list of found sellers by department
     */
    @Override
    public List<Seller> findByDepartment(Department department) {
        return List.of();
    }
}
