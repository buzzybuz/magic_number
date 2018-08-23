package controller;

import dataStore.CurrentGame;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * отображает страницу с кнопкой для старта игры
 */

@WebServlet("/inner/start")
public class StartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/start.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //данные текущей игры записываются и читаются из сессии
        CurrentGame currentGame= (CurrentGame) req.getSession().getAttribute("currentGame");
        if (currentGame == null){
            currentGame =new CurrentGame();
        }
        currentGame.newGame();
        req.getSession().setAttribute("currentGame", currentGame);
        req.getSession().setAttribute("guessed", false);
        resp.sendRedirect(req.getContextPath() + "/inner/game");
    }
}
