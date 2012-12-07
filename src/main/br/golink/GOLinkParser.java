/*
 * $Id$
 * 
 * Filename : GOLinkParser.java 
 * Project  : GOLink_API
 */
package br.golink;

/**
 * Class responsable for the GOLink output application.
 * 
 * @author Roque Pinel
 */
public class GOLinkParser
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		String term = null;
		String entry = null;
		String[] fields = null;
		String style = null;

		for (int i = 0; i < args.length; i++)
		{
			if ((args[i].startsWith(Constants.PARAMETER_TERM_SINGLE) || args[i].startsWith(Constants.PARAMETER_TERM)) && i + 1 < args.length)
				term = args[++i];
			else if ((args[i].startsWith(Constants.PARAMETER_ENTRY_SINGLE) || args[i].startsWith(Constants.PARAMETER_ENTRY)) && i + 1 < args.length)
				entry = args[++i];
			else if ((args[i].startsWith(Constants.PARAMETER_FIELD_SINGLE) || args[i].startsWith(Constants.PARAMETER_FIELD)) && i + 1 < args.length)
				fields = args[++i].split(",");
			else if ((args[i].startsWith(Constants.PARAMETER_STYLE_SINGLE) || args[i].startsWith(Constants.PARAMETER_STYLE)) && i + 1 < args.length)
				style = args[++i];
			else
			{
				System.out.println(getHelp());
				System.exit(1);
			}
		}

		boolean fieldName = false;
		boolean fieldAccession = false;
		boolean fieldOntology = false;
		boolean fieldDefinition = false;
		boolean fieldComment = false;

		/*
		 * Check the possibles fields. 
		 */
		for (int i = 0; fields != null && i < fields.length; i++)
		{
			if (fields[i].equalsIgnoreCase(Constants.FIELD_NAME))
				fieldName = true;
			else if (fields[i].equalsIgnoreCase(Constants.FIELD_ACCESSION))
				fieldAccession = true;
			else if (fields[i].equalsIgnoreCase(Constants.FIELD_ONTOLOGY))
				fieldOntology = true;
			else if (fields[i].equalsIgnoreCase(Constants.FIELD_DEFINITON))
				fieldDefinition = true;
			else if (fields[i].equalsIgnoreCase(Constants.FIELD_COMMENT))
				fieldComment = true;
			else
			{
				System.out.println(getHelp());
				System.exit(1);
			}
		}

		GOLinkController goLink = new GOLinkController();

		GOEntry goEntry = null;
		
		try
		{
			if (term != null)
			{
				goEntry = goLink.getGOTermGivenAccession(term);
			}
			else if (entry != null)
			{
				goEntry = goLink.getGOTermGivenEntry(entry);
			}
			else
			{
				System.out.println(getHelp());
				System.exit(1);
			}

			StringBuffer resultBuffer = new StringBuffer();

			if (style != null)
			{
				resultBuffer.append("<style>\r\n@import url('").append(style).append("');\r\n</style>").append(Constants.NL);                   
			}

			if (fields == null || fields.length <= 0)
			{
				resultBuffer.append("<div class=\"go-contents-term\">").append(Constants.NL);

				if (goEntry.getName() != null)
				{
					resultBuffer.append("<h1 class=\"go-name\">" + goEntry.getName() + "</h1>").append(Constants.NL);
				}

				resultBuffer.append("<div class=\"go-info\">").append(Constants.NL);

				resultBuffer.append("<h2 class=\"go-term\">Term Information</h2>").append(Constants.NL);

				resultBuffer.append("<dl class=\"go-term-info\">").append(Constants.NL);

				if (goEntry.getAccession() != null)
				{
					resultBuffer.append("<dt class=\"go-acc\">Accession</dt>").append(Constants.NL);

					resultBuffer.append("<dd class=\"go-acc-info\">"+ goEntry.getAccession() + "</dd>").append(Constants.NL);
				}

				if (goEntry.getType() != null)
				{
					resultBuffer.append("<dt class=\"go-type\">>Ontology</dt>").append(Constants.NL);

					resultBuffer.append("<dd class=\"go-type-info\">" + goEntry.getType() + "</dd>").append(Constants.NL);
				}

				if (goEntry.getDefinition() != null)
				{
					resultBuffer.append("<dt class=\"go-def\">>Definition</dt>").append(Constants.NL);

					resultBuffer.append("<dd class=\"go-def-info\">" + goEntry.getDefinition() + "</dd>").append(Constants.NL);
				}

				if (goEntry.getComment() != null)
				{
					resultBuffer.append("<dt class=\"go-comment\">>Comment</dt>").append(Constants.NL);

					resultBuffer.append("<dd class=\"go-comment-info\">" + goEntry.getComment() + "</dd>").append(Constants.NL);
				}

				resultBuffer.append("</dl>").append(Constants.NL);

				resultBuffer.append("</div>").append(Constants.NL);

				resultBuffer.append("</div>").append(Constants.NL);
			}
			else if (fields.length == 1)
			{
				if (fieldName && goEntry.getName() != null)
				{
					resultBuffer.append("<p class=\"go-name\">" + goEntry.getName() + "</p>").append(Constants.NL);
				}
				else if (fieldAccession && goEntry.getAccession() != null)
				{
					resultBuffer.append("<p class=\"go-acc-info\">" + goEntry.getAccession() + "</p>").append(Constants.NL);
				}
				else if (fieldOntology && goEntry.getType() != null)
				{
					resultBuffer.append("<p class=\"go-type-info\">" + goEntry.getType() + "</p>").append(Constants.NL);
				}
				else if (fieldDefinition && goEntry.getDefinition() != null)
				{
					resultBuffer.append("<p class=\"go-def-info\">" + goEntry.getDefinition() + "</p>").append(Constants.NL);
				}
				else if (fieldComment && goEntry.getComment() != null)
				{
					resultBuffer.append("<p class=\"go-comment-info\">" + goEntry.getComment() + "</p>").append(Constants.NL);	
				}
			}
			else
			{
				resultBuffer.append("<div class=\"go-contents-term\">").append(Constants.NL);

				if (fieldName && goEntry.getName() != null)
				{
					resultBuffer.append("<h1 class=\"go-name\">" + goEntry.getName() + "</h1>").append(Constants.NL);
				}

				resultBuffer.append("<div class=\"go-info\">").append(Constants.NL);

				resultBuffer.append("<h2 class=\"go-term\">Term Information</h2>").append(Constants.NL);

				resultBuffer.append("<dl class=\"go-term-info\">").append(Constants.NL);

				if (fieldAccession && goEntry.getAccession() != null)
				{
					resultBuffer.append("<dt class=\"go-acc\">Accession</dt>").append(Constants.NL);

					resultBuffer.append("<dd class=\"go-acc-info\">"+ goEntry.getAccession() + "</dd>").append(Constants.NL);
				}

				if (fieldOntology && goEntry.getType() != null)
				{
					resultBuffer.append("<dt class=\"go-type\">>Ontology</dt>").append(Constants.NL);

					resultBuffer.append("<dd class=\"go-type-info\">" + goEntry.getType() + "</dd>").append(Constants.NL);
				}

				if (fieldDefinition && goEntry.getDefinition() != null)
				{
					resultBuffer.append("<dt class=\"go-def\">>Definition</dt>").append(Constants.NL);

					resultBuffer.append("<dd class=\"go-def-info\">" + goEntry.getDefinition() + "</dd>").append(Constants.NL);
				}

				if (fieldComment && goEntry.getComment() != null)
				{
					resultBuffer.append("<dt class=\"go-comment\">>Comment</dt>").append(Constants.NL);

					resultBuffer.append("<dd class=\"go-comment-info\">" + goEntry.getComment() + "</dd>").append(Constants.NL);
				}

				resultBuffer.append("</dl>").append(Constants.NL);

				resultBuffer.append("</div>").append(Constants.NL);

				resultBuffer.append("</div>").append(Constants.NL);
			}

			System.out.println(resultBuffer);
		}
		catch (GOException e)
		{
			e.printStackTrace();
		}

		System.exit(0);
	}

	/**
	 * Get the help information.
	 * 
	 * @return Help information.
	 */
	private static String getHelp()
	{
		StringBuffer help = new StringBuffer();

		help.append("Usage:\n");

		help.append(Constants.PARAMETER_TERM_SINGLE + ", " + Constants.PARAMETER_TERM + "\t");
		help.append("Gene Ontology accession id\n");

		help.append(Constants.PARAMETER_ENTRY_SINGLE + ", " + Constants.PARAMETER_ENTRY + "\t");
		help.append("entry to be searched\n");

		help.append(Constants.PARAMETER_FIELD_SINGLE + ", " + Constants.PARAMETER_FIELD + "\t");
		help.append("comma-separated list of fields to be displayed\n");
		help.append("\t\t[name,accession,ontology,definition,comment]\n");

		help.append(Constants.PARAMETER_STYLE_SINGLE + ", " + Constants.PARAMETER_STYLE + "\t");
		help.append("stylesheet to use\n");

		return help.toString();
	}
}
