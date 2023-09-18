DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS meal;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS restaurant;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name       VARCHAR                           NOT NULL,
    email      VARCHAR                           NOT NULL,
    password   VARCHAR                           NOT NULL,
    registered TIMESTAMP           DEFAULT now() NOT NULL
);

CREATE TABLE restaurant
(
    id       INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name     VARCHAR NOT NULL,
    admin_id INTEGER NOT NULL,
    FOREIGN KEY (admin_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE meal
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name          VARCHAR NOT NULL,
    price         INTEGER NOT NULL,
    restaurant_id INTEGER NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE TABLE user_role
(
    user_id INTEGER NOT NULL,
    role    VARCHAR NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);