package adcowebsolutions.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import adcowebsolutions.beans.SearchPage;
import adcowebsolutions.beans.SearchResult;

public class SearchServiceTest {
	
	private static final Integer START_PAGE_0 = 0;
	private static final Integer START_PAGE_10 = 10;
	private static final Integer START_PAGE_20 = 20;
	private static final Integer START_PAGE_30 = 30;
	private static final String MAX_RESULTS_PER_PAGE = "10";
	private static final Integer TOTAL_RESULTS_35 = 35;
	private static final Integer TOTAL_RESULTS_0 = 0;
	private static final Integer TOTAL_RESULTS_1 = 1;
	private static final Integer TOTAL_RESULTS_20 = 20;
	private SearchService searchService;
	private List<SearchResult> searchResults = new ArrayList<SearchResult>();

	@Before
	public void setUp() {
		searchService = new SearchService();
		searchService.setMaxResultsPerPage(MAX_RESULTS_PER_PAGE);
		searchService.setStartPage(START_PAGE_0);
		buildSearchResults();
	}

	@After
	public void tearDown() {
		searchService = null;
	    searchResults = null;
	}

	@Test
	public void testNumberOfPages() {

		assertNotNull(searchService);
	
		searchService.setSearchPages(TOTAL_RESULTS_35);
		List<SearchPage> pagesToDisplay = searchService.getSearchPages();
		assertNotNull(pagesToDisplay);
		assertEquals(4, pagesToDisplay.size());
	
		searchService.setSearchPages(TOTAL_RESULTS_0);
		pagesToDisplay = searchService.getSearchPages();
		assertNotNull(pagesToDisplay);
		assertEquals(1, pagesToDisplay.size());
	
		searchService.setSearchPages(TOTAL_RESULTS_1);
		pagesToDisplay = searchService.getSearchPages();
		assertNotNull(pagesToDisplay);
		assertEquals(1, pagesToDisplay.size());
	
		searchService.setSearchPages(TOTAL_RESULTS_20);
		pagesToDisplay = searchService.getSearchPages();
		assertNotNull(pagesToDisplay);
		assertEquals(2, pagesToDisplay.size());
	}

	@Test
	public void testCurrentPage() {

		assertNotNull(searchService);
		assertEquals(35, searchResults.size());
	
		searchService.setStartPage(START_PAGE_0);
		searchService.setSearchPages(searchResults.size());
		List<SearchPage> pagesToDisplay = searchService.getSearchPages();
		assertNotNull(pagesToDisplay);
		assertEquals(4, pagesToDisplay.size());
		assertTrue(pagesToDisplay.get(0).isCurrentPage());
	
		searchService.setStartPage(START_PAGE_10);
		searchService.setSearchPages(searchResults.size());
		pagesToDisplay = searchService.getSearchPages();
		assertNotNull(pagesToDisplay);
		assertEquals(4, pagesToDisplay.size());
		assertTrue(pagesToDisplay.get(1).isCurrentPage());
	
		searchService.setStartPage(START_PAGE_20);
		searchService.setSearchPages(searchResults.size());
		pagesToDisplay = searchService.getSearchPages();
		assertNotNull(pagesToDisplay);
		assertEquals(4, pagesToDisplay.size());
		assertTrue(pagesToDisplay.get(2).isCurrentPage());
	
		searchService.setStartPage(START_PAGE_30);
		searchService.setSearchPages(searchResults.size());
		pagesToDisplay = searchService.getSearchPages();
		assertNotNull(pagesToDisplay);
		assertEquals(4, pagesToDisplay.size());
		assertTrue(pagesToDisplay.get(3).isCurrentPage());

	}

	private void buildSearchResults() {

		SearchResult sr = null;
	
		for (int i = 0; i < 35; i++) {
			sr = new SearchResult();
			sr.setTitle("Test Title "+(i+1));
			sr.setDetail("Test Detail "+(i+1));
			searchResults.add(sr);
		}
	}
}
