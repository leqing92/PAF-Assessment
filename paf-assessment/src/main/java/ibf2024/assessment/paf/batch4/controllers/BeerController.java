package ibf2024.assessment.paf.batch4.controllers;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ibf2024.assessment.paf.batch4.models.Beer;
import ibf2024.assessment.paf.batch4.models.Brewery;
import ibf2024.assessment.paf.batch4.models.Order;
import ibf2024.assessment.paf.batch4.models.PurchaseOrder;
import ibf2024.assessment.paf.batch4.models.Style;
import ibf2024.assessment.paf.batch4.services.BeerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class BeerController {
	@Autowired
	BeerService beerSvc;
	//TODO Task 2 - view 0
	@GetMapping(path = {"/beers", "/", "/index.html"})
	public String getStyles(Model model) {
		List<Style> styles = beerSvc.getStyles();
		model.addAttribute("styles", styles);

		return "view0";
	}
	
	
	//TODO Task 3 - view 1
	@GetMapping("/beer/style/{id}")
	public String getMethodName(@PathVariable ("id") String id, 
							@RequestParam (value = "styleName", defaultValue = "") String name,
							Model model) {
		List<Beer> beers = beerSvc.getBreweriesByBeer(id);

		model.addAttribute("styleName", name);
		model.addAttribute("beers", beers);

		return "view1";
	}

	//TODO Task 4 - view 2
	@GetMapping("/brewery/{id}")
	public String getMethodName(@PathVariable ("id") String id, Model model) {
		Boolean notFound = false;
		Optional<Brewery> opt = beerSvc.getBeersFromBrewery(id);
		// System.out.println(opt.toString());
		if(opt.isEmpty()){
			notFound = true;
		}
		else{
			Brewery brewery = opt.get();
			model.addAttribute("brewery", brewery);
		}
		model.addAttribute("notFound", notFound);
		model.addAttribute("order", new PurchaseOrder());
		return "view2";
	}
	
	// 
	//TODO Task 5 - view 2, place order
	@PostMapping("/brewery/{id}/order")
	public String postMethodName(@PathVariable("id") String id,
								@RequestBody MultiValueMap <String, String> payload, 
								Model model) {
		// System.out.println("pathvariable:" + id);
		PurchaseOrder purchaseOrder = new PurchaseOrder();

		// purchaseOrder.setOrderId(UUID.randomUUID().toString().substring(0, 8));
		purchaseOrder.setOrderDate(LocalDateTime.now());
		purchaseOrder.setBreweryId(Integer.parseInt(id));
		
		List<Order> orders = new LinkedList<>();
		List<String> beerId = payload.get("beerId");
		List<String> quantities = payload.get("quantity");
		for(int i = 0; i < beerId.size(); i++){
			Order order = new Order();
			order.setBeerId(Integer.parseInt(beerId.get(i)));
			if("".equals(quantities.get(i))){
				order.setQuantity(0);
			}else{
				order.setQuantity(Integer.parseInt(quantities.get(i)));
				orders.add(order);
			}
			
		}
		purchaseOrder.setOrders(orders);

		String orderId = beerSvc.placeOrder(purchaseOrder);
		model.addAttribute("orderId", orderId);
		// System.out.println(purchaseOrder.toString());
		// System.out.println(purchaseOrder.toJSON().toString());
		
		return "view3";
	}
	


}
