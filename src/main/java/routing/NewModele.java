package routing;

import java.io.IOException;
import java.sql.SQLException;

import dataaccess.ModeleDAO;
import dataaccess.MarqueDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Modele;

/**
 * Servlet implementation class NewModele
 */
public class NewModele extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewModele() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "";
		String modeleId = request.getParameter("modeleId");
		Modele modele = null;

		if (modeleId == null) {
			modele = new Modele();
		} else {
			try {
				modele = ModeleDAO.getModeleById(Integer.parseInt(modeleId));
			} catch (Exception e) {
				message = ">Erreur";
			}
		}

		request.setAttribute("message", message);
		HttpSession session = request.getSession();
		// Put modele in the request for the next page
		session.setAttribute("modele", modele);
		getServletContext().getRequestDispatcher("/WEB-INF/newmodele.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String message = "";
		Modele modele = (Modele) session.getAttribute("modele");
		modele.setModeleNom(request.getParameter("nom"));
		modele.setModeleDescription(request.getParameter("description"));

		try {
			if (modele.getModeleId() > 0) {
				// already exists so do an update
				ModeleDAO.updateModele(modele);
				message = "Modèle updated";
			} else {
				ModeleDAO.insertModele(modele);
				message = "Modèle created";
			}
		} catch (SQLException e) {
			message = "Entrez un nouveau nom.";
		}

		request.setAttribute("message", message);
		session.setAttribute("modele", modele);
		getServletContext().getRequestDispatcher("/WEB-INF/newmodele.jsp").forward(request, response);
	}
}
