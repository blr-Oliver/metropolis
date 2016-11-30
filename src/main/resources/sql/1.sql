select s.id, sum(score.score) / 2 score from shop s join
(
    select sa.id_shop, sa.id_attribute, sum(case when sa.id_val is null and mtch.id_val is not null then 1.0/mtch.n else 1.0 end) score from shop_attribute sa join
		(select 4 as id_attribute, 1 as id_val, 3 as n
		union all select 4, 2, 3
        union all select 1, null, 2) mtch
	on
		sa.id_attribute = mtch.id_attribute and
		not(coalesce(sa.id_val != mtch.id_val, false))
	group by sa.id_shop, sa.id_attribute
) score
on
	s.id = score.id_shop
group by s.id
having count(distinct score.id_attribute) = 2
order by score desc;