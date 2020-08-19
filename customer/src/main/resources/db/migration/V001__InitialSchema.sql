CREATE TABLE customer (
  id UUID NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE address (
  id UUID NOT NULL,
  customer_id UUID NOT NULL,
  line1 VARCHAR(255) NOT NULL,
  line2 VARCHAR(255) NULL,
  line3 VARCHAR(255) NULL,
  city VARCHAR(255) NOT NULL,
  state VARCHAR(255) NOT NULL,
  zip_code VARCHAR(9) NOT NULL,
  country VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_customer FOREIGN KEY(customer_id) REFERENCES customer(id)
);
