package controller;

import dataStore.CurrentGame;
import db.dao.DAO;
import pojo.Game;
import pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

/**
 * отображает интерфейс игры
 */

@WebServlet("/inner/game")
public class GameServlet extends HttpServlet {
    private int userNumber;     //введенное пользователем число
    private CurrentGame currentGame;    //данные текущей игры
    private DAO dao = new DAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/game.jsp").forward(req, resp);
    }

    /**
     * проверяет что введенные пользователем символы являются числом,
     * удовлетворяющем необходимым условиям
     * @param input - введенные пользователем символы
     * @return - преобразованное из входных данных число
     */
    private String setNumber(String input) {
        String result = null;
        try {
            userNumber = Integer.parseInt(input);
            if (userNumber < 1000 || userNumber > 9999) {
                result = "число не является четырехзначным";
            } else if (!currentGame.setUserNumber(userNumber)) {
                result = "в введенном числе есть повторяющиеся цифры";
            }
        } catch (NumberFormatException e) {
            result = "введенные символы не являются числом";
        }
        return result;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // чтение строки из формы
        String input = req.getParameter("input_number").trim();
        // чтение данных о текущей игре и пользователе из сессии
        currentGame = (CurrentGame) req.getSession().getAttribute("currentGame");
        User user = (User) req.getSession().getAttribute("user");
        // параметр сообщающей об окончании игры, если пользователь угадал число
        boolean guessed = false;
        // сообщение об ошибке
        String errReply = setNumber(input);

        if (errReply == null) {
            // если нет ошибки - то записать попытку
            // проверка что число совпадает с загаданным
            if (currentGame.setAttempt()) {
                // игрок выиграл
                // запись данных о завешении игры
                currentGame.guessed();
                guessed = true;
                try {
                    // запись данных об игре (user id, время старта, продолжительность, количество попыток)
                    dao.addGame(new Game(user.getId(), currentGame.getStartTime(), currentGame.getTotalTime(), currentGame.getAttempts().size()));
                } catch (SQLException e) {
                    errReply = "ошибка при сохранении игры" + e.toString();
                    String encodeMsg = URLEncoder.encode(errReply, "UTF-8");
                    resp.sendRedirect(req.getContextPath() + "/inner/game?errReply=" + encodeMsg);
                }
            }

            // если нет ошибки - то отобразить страницу игры
            req.getSession().setAttribute("guessed", guessed);
            resp.sendRedirect(req.getContextPath() + "/inner/game");
        } else {
            // вывод инвормации о неверном формате введенных данных или об ошибке при записи в БД
            String encodeMsg = URLEncoder.encode(errReply, "UTF-8");
            resp.sendRedirect(req.getContextPath() + "/inner/game?errReply=" + encodeMsg);
        }
    }
}
