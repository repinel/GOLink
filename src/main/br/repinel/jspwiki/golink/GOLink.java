package br.repinel.jspwiki.golink;

import java.util.Map;

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
	 * @see com.ecyrd.jspwiki.plugin.WikiPlugin#execute(com.ecyrd.jspwiki.WikiContext, java.util.Map)
	 */
	public String execute(WikiContext context, Map params) throws PluginException
	{
		StringBuffer out = new StringBuffer();

		out.append("-- apenas um teste --");

		return out.toString();
	}
}
