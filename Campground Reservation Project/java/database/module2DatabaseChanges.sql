

SELECT * FROM site s join campground c on s.campground_id = c.campground_id WHERE s.site_id = ?;

select * from park;
select * from reservation;
select count(*), campground_id from site group by campground_id order by campground_id;
select campground.name from campground group by park_id, campground.name order by park_id;.

SELECT site_id FROM site where  site_number = ?

SELECT site_id FROM site s join campground c on s.campground_id = c.campground_id WHERE s.site_number = ?

SELECT site_number FROM site s join campground c on s.campground_id = c.campground_id WHERE s.site_id = ?

select site_id from site where campground_id = site.campground_id where site_id not in(
select site_id from reservation where(( to_date between arrivalDate::date and departureDate::date) 
or (from_date between arrivalDate::date and departureDate::date)) group by site_id) limit 5;

select * from reservation order by reservation_id desc;
select count(*) from campground;
select count(*) from campground where park_id = 1;
select count(*) from campground where park_id = 2;
select count(*) from campground where park_id = 3;
select count(*) from campground where park_id = 4;
select * from reservation where name = 'bgbggg';
