-- Таблица пользователей
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role ENUM('USER', 'ADMIN') DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблица категорий
CREATE TABLE IF NOT EXISTS categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT
);

-- Таблица товаров
CREATE TABLE IF NOT EXISTS products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    category_id INT,
    image_url VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

-- Таблица корзины
CREATE TABLE IF NOT EXISTS cart (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    UNIQUE KEY unique_cart (user_id, product_id)
);

-- Таблица заказов
CREATE TABLE IF NOT EXISTS orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    status ENUM('PENDING', 'CONFIRMED', 'SHIPPED', 'CANCELLED') DEFAULT 'PENDING',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Таблица элементов заказа
CREATE TABLE IF NOT EXISTS order_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Тестовые данные
INSERT INTO categories (name, description) VALUES
('Электроника', 'Техника и гаджеты'),
('Одежда', 'Одежда и аксессуары'),
('Книги', 'Художественная и учебная литература');

INSERT INTO products (name, description, price, category_id, image_url) VALUES
('Смартфон', 'Современный смартфон с камерой 48Мп', 29999.00, 1, 'https://placehold.co/300x200/212529/FFFFFF?text=Смартфон'),
('Ноутбук', 'Лёгкий ноутбук для работы', 59999.00, 1, 'https://placehold.co/300x200/212529/FFFFFF?text=Ноутбук'),
('Футболка', 'Хлопковая футболка, размер M', 1299.00, 2, 'https://placehold.co/300x200/212529/FFFFFF?text=Футболка'),
('Джинсы', 'Классические джинсы', 2499.00, 2, 'https://placehold.co/300x200/212529/FFFFFF?text=Джинсы'),
('Книга по Java', 'Изучаем программирование на Java', 899.00, 3, 'https://placehold.co/300x200/212529/FFFFFF?text=Книга+Java');

