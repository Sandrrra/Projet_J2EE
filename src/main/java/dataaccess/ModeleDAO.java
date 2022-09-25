package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Modele;

public class ModeleDAO {
	public static List<Modele> getAllModele() throws SQLException {
		List<Modele> modeleList = new ArrayList<>();

		String q = "SELECT modele_id, modele_nom, modele_description "
				+ "FROM modele";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {

			// execute the query, and get a java resultset
			try (ResultSet rs = p.executeQuery()) {

				// iterate through the java resultset
				while (rs.next()) {
					Modele modele = new Modele();

					modele.setModeleId(rs.getInt("modele_id"));
					modele.setModeleNom(rs.getString("modele_nom"));
					modele.setModeleDescription(rs.getString("modele_description"));

					modeleList.add(modele);
				}
			}
		}

		return modeleList;
	}

	public static void updateModele(Modele modele) throws SQLException {
		String q = "update modele "
				+ "set modele_nom = ?,"
				+ "modele_description=? "
				+ "where modele_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setString(1, modele.getModeleNom());
			p.setString(2, modele.getModeleDescription());
			p.setInt(3, modele.getModeleId());

			p.execute();
		}
	}

	public static void insertModele(Modele modele) throws SQLException {
		String q = "insert modele values(null,?,?)";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {
			p.setString(1, modele.getModeleNom());
			p.setString(2, modele.getModeleDescription());
			int affectedRows = p.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating modèle failed, no rows affected.");
			}

			try (ResultSet generatedKeys = p.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					modele.setModeleId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating modèle failed, no ID obtained.");
				}
			}
		}
	}

	public void deleteModeleById(int modeleId) throws SQLException {
		String q = "Delete from modele where modele_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, modeleId);
			p.execute();
		}
	}

	public static Modele getModeleById(int modeleId) throws SQLException {
		String q = "SELECT modele_Id, modele_nom, modele_description "
				+ "FROM modele where modele_id=?";
		Modele modele = new Modele();

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, modeleId);
			
			// execute the query, and get a java resultset
			try (ResultSet rs = p.executeQuery()) {

				// iterate through the java resultset
				while (rs.next()) {

					modele.setModeleId(rs.getInt("modele_id"));
					modele.setModeleNom(rs.getString("modele_nom"));
					modele.setModeleDescription(rs.getString("modele_description"));
					break;
				}
			}
		}

		return modele;
	}
}
