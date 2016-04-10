package com.example.db;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.sql.*;

public class DaoSql {

    private static final String INSTALL_DIR = "/Library/Tomcat/webapps/ROOT";
    private static final String DB_DIR = System.getProperty("user.home") + "/.taskManager/db";
    private static final String SQLITE_CLASS = "org.sqlite.JDBC";
    private static final String CONN_STRING = "jdbc:sqlite:" + DB_DIR + "/taskManager.db";

    public DaoSql() {
    }

    public void createTableIfNeeded(String table, String[] columns) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(table)
                  .append("(")
                  .append(StringUtils.join(columns, ", "))
                  .append(")");
        String sql = sqlBuilder.toString();
        try {
            update(sql);
        }
        catch (Exception e) {
            System.err.println(e.getMessage() + ": Unable to create " + table + " table. SQL: " + sql);
        }
    }

    public void update(String sql, String... args) {
        Connection conn = null;

        try {
            conn = getConnection();

            int col = 1;
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (String arg : args) {
                stmt.setString(col++, arg);
            }

            stmt.executeUpdate();
            conn.commit();
        }
        catch (Exception e) {
            try {
                StringBuilder errBuilder = new StringBuilder(e.getMessage()).append(": Error while executing SQL: ").append(sql)
                        .append("\n").append(StringUtils.join(args, ", "));
                System.err.println(errBuilder.toString());
                e.printStackTrace();
                if (conn != null && !conn.isClosed()) {
                    conn.rollback();
                }
            }
            catch (SQLException sqle) {
                // Swallow
            }

        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                }
                catch (SQLException e) {
                    System.err.println("Unable to close connection");
                }
            }
        }
    }

    protected Connection getConnection()
    throws Exception{
        File dbDir = new File(DB_DIR);
        if (!dbDir.exists()) {
            dbDir.mkdirs();
        }

        Class.forName(SQLITE_CLASS);
        Connection conn = DriverManager.getConnection(CONN_STRING);
        conn.setAutoCommit(false);
        return conn;
    }
}
