/*
 * $Id$
 * 
 * Filename : Constants.java 
 * Project  : GOLink_API
 */
package br.golink;

/**
 * Constants.
 * 
 * @author Roque Pinel
 */
public class Constants
{
	/**
	 * Parameters.
	 */
	public static final String PARAMETER_TERM = "--term";
	public static final String PARAMETER_TERM_SINGLE = "-t";
	public static final String PARAMETER_ENTRY = "--entry";
	public static final String PARAMETER_ENTRY_SINGLE = "-e";
	public static final String PARAMETER_FIELD = "--field";
	public static final String PARAMETER_FIELD_SINGLE = "-f";
	public static final String PARAMETER_STYLE = "--style";
	public static final String PARAMETER_STYLE_SINGLE = "-s";

	/**
	 * Fields.
	 */
	public static final String FIELD_NAME = "name";
	public static final String FIELD_ACCESSION = "accession";
	public static final String FIELD_ONTOLOGY = "ontology";
	public static final String FIELD_DEFINITON = "definition";
	public static final String FIELD_COMMENT = "comment";
	
	/**
	 * Especial Character.
	 */
	public static final String NL = "\n";

	/**
	 * Database Constants.
	 */
	public static final String DB_HOSTNAME = "mysql.ebi.ac.uk:4085";
	public static final String DB_USERNAME = "go_select";
	public static final String DB_PASSWORD = "amigo";
	public static final String DATABASE    = "go_latest";
}
