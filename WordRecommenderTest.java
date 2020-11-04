import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WordRecommenderTest {
	
	WordRecommender testWordRecommender;
	
	@BeforeEach
	void Setup() throws Exception {
		testWordRecommender = new WordRecommender("testDict.txt");
	}
	
	@AfterEach
	void TearDown() throws Exception{
		
	}
	
	
	@Test
	void testBuildAllWords() {
		List<String> listOfWords = Arrays.asList("automatically", "awoke", "autonomously", "delate", "faction", "morbid", "moisture", "orbit", "orchard", "outweigh", "unarticulated", "sake", "scorer", "tomorrow", "unbrace", "wedge", "wok", "woo", "yonder", "zone");
		HashSet<String> testWordsSet = new HashSet<>(listOfWords);
//		System.out.println(testWordsSet.toString());
		assertEquals(testWordsSet, testWordRecommender.allWords);
		
	}
	
	@Test
	void testBuildAllWords2() {
		//test the exception returned when there is not the file
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
		new WordRecommender("te88stDict.txt");
		assertEquals("An error occurred.", outputStreamCaptor.toString().trim());
	}
	

	@Test
	void testGetDict() {
		List<String> listOfWords = Arrays.asList("automatically", "awoke", "autonomously", "delate", "faction", "morbid", "moisture", "orbit", "orchard", "outweigh", "unarticulated", "sake", "scorer", "tomorrow", "unbrace", "wedge", "wok", "woo", "yonder", "zone");
		HashSet<String> testWordsSet = new HashSet<>(listOfWords);
		HashSet<String> testGetDictSet = testWordRecommender.getDict();
		assertEquals(testWordsSet, testGetDictSet);
	}

	@Test
	void testGetSimilarity1() {
		double testSimilarity = testWordRecommender.getSimilarity("oblige", "oblivion");
		assertEquals(testSimilarity, 2.5);
	}
	
	@Test
	void testGetSimilarity2() {
		double testSimilarity2 = testWordRecommender.getSimilarity("aghast", "gross");
		assertEquals(testSimilarity2, 1.5);
	}
	

	@Test
	void testGetWordSuggestions1() {
		ArrayList<String> testRecommend1 = testWordRecommender.getWordSuggestions("morbit", 5, 0.3, 3);
		Object[] testRecommendArray1 = testRecommend1.toArray();
		Object[] testResult1 = {"morbid", "orbit", "tomorrow"};
		assertArrayEquals(testRecommendArray1, testResult1);
	}
	
	@Test
	void testGetWordSuggestions2() {
		ArrayList<String> testRecommend2 = testWordRecommender.getWordSuggestions("automagically", 3, 0.5, 4);
		Object[] testRecommendArray2 = testRecommend2.toArray();
		Object[] testResult2 = {"automatically", "autonomously"};
		assertArrayEquals(testRecommendArray2, testResult2);
	}
	
	@Test
	void testGetWordSuggestions3() {
		ArrayList<String> testRecommend3 = testWordRecommender.getWordSuggestions("sleepyyyyyyy", 3, 0.7, 4);
		Object[] testRecommendArray3 = testRecommend3.toArray();
		Object[] testResult3 = {};
		assertArrayEquals(testRecommendArray3, testResult3);
	}
	

	@Test
	void testGetCommonPercent1() {
		double testCommonPercent1 = testWordRecommender.getCommonPercent("committee", "comet");
		double result1 = 5.0/6;
		assertEquals(testCommonPercent1, result1);
	}
	
	@Test
	void testGetCommonPercent2() {
		double testCommonPercent2 = testWordRecommender.getCommonPercent("gardener", "nerdier");
		double result2 = 4.0/7;
		assertEquals(testCommonPercent2, result2);
	}
	
	@Test
	void testGetCommonPercent3() {
		double testCommonPercent2 = testWordRecommender.getCommonPercent("gardener", "nerdier");
		double testCommonPercent3 = testWordRecommender.getCommonPercent("garde", "nerdier");
		double result3 = 3.0/7;
		assertEquals(testCommonPercent3, result3);
	}
	
	

	@Test
	void testPrettyPrint() {
		ArrayList<String> testPrettyPrintList = new ArrayList<String>(Arrays.asList("biker","tiger", "bigger"));
		String testPrettyPrintString = testWordRecommender.prettyPrint(testPrettyPrintList);
		String testPrettyPrintResult = "1. biker\n2. tiger\n3. bigger\n";
		assertEquals(testPrettyPrintString, testPrettyPrintResult);
	}

}
