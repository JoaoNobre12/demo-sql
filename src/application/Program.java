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

			Seller seller = sellerDao.findById(7);

			System.out.println(seller);

			System.out.println("teste by department\n\n\n");

			List<Seller> sellerList = sellerDao.findByDepartment(new Department(2, null));

			System.out.println(sellerList);
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
