drop table if exists oauth_token;
drop table if exists account;
drop table if exists user;
drop table if exists oauth2;

create table oauth2 (
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name varchar(30) NOT NULL UNIQUE,
	authorize_url varchar(500) NOT NULL,
	token_url varchar(500) NOT NULL,
	client_id varchar(128) NOT NULL,
	client_secret varchar(128) NOT NULL,
	scope varchar(1000),
	query varchar(1000)
);

CREATE TABLE user (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(250) NOT NULL,
  email varchar(250),
  enabled tinyint(1) NOT NULL DEFAULT '1',
  locked tinyint(1) NOT NULL DEFAULT '0',
  INDEX idx_email (email) USING HASH
);

CREATE TABLE account (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  id_user int NOT NULL REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
  id_auth int NOT NULL REFERENCES oauth2(id) ON DELETE CASCADE ON UPDATE CASCADE,
  client_id varchar(128) NOT NULL,
  password varchar(256),
  UNIQUE KEY u_user_auth (id_user, id_auth),
  UNIQUE KEY u_auth_login (id_auth, client_id)
);

DELIMITER ;;
CREATE TRIGGER account_checks BEFORE INSERT ON account
for each row BEGIN
    if new.id_auth = 0 then
		if new.password is null then
        	SIGNAL SQLSTATE '23000' SET MESSAGE_TEXT = 'Column \'password\' cannot be null for native authorization';
        end if;
        set new.client_id = CONCAT('id', new.id_user);
    else
        set new.password = null;
    end if;
END ;;
DELIMITER ;

CREATE TABLE oauth_token (
	id_account int NOT NULL PRIMARY KEY,
	access_token varchar(1024),
	refresh_token varchar(1024),
	expires_at datetime,
	FOREIGN KEY account_token(id_account) REFERENCES account(id) ON DELETE CASCADE ON UPDATE CASCADE
);

insert into oauth2(id, name, authorize_url, token_url, client_id, client_secret, scope, query) values(0,'native','','','','',NULL,NULL);
insert into oauth2(name, authorize_url, token_url, client_id, client_secret, scope, query) values('vk','https://oauth.vk.com/authorize','https://oauth.vk.com/access_token','4884707','g4AnVKJOrsmEaL3tO3JO','email','v=5.37&display=page');
insert into oauth2(name, authorize_url, token_url, client_id, client_secret, scope, query) values('fb','https://www.facebook.com/dialog/oauth','https://graph.facebook.com/v2.3/oauth/access_token','461167114049635','f87c1ae90a74f490b53725eaa3bf28f1',NULL,NULL);