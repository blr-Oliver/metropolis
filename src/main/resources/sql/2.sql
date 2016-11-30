select
	s.id,
	case when id_category = 33 then 1.0 when id_category in (28) then 0.8 else null end c_score,
    case when t.score > 0 then t.score else null end t_score,
    case when a.score > 0 then a.score else null end a_score
from
	shop s
    join (
		select id_shop id, sum(case when id_tag in (112, 164, 169) then 1 else 0 end) / 3.0 score from shop_tag t group by id_shop
    ) t on s.id = t.id
    join (
		select id_shop id, sum(
			case when id_attribute in (0) then 1.0 / (id_attribute % 10) else 0.0 end / 1 +
			case when id_attribute in (43) and (id_val in (431, 432) or id_val is null) then 1.0 / substring('0002', id_attribute / 10, 1) else 0.0 end
		) / 2 score from shop_attribute
		group by id_shop
    ) a on s.id = a.id
where case when id_category = 33 then 1.0 when id_category in (28) then 0.8 else 0.0 end + t.score + a.score > 0;