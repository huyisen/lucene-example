package org.yisen.share.lucene04;

import org.junit.Test;

public class TestCustomScore {

	@Test
	public void test01() {
		MyScoreQuery msq = new MyScoreQuery();
		msq.searchByScoreQuery();
	}
	
	@Test
	public void test02() {
		MyScoreQuery msq = new MyScoreQuery();
		msq.searchByFileScoreQuery();
	}
}
