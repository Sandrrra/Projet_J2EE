package routing;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Marque;

import java.io.IOException;
import java.sql.SQLException;

import dataaccess.MarqueDAO;

/**
 * Servlet implementation class NewMarque
 */
public class NewMarque extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewMarque() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Marque marque = null;
		String message = "";

		String marqueId = request.getParameter("marqueId");
		if (marqueId == null) {
			// Create an empty marque so that the page is empty
			marque = new Marque();
		} else {
			// Get the marque from the DAO
			try {
				marque = MarqueDAO.getMarqueByMarqueId(Integer.parseInt(marqueId));
			} catch (Exception e) {
				message = "Erreur";
			}
		}
		HttpSession session = request.getSession();
		// Put fermentation in the session for the next page
		session.setAttribute("marque", marque);
		request.setAttribute("message", message);
		getServletContext().getRequestDispatcher("/WEB-INF/newmarque.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "";
		HttpSession session = request.getSession();
		Marque marque = (Marque) session.getAttribute("marque");
		marque.setMarqueNom(request.getParameter("nom"));
		marque.setMarqueDescription(request.getParameter("description"));

		try {
			if (marque.getMarqueId() > 0) {
				// We already have a marque_Id so do an update
				MarqueDAO.updateMarque(marque);
				message = "marque updated";
			} else {
				MarqueDAO.insertMarque(marque);
				message = "marque created";
			}
		} catch (SQLException e) {
			message = "Enter a new marque name.";
		}

		request.setAttribute("message", message);
		session.setAttribute("marque", marque);
		getServletContext().getRequestDispatcher("/WEB-INF/newmarque.jsp").forward(request, response);
	}
}