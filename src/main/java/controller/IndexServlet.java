package controller;

import db.dao.DAO;
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
 * отображает стартовую страницу
 * форма для входа и регистрации
 */

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private DAO dao = new DAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // читается параметр action из реквеста
        // если action = logout то выход из сессии, и редирект на стартовую страницу
        String action = req.getParameter("action");
        if ("logout".equals(action)) {
            req.getSession().invalidate();
        }
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // введенные в форму параметры: имя пользователя и пароль
        String userName = req.getParameter("userName").trim();
        String password = req.getParameter("userPassword").trim();

        String errReply = null;
        User user = null;

        if (userName.isEmpty()) {
            errReply = "ведите имя";
        } else if (password.isEmpty()) {
            errReply = "ведите пароль";
        } else {
            //если нажата кнопка логин
            if (req.getParameter("button_login") != null) {
                try {
                    //выборка из БД юзера с данным логином и паролем
                    user = dao.login(userName, password);
                    if (user == null) {
                        errReply = "неверное имя пользовтеля или пароль";
                    }
                } catch (SQLException e) {
                    errReply = e.toString();
                }

                //если нажата кнопка зарегистрировать данного пользователя
            } else if (req.getParameter("button_new") != null) {
                try {
                    // запись данных в БД
                    // если такой логин уже существует, то БД вернет исключение
                    // так как в таблице поле юзер уникально
                    user = dao.addUser(userName, password);
                } catch (SQLException e) {
                    errReply = e.toString();
                }
            }
        }

        // запись в сессию данных пользователя
        req.getSession().setAttribute("user", user);

        if (errReply == null) {
            // если нет ошибки то открыть начало игры
            resp.sendRedirect(req.getContextPath() + "/inner/start");
        } else {
            // вывод информации или ошибки на начальной страниуе
            String encodeMsg = URLEncoder.encode(errReply, "UTF-8");
            resp.sendRedirect(req.getContextPath() + "/index?errReply=" + encodeMsg);
        }
    }

}

