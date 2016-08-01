package br.com.augustoccesar.querybuilder.executors;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by augustoccesar on 7/12/16.
 */
public class PreparedStatementExecutor {
    public static void execute(DataSource ds, String sql, Callback callback) {
        try (Connection con = ds.getConnection()) {
            con.setReadOnly(true);
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    callback.execute(rs);
                }
            }
            con.setReadOnly(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
