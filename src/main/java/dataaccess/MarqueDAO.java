package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Marque;

public class MarqueDAO {
	public static List<Marque> getAllMarque() throws SQLException {
		return getMarque(Optional.empty(), Optional.empty());
	}

	public static Marque getMarqueByMarqueId(Integer marqueId) throws SQLException {
		Optional<String> whereClause = Optional.ofNullable("where marque.marque_id = ?");
		return getMarque(whereClause, Optional.ofNullable(marqueId))
				.stream()
				.findAny()
				.get();
	}

	private static List<Marque> getMarque(Optional<String> whereClause, Optional<Integer> id)
			throws SQLException {
		List<Marque> marqueList = new ArrayList<>();

		String q = "Select marque_id, marque_nom, marque_description "
				+ "FROM marque ";

		if (whereClause.isPresent()) {
			q += whereClause.get();
		}

		// try with resources PreparedStatement implements AutoCloseable
		// ConnectionFactory c'est une usine qui donne une connection
		// PreparedStatement plus securisé que statement normal (pas de SQL injection)
		// pour envoyé au BDD la requête.
		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			if (id.isPresent()) {
				p.setInt(1, id.get());
			}
			// execute the query, and get a java resultset
			try (ResultSet rs = p.executeQuery()) {

				// iterate through the java resultset
				while (rs.next()) {
					Marque marque = new Marque();

					marque.setMarqueId(rs.getInt("marque_id"));
					marque.setMarqueNom(rs.getString("marque_nom"));
					marque.setMarqueDescription(rs.getString("marque_description"));

					marqueList.add(marque);
				}
			}
		}

		return marqueList;
	}

	public static void insertMarque(Marque marque) throws SQLException {
		String q = "insert marque values(null,?,?)";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {

			p.setString(1, marque.getMarqueNom());
			p.setString(2, marque.getMarqueDescription());

			int affectedRows = p.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating marque failed, no rows affected.");
			}

			try (ResultSet generatedKeys = p.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					marque.setMarqueId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating marque failed, no ID obtained.");
				}
			}
		}
	}

	public void deleteMarqueById(int marqueId) throws SQLException {
		String q = "Delete from marque where marque_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, marqueId);
			p.execute();
		}
	}

	public static void updateMarque(Marque marque) throws SQLException {
		String q = "update marque set marque_nom = ?,"
				+ "marque_description=? "
				+ "where marque_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setString(1, marque.getMarqueNom());
			p.setString(2, marque.getMarqueDescription());
			p.setInt(3, marque.getMarqueId());

			p.execute();
		}
	}

	public static List<Marque> getVoitureMarque(int voitureId) throws SQLException {
		List<Marque> marqueList = new ArrayList<>();
		String q = "select p.marque_id, p.marque_nom, p.marque_description, "
				+ "(select count(*) from voituremarque jp where jp.voiture_id=? and p.marque_id=jp.marque_id) as checked "
				+ "from marque p";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, voitureId);
			try (ResultSet rs = p.executeQuery()) {

				// iterate through the java resultset
				while (rs.next()) {
					Marque marque = new Marque();

					marque.setMarqueId(rs.getInt("marque_Id"));
					marque.setMarqueNom(rs.getString("marque_nom"));
					marque.setMarqueDescription(rs.getString("marque_description"));
					marque.setChecked(rs.getBoolean("checked"));

					marqueList.add(marque);
				}
			}
		}

		return marqueList;
	}
}
