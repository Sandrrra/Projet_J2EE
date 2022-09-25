package routing;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dataaccess.ModeleDAO;
import dataaccess.VoitureDAO;
import dataaccess.MarqueDAO;

import model.Voiture;

/**
 * Servlet implementation class GamePage
 */
public class VoiturePage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VoiturePage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String voitureId = request.getParameter("voitureId");

			Voiture voiture = null;
			if (voitureId == null) {
				voiture = new Voiture();
			} else {
				voiture = VoitureDAO.getVoitureById(Integer.parseInt(voitureId));
			}
			request.setAttribute("modeles", ModeleDAO.getAllModele());

			HttpSession session = request.getSession();
			session.setAttribute("voiture", voiture);
			session.setAttribute("marques", MarqueDAO.getVoitureMarque(voiture.getVoitureId()));

			getServletContext().getRequestDispatcher("/WEB-INF/voiturepage.jsp").forward(request, response);
		} catch (Exception e) {
			getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "";

		HttpSession session = request.getSession();
		Voiture voiture = (Voiture) session.getAttribute("voiture");

		voiture.setVoitureNom(request.getParameter("nom"));
		voiture.setVoitureDescription(request.getParameter("description"));

		voiture.setPays(request.getParameter("pays"));
		int portes = Integer.parseInt(request.getParameter("portes"));
		voiture.setPortes(portes);
		int modeleId = Integer.parseInt(request.getParameter("modeles"));
		voiture.setModeleId(modeleId);
		voiture.setCarburant(request.getParameter("carburant"));
	
		try {
			double prix = Double.parseDouble(request.getParameter("prix"));
			voiture.setPrix(prix);
		} catch (NumberFormatException e) {
			message = "Entrez un prix valide";
		}

		try {
	
			LocalDate miseAuMarche = LocalDate.parse(request.getParameter("miseAuMarche"));
			Date date = Date.valueOf(miseAuMarche);
			voiture.setMiseAuMarche(date);
		} catch (java.time.format.DateTimeParseException e) {
			message = "Entrez une date au format AAAA-MM-JJ";
		}

		VoitureDAO dao = new VoitureDAO();

		if (voiture.getVoitureNom().isBlank() ||
				voiture.getVoitureDescription().isBlank()) {
			message = "Vous devez remplir tout les champs";
		} else {
			try {
				if (voiture.getVoitureId() == 0) {
					dao.insertVoiture(voiture);
					message = "Voiture ajoutée.";
				} else {
					dao.updateVoiture(voiture);
					message = "Voiture mise à jour.";
				}

				String[] marque = request.getParameterValues("marques");
				dao.clearMarque(voiture.getVoitureId());
				if (marque != null) {
					dao.updateMarque(voiture.getVoitureId(), marque);
				}
				session.setAttribute("marques", MarqueDAO.getVoitureMarque(voiture.getVoitureId()));
			} catch (SQLException e) {
				message = "Il y a une erreur.";
			}
		}
		session.setAttribute("voiture", voiture);

		request.setAttribute("message", message);
		getServletContext().getRequestDispatcher("/WEB-INF/voiturepage.jsp").forward(request, response);
	}
}
