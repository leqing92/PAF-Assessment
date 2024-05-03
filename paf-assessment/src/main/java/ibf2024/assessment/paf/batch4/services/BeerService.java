package ibf2024.assessment.paf.batch4.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2024.assessment.paf.batch4.models.Beer;
import ibf2024.assessment.paf.batch4.models.Brewery;
import ibf2024.assessment.paf.batch4.models.PurchaseOrder;
import ibf2024.assessment.paf.batch4.models.Style;
import ibf2024.assessment.paf.batch4.repositories.BeerRepository;
import ibf2024.assessment.paf.batch4.repositories.OrderRepository;

@Service
public class BeerService {

	@Autowired
	BeerRepository beerRepo;

	@Autowired
	OrderRepository orderRepo;

	// DO NOT CHANGE THE METHOD'S NAME OR THE RETURN TYPE OF THIS METHOD
	// TODO: Task 5 
	public String placeOrder(PurchaseOrder po) {
		po.setOrderId(UUID.randomUUID().toString().substring(0, 8));
		orderRepo.insertPO(po);

		return po.getOrderId();
	}

	public List<Style> getStyles() {
		return beerRepo.getStyles();
	}

	public List<Beer> getBreweriesByBeer(String id) {
		return beerRepo.getBreweriesByBeer(id);
	}

	public Optional<Brewery> getBeersFromBrewery(String id) {
		return beerRepo.getBeersFromBrewery(id);
	}
}
