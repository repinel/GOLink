package br.golink;

public class TestGOLink
{

	/**
	 * @param args
	 */
	public static void main(String[] as)
	{
		String[] args = new String[2];

		args[0] = new String("-e");
		args[1] = new String("golgi"); 

		GOLinkParser.main(args);
	}
}
