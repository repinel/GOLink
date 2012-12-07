package de.d3web.we.jspwiki.comment;

import java.util.Map;

import com.ecyrd.jspwiki.WikiContext;
import com.ecyrd.jspwiki.plugin.PluginException;
import com.ecyrd.jspwiki.plugin.WikiPlugin;

/**
 * @author kazamatzuri
 * Simple plugin for JSPWiki to allow comments in Pages
 *
 */
public class Comment implements WikiPlugin{

	/* (non-Javadoc)
	 * @see com.ecyrd.jspwiki.plugin.WikiPlugin#execute(com.ecyrd.jspwiki.WikiContext, java.util.Map)
	 */
	public String execute(WikiContext context, Map params)
			throws PluginException {
		final String COMMENT_ICON 
		= "<span style=\"color:red;background:yellow\">COMMENT</span>";
	if( !params.isEmpty()
			&& params.values().iterator().next() != null )
	{

		return COMMENT_ICON.replaceAll("COMMENT", 
				(String)params.get("_cmdline") );
	}
	return "dead";	
	}

}