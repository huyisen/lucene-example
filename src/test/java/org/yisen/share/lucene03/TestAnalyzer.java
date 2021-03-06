package org.yisen.share.lucene03;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

public class TestAnalyzer {

	@Test
	public void test01() {
		Analyzer a1 = new StandardAnalyzer(Version.LUCENE_35);
		Analyzer a2 = new StopAnalyzer(Version.LUCENE_35);
		Analyzer a3 = new SimpleAnalyzer(Version.LUCENE_35);
		Analyzer a4 = new WhitespaceAnalyzer(Version.LUCENE_35);
		String txt = "this is my house,I am come from yunnang zhaotong," +
				"My email is ynkonghao@gmail.com,My QQ is 64831031";
		
		AnalyzerUtils.displayToken(txt, a1);
		AnalyzerUtils.displayToken(txt, a2);
		AnalyzerUtils.displayToken(txt, a3);
		AnalyzerUtils.displayToken(txt, a4);
	}
	
	@Test
	public void test02() {
		Analyzer a1 = new StandardAnalyzer(Version.LUCENE_35);
		Analyzer a2 = new StopAnalyzer(Version.LUCENE_35);
		Analyzer a3 = new SimpleAnalyzer(Version.LUCENE_35);
		Analyzer a4 = new WhitespaceAnalyzer(Version.LUCENE_35);
		Analyzer a5 = new MMSegAnalyzer();
		String txt = "我来自中国广西，钦州市钦南区北部湾大学";
		
		System.out.println("==============StandardAnalyzer===============");
		AnalyzerUtils.displayToken(txt, a1);
		System.out.println("==============StopAnalyzer===============");
		AnalyzerUtils.displayToken(txt, a2);
		System.out.println("==============SimpleAnalyzer===============");
		AnalyzerUtils.displayToken(txt, a3);
		System.out.println("==============WhitespaceAnalyzer===============");
		AnalyzerUtils.displayToken(txt, a4);
		System.out.println("==============MMSegAnalyzer===============");
		AnalyzerUtils.displayToken(txt, a5);
	}
	
	@Test
	public void test03() {
		Analyzer a1 = new StandardAnalyzer(Version.LUCENE_35);
		Analyzer a2 = new StopAnalyzer(Version.LUCENE_35);
		Analyzer a3 = new SimpleAnalyzer(Version.LUCENE_35);
		Analyzer a4 = new WhitespaceAnalyzer(Version.LUCENE_35);
		String txt = "how are you thank you";
		
		AnalyzerUtils.displayAllTokenInfo(txt, a1);
		System.out.println("------------------------------");
		AnalyzerUtils.displayAllTokenInfo(txt, a2);
		System.out.println("------------------------------");
		AnalyzerUtils.displayAllTokenInfo(txt, a3);
		System.out.println("------------------------------");
		AnalyzerUtils.displayAllTokenInfo(txt, a4);
		
	}
	
	@Test
	public void test04() {
		Analyzer a1 = new MyStopAnalyzer(new String[]{"I","you","hate"});
		Analyzer a2 = new MyStopAnalyzer();
		String txt = "how are you thank you I hate you";
		AnalyzerUtils.displayToken(txt, a1);
		AnalyzerUtils.displayToken(txt, a2);
	}
	
	@Test
	public void test05() {
		try {
			Analyzer a2 = new MySameAnalyzer(new SimpleSamewordContext2());
			String txt = "我来自中国钦州北部湾大学";
			Directory dir = new RAMDirectory();
			IndexWriter writer = new IndexWriter(dir,new IndexWriterConfig(Version.LUCENE_35, a2));
			Document doc = new Document();
			doc.add(new Field("content",txt,Field.Store.YES,Field.Index.ANALYZED));
			writer.addDocument(doc);
			writer.close();
			IndexSearcher searcher = new IndexSearcher(IndexReader.open(dir));
			TopDocs tds = searcher.search(new TermQuery(new Term("content","天朝")),10);
			for ( ScoreDoc sd : tds.scoreDocs) {
				Document d = searcher.doc(sd.doc);
				//System.out.println(d.get("content"));
			}
			//AnalyzerUtils.displayAllTokenInfo(txt, a2);
			searcher.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
