:: GOLink Plugin ::

This plugin will retrieve the Gene Ontology content for a term and displays it.

The content is displayed using <div>'s, dl's, dt's and dl's. You can use the
global style sheet or your own style sheet to format the content so that it looks nicely.


-- Installation --

* copy the GOLink-*.jar and the GOLink_API-*.jar files to a folder in the path, typically {{$WIKI_HOME/WEB-INF/lib/}}
* in the ''Plugin search paths'' section of the {{$WIKI_HOME/WEB-INF/jspwiki.properties}} file, edit the comma-delimited "jspwiki.plugin.searchPath" property to include the package designation of the plugin
** eg: {{br.repinel.jspwiki.golink}}
** make sure the comma-delimited property is [[comma] delimited, not [[comma][[space] delimited
    jspwiki.plugin.searchPath = br.repinel.jspwiki.golink
* cycle the wiki


-- Usage --

In a topic enter:

[[{'GOLink term='...Gene Ontology accession id...' entry='...a entry to be searched...' field='...list of fields...' style='...stylesheet to use...'}]


-- The parameters --

term: (required)
Gene Ontology accession id.

entry: (required)
A entry to be searched at the Gene Ontology database.

field: (optional)
A comma-separated list of fields to be displayed. Current fields: name, accession, ontology, definition and comment.

style: (optional)
The stylesheet to use. Place the stylesheet in the jsp directory or give a full url to the stylesheet location.
If both, term and entry, are defined, them use the term definition.


-- Usage Example --

Print the content for 'Golgi apparatus'.
[[{GOLink term='GO:0005794'}]

Print the content for 'nucleus'.
[[{GOLink entry='nucleus'}]

Print the field description for 'Golgi apparatus'.
[[{GOLink term='GO:0005794' field:'definition'}]

Print the fields name and description for 'nucleus'.
[[{GOLink entry='nucleus' field='name,definition'}]


Print the content for 'Golgi apparatus' since the term has a high priority.
[[{GOLink term='GO:0005794' entry='nucleus'}]
