


CREATE TABLE test (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      brand_id BIGINT NOT NULL,
                                      category_id BIGINT NOT NULL,
                                      old_price DECIMAL(10,2),
                                      new_price DECIMAL(10,2),
                                      change_type VARCHAR(50) NOT NULL,
                                      change_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      FOREIGN KEY (brand_id) REFERENCES brands(id),
                                      FOREIGN KEY (category_id) REFERENCES categories(id)
);
