truncate category_attribute;
alter table category_attribute add accumulated int;
insert into category_attribute select id_category, id_attribute, count(*) as relevance, null as accumulated from shop join shop_attribute on shop.id = shop_attribute.id_shop group by id_category, id_attribute;
insert into category_attribute select c.id as id_category, a.id as id_attribute, 0 as relevance, null as accumulated from category c, attribute a where not exists(select * from category_attribute where id_category = c.id and id_attribute = a.id);
update category_attribute ca set accumulated = -1 where not exists (select * from category where id_parent = ca.id_category);

update category_attribute ct
left join (select category.id_parent, id_attribute, sum(relevance) as relevance from
	category join category_attribute on category.id = category_attribute.id_category
    where id_parent is not null and accumulated = -1
    group by id_parent, id_attribute
    having sum(relevance) > 0) d on
    ct.id_category = d.id_parent and ct.id_attribute = d.id_attribute
set
    accumulated = d.relevance;
update category_attribute set accumulated = null where accumulated = -1;
update category_attribute set relevance = relevance + accumulated, accumulated = -1 where accumulated > 0;

delete from category_attribute where relevance = 0;
alter table category_attribute drop accumulated;
