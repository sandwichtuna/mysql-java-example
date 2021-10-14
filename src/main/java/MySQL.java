import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQL {

//    private final String jdbcDriverStr = "com.mysql.jdbc.Driver";
    private final String jdbcURL = "jdbc:mysql://172.17.0.2:3306/swing_test?"
            + "user=root&password=banana";

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public MySQL() {
        try {
            connection = DriverManager.getConnection(jdbcURL);
//            if (connection != null) {
//                System.out.println("Successfully connected to MySQL");
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void insertUser(int id, String username, String password) {
        try {
            connection = DriverManager.getConnection(jdbcURL);
            String query = "INSERT INTO user VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close();
        }
    }

    public List<Object> getUsers() {
        List<Object> users = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(jdbcURL);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from swing_test.user;");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String username = resultSet.getString(2);
                String password = resultSet.getString(3);
                System.out.printf("%d\t%s\t%s\n", id, username, password);
                users.add(username);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close();
        }
        return users;
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MySQL mySqlInstance = new MySQL();
        mySqlInstance.insertUser(6, "max", "noname");
        mySqlInstance.getUsers();
    }
}
