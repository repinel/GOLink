package br.golink;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class GODB
{
	public static Connection getConnectionDS() throws GOException
	{
		Connection connection = null;

		try
		{
			Context initContext = new InitialContext();

			if (initContext == null)
			{
				throw new GOException("Datasource error - no context!");
			}

			Context envContext  = (Context) initContext.lookup("java:/comp/env");

			DataSource ds = (DataSource) envContext.lookup("GOLINK_DS");

			if (ds == null)
			{
				throw new GOException("Datasource error - not found!");
			}

			connection = ds.getConnection();
			connection.setAutoCommit(false);
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return connection;
	}

	public static Connection getConnection() throws GOException
	{
		Connection connection = null;

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + Constants.DB_HOSTNAME + "/" + Constants.DATABASE, Constants.DB_USERNAME, Constants.DB_PASSWORD);
			connection.setAutoCommit(false);
		}
		catch (ClassNotFoundException e)
		{
			throw new GOException("JDBC error - com.mysql.jdbc.Driver", e);
		}
		catch (SQLException e)
		{
			throw new GOException("JDBC error - connection", e);
		}

		return connection;
	}
}
