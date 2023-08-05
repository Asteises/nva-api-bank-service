INSERT INTO credit_cards (blocked,
                          credit_funds,
                          credit_limit,
                          debit_funds,
                          id,
                          card_number)
VALUES (false,
        100000.0,
        100000.0,
        0.0,
        '85d03f9d-5453-4940-aa07-f4f04c22b8c9',
        1111222233334444);

INSERT INTO credit_cards (blocked,
                          credit_funds,
                          credit_limit,
                          debit_funds,
                          id,
                          card_number)
values (false,
        25000.0,
        25000.0,
        5000.0,
        '85d03f9d-5453-4940-aa07-f4f04c22b8c8',
        1111222233334445);

INSERT INTO debit_cards (balance,
                         blocked,
                         id,
                         card_number)
VALUES (0.0,
        false,
        '85d03f9d-5453-4940-aa07-f4f04c22b8c1',
        1111222233334441);

INSERT INTO debit_cards (balance,
                         blocked,
                         id,
                         card_number)
VALUES (10000.0,
        false,
        '85d03f9d-5453-4940-aa07-f4f04c22b8c2',
        1111222233334442);