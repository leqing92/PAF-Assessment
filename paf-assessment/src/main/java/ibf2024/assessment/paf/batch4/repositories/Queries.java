package ibf2024.assessment.paf.batch4.repositories;

public class Queries {
    public static final String GET_ALL_STYLES = 
    """
        select s.id as id, 
        s.style_name as style,
        count(b.id) as beer_count
        from styles as s
        left join beers as b
        on s.id = b.style_id
        group by s.id
        order by beer_count desc, style asc;        
    """;

    public static final String GET_BEERS_BY_STYLE_ID =
    """
        select b.id as beerId,
        b.name as beerName,
        b.descript as beerDescription,
        bs.id as breweryId,
        bs.name as breweryName
        from styles as s
        left join beers as b
        on s.id = b.style_id 
        join breweries as bs
        on bs.id = b.brewery_id
        where s.id = ?
        order by beerName asc;         
    """;

    public static final String GET_BREWERY_BY_ID = 
    """
        select breweryId, name, address1, address2, city, phone, website, description,
        JSON_ARRAYAGG(beer) as beers
        from (
        select
        bs.id as breweryId,
        bs.name as name,
        bs.address1 as address1,
        bs.address2 as address2,
        bs.city as city,
        bs.phone as phone,
        bs.website as website,
        bs.descript as description,
        JSON_OBJECT("id", b.id, "name",b.name, "desciption", b.descript) as beer	
        from breweries as bs
        left join beers as b
        on bs.id = b.brewery_id    
        where bs.id = ?) as agg;
    """;

    public static final String COUNT_BREWERY_BY_ID = 
    """
        select count(*) as count from breweries where id =?;
    """;
}
