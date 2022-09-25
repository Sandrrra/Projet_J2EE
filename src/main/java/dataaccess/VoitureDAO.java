package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Voiture;

public class VoitureDAO {
	public static List<Voiture> getAllVoiture() throws SQLException {
		return getVoiture(Optional.empty(), Optional.empty(), Optional.empty());
	}

	public static Voiture getVoitureById(Integer voitureId) throws SQLException {
		Optional<String> whereClause = Optional.ofNullable("where voiture.voiture_id = ?");
		return getVoiture(whereClause, Optional.ofNullable(voitureId), Optional.empty())
				.stream()
				.findAny()
				.get();
	}

	public static List<Voiture> getVoitureByModele(int modeleId) throws SQLException {
		Optional<String> whereClause = Optional.ofNullable("where voiture.modele_id = ?");
		return getVoiture(whereClause, Optional.ofNullable(modeleId), Optional.empty());
	}

	public static List<Voiture> getVoitureByMarque(int marqueId) throws SQLException {
		Optional<String> whereClause = Optional.ofNullable("where voituremarque.marque = ?");
		return getVoiture(whereClause, Optional.empty(), Optional.ofNullable(marqueId));
	}

	public static List<Voiture> getVoitureByModeleAndMarque(int modeleId, int marqueId) throws SQLException {
		Optional<String> whereClause = Optional.ofNullable("where voiture.modele_id = ? "
				+ "and voituremarque.marque_id = ?");
		return getVoiture(whereClause, Optional.ofNullable(modeleId), Optional.ofNullable(marqueId));
	}

	private static List<Voiture> getVoiture(
			Optional<String> whereClause,
			Optional<Integer> id,
			Optional<Integer> marqueId) throws SQLException {
		List<Voiture> voitureList = new ArrayList<>();

		String q = "SELECT voiture.voiture_id,voiture_nom,voiture_description,voiture_prix,voiture_miseaumarche,"
				+ "voiture_pays,voiture_carburant,voiture_portes,voiture.modele_id, "
				+ "modele.modele_nom,"
				+ "marque.marque_nom, marque.marque_id "
				+ "FROM voiture "
				+ "inner join modele on modele.modele_id = voiture.modele_id "
				+ "inner join voituremarque on voiture.voiture_id = voituremarque.voiture_id "
				+ "inner join marque on marque.marque_id = voituremarque.marque_id ";

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
				if (marqueId.isPresent()) {
					p.setInt(2, marqueId.get());
				}
			} else {
				if (marqueId.isPresent()) {
					p.setInt(1, marqueId.get());
				}
			}
			// execute the query, and get a java resultset
			try (ResultSet rs = p.executeQuery()) {

				// iterate through the java resultset
				while (rs.next()) {
					Voiture voiture = new Voiture();

					voiture.setVoitureId(rs.getInt("voiture_id"));
					voiture.setVoitureNom(rs.getString("voiture_nom"));
					voiture.setVoitureDescription(rs.getString("voiture_description"));
					voiture.setPrix(rs.getDouble("voiture_prix"));
					voiture.setMiseAuMarche(rs.getDate("voiture_miseaumarche"));
					voiture.setPays(rs.getString("voiture_pays"));
					voiture.setCarburant(rs.getString("voiture_carburant"));
					voiture.setPortes(rs.getInt("voiture_portes"));
					voiture.setModeleId(rs.getInt("modele_id"));
					voiture.setModeleNom(rs.getString("modele_nom"));
					voiture.setMarqueNom(rs.getString("marque_nom"));
					voiture.setMarqueId(rs.getInt("marque_id"));

					voitureList.add(voiture);
				}
			}
		}

		return voitureList;
	}

	public void updateNomById(int voitureId, String newNom) throws SQLException {
		String q = "update voiture set voiture_nom = ? where voiture_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setString(1, newNom);
			p.setInt(2, voitureId);

			p.execute();
		}
	}

	public void insertVoiture(Voiture voiture) throws SQLException {
		String q = "insert voiture values(null,?,?,?,?,?,?,?,?)";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {
			p.setString(1, voiture.getVoitureNom());
			p.setString(2, voiture.getVoitureDescription());
			p.setDouble(3, voiture.getPrix());
			p.setDate(4, voiture.getMiseAuMarche());
			p.setString(5, voiture.getPays());
			p.setString(6, voiture.getCarburant());
			p.setInt(7, voiture.getPortes());
			p.setInt(8, voiture.getModeleId());
			int affectedRows = p.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating voiture failed, no rows affected.");
			}

			try (ResultSet generatedKeys = p.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					voiture.setVoitureId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating voiture failed, no ID obtained.");
				}
			}
		}
	}

	public void deleteVoitureById(int voitureId) throws SQLException {
		String q = "Delete from voiture where voiture_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, voitureId);
			p.execute();
		}
	}

	public void updateVoiture(Voiture voiture) throws SQLException {
		String q = "update voiture set voiture_nom = ?,"
				+ "voiture_description=?,"
				+ "voiture_pays=?,"
				+ "voiture_carburant=?,"
				+ "voiture_portes=?, "
				+ "voiture_prix=?, "
				+ "modele_id=? "
				+ "where voiture_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setString(1, voiture.getVoitureNom());
			p.setString(2, voiture.getVoitureDescription());
			p.setString(3, voiture.getPays());
			p.setString(4, voiture.getCarburant());
			p.setInt(5, voiture.getPortes());
			p.setDouble(6, voiture.getPrix());
			p.setInt(7, voiture.getModeleId());
			p.setInt(8, voiture.getVoitureId());

			p.execute();
		}
	}

	public void clearMarque(int voitureId) throws SQLException {
		String q = "Delete from voituremarque where voiture_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, voitureId);
			p.execute();
		}
	}

	public void updateMarque(int voitureId, String[] marque) throws SQLException {
		for (String string : marque) {
			String q = "insert voituremarque(voiture_id, marque_id) values(?,?)";

			try (Connection connection = ConnectionFactory.getInstance().getConnection();
					PreparedStatement p = connection.prepareStatement(q)) {
				p.setInt(1, voitureId);
				int marqueId = Integer.parseInt(string);
				p.setInt(2, marqueId);
				p.execute();
			}
		}
	}
}
