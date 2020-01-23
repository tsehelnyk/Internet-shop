CREATE SCHEMA test DEFAULT CHARACTER SET utf8 ;

CREATE TABLE test.items (
                                         `id` INT NOT NULL AUTO_INCREMENT,
                                         `name` VARCHAR(225) NOT NULL,
                                         `price` DECIMAL(6,2) NOT NULL,
                                         PRIMARY KEY (`id`));

INSERT INTO test.items (`name`, `price`) VALUES ('name', 100);

SELECT * FROM test.items WHERE `id` = 1;

SELECT * FROM test.items

UPDATE test.items SET `name` = 'name' , `price` = 100 WHERE `id` = 1;

DELETE FROM test.items WHERE `id` = 1;
