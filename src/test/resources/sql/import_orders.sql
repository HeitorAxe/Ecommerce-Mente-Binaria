insert into ADDRESS (id, number, complement, postalCode, city, street, state) values (1, 16, 'Em frente a praça', 68990-000, 'Tartarugalzinho', 'Perpétuo Socorro', 'Amapá');

insert into PRODUCTS (id, name, description, price) values (100, 'Televisão', 'Televisão da boa', 200);
insert into ORDER_PRODUCT (order_id, product_id) values(100, 100);

insert into ORDERS (id, address, paymentMethod, orderStatus, hasDiscount, totalValue,
                    subTotalValue, creationDate, cancelationDate, cancelReason)
                    values(
                            100, 100, 'PIX', 'CONFIRMED', true, 1000.00, 950.00, CURRENT_TIME, null, null
                            );
insert into ORDERS (id, address, paymentMethod, orderStatus, hasDiscount, totalValue,
                    subTotalValue, creationDate, cancelationDate, cancelReason)
                    values(
                            100, 100, 'CREDIT_CARD', 'SENT', true, 1000.00, 950.00, CURRENT_TIME, null, null
                            );
insert into ORDERS (id, address, paymentMethod, orderStatus, hasDiscount, totalValue,
                    subTotalValue, creationDate, cancelationDate, cancelReason)
                    values(
                            100, 100, 'PIX', 'CANCELED', true, 1000.00, 950.00, CURRENT_TIME, DATE_ADD(CURRENT_TIME, INTERVAL 90 DAY), 'Motivo do cancelamento'
                           );