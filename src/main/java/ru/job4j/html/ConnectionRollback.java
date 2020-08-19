package ru.job4j.html;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection, which rollback all commits.
 * It is used for integration test.
 */
public class ConnectionRollback {
    /**
     * Create connection with autocommit=false mode and rollback call, when conneciton is closed.
     * @param cn connection.
     * @return Connection object.
     * @throws SQLException possible exception.
     */
    public static Connection create(Connection cn) throws SQLException {
        cn.setAutoCommit(false);
        return (Connection) Proxy.newProxyInstance(
                ru.job4j.html.ConnectionRollback.class.getClassLoader(),
                new Class[] {Connection.class },
                (proxy, method, args) -> {
                    Object rsl = null;
                    if ("close".equals(method.getName())) {
                        cn.rollback();
                        cn.close();
                    } else {
                        rsl = method.invoke(cn, args);
                    }
                    return rsl;
                }
        );
    }
}
