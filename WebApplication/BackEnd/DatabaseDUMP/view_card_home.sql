create view cards_home as
    select e.poster,e."position",e."date" ,e."time",e.title ,p.username  
    from "event" e  inner join person p on e.organizer=p.id  