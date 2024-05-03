package ibf2024.assessment.paf.batch4.repositories;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf2024.assessment.paf.batch4.models.Beer;
import ibf2024.assessment.paf.batch4.models.Brewery;
import ibf2024.assessment.paf.batch4.models.Style;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

@Repository
public class BeerRepository {
	@Autowired
	JdbcTemplate jdbcTemp;
	// DO NOT CHANGE THE SIGNATURE OF THIS METHOD
	// TODO: Task 2
	public List<Style> getStyles() {
		List<Style> styles = new LinkedList<>();
		SqlRowSet rs = jdbcTemp.queryForRowSet(Queries.GET_ALL_STYLES);

		while(rs.next()){
			Style style = new Style();
			style.setStyleId(rs.getInt("id"));			
			style.setName(rs.getString("style"));
			style.setBeerCount(rs.getInt("beer_count"));

			styles.add(style);
		}
		// for(Style s : styles){
		// 	System.out.println(s.toString());
		// }

		return styles;
	}
		
	// DO NOT CHANGE THE METHOD'S NAME OR THE RETURN TYPE OF THIS 
	// TODO: Task 3
	public List<Beer> getBreweriesByBeer(String id) {
		List<Beer> beers = new LinkedList<>();
		SqlRowSet rs = jdbcTemp.queryForRowSet(Queries.GET_BEERS_BY_STYLE_ID, Integer.parseInt(id));
		while(rs.next()){
			Beer beer = new Beer();
			beer.setBeerId(rs.getInt("beerId"));
			beer.setBeerName(rs.getString("beerName"));
			beer.setBeerDescription(rs.getString("beerDescription"));
			beer.setBreweryId(rs.getInt("breweryId"));
			beer.setBreweryName(rs.getString("breweryName"));

			beers.add(beer);
		}
		return beers;
	}

	// DO NOT CHANGE THE METHOD'S NAME OR THE RETURN TYPE OF THIS METHOD
	// TODO: Task 4
	public Optional<Brewery> getBeersFromBrewery(String id) {
		
		SqlRowSet rs = jdbcTemp.queryForRowSet(Queries.GET_BREWERY_BY_ID, Integer.parseInt(id));

		if(!isBreweryExist(id)){
			return Optional.empty();
		}else{
			rs.next();
			Brewery brewery = new Brewery();
			brewery.setBreweryId(rs.getInt("breweryId"));
			brewery.setName(rs.getString("name"));
			brewery.setAddress1(rs.getString("address1"));
			brewery.setAddress2(rs.getString("address2"));
			brewery.setCity(rs.getString("city"));
			brewery.setPhone(rs.getString("phone"));
			brewery.setWebsite(rs.getString("website"));
			brewery.setDescription(rs.getString("description"));
			
			List<Beer> beers = new LinkedList<>();
			String beersString = rs.getString("beers");
			JsonArray jArray = Json.createReader(new StringReader(beersString)).readArray();
			for(JsonValue jVal : jArray){
				JsonObject jObject = jVal.asJsonObject();
				Beer beer = new Beer();
				beer.setBeerId(jObject.getInt("id"));
				beer.setBeerName(jObject.getString("name"));
				beer.setBeerDescription(jObject.getString("desciption"));

				beers.add(beer);
			}
			brewery.setBeers(beers);

			return Optional.of(brewery);
		}		
	}

	public Boolean isBreweryExist (String id){
		SqlRowSet rs = jdbcTemp.queryForRowSet(Queries.COUNT_BREWERY_BY_ID, Integer.parseInt(id));
		if(rs.next()){
			int x = rs.getInt("count");
			if(x > 0){
				return true;
			}
		}
		return false;
	}
}
