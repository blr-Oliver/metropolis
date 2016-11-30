CREATE USER 'metro-web-app'@'localhost' IDENTIFIED BY 'RalTirTalSol';
GRANT select, insert, update, delete, execute ON metropolis.* TO 'metro-web-app'@'localhost';
