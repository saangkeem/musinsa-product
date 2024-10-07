INSERT INTO brands (name) VALUES ('A');
INSERT INTO brands (name) VALUES ('B');
INSERT INTO brands (name) VALUES ('C');
INSERT INTO brands (name) VALUES ('D');
INSERT INTO brands (name) VALUES ('E');
INSERT INTO brands (name) VALUES ('F');
INSERT INTO brands (name) VALUES ('G');
INSERT INTO brands (name) VALUES ('H');
INSERT INTO brands (name) VALUES ('I');

INSERT INTO categories (name) VALUES ('상의');
INSERT INTO categories (name) VALUES ('아우터');
INSERT INTO categories (name) VALUES ('바지');
INSERT INTO categories (name) VALUES ('스니커즈');
INSERT INTO categories (name) VALUES ('가방');
INSERT INTO categories (name) VALUES ('모자');
INSERT INTO categories (name) VALUES ('양말');
INSERT INTO categories (name) VALUES ('액세서리');


-- 브랜드 A 상품
INSERT INTO products (brand_id, category_id, price) VALUES (1, 1, 11200); -- 상의
INSERT INTO products (brand_id, category_id, price) VALUES (1, 2, 5500);  -- 아우터
INSERT INTO products (brand_id, category_id, price) VALUES (1, 3, 4200);  -- 바지
INSERT INTO products (brand_id, category_id, price) VALUES (1, 4, 9000);  -- 스니커즈
INSERT INTO products (brand_id, category_id, price) VALUES (1, 5, 2000);  -- 가방
INSERT INTO products (brand_id, category_id, price) VALUES (1, 6, 1700);  -- 모자
INSERT INTO products (brand_id, category_id, price) VALUES (1, 7, 1800);  -- 양말
INSERT INTO products (brand_id, category_id, price) VALUES (1, 8, 2300);  -- 액세서리

-- 브랜드 B 상품
INSERT INTO products (brand_id, category_id, price) VALUES (2, 1, 10500);
INSERT INTO products (brand_id, category_id, price) VALUES (2, 2, 5900);
INSERT INTO products (brand_id, category_id, price) VALUES (2, 3, 3800);
INSERT INTO products (brand_id, category_id, price) VALUES (2, 4, 9100);
INSERT INTO products (brand_id, category_id, price) VALUES (2, 5, 2100);
INSERT INTO products (brand_id, category_id, price) VALUES (2, 6, 2000);
INSERT INTO products (brand_id, category_id, price) VALUES (2, 7, 2000);
INSERT INTO products (brand_id, category_id, price) VALUES (2, 8, 2200);

-- 브랜드 C 상품
INSERT INTO products (brand_id, category_id, price) VALUES (3, 1, 10000);
INSERT INTO products (brand_id, category_id, price) VALUES (3, 2, 6200);
INSERT INTO products (brand_id, category_id, price) VALUES (3, 3, 3300);
INSERT INTO products (brand_id, category_id, price) VALUES (3, 4, 9200);
INSERT INTO products (brand_id, category_id, price) VALUES (3, 5, 2200);
INSERT INTO products (brand_id, category_id, price) VALUES (3, 6, 1900);
INSERT INTO products (brand_id, category_id, price) VALUES (3, 7, 2200);
INSERT INTO products (brand_id, category_id, price) VALUES (3, 8, 2100);

-- 브랜드 D 상품
INSERT INTO products (brand_id, category_id, price) VALUES (4, 1, 10100);
INSERT INTO products (brand_id, category_id, price) VALUES (4, 2, 5100);
INSERT INTO products (brand_id, category_id, price) VALUES (4, 3, 3000);
INSERT INTO products (brand_id, category_id, price) VALUES (4, 4, 9500);
INSERT INTO products (brand_id, category_id, price) VALUES (4, 5, 2500);
INSERT INTO products (brand_id, category_id, price) VALUES (4, 6, 1500);
INSERT INTO products (brand_id, category_id, price) VALUES (4, 7, 2400);
INSERT INTO products (brand_id, category_id, price) VALUES (4, 8, 2000);

