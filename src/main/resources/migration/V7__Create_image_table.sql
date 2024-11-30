CREATE TABLE images(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fileType VARCHAR NOT NULL,
    fileName VARCHAR(128) NOT NULL,
    images BYTEA NOT NULL,
    viewUrl VARCHAR NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id)
);