package db.dao;

import db.connection.ConnectionManager;
import pojo.Game;
import pojo.Statistic;
import pojo.User;

import java.sql.*;
import java.util.ArrayList;

public class DAO {
    private static ConnectionManager connectionManager = ConnectionManager.getInstance();

    /**
     * проверяет есть ли пользователь с такими данными в базе
     * @param name - имя пользователя
     * @param pass - пароль
     * @return возвращает пользователя если он присутствует в базе, иначе null
     * @throws SQLException
     */
    public User login(String name, String pass) throws SQLException {
        User result = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM users WHERE name = ? AND password = ?");) {
            statement.setString(1, name);
            statement.setString(2, pass);
            try (ResultSet resultSet = statement.executeQuery();) {
                if (resultSet.next()) {
                    result = new User(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    );
                }
            }
        }
        return result;
    }

    /**
     * добавляет пользователя в БД
     * @param name - логин
     * @param password - пароль
     * @return - данные пользователя
     * @throws SQLException - если такой логин уже есть в БД, то INSERT выдаст исключение
     * так как в таблице поле юзер уникально
     */
    public User addUser(String name, String password) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO users (name, password) VALUES (?, ?)");) {

            ps.setString(1, name);
            ps.setString(2, password);
            ps.executeUpdate();
        }
        return login(name, password);
    }

    /**
     * записывает данные об игре
     * @param game - класс игры
     * @throws SQLException
     */
    public void addGame(Game game) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO guess_log VALUES (?, ?, ?, ?)");) {
            ps.setInt(1, game.getUserId());
            ps.setTimestamp(2, new Timestamp(game.getTimeBegin().getTime()));
            ps.setTimestamp(3, new Timestamp(game.getTimeTotal().getTime()));
            ps.setInt(4, game.getAttempts());
            ps.executeUpdate();
        }
    }

    /**
     * список игр пользователя (история)
     * @param userId
     * @return
     * @throws SQLException
     */
    public ArrayList<Game> getGamesById(int userId) throws SQLException {
        ArrayList<Game> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM guess_log WHERE user_id=?");) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    result.add(new Game(
                            userId,
                            resultSet.getTimestamp(2),
                            resultSet.getTimestamp(3),
                            resultSet.getInt(4)
                    ));
                }
            }
            return result;
        }
    }

    /**
     * рейтинг игроков
     * @return список статистики игрока
     * (id, имя, сумма всех попыток во всех играх (a), кол-во игр (g), рейтинг = g/a)
     * @throws SQLException
     */
    public ArrayList<Statistic> getRating() throws SQLException {
        ArrayList<Statistic> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT l.user_id, name, COUNT(l.user_id), SUM(attempts), COUNT(l.user_id)/SUM(attempts) AS rating " +
                             "FROM users u, guess_log l " +
                             "WHERE u.user_id=l.user_id " +
                             "GROUP BY user_id ORDER BY rating DESC"
             )) {
            while (resultSet.next()) {
                result.add(new Statistic(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getDouble(5)
                ));
            }
        }
        return result;
    }
}
