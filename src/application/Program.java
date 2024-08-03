package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.entities.Department;
import model.entities.Seller;
import model.entities.dao.DaoFactory;
import model.entities.dao.SellerDao;

import javax.swing.*;

public class Program {

	public static void main(String[] args) {

		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();

			SellerDao sellerDao = DaoFactory.createSellerDao();

			System.out.println("find by id");
			Seller seller = sellerDao.findById(7);

			System.out.println(seller);

			System.out.println("\n\n\nteste by department");

			List<Seller> sellerList = sellerDao.findByDepartment(new Department(2, null));

			sellerList.forEach(System.out::println);

			System.out.println("\n\n\nteste by all");

			List<Seller> sellerList2 = sellerDao.findAll();

			sellerList2.forEach(System.out::println);

		}
		catch (Exception e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}
