package adcowebsolutions.search;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import adcowebsolutions.beans.SearchPage;
import adcowebsolutions.beans.SearchResult;

public class SearchService {

	private Logger log = LoggerFactory.getLogger(SearchService.class);

    private FullTextEntityManager em;

    private String searchPattern;
    
    private Integer startPage;
    
    private String[] searchFields;
    
    private Float duration;
    
    private Integer totalResults;
    
    private Integer numberOfResultsOnPage;
    
    private Integer firstResultOnPage;
    
    private List<SearchPage> searchPages;
    
    private String maxResultsPerPage;
    
    private boolean firstPage;
    
    private boolean lastPage;
    
    private Integer previousPage;
    
    private Integer nextPage;

    public List<SearchResult> getSearchResults() {
    	
    	long startTime = System.currentTimeMillis();
    	
    	List<SearchResult> searchResults = null;
    	
    	if (searchPattern == null || "".equals(searchPattern) ) {
            searchPattern = null;
            //FacesMessages.instance().add(Severity.WARN, WARN, "search_pattern_empty", null, null);
            return new ArrayList<SearchResult>();
        }
    	
    	Query luceneQuery = null;
    	
        MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_29, getSearchFields(), new StandardAnalyzer(Version.LUCENE_29));
        parser.setAllowLeadingWildcard(true);

    	try {
    		luceneQuery = parser.parse(searchPattern);    		
    	} catch (ParseException pe) {
    		log.error("Failed to parse {} into Lucene query due to: {}", searchPattern, String.valueOf(pe.getMessage()));
    		return null;
		}
    	
    	FullTextQuery ftq = em.createFullTextQuery(luceneQuery);

        List results = ftq.setFirstResult(getStartPage()).setMaxResults(Integer.parseInt(maxResultsPerPage)).getResultList();
        
        if (results != null && !results.isEmpty()) {
        	searchResults = formatSearchResults(luceneQuery, results);
        } else {
        	searchResults = new ArrayList<SearchResult>();
        }
         
        setTotalResults(ftq.getResultSize() > 0 ? ftq.getResultSize() : 0);
        
        setSearchPages(getTotalResults());
    	
    	long endTime = System.currentTimeMillis();
    	setDuration((float)(endTime - startTime) / 1000);
        
