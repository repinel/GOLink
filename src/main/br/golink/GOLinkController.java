/*
 * $Id$
 * 
 * Filename : GOLinkController.java 
 * Project  : GOLink_API
 */
package br.golink;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class responsable for the GOLink information.
 * 
 * @author Roque Pinel
 */
public class GOLinkController
{
	/**
	 * Default constructor.
	 */
	public GOLinkController()
	{
		// empty.
	}

	/**
	 * Get the Gene Ontology term information given the accession.
	 * 
	 * @throws GOException 
	 */
	public GOEntry getGOTermGivenAccession(String accession) throws GOException
	{
		Connection connection;

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

		GOEntry goEntry = null;

		try
		{
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT term.name, term.term_type, term.acc, term_definition.term_definition, term_definition.term_comment FROM term INNER JOIN term_definition ON (term.id = term_definition.term_id) WHERE acc = ?");
			preparedStatement.setString(1, accession);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
			{
				goEntry = new GOEntry(resultSet.getString("acc"), resultSet.getString("name"),
									  resultSet.getString("term_type"), resultSet.getString("term_definition"),
									  resultSet.getString("term_comment"));
			}

			resultSet.close();

			preparedStatement.close();

			connection.commit();
		}
		catch (SQLException e)
		{
			try
			{
				connection.rollback();
			}
			catch (SQLException e1)
			{
				throw new GOException("JDBC error - rollback", e);
			}

			throw new GOException("JDBC error", e);
		}
		finally
		{
			try
			{
				connection.close();
			}
			catch (SQLException e)
			{
				throw new GOException("JDBC error - close", e);
			}
		}

		return goEntry;
	}

	/**
	 * Get the Gene Ontology term information given the entry.
	 * 
	 * @throws GOException 
	 */
	public GOEntry getGOTermGivenEntry(String entry) throws GOException
	{
		Connection connection;

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

		GOEntry goEntry = null;

		try
		{
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT term.name, term.term_type, term.acc, term_definition.term_definition, term_definition.term_comment FROM term INNER JOIN term_definition ON (term.id = term_definition.term_id) WHERE LOWER(term.name) = ?");
			preparedStatement.setString(1, entry.toLowerCase());

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
			{
				goEntry = new GOEntry(resultSet.getString("acc"), resultSet.getString("name"),
									  resultSet.getString("term_type"), resultSet.getString("term_definition"),
									  resultSet.getString("term_comment"));
			}
			else
			{
				preparedStatement = connection.prepareStatement("SELECT term.name, term.term_type, term.acc, term_definition.term_definition, term_definition.term_comment FROM term INNER JOIN term_synonym ON (term.id = term_synonym.term_id) INNER JOIN term_definition ON (term.id = term_definition.term_id) WHERE LOWER(term_synonym) like ?");
				preparedStatement.setString(1, entry.toLowerCase());

				resultSet = preparedStatement.executeQuery();

				if (resultSet.next())
				{
					goEntry = new GOEntry(resultSet.getString("acc"), resultSet.getString("name"),
										  resultSet.getString("term_type"), resultSet.getString("term_definition"),
										  resultSet.getString("term_comment"));
				}
			}

			resultSet.close();

			preparedStatement.close();

			connection.commit();
		}
		catch (SQLException e)
		{
			try
			{
				connection.rollback();
			}
			catch (SQLException e1)
			{
				throw new GOException("JDBC error - rollback", e);
			}

			throw new GOException("JDBC error", e);
		}
		finally
		{
			try
			{
				connection.close();
			}
			catch (SQLException e)
			{
				throw new GOException("JDBC error - close", e);
			}
		}

		return goEntry;
	}
}
