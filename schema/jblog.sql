desc user;
desc blog;
desc category;
desc post;

select * from user;
select * from blog;
select * from category;
select * from post;

delete from user;
delete from blog;
delete from category;
delete from post;

insert into user value('sjg3234','서정권',password('1234'));
select name, id, password
		  	  from user
			 where id='sjg3234'
		  	   and password=password('1234');

insert into blog value('sjg3234의 jblog', '/assets/images/spring-logo.jpg', 'sjg3234');

select title, image
		  	  from blog
			 where blog_id='sjg3234';