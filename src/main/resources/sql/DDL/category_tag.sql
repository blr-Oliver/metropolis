truncate category_tag;
alter table category_tag add accumulated int;
insert into category_tag select id_category, id_tag, count(*) as relevance, null as accumulated from shop join shop_tag on shop.id = shop_tag.id_shop group by id_category, id_tag;
insert into category_tag select c.id as id_category, t.id as id_tag, 0 as relevance, null as accumulated from category c, tag t where not exists(select * from category_tag where id_category = c.id and id_tag = t.id);
update category_tag ct set accumulated = -1 where not exists (select * from category where id_parent = ct.id_category);

update category_tag ct
left join (select category.id_parent, id_tag, sum(relevance) as relevance from
	category join category_tag on category.id = category_tag.id_category
    where id_parent is not null and accumulated = -1
    group by id_parent, id_tag
    having sum(relevance) > 0) d on
    ct.id_category = d.id_parent and ct.id_tag = d.id_tag
set
    accumulated = d.relevance;
update category_tag set accumulated = null where accumulated = -1;
update category_tag set relevance = relevance + accumulated, accumulated = -1 where accumulated > 0;

delete from category_tag where relevance = 0;
alter table category_tag drop accumulated;
