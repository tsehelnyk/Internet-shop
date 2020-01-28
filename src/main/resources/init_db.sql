CREATE SCHEMA test DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `items` (
                         `item_id` int(11) NOT NULL AUTO_INCREMENT,
                         `name` varchar(45) NOT NULL,
                         `price` double NOT NULL,
                         PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

INSERT INTO test.items (name, price) VALUES ('name', 100);

SELECT * FROM test.items WHERE item_id = 1;

SELECT * FROM test.items;

UPDATE test.items SET name = 'name' , price = 100 WHERE item_id = 1;

DELETE FROM test.items WHERE item_id = 1;



CREATE TABLE `buckets` (
                            `bucket_id` int(11) NOT NULL AUTO_INCREMENT,
                            `user_id` int(11) NOT NULL,
                            PRIMARY KEY (`bucket_id`),
                            KEY `buckets_users_fk_idx` (`user_id`),
                            CONSTRAINT `buckets_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `buckets_items` (
                            `id` int(11) NOT NULL,
                            `bucket_id` int(11) NOT NULL,
                             `item_id` int(11) NOT NULL,
                             PRIMARY KEY (`id`),
                             KEY `buckets_items_items_fk_idx` (`item_id`),
                             KEY `buckets_items_buckets_fk_idx` (`bucket_id`),
                             CONSTRAINT `buckets_items_buckets_fk` FOREIGN KEY (`bucket_id`) REFERENCES `buckets` (`bucket_id`) ON DELETE CASCADE ON UPDATE CASCADE,
                             CONSTRAINT `buckets_items_items_fk` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO test.buckets (user_id) VALUES (1);

SELECT b.user_id, b.bucket_id, i.item_id, i.name, i.price
FROM buckets b
         JOIN buckets_items bi ON bi.bucket_id = b.bucket_id
         JOIN items i on bi.item_id = i.item_id AND b.user_id = 2;

SELECT * FROM buckets;

DELETE FROM test.buckets_items WHERE bucket_id = 1;
INSERT INTO test.buckets_items (bucket_id, item_id) VALUES (1, 1);

DELETE FROM test.buckets WHERE bucket_id = 1;



CREATE TABLE `orders` (
                          `order_id` int(11) NOT NULL AUTO_INCREMENT,
                          `user_id` int(11) NOT NULL,
                          PRIMARY KEY (`order_id`),
                          KEY `orders_users_fk_idx` (`user_id`),
                          CONSTRAINT `orders_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `orders_items` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `order_id` int(11) NOT NULL,
                                `item_id` int(11) NOT NULL,
                                PRIMARY KEY (`id`),
                                KEY `orders_items_orders_id_idx` (`order_id`),
                                KEY `orders_items_items_id_idx` (`item_id`),
                                CONSTRAINT `orders_items_items_id` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                                CONSTRAINT `orders_items_orders_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO test.orders (user_id) VALUES (1);

SELECT o.user_id, o.order_id, i.item_id, i.name, i.price
FROM orders o
         JOIN orders_items oi ON oi.order_id = o.order_id
         JOIN items i on oi.item_id = i.item_id AND o.order_id = 2;

SELECT * FROM test.orders;

DELETE FROM test.orders WHERE order_id = 1;
INSERT INTO test.orders_items (order_id, item_id) VALUES (1, 1);

DELETE FROM test.orders WHERE order_id = 1;



CREATE TABLE `users` (
                         `user_id` int(11) NOT NULL AUTO_INCREMENT,
                         `name` varchar(45) NOT NULL,
                         `login` varchar(45) NOT NULL,
                         `password` varchar(45) NOT NULL,
                         `token` varchar(45) NOT NULL,
                         PRIMARY KEY (`user_id`),
                         UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `users_roles` (
                               `id` int(11) NOT NULL,
                               `user_id` int(11) NOT NULL,
                               `user_role` int(4) NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `users_roles_roles_id_idx` (`user_role`),
                               KEY `users_roles_users_fk_idx` (`user_id`),
                               CONSTRAINT `users_roles_roles_fk` FOREIGN KEY (`user_role`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                               CONSTRAINT `users_roles_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `roles` (
                         `id` int(4) NOT NULL,
                         `role` varchar(45) NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SELECT * FROM test.users;

SELECT r.role
FROM test.users_roles ur
         JOIN roles r ON (ur.user_role = r.id) AND ur.user_id = 1;

INSERT INTO test.users_roles (user_id, user_role) VALUES (?, ?);

SELECT r.id, r.role
                        FROM test.users_roles ur
                        JOIN test.roles r ON ur.user_role = r.id AND ur.user_id = 1;

SELECT id FROM test.roles WHERE role = 'USER';

SELECT u.name, u.login, u.password, u.token, u.user_id, r.role, r.id
FROM users_roles ur
         JOIN roles r ON (ur.user_role = r.id)
         JOIN users u ON (ur.user_id = u.user_id) AND ur.user_id = 1;

SELECT name, login, password, token, user_id
FROM test.users WHERE user_id = 1;

DELETE FROM test.users_roles WHERE user_id = 1;

UPDATE test.users
SET name = 'name', login = 'login', password = 'password', token = 'token'
WHERE user_id = 1;