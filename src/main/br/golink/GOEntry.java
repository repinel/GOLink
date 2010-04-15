/*
 * $Id$
 * 
 * Filename : GOEntry.java 
 * Project  : GOLink_API
 */
package br.golink;

/**
 * Class to hold the Gene Ontology entry.
 * 
 * @author Roque Pinel
 */
public class GOEntry
{
	private String accession;

	private String name;

	private String type;

	private String definition;

	private String comment;

	/**
	 * Create a new GOEntry.
	 * 
	 * @param accession
	 * @param name
	 * @param type
	 * @param definition
	 * @param comment
	 */
	public GOEntry(String accession, String name, String type, String definition, String comment)
	{
		this.setAccession(accession);
		this.setName(name);
		this.setType(type);
		this.setDefinition(definition);
		this.setComment(comment);
	}

	/**
	 * @return the accession.
	 */
	public String getAccession()
	{
		return accession;
	}

	/**
	 * @param accession the accession to set.
	 */
	public void setAccession(String accession)
	{
		this.accession = accession;
	}

	/**
	 * @return the name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the type.
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type the type to set.
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @return the definition
	 */
	public String getDefinition()
	{
		return definition;
	}

	/**
	 * @param definition the definition to set.
	 */
	public void setDefinition(String definition)
	{
		this.definition = definition;
	}

	/**
	 * @return the comment.
	 */
	public String getComment()
	{
		return comment;
	}

	/**
	 * @param comment the comment to set.
	 */
	public void setComment(String comment)
	{
		this.comment = comment;
	}
}
