
insert into ADDRESS (id, number, complement, postalCode, city, street, state) values (1, 16, 'Em frente a praça', '68990-000', 'Tartarugalzinho', 'Perpétuo Socorro', 'Amapá');
insert into ADDRESSES (id, city, complement, number, postal_code, state, street) values (100, 'São Paulo', 'Próximo a av. Pitanga', 10, '01001000', 'SC',  'Praça da Sé');
insert into ADDRESSES (id, city, complement, number, postal_code, state, street) values (101, 'São Paulo', 'Próximo a av. Pitanga', 10, '01001000', 'SC',  'Praça da Sé');
insert into ADDRESSES (id, city, complement, number, postal_code, state, street) values (102, 'São Paulo', 'Próximo a av. Pitanga', 10, '01001000', 'SC',  'Praça da Sé');

insert into PRODUCTS (id, name, description, price) values (100, 'Televisão', 'Televisão da boa', 200.00);

insert into ORDERS (id, id_address, payment_method, order_status, has_discount, total_value,
                    subtotal_value, creation_date, cancelation_date, cancel_reason)
                    values(
                            100, 100, 'PIX', 'CONFIRMED', true, 1000.00, 950.00, CURRENT_TIME, null, null
                            );
insert into ORDERS (id, id_address, payment_method, order_status, has_discount, total_value,
                    subtotal_value, creation_date, cancelation_date, cancel_reason)
                    values(
                            101, 101, 'CREDIT_CARD', 'SENT', true, 1000.00, 950.00, CURRENT_TIME, null, null
                            );
insert into ORDERS (id, id_address, payment_method, order_status, has_discount, total_value,
                    subtotal_value, creation_date, cancelation_date, cancel_reason)
                    values(
                            102, 102, 'PIX', 'CANCELED', true, 1000.00, 950.00, CURRENT_TIME, CURRENT_TIME, 'Motivo do cancelamento'
                           );
insert into ORDER_PRODUCT (product_id, order_id) values (100, 100);