
INSERT INTO User (first_name, last_name, password, user_name, home_phone_no, office_phone_no, fax_no) VALUES
  ('Aliko', 'Dangote','alidan98', 'ali_dan', '+919977448563','+919854673265','+9189765643'),
  ('Bill', 'Gates','gatesbill23', 'bill_gates','+1(416)474-3431', '+1(704)765-8978', '+1(647)786-3421'),
  ('Folrunsho', 'Alakija','folal@56', 'fol_ala', '675895746','658379287','76846352');
  
  
INSERT INTO Address (user_id, street_name, city, country, postal_code)
select id , '4 Clifton Hill', 'Brampton','Canada','L6H9K3'
from User where user_name='ali_dan';


INSERT INTO Address (user_id, street_name, city, country, postal_code)
select id , '6 Clyde Road', 'Missisauga','Canada','L4T0X8'
from User where user_name='bill_gates';

INSERT INTO Address (user_id, street_name, city, country, postal_code)
select id , '24 Watline Avenue', 'Brampton','Canada','L5V4C2'
from User where user_name='bill_gates';


INSERT INTO Address (user_id, street_name, city, country, postal_code)
select id , '28 Churchill Avenue', 'Malton','Canada','L3P2M1'
from User where user_name='fol_ala';