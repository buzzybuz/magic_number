package controller;

import db.dao.DAO;
import pojo.Game;
import pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * отображает историю игр текущего пользователя
 */

@WebServlet("/inner/history")
public class HistoryServlet extends HttpServlet {
    private DAO dao = new DAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //читается информация о текущем пользователе из сессии
        User user = (User) req.getSession().getAttribute("user");
        ArrayList<Game> games = null;
        try {
            games = dao.getGamesById(user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //данные об играх записываются в реквест
        req.setAttribute("games", games);
        req.getRequestDispatcher("/history.jsp").forward(req, resp);
    }
}
