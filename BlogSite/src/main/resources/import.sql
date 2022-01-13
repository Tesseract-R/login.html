INSERT INTO user (id, pid, password, username, email)
VALUES (1, '2000022728', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'zrc5', 'zrc5@sina.com');

INSERT INTO authority (id, name)
VALUES (1, 'ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_id)
VALUES (1, 1);
