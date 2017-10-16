package com.alacriti.p2p.biz.delegate;

import java.sql.Connection;
import java.sql.SQLException;

import com.alacriti.p2p.datasource.MySqlDataSource;
import com.alacriti.p2p.log.impl.AppLogger;
import com.alacriti.p2p.util.LogUtil;

public class BaseDelegate {
	private static final AppLogger log = LogUtil.getLogger(BaseDelegate.class);

	private Connection connection;

	public void setConnection(Connection _connection) {
		log.debugPrintCurrentMethodName();
		this.connection = _connection;
	}

	public Connection getConnection() {
		log.debugPrintCurrentMethodName();
		return connection;
	}

	protected void endDBTransaction(Connection connection) {
		log.debugPrintCurrentMethodName();
		try {
			connection.commit();

		} catch (SQLException e) {
			log.logError("SQLException in endDBTransaction " + e.getMessage(), e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.logError("SQLException in endDBTransaction" + e1.getMessage(), e1);
			}
		} catch (Exception e) {
			log.logError("Exception in endDBTransaction" + e.getMessage(), e);
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				log.logError("SQLException in endDBTransaction" + e.getMessage(), e);
			}
		}

	}

	protected void endDBTransaction(Connection connection, boolean isRollback) {
		log.debugPrintCurrentMethodName();
		if (isRollback) {
			try {
				connection.rollback();
				log.logInfo("Rolled Back on some exception....!!!");
			} catch (SQLException e) {
				log.logError("SQLException in endDBTransaction " + e.getMessage(), e);
			}

			finally {
				try {
					if (connection != null)
						connection.close();
				} catch (SQLException e) {
					log.logError("SQLException in endDBTransaction " + e.getMessage(), e);
				}
			}
		} else {
			endDBTransaction(connection);
		}

	}

	protected Connection startDBTransaction() {
		log.debugPrintCurrentMethodName();
		Connection conn = null;
		try {
			if (conn == null || conn.isClosed())
				conn = MySqlDataSource.getInstance().getConnection();

			conn.setAutoCommit(false);
		} catch (SQLException e) {
			log.logError("SQLException in startDBTransaction " + e.getMessage(), e);
		}
		return conn;

	}
}
