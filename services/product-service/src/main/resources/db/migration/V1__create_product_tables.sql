create table categories(
category_id UUID primary Key DEFAULT gen_random_uuid(),
category_name varchar(255) Not null,
parent_category_id UUID references categories(category_id),
is_active BOOLEAN DEFAULT TRUE,
created_at TIMESTAMP DEFAULT NOW(),
updated_at TIMESTAMP
);

create table products(
product_id UUID primary key DEFAULT gen_random_uuid(),
product_name varchar(255) not null,
product_description TEXT not null,
product_selling_price DECIMAL(10,2),
product_original_price DECIMAL(10,2)	,
product_category_id UUID references categories(category_id),
product_status varchar(20) check (product_status IN ('active', 'out_of_stock', 'discontinued', 'coming_soon')),
created_by UUID not null,
is_active BOOLEAN DEFAULT TRUE,
created_at TIMESTAMP DEFAULT now(),
updated_at TIMESTAMP
);

create table product_images(
image_id uuid primary key DEFAULT gen_random_uuid(),
product_id UUID References products(product_id),
image_url VARCHAR(500) NOT NULL,
is_primary boolean DEFAULT false,
display_order numeric,
created_at TIMESTAMP DEFAULT now(),
updated_at TIMESTAMP
);