package com.techeffic.blog.dal.datasource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 抽象数据源
 * Created by liaoxudong on 2017/5/16.
 */
public abstract class AbstractDataSource implements DataSource{
    private DataSource dataSource = null;

    public AbstractDataSource() {
    }

    public AbstractDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        if (dataSource != null) {
            return dataSource.getLogWriter();
        }
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        if (dataSource != null) {
            dataSource.setLogWriter(out);
        }
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        if (dataSource != null) {
            dataSource.setLoginTimeout(seconds);
        }
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        if (dataSource != null) {
            return dataSource.getLoginTimeout();
        }
        return 0;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (dataSource != null) {
            return dataSource.getConnection();
        }
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (dataSource != null) {
            return dataSource.unwrap(iface);
        }
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        if (dataSource != null) {
            return dataSource.isWrapperFor(iface);
        }
        return false;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        if (dataSource != null) {
            return dataSource.getConnection(username,password);
        }
        return null;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }


}
