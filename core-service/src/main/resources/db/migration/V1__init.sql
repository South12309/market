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
    username    	varchar(255) not null,
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
