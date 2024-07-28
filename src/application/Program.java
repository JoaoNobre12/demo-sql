package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
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
