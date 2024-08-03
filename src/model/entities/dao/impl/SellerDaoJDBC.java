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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    """
                        INSERT INTO seller
                        (Name, Email, BirthDate, BaseSalary, DepartmentId)
                        VALUES (?, ?, ?, ?, ?)
                        """,
                    PreparedStatement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1, obj.getName());
            preparedStatement.setString(2, obj.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            preparedStatement.setDouble(4, obj.getBaseSalary());
            preparedStatement.setInt(5, obj.getDepartment().getId());

            int rows = preparedStatement.executeUpdate();
            System.out.println(rows);

            if (rows<1){
                throw new DbException("Error when trying to insert");
            }


        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(preparedStatement);
        }
    }

    /**
     * @param obj seller implementation
     */
    @Override
    public void update(Seller obj) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    """
                        UPDATE seller
                        SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?
                        WHERE id = ?
                        """
            );

            preparedStatement.setString(1, obj.getName());
            preparedStatement.setString(2, obj.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            preparedStatement.setDouble(4, obj.getBaseSalary());
            preparedStatement.setInt(5, obj.getDepartment().getId());

            preparedStatement.setInt(6, obj.getId());

            preparedStatement.executeUpdate();


        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(preparedStatement);
        }
    }

    /**
     * @param id id of seller
     */
    @Override
    public void deleteById(Integer id) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    """
                        DELETE FROM seller
                        WHERE id = ?
                        """
            );

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();


        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(preparedStatement);
        }
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
                Department department = instantiateDepartment(resultSet);
                return instantiateSeller(resultSet, department);
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

    private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException{
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
        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    """
                            SELECT seller.*, department.Name AS depName
                            FROM seller INNER JOIN department ON seller.DepartmentId = department.Id
                        """
            );

            resultSet = preparedStatement.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while(resultSet.next()){
                Department department1 = map.get(resultSet.getInt("DepartmentId"));
                if (department1 == null){
                    department1 = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), department1);
                }
                Seller seller = instantiateSeller(resultSet, department1);
                list.add(seller);
            }
            return list;

        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    /**
     * @param department department implementation
     * @return list of found sellers by department
     */
    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    """
                            SELECT seller.*, department.Name AS depName
                            FROM seller INNER JOIN department ON seller.DepartmentId = department.Id
                            WHERE DepartmentId = ?;
                        """
            );

            preparedStatement.setInt(1, department.getId());

            resultSet = preparedStatement.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while(resultSet.next()){
                Department department1 = map.get(resultSet.getInt("DepartmentId"));
                if (department1 == null){
                    department1 = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), department1);
                }
                Seller seller = instantiateSeller(resultSet, department1);
                list.add(seller);
            }
            return list;

        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }
}
