package org.yisen.share.lucene03;


import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MaxWordSeg;
import com.chenlb.mmseg4j.analysis.MMSegTokenizer;

public class MySameAnalyzer extends Analyzer {
	private SamewordContext samewordContext;
	
	public MySameAnalyzer(SamewordContext swc) {
		samewordContext = swc;
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		Dictionary dic = Dictionary.getInstance();
		return new MySameTokenFilter(
				new MMSegTokenizer(new MaxWordSeg(dic), reader),samewordContext);
	}

}
