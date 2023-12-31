CREATE TABLE users
(
    id    INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email      VARCHAR(32)  NOT NULL UNIQUE,
    first_name VARCHAR(16)  NOT NULL,
    last_name  VARCHAR(16)  NOT NULL,
    password   VARCHAR(255) NOT NULL,
    phone      VARCHAR(15)  NOT NULL,
    role       VARCHAR(10) DEFAULT 'USER',
    image_id   INTEGER,

    CONSTRAINT phone_constraint CHECK (phone LIKE ('+7%')),
    FOREIGN KEY (image_id) REFERENCES images (id)
);