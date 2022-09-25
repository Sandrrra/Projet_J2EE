package routing;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import dataaccess.ModeleDAO;
import dataaccess.VoitureDAO;
import dataaccess.MarqueDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Voiture;

/**
 * Servlet implementation class ListVoiture
 */
public class ListVoiture extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListVoiture() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer marqueId = null;
		Integer modeleId = null;
		String page = "/WEB-INF/showvoitures.jsp";
		

		try {
			Optional<String> modele = Optional.ofNullable(request.getParameter("modele"));
			Optional<String> marque = Optional.ofNullable(request.getParameter("marque"));

			if (modele.isEmpty()) {
				modeleId = 0;
			} else if (modele.get().equals("all")) {
				modeleId = 0;
			} else if (modele.get().equals("new")) {
				page = "/NewModele";
				getServletContext().getRequestDispatcher(page).forward(request, response);
				return;
			} else {
				modeleId = Integer.parseInt(modele.get());
			}

			if (marque.isEmpty()) {
				marqueId = 0;
			} else if (marque.get().equals("all")) {
				marqueId = 0;
			} else if (marque.get().equals("new")) {
				// TODO Add this servlet
				page = "/NewMarque";
				getServletContext().getRequestDispatcher(page).forward(request, response);
				return;
			} else {
				marqueId = Integer.parseInt(marque.get());
			}

			List<Voiture> voitures;
			if (modeleId == 0 && marqueId == 0) {
				voitures = VoitureDAO.getAllVoiture();
			} else {
				if (modeleId == 0) {
					// Selection by marque only
					voitures = VoitureDAO.getVoitureByMarque(marqueId);
				} else if (marqueId == 0) {
					// Selection by modèle only
					voitures = VoitureDAO.getVoitureByModele(modeleId);
				} else {
					// Selection by modele and marque
					voitures = VoitureDAO.getVoitureByModeleAndMarque(modeleId, marqueId);
				}
			}
			// data for the following page
			request.setAttribute("voiture", voitures);
			request.setAttribute("selected", modeleId);
			request.setAttribute("selectedMarque", marqueId);

			HttpSession session = request.getSession();
			session.setAttribute("modele", ModeleDAO.getAllModele());
			session.setAttribute("marque", MarqueDAO.getAllMarque());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getServletContext().getRequestDispatcher(page).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
