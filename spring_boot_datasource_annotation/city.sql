drop table if exists city;
CREATE TABLE city (
  id    integer(20),
  city_code   varchar(64),
  city         varchar(50),
  city_name     varchar(50),
  province_id   varchar(12),
  province      varchar(50),
  description   varchar(100),
  parent_id      integer(20),
  level         int(2),
  is_enable      int(1),
  is_delete     int(1),
  PRIMARY KEY (id),
  unique key (city_code),
  key (city,province, level)
);


SELECT * FROM city where city_name = '北京市';