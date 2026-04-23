CREATE TABLE users (
                       user_id UUID PRIMARY KEY default gen_random_uuid(), username varchar(255), email varchar(255) NOT NULL UNIQUE,
                       password varchar(255),
                       phone_number varchar(255),
                       is_active BOOLEAN DEFAULT TRUE,
                       created_at TIMESTAMP DEFAULT now(),
                       updated_at TIMESTAMP
);


create table address(
                        address_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        user_id UUID NOT NULL REFERENCES users(user_id),
                        street varchar(255) NOT NULL ,
                        city varchar(255) NOT NULL,
                        state char(50) NOT NULL,
                        zip_code char (10) Not null,
                        country varchar(255) NOT NULL,
                        is_default BOOLEAN DEFAULT FALSE,
                        created_at TIMESTAMP DEFAULT now(),
                        updated_at TIMESTAMP
);