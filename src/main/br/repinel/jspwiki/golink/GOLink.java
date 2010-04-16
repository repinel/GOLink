package br.repinel.jspwiki.golink;

import java.util.Map;

import org.apache.log4j.Logger;

import br.golink.GOEntry;
import br.golink.GOException;
import br.golink.GOLinkController;

import com.ecyrd.jspwiki.WikiContext;
import com.ecyrd.jspwiki.plugin.PluginException;
import com.ecyrd.jspwiki.plugin.WikiPlugin;

/**
 * Plugin for JSPWiki to allow references to the Gene Ontology in pages.
 * 
 * @author Roque Pinel
 * 
 */
public class GOLink implements WikiPlugin
{
	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(GOLink.class);

	/**
	 * Parameter for the term referred by Gene Ontology, like a id, it's unique.
	 */
	private static final String PARAMETER_TERM = "term";

	/**
	 * Parameter for the entry to be searched with exact match.
	 */
	private static final String PARAMETER_ENTRY = "entry";

	/**
	 * Parameter for the fields to be displayed.
	 */
	private static final String PARAMETER_FIELD = "field";

	/**
	 * Parameter for the style to apply.
	 */
	private static final String PARAMETER_STYLE = "style";

	/**
	 * Fields.
	 */
	public static final String FIELD_NAME = "name";
	public static final String FIELD_ACCESSION = "accession";
	public static final String FIELD_ONTOLOGY = "ontology";
	public static final String FIELD_DEFINITON = "definition";
	public static final String FIELD_COMMENT = "comment";

	/**
	 * New line.
	 */
	private static final String NL = "\n";

