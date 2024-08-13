CREATE SCHEMA IF NOT EXISTS taco_cloud;

CREATE TABLE IF NOT EXISTS taco_cloud.taco_order (
    id serial,
    delivery_name varchar(50) not null,
    delivery_street varchar(50) not null,
    delivery_city varchar(50) not null,
    delivery_state varchar(2) not null,
    delivery_zip varchar(10) not null,
    cc_number varchar(16) not null,
    cc_expiration varchar(5) not null,
    cc_cvv varchar(3) not null,
    placed_at timestamp not null
);

CREATE TABLE IF NOT EXISTS taco_cloud.taco (
    id serial,
    name varchar(50) not null,
    taco_order bigint not null,
    taco_order_key bigint not null,
    created_at timestamp not null
);

CREATE TABLE IF NOT EXISTS taco_cloud.ingredient_ref (
    ingredient varchar(4) not null,
    taco bigint not null,
    taco_key bigint not null
);

CREATE TABLE IF NOT EXISTS taco_cloud.ingredient (
    id varchar(4) PRIMARY KEY NOT NULL,
    name varchar(25) not null,
    type varchar(10) not null
);

ALTER TABLE taco_cloud.taco ADD FOREIGN KEY (taco_order) REFERENCES taco_cloud.taco_order(id);
ALTER TABLE taco_cloud.ingredient_ref ADD FOREIGN KEY (ingredient) REFERENCES taco_cloud.ingredient(id);