        return searchResults;
    }

	private List<SearchResult> formatSearchResults(Query luceneQuery, List results) {
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		SearchResult sr = null;
		List<SearchResult> searchResults = new ArrayList<SearchResult>();
		StringBuilder link = null;
		String highlightedDetailText = null;
		String highlightedTitleText = null;
    	
		// Pass the Lucene Query to a Scorer that extracts matching spans for
        // the query and then uses these spans to score each fragment
        QueryScorer scorer = new QueryScorer(luceneQuery);
        SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span class='termHL'>", "</span>");
//        Highlighter highlighter = new Highlighter(formatter, scorer);
//        highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer, 390));
//        Analyzer analyzer =  new StandardAnalyzer(Version.LUCENE_29);
                
//    	for (DTO result : results) {
//    		highlightedDetailText = null;
//    		highlightedTitleText = null;
//    		link = null;
//    		if (result instanceof GenericContent) {
//    			GenericContent content = (GenericContent) result;
//    			if (CONST_FAQ.equals(content.getType()) && CONST_ACTIVE.equals(content.getStatus())) {
//    				sr = new SearchResult();
//    				try {
//    					highlightedTitleText = highlighter.getBestFragment(analyzer, "title", content.getTitle());
//    				} catch (Exception e) {
//    					log.warn("Failed to highlight title search text for Faq {0} due to: {1}", content.getId(), e.toString());
//    				}
//    				try {
//    					highlightedDetailText = highlighter.getBestFragment(analyzer, "detail", content.getDetail());
//    				} catch (Exception e) {
//    					log.warn("Failed to highlight detail search text for Faq {0} due to: {1}", content.getId(), e.toString());
//    				}
//    				
//    				link = new StringBuilder().append("<a href='").append(request.getContextPath()).append("/faq.jsf?contentType=FAQ&actionMethod=search.xhtml%3AcontentAction.view'>")
//    										  .append(highlightedTitleText != null ? highlightedTitleText : content.getTitle())
//    										  .append("</a>");
//    				sr.setTitle(link.toString());
//    				sr.setDetail(highlightedDetailText != null ? highlightedDetailText : content.getDetail());
//    				searchResults.add(sr);
//    			}
//    		} 
//	    }
        
        return searchResults;
	}

	public void setSearchPages(Integer totalResults) {
        searchPages = new ArrayList<SearchPage>();
		SearchPage searchPage = null;
       
		Double maxResPerPage = Double.parseDouble(getMaxResultsPerPage());
        Double pagesAsDouble = totalResults / maxResPerPage;
        
        Integer pagesAsInt = (int) Math.ceil(pagesAsDouble);
        
        if (pagesAsInt.equals(0)) {
        	pagesAsInt = pagesAsInt + 1;
        } 
        
        for (int i = 1; i <= pagesAsInt; i++) {
        	searchPage = new SearchPage();
        	searchPage.setNumber(i);
        	searchPage.setStartPage(setPageValue(i));
        	
        	if (i == currentPage()) {
        		searchPage.setCurrentPage(true);
        		setNextPage(determineNextPage(searchPage.getStartPage()));
            	setPreviousPage(determinePreviousPage(searchPage.getStartPage()));
            	
            	if (i == 1) {
        			setFirstPage(true);
        		}
        		
        		if (i == pagesAsInt) {
        			setLastPage(true);
        		}
        	}
        	
        	searchPages.add(searchPage);
		}
	}

	private Integer currentPage() {
		Integer resultsPerPage = getMaxResultsPerPage() != null ? Integer.parseInt(getMaxResultsPerPage()) : 1;
		return (getStartPage() / resultsPerPage) + 1;
	}
	
	private Integer setPageValue(int pageNumber) {
		Integer resultsPerPage = getMaxResultsPerPage() != null ? Integer.parseInt(getMaxResultsPerPage()) : 1;
		Integer page = (pageNumber - 1) * resultsPerPage;
		if (page == null) {
			page = 0;
		}
		return page;
	}
	
	private Integer determineNextPage(int pageNumber) {
		Integer resultsPerPage = getMaxResultsPerPage() != null ? Integer.parseInt(getMaxResultsPerPage()) : 0;
		return pageNumber + resultsPerPage;
	}
	
	private Integer determinePreviousPage(Integer pageNumber) {
		Integer resultsPerPage = getMaxResultsPerPage() != null ? Integer.parseInt(getMaxResultsPerPage()) : 0;
		Integer prevPage = pageNumber - resultsPerPage;
		if (prevPage < 0) {
			return 0;
		}
		return prevPage;
	}
	
	public Integer getNumberOfResultsOnPage() {
		Integer maxResultsAsInt = getMaxResultsPerPage() != null ? Integer.parseInt(getMaxResultsPerPage()) : 0;
		if (getTotalResults() < maxResultsAsInt) {
			numberOfResultsOnPage = getStartPage() + getTotalResults();
		} else {
			numberOfResultsOnPage = getStartPage() + maxResultsAsInt;
		}
		
		return numberOfResultsOnPage;
	}

	public Integer getFirstResultOnPage() {
		firstResultOnPage = getStartPage() + 1;
		return firstResultOnPage;
	}
	
	public String getSearchPattern() {
		return searchPattern;
	}

	public void setSearchPattern(String searchPattern) {
		this.searchPattern = searchPattern;
	}

	public String[] getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(String[] searchFields) {
		this.searchFields = searchFields;
	}

	public Float getDuration() {
		return duration;
	}

	public void setDuration(Float duration) {
		this.duration = duration;
	}

	public Integer getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}

	public Integer getStartPage() {
		if (startPage == null) {
			startPage = 0;
		}
		return startPage;
	}

	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}

	public String getMaxResultsPerPage() {
		return maxResultsPerPage;
	}

	public void setMaxResultsPerPage(String maxResultsPerPage) {
		this.maxResultsPerPage = maxResultsPerPage;
	}
	
	public List<SearchPage> getSearchPages() {
		return this.searchPages;
	}
	
	public boolean isFirstPage() {
		return firstPage;
	}

	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

	public Integer getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(Integer previousPage) {
		this.previousPage = previousPage;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}
}