	/**
	 * @see com.ecyrd.jspwiki.plugin.WikiPlugin#execute(com.ecyrd.jspwiki.WikiContext, java.util.Map)
	 */
	public String execute(WikiContext context, Map params) throws PluginException
	{
		String term = (String) params.get(PARAMETER_TERM);
		String entry = (String) params.get(PARAMETER_ENTRY);
		String style = (String) params.get(PARAMETER_STYLE);

		boolean fieldName = false;
		boolean fieldAccession = false;
		boolean fieldOntology = false;
		boolean fieldDefinition = false;
		boolean fieldComment = false;

		int countFields = 0;

		if (params.get(PARAMETER_FIELD) != null)
		{
			String[] fields = ((String) params.get(PARAMETER_FIELD)).substring(PARAMETER_FIELD.length() + 1).trim().split(",");

			/*
			 * Check the possibles fields. 
			 */
			for (int i = 0; fields != null && i < fields.length; i++)
			{
				if (fields[i].equalsIgnoreCase(FIELD_NAME))
					fieldName = true;
				else if (fields[i].equalsIgnoreCase(FIELD_ACCESSION))
					fieldAccession = true;
				else if (fields[i].equalsIgnoreCase(FIELD_ONTOLOGY))
					fieldOntology = true;
				else if (fields[i].equalsIgnoreCase(FIELD_DEFINITON))
					fieldDefinition = true;
				else if (fields[i].equalsIgnoreCase(FIELD_COMMENT))
					fieldComment = true;
			}

			countFields = fields.length;
		}

		StringBuffer out = new StringBuffer();

		GOLinkController goLink = new GOLinkController();

		GOEntry goEntry = null;

		log.info("GOLink: running..." + NL);

		try
		{
			if (term != null)
			{
				log.info("GOLink: term = " + term + NL);

				goEntry = goLink.getGOTermGivenAccession(term);
			}
			else if (entry != null)
			{
				log.info("GOLink: entry = " + term + NL);

				goEntry = goLink.getGOTermGivenEntry(entry);
			}
			else
			{
				log.info("GOLink: you must specify a term or an entry, see the documentation." + NL);

				out.append("You must specify a term or an entry, see the documentation.");
				return out.toString();
			}

			if (style != null)
			{
				out.append("<style>\r\n@import url('").append(style).append("');\r\n</style>").append(NL);
			}

			if (countFields == 0)
			{
				out.append("<div class=\"go-contents-term\">").append(NL);

				if (goEntry.getName() != null)
				{
					out.append("<h1 class=\"go-name\">" + goEntry.getName() + "</h1>").append(NL);
				}

				out.append("<div class=\"go-info\">").append(NL);

				out.append("<h2 class=\"go-term\">Term Information</h2>").append(NL);

				out.append("<dl class=\"go-term-info\">").append(NL);

				if (goEntry.getAccession() != null)
				{
					out.append("<dt class=\"go-acc\">Accession</dt>").append(NL);

					out.append("<dd class=\"go-acc-info\">"+ goEntry.getAccession() + "</dd>").append(NL);
				}

				if (goEntry.getType() != null)
				{
					out.append("<dt class=\"go-type\">Ontology</dt>").append(NL);

					out.append("<dd class=\"go-type-info\">" + goEntry.getType() + "</dd>").append(NL);
				}

				if (goEntry.getDefinition() != null)
				{
					out.append("<dt class=\"go-def\">Definition</dt>").append(NL);

					out.append("<dd class=\"go-def-info\">" + goEntry.getDefinition() + "</dd>").append(NL);
				}

				if (goEntry.getComment() != null)
				{
					out.append("<dt class=\"go-comment\">Comment</dt>").append(NL);

					out.append("<dd class=\"go-comment-info\">" + goEntry.getComment() + "</dd>").append(NL);
				}

				out.append("</dl>").append(NL);

				out.append("</div>").append(NL);

				out.append("</div>").append(NL);
			}
			else if (countFields == 1)
			{
				if (fieldName && goEntry.getName() != null)
				{
					out.append("<p class=\"go-name\">" + goEntry.getName() + "</p>").append(NL);
				}
				else if (fieldAccession && goEntry.getAccession() != null)
				{
					out.append("<p class=\"go-acc-info\">" + goEntry.getAccession() + "</p>").append(NL);
				}
				else if (fieldOntology && goEntry.getType() != null)
				{
					out.append("<p class=\"go-type-info\">" + goEntry.getType() + "</p>").append(NL);
				}
				else if (fieldDefinition && goEntry.getDefinition() != null)
				{
					out.append("<p class=\"go-def-info\">" + goEntry.getDefinition() + "</p>").append(NL);
				}
				else if (fieldComment && goEntry.getComment() != null)
				{
					out.append("<p class=\"go-comment-info\">" + goEntry.getComment() + "</p>").append(NL);	
				}
			}
			else
			{
				out.append("<div class=\"go-contents-term\">").append(NL);

				if (fieldName && goEntry.getName() != null)
				{
					out.append("<h1 class=\"go-name\">" + goEntry.getName() + "</h1>").append(NL);
				}

				out.append("<div class=\"go-info\">").append(NL);

				out.append("<h2 class=\"go-term\">Term Information</h2>").append(NL);

				out.append("<dl class=\"go-term-info\">").append(NL);

				if (fieldAccession && goEntry.getAccession() != null)
				{
					out.append("<dt class=\"go-acc\">Accession</dt>").append(NL);

					out.append("<dd class=\"go-acc-info\">"+ goEntry.getAccession() + "</dd>").append(NL);
				}

				if (fieldOntology && goEntry.getType() != null)
				{
					out.append("<dt class=\"go-type\">Ontology</dt>").append(NL);

					out.append("<dd class=\"go-type-info\">" + goEntry.getType() + "</dd>").append(NL);
				}

				if (fieldDefinition && goEntry.getDefinition() != null)
				{
					out.append("<dt class=\"go-def\">Definition</dt>").append(NL);

					out.append("<dd class=\"go-def-info\">" + goEntry.getDefinition() + "</dd>").append(NL);
				}

				if (fieldComment && goEntry.getComment() != null)
				{
					out.append("<dt class=\"go-comment\">>Comment</dt>").append(NL);

					out.append("<dd class=\"go-comment-info\">" + goEntry.getComment() + "</dd>").append(NL);
				}

				out.append("</dl>").append(NL);

				out.append("</div>").append(NL);

				out.append("</div>").append(NL);
			}
		}
		catch (GOException e)
		{
			log.info("GOLink:" + NL + e.getLocalizedMessage() + NL);

			out.append("GOLink ").append(e.getLocalizedMessage());
		}

		return out.toString();
	}
}
