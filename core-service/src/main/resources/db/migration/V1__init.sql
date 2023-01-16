-- Таблица пользователей. 
create table users (
                       id         bigserial primary key,
                       username   varchar(36) not null,
                       password   varchar(80) not null,
                       email      varchar(50) unique,
                       created_at timestamp default current_timestamp,
                       updated_at timestamp default current_timestamp
);

--Таблица ролей
create table roles (
                       id         bigserial primary key,
                       name       varchar(50) not null,
                       created_at timestamp default current_timestamp,
                       updated_at timestamp default current_timestamp
);

--Таблица связи многие-ко-многим ролей и пользователей.
create table users_roles (
                             user_id    bigint not null references users (id),
                             role_id    bigint not null references roles (id),
                             created_at timestamp default current_timestamp,
                             updated_at timestamp default current_timestamp,
                             primary key (user_id, role_id)
);

--Таблица категорий (связьи с таблицей продуктов один-ко-многим)
create table categories
(
    id              bigserial primary key,
    title           varchar(255),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

--Таблица продуктов
create table products
(
    id              bigserial primary key,
    title           varchar(255),
    price           numeric(8, 2) not null,
    category_id     bigint references categories (id),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

--Таблица заказов. Связь с таблицей пользователей многие-к-одному
create table orders
(
    id              bigserial primary key,
    user_id    	    bigint not null references users (id),
    total_price     numeric(8, 2),
    address         varchar(255),
    phone           varchar(15),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

--Элемент заказа. Содержит в себе ссылку на продукт, ссылку на заказ в который он входит, цена за единицу и общую цену по данному продукту.
create table orders_items
(
    id                      bigserial primary key,
    order_id                bigint references orders (id),
    product_id              bigint references products (id),
    price_per_product       numeric(8, 2),
    quantity                int,
    price                   numeric(8, 2),
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

insert into categories (title) values
                                   ('Food'),
                                   ('Electronic');

insert into products (title, price, category_id) values
                                                     ('Bread', 32.00, 1),
                                                     ('Milk', 120.00, 1),
                                                     ('Butter', 320.00, 1),
                                                     ('Cheese', 500.00, 1);
insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com'),
       ('john', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);