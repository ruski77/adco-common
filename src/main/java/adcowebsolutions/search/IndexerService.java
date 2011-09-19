package adcowebsolutions.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public class IndexerService {
	
	private Logger log = LoggerFactory.getLogger(IndexerService.class);
	
	//private FullTextEntityManager em;

	//@SuppressWarnings("unchecked")
	//@Create
	@Transactional
   	public void index() {
//		//em.purgeAll(GenericContent.class);
//		List<Object> entries = em.createQuery("select g from GenericContent g").getResultList();
//		for (Object o : entries) {
//			em.index(o);
//		}
		log.debug("Successfully indexed GenericContent.class.");
	}

	//@Remove
	//@Destroy
	public void stop() {}

}
