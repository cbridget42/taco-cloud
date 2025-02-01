CREATE SCHEMA IF NOT EXISTS taco_cloud;

CREATE TABLE IF NOT EXISTS taco_cloud.taco_order (
    id serial unique,
    delivery_name varchar(50) not null,
    delivery_street varchar(50) not null,
    delivery_city varchar(50) not null,
    delivery_state varchar(2) not null,
    delivery_zip varchar(10) not null,
    cc_number varchar(16) not null,
    cc_expiration varchar(5) not null,
    cc_cvv varchar(3) not null,
    placed_at timestamp not null,
    taco_user bigint not null
);

CREATE TABLE IF NOT EXISTS taco_cloud.taco (
    id serial unique,
    name varchar(50) not null,
    taco_order bigint not null,
    taco_order_key bigint not null,
    created_at timestamp not null
);

CREATE TABLE IF NOT EXISTS taco_cloud.ingredient_ref (
    ingredient varchar(4) not null,
    taco bigint not null
);

CREATE TABLE IF NOT EXISTS taco_cloud.ingredient (
    id varchar(4) PRIMARY KEY NOT NULL,
    name varchar(25) not null,
    type varchar(10) not null
);

CREATE TABLE IF NOT EXISTS taco_cloud.taco_user (
    id serial unique,
    username varchar(50) not null,
    password varchar(150) not null,
    full_name varchar(50) not null,
    street varchar(30) not null,
    city varchar(30) not null,
    state varchar(2) not null,
    zip varchar(10) not null,
    phone varchar(20) not null
);

ALTER TABLE taco_cloud.taco ADD FOREIGN KEY (taco_order) REFERENCES taco_cloud.taco_order(id) ON DELETE CASCADE;
ALTER TABLE taco_cloud.ingredient_ref ADD FOREIGN KEY (ingredient) REFERENCES taco_cloud.ingredient(id);
ALTER TABLE taco_cloud.taco_order ADD FOREIGN KEY (taco_user) REFERENCES taco_cloud.taco_user(id);