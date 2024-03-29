
delete from user;
delete from blog;
delete from category;
delete from post;

desc user;
desc blog;
desc category;
desc post;

select * from user;
select * from blog;
select * from category;
select * from post;

select count(b.no) as postCount, a.no, a.name, a.description from category a 
LEFT JOIN post b ON a.no = b.category_no
where a.blog_id = 'sjg3234'
group by a.no;

select a.title, a.contents, a.category_no, b.name, b.blog_id from post a, category b
where a.category_no = b.no
and blog_id = (select a.blog_id from category a where a.no = 19)
and a.category_no = 19;

select a.title, a.contents
					  from post a
					  where a.no = 8;


select a.category_no, a.title, a.contents
from post a
where a.category_no = 19 order by a.no asc limit 1;
(select min(b.no) 
from category b
where b.blog_id = 'sjg3234'
and exists(select 1 from post c where c.category_no = b.no))
order by a.no asc limit 1;

select b.no
from category b
where b.blog_id = 'sjg3234'
and exists(select 1 from post c where c.category_no = b.no);

select a.title, a.contents
						  from post a
						 where a.no = 5;
                         
select MIN(a.no)
from post b
where exist(b.category_no = (select MIN(a.no) from category a where blog_id = 'sjg3234');




select a.no from category a where blog_id = 'sjg3234'
order by a.no asc limit 1;

select a.blog_id from category a where a.category_no = 20;



insert into post value (null, '12345a글쓰기12345', 'aaabcde', 12);

insert into user value('sjg3234','서정권',password('1234'));
select name, id, password
		  	  from user
			 where id='sjg3234'
		  	   and password=password('1234');

insert into blog value(concat('sjg3234','의 jblog'), '/assets/images/spring-logo.jpg', 'sjg3234');

insert into category value( null, '미분류', '카테고리를 정하지 않음', 'sjg3234');

select no from category where blog_id = 'sjg3234';

select count(b.no) as postCount, a.no, a.name, a.description from category a, post b
where a.no = b.category_no
and a.blog_id = 'sjg3234'
group by a.no;

select title, image, blog_id as blogId
		  	  from blog
			 where blog_id='sjg3234';
             
 delete a, b from category a join post b on a.no = b.category_no where a.no = 12 and a.blog_id = 'sjg3234';


             update blog
			   set title = 'sjg3234',
				   image = '/assets/images/spring-logo.jpg'
			 where blog_id='sjg3234';