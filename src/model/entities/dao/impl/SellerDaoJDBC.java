package model.entities.dao.impl;

import db.DB;
import db.DbException;
import model.entities.Department;
import model.entities.Seller;
import model.entities.dao.SellerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

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
        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    """
                            SELECT seller.*,department.Name as DepName
                            FROM seller INNER JOIN department
                            ON seller.DepartmentId = department.Id
                            WHERE seller.Id = ?
                        """
            );

            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                return instantiateSeller(resultSet);
            }
            return null;

        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    private Seller instantiateSeller(ResultSet resultSet) throws SQLException{
        Department department = instantiateDepartment(resultSet);
        Seller seller = new Seller();

        seller.setId(resultSet.getInt("Id"));
        seller.setName(resultSet.getString("Name"));
        seller.setEmail(resultSet.getString("Email"));
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        seller.setBirthDate(resultSet.getDate("BirthDate"));
        seller.setDepartment(department);

        return seller;
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException{
        Department department = new Department();

        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepName"));

        return department;
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
