package ibf2024.assessment.paf.batch4.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import ibf2024.assessment.paf.batch4.models.PurchaseOrder;

@Repository
public class OrderRepository {

	@Autowired
	MongoTemplate mongoTemp;
	// TODO: Task 5
	public void insertPO(PurchaseOrder po){
		mongoTemp.save(po, "orders");
	}
}
