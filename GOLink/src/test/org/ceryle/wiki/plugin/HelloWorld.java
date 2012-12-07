/*
 * Copyright 2001-2007 Murray Altheim.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * $Id: HelloWorld.java,v 1.2 2007-06-15 12:10:10 altheim Exp $
 */

package org.ceryle.wiki.plugin;

import  com.ecyrd.jspwiki.WikiContext;
import  com.ecyrd.jspwiki.TextUtil;
import  com.ecyrd.jspwiki.plugin.WikiPlugin;
import  com.ecyrd.jspwiki.plugin.PluginManager;
import  com.ecyrd.jspwiki.plugin.PluginException;
import  com.ecyrd.jspwiki.auth.authorize.Role;

import  org.apache.log4j.Logger;

import  java.util.Iterator;
import  java.util.Map;

/**
 *  This is a bit more than a minimal 'Hello World' plugin, providing a core set
 *  of features (such as parameter handling and authentication checking) as a
 *  demo and beginning point for plugin development.
 *
 *  <h4>Parameters</h4>
 *  <ul>
 *    <li><b>debug</b> = <i>'true' | 'false'</i>.
 *      When true, displays debugging level information. Defaults to false.
 *    </li>
 *    <li><b>text</b> = <i>'content'</i>.
 *      An optional parameter to provide alternate "Hello World" text
 *      to the plugin.
 *    </li>
 *  </ul>
 *
 *  <h3>Notes</h3>
 *  <ul>
 *    <li>
 *    Note that plugin invocation occurs more than once per page view,
 *    as plugins get parsed and invoked during permission checks and other
 *    calls for the page's HTML content. This in itself isn't a problem,
 *    but you should know that the plugin's execute() method gets called
 *    multiple times per page view, which whatever impact that may have.
 *    </li>
 *  </ul>
 *
 *  @since 2.4.x
 *  @author Murray Altheim
 */
public class HelloWorld implements WikiPlugin
{
    private static Logger log = Logger.getLogger(HelloWorld.class);

    /** The name of the optional parameter to provide alternate
     *  "Hello World" text for the plugin. */
    public static final String PARAM_TEXT = "text";

    /** The default text string used by the plugin. */
    public static String DEFAULT_TEXT = "Hello, World!"; // I18N (i.e., needs localization)

    /** If true, check to see that user is authenticated. This is
     *  a compile-time flag whose default value is {@value}. */
    protected static final boolean CHECK_AUTHENTICATION = false;

    private boolean m_debug = false;

    // ............


    public String execute( WikiContext context, Map params ) throws PluginException
    {
        StringBuffer out = new StringBuffer();
        try {
            if ( CHECK_AUTHENTICATION ) {
                if ( !context.getEngine().getAuthorizationManager().isUserInRole(
                            context.getWikiSession(), Role.AUTHENTICATED ) ) {
                    log.info( "unauthorized user:  " + context.getWikiSession().getUserPrincipal().getName() );
                    out.append( "<div class=\"error\">\n" );
                    out.append( "Plugin execution not permitted for unauthenticated users." ); // I18N
                    out.append( "</div>\n" );
                    return out.toString();
                }
            }
            // process parameters
            m_debug     = TextUtil.isPositive((String)params.get(PluginManager.PARAM_DEBUG));
            String body = (String)params.get(PluginManager.PARAM_BODY);
            String cmd  = (String)params.get(PluginManager.PARAM_CMDLINE);
            String text = (String)params.get(PARAM_TEXT);
            if ( text == null ) text = DEFAULT_TEXT;

            // generate XHTML output .........................................

            out.append( "<div class=\"helloworld\">\n" );
            if ( m_debug ) {
                out.append( "<h4>" );
                out.append( "Debug Output:" ); // I18N
                out.append( "</h4>\n" );
                // list of params
                out.append( "<p><i>parameters:</i><br />\n" ); // I18N
                Iterator it = params.keySet().iterator();
                while ( it.hasNext() ) {
                    String key = (String)it.next();
                    Object value = params.get(key);
                    out.append( "<b>key</b>='" + key + "' <b>value</b>='"
                            + ( value instanceof String ? (String)value : value.getClass().getName() ) + "'<br />\n" );
                }
                out.append( "</p>\n" );
                // plugin body
                out.append( "<p><i>body content:</i>\n" ); // I18N
                out.append( "<pre>" );
                out.append( body );
                out.append( "</pre>\n" );
                out.append( "</p>\n" );
                // plugin command line
                out.append( "<p><i>command line content:</i>\n" ); // I18N
                out.append( "<pre>" );
                out.append( cmd );
                out.append( "</pre>\n" );
                out.append( "</p>\n" );
            }

            // normal output
            out.append( "<p>" );
            out.append( text );
            out.append( "</p>" );

            out.append( "</div>" );

        } catch ( Exception e ) {
            log.error( e.getClass().getName() + " thrown by HelloWorld plugin: " + e.getMessage() ); // I18N
            out.append( "<div class=\"error\">\n" );
            out.append( e.getClass().getName() + " thrown by HelloWorld plugin: " + e.getMessage() ); // I18N
            out.append( "</div>\n" );
        }
        return out.toString();
    }


} // end org.ceryle.wiki.plugin.HelloWorld
