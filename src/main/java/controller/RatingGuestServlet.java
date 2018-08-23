package controller;

import db.dao.DAO;
import pojo.Statistic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * отображает рейтинг для неаутентифицированного пользователя
 */

@WebServlet("/ratingGuest")
public class RatingGuestServlet extends HttpServlet {
    private DAO dao = new DAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Statistic> rating = null;
        try {
            rating = dao.getRating();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //данные рейтинга записываются в реквест
        req.setAttribute("rating", rating);
        req.getRequestDispatcher("/ratingGuest.jsp").forward(req, resp);
    }
}
