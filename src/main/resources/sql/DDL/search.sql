select t.id, sum(t.score) as score from(
    select shop.id as id, 9 as score from shop inner join category on shop.id_category = category.id where category.id = ?4
    union all
    select data.id, case when data.term = m.match then data.exact_match else data.partial_match end as score
    from (select shop.id as id, category.display_name as term, 3 as exact_match, 2 as partial_match
        from shop
        join category on shop.id_category = category.id
        union all select shop.id, shop.display_name, 6, 4 from shop)data
    join (select ?1 as match
        union select ?2
        union select ?3
    ) m on data.term like '%' || m.match || '%'
) t group by t.id order by sum(t.score) desc