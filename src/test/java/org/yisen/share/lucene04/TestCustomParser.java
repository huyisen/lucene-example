package org.yisen.share.lucene04;

import org.junit.Test;

public class TestCustomParser {

	
	@Test
	public void test01() {
		CustomParserUtil cpu = new CustomParserUtil();
		//cpu.searcherByQuery("ja?va");
//		cpu.searcherByQuery("content:[a TO j]");
		cpu.searcherByQuery("date:[2011-12-02 TO 2013-10-02]");
	}
}
