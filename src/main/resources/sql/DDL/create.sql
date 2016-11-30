CREATE TABLE resource (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name nvarchar(255) NOT NULL,
  mime varchar(127) NOT NULL,
  data mediumblob NOT NULL,
  version int NOT NULL DEFAULT '1',
  UNIQUE KEY name (name)
);
CREATE TRIGGER resource_upd_version BEFORE UPDATE ON resource
    FOR EACH ROW set new.version = old.version + 1;

CREATE TABLE category (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  display_name nvarchar(255) NOT NULL,
  scope_display_name nvarchar(255) NOT NULL,
  element_display_name nvarchar(255) NOT NULL,
  id_parent int DEFAULT NULL,
  FOREIGN KEY (id_parent) REFERENCES category (id)
);
CREATE TABLE attribute (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  display_name nvarchar(255) NOT NULL,
  type tinyint NOT NULL DEFAULT '0'
);
CREATE TABLE attribute_value (
  id int NOT NULL AUTO_INCREMENT,
  display_name nvarchar(255) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE attribute_choice (
  id_attribute int NOT NULL,
  id_value int NOT NULL,
  ordinal int NOT NULL,
  PRIMARY KEY (id_attribute,id_value),
  UNIQUE KEY id_attribute (id_attribute, id_value, ordinal),
  FOREIGN KEY (id_attribute) REFERENCES attribute (id),
  FOREIGN KEY (id_value) REFERENCES attribute_value (id)
);
CREATE TABLE tag (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  display_name nvarchar(255) NOT NULL
);
CREATE TABLE brand (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  display_name nvarchar(255) NOT NULL
);
CREATE TABLE time_table (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY
);
CREATE TABLE timing_group (
  id_time int NOT NULL,
  length tinyint NOT NULL,
  begin varchar(7),
  end varchar(7),
  ordinal tinyint NOT NULL,
  PRIMARY KEY (id_time, ordinal),
  FOREIGN KEY (id_time) REFERENCES time_table(id)
);
CREATE TABLE category_attribute (
  id_category int NOT NULL,
  id_attribute int NOT NULL,
  relevance int NOT NULL DEFAULT '1',
  PRIMARY KEY (id_category, id_attribute),
  FOREIGN KEY (id_category) REFERENCES category (id) ON DELETE CASCADE,
  FOREIGN KEY (id_attribute) REFERENCES attribute (id) ON DELETE CASCADE
);
CREATE TABLE category_tag (
  id_category int NOT NULL,
  id_tag int NOT NULL,
  relevance int NOT NULL DEFAULT '1',
  PRIMARY KEY (id_category, id_tag),
  FOREIGN KEY (id_category) REFERENCES category (id) ON DELETE CASCADE,
  FOREIGN KEY (id_tag) REFERENCES tag (id) ON DELETE CASCADE
);
CREATE TABLE category_brand (
  id_category int NOT NULL,
  id_brand int NOT NULL,
  relevance int NOT NULL DEFAULT '1',
  PRIMARY KEY (id_category, id_brand),
  FOREIGN KEY (id_category) REFERENCES category (id) ON DELETE CASCADE,
  FOREIGN KEY (id_brand) REFERENCES brand (id) ON DELETE CASCADE
);

CREATE TABLE shop (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  id_category int NOT NULL,
  display_name nvarchar(256) NOT NULL,
  description nvarchar(1024) DEFAULT NULL,
  id_time int,
  user_id int NOT NULL,
  title nvarchar(128),
  full_name nvarchar(512) NOT NULL,
  slogan nvarchar(512),
  short_desc nvarchar(512),
  web nvarchar(128),
  room nvarchar(64),
  owner_name nvarchar(128),
  highlights nvarchar(256),
  FOREIGN KEY (id_category) REFERENCES category (id),
  FOREIGN KEY (id_time) REFERENCES time_table (id)
);
CREATE TABLE shop_tag (
  id_shop int NOT NULL,
  id_tag int NOT NULL,
  PRIMARY KEY (id_shop,id_tag),
  FOREIGN KEY (id_shop) REFERENCES shop (id) ON DELETE CASCADE,
  FOREIGN KEY (id_tag) REFERENCES tag (id) ON DELETE CASCADE
);
CREATE TABLE shop_brand (
  id_shop int NOT NULL,
  id_brand int NOT NULL,
  PRIMARY KEY (id_shop,id_brand),
  FOREIGN KEY (id_shop) REFERENCES shop (id) ON DELETE CASCADE,
  FOREIGN KEY (id_brand) REFERENCES brand (id) ON DELETE CASCADE
);
CREATE TABLE shop_attribute (
  id_shop int NOT NULL,
  id_attribute int NOT NULL,
  id_val int DEFAULT NULL,
  UNIQUE KEY id_shop (id_shop,id_attribute,id_val),
  FOREIGN KEY (id_shop) REFERENCES shop (id) ON DELETE CASCADE,
  FOREIGN KEY (id_attribute) REFERENCES attribute (id) ON DELETE CASCADE,
  FOREIGN KEY (id_val) REFERENCES attribute_value (id)
);
CREATE TABLE contact (
  id_shop int NOT NULL,
  type nvarchar(127) NOT NULL,
  value nvarchar(511) NOT NULL,
  FOREIGN KEY (id_shop) REFERENCES shop (id) ON DELETE CASCADE
);

CREATE TABLE location(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	display_name nvarchar(255) NOT NULL,
	address nvarchar(511),
	id_time int REFERENCES time_table(id)
);
CREATE TABLE sub_location(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_location int NOT NULL REFERENCES location(id),
	display_name nvarchar(255) NOT NULL,
	view_box varchar(63) NOT NULL,
	basis varchar(4095),
	font_size smallint NOT NULL DEFAULT '12' 
);
CREATE TABLE area(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_sub_location int NOT NULL REFERENCES sub_location(id),
	id_shop int REFERENCES shop(id),
	path varchar(4095) NOT NULL,
    type varchar(63),
	display_name nvarchar(255),
	x int NOT NULL DEFAULT 0,
	y int NOT NULL DEFAULT 0,
	width int UNSIGNED NOT NULL DEFAULT 50,
	height int UNSIGNED NOT NULL DEFAULT 50,
	labelX int,
	labelY int
);