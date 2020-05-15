create or replace view auction_view as
    select f.id as propertyId, f.userid, ur.roles_id, r."type", a.street, a.homenumber, a.localnumber, a.city, a.postcode, f.price, f."size", f.rooms, f.floor
    from address a, flat f, realassets r, user_roles ur
    where f.addressid = a.id and a.realassetid =r.id and f.userid = ur.user_id and ur.roles_id = 2
    union
    select p.id as propertyId, p.userid, ur.roles_id, r."type", a.street, a.homenumber, a.localnumber, a.city, a.postcode, p.price, p."size", null as rooms, null as floor
    from address a, plot p, realassets r, user_roles ur
    where p.addressid = a.id and a.realassetid =r.id and p.userid = ur.user_id and ur.roles_id = 2
    union
    select  h.id as propertyId, h.userid, ur.roles_id, r."type", a.street, a.homenumber, a.localnumber, a.city, a.postcode, h.price, h."size", h.rooms, null as floor
    from address a, house h, realassets r, user_roles ur
    where h.addressid = a.id and a.realassetid =r.id and h.userid = ur.user_id and ur.roles_id = 2