-- 브랜드 E 상품
INSERT INTO products (brand_id, category_id, price) VALUES (5, 1, 10700);
INSERT INTO products (brand_id, category_id, price) VALUES (5, 2, 5000);
INSERT INTO products (brand_id, category_id, price) VALUES (5, 3, 3800);
INSERT INTO products (brand_id, category_id, price) VALUES (5, 4, 9900);
INSERT INTO products (brand_id, category_id, price) VALUES (5, 5, 2300);
INSERT INTO products (brand_id, category_id, price) VALUES (5, 6, 1800);
INSERT INTO products (brand_id, category_id, price) VALUES (5, 7, 2100);
INSERT INTO products (brand_id, category_id, price) VALUES (5, 8, 2100);

-- 브랜드 F 상품
INSERT INTO products (brand_id, category_id, price) VALUES (6, 1, 11200);
INSERT INTO products (brand_id, category_id, price) VALUES (6, 2, 7200);
INSERT INTO products (brand_id, category_id, price) VALUES (6, 3, 4000);
INSERT INTO products (brand_id, category_id, price) VALUES (6, 4, 9300);
INSERT INTO products (brand_id, category_id, price) VALUES (6, 5, 2100);
INSERT INTO products (brand_id, category_id, price) VALUES (6, 6, 1600);
INSERT INTO products (brand_id, category_id, price) VALUES (6, 7, 2300);
INSERT INTO products (brand_id, category_id, price) VALUES (6, 8, 1900);

-- 브랜드 G 상품
INSERT INTO products (brand_id, category_id, price) VALUES (7, 1, 10500);
INSERT INTO products (brand_id, category_id, price) VALUES (7, 2, 5800);
INSERT INTO products (brand_id, category_id, price) VALUES (7, 3, 3900);
INSERT INTO products (brand_id, category_id, price) VALUES (7, 4, 9000);
INSERT INTO products (brand_id, category_id, price) VALUES (7, 5, 2200);
INSERT INTO products (brand_id, category_id, price) VALUES (7, 6, 1700);
INSERT INTO products (brand_id, category_id, price) VALUES (7, 7, 2100);
INSERT INTO products (brand_id, category_id, price) VALUES (7, 8, 2000);

-- 브랜드 H 상품
INSERT INTO products (brand_id, category_id, price) VALUES (8, 1, 10800);
INSERT INTO products (brand_id, category_id, price) VALUES (8, 2, 6300);
INSERT INTO products (brand_id, category_id, price) VALUES (8, 3, 3100);
INSERT INTO products (brand_id, category_id, price) VALUES (8, 4, 9700);
INSERT INTO products (brand_id, category_id, price) VALUES (8, 5, 2100);
INSERT INTO products (brand_id, category_id, price) VALUES (8, 6, 1600);
INSERT INTO products (brand_id, category_id, price) VALUES (8, 7, 2000);
INSERT INTO products (brand_id, category_id, price) VALUES (8, 8, 2000);

-- 브랜드 I 상품
INSERT INTO products (brand_id, category_id, price) VALUES (9, 1, 11400);
INSERT INTO products (brand_id, category_id, price) VALUES (9, 2, 6700);
INSERT INTO products (brand_id, category_id, price) VALUES (9, 3, 3200);
INSERT INTO products (brand_id, category_id, price) VALUES (9, 4, 9500);
INSERT INTO products (brand_id, category_id, price) VALUES (9, 5, 2400);
INSERT INTO products (brand_id, category_id, price) VALUES (9, 6, 1700);
INSERT INTO products (brand_id, category_id, price) VALUES (9, 7, 1700);
INSERT INTO products (brand_id, category_id, price) VALUES (9, 8, 2400);


