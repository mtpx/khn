CREATE OR REPLACE VIEW public.property_view
AS SELECT f.id AS propertyid,
    f.userid,
    u.email,
    r.type,
    a.street,
    a.homenumber,
    a.localnumber,
    a.city,
    a.postcode,
    f.price,
    f.size,
    f.rooms,
    f.floor,
    f.sold
   FROM address a,
    flat f,
    realassets r,
    users u
  WHERE f.addressid = a.id AND a.realassetid = r.id AND f.userid = u.id
UNION
 SELECT p.id AS propertyid,
    p.userid,
    u.email,
    r.type,
    a.street,
    a.homenumber,
    a.localnumber,
    a.city,
    a.postcode,
    p.price,
    p.size,
    NULL::integer AS rooms,
    NULL::integer AS floor,
    p.sold
   FROM address a,
    plot p,
    realassets r,
    users u
  WHERE p.addressid = a.id AND a.realassetid = r.id AND p.userid = u.id
UNION
 SELECT h.id AS propertyid,
    h.userid,
    u.email,
    r.type,
    a.street,
    a.homenumber,
    a.localnumber,
    a.city,
    a.postcode,
    h.price,
    h.size,
    h.rooms,
    NULL::integer AS floor,
    h.sold
   FROM address a,
    house h,
    realassets r,
    users u
  WHERE h.addressid = a.id AND a.realassetid = r.id AND h.userid = u.id;

-- Permissions

ALTER TABLE public.property_view OWNER TO postgres;
GRANT ALL ON TABLE public.property_view TO postgres;
