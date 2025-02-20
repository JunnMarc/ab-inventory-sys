-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 21, 2025 at 12:15 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bakery_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`category_id`, `category_name`) VALUES
(1, 'HUMUKON'),
(2, 'd');

--
-- Triggers `category`
--
DELIMITER $$
CREATE TRIGGER `before_insert_category` BEFORE INSERT ON `category` FOR EACH ROW BEGIN
    DECLARE next_id INT;

    -- Find the next available category_id
    SELECT COALESCE(MIN(c.category_id + 1), 1) INTO next_id
    FROM Category c
    WHERE NOT EXISTS (
        SELECT 1 FROM Category WHERE category_id = c.category_id + 1
    );

    -- Assign the next available category_id
    SET NEW.category_id = next_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `employee_id` int(11) NOT NULL,
  `employee_fname` varchar(100) NOT NULL,
  `employee_lname` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE `inventory` (
  `inventory_id` int(11) NOT NULL,
  `record_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `total_stock` int(11) NOT NULL DEFAULT 0,
  `last_updated` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL,
  `product_name` varchar(100) NOT NULL,
  `product_category` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_id`, `product_name`, `product_category`) VALUES
(1, 'Cake', 1);

--
-- Triggers `product`
--
DELIMITER $$
CREATE TRIGGER `before_insert_product` BEFORE INSERT ON `product` FOR EACH ROW BEGIN
    DECLARE next_id INT;

    -- Find the next available product_id
    SELECT COALESCE(MIN(t1.product_id + 1), 1) INTO next_id
    FROM Product t1
    WHERE NOT EXISTS (
        SELECT 1 FROM Product t2 WHERE t2.product_id = t1.product_id + 1
    );

    -- Assign the next available ID, should be next to previous ID
    SET NEW.product_id = next_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `stack_in_record`
--

CREATE TABLE `stack_in_record` (
  `record_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `employee_id` int(11) NOT NULL,
  `production_date` datetime DEFAULT current_timestamp(),
  `quantity_produced` int(11) NOT NULL CHECK (`quantity_produced` > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Triggers `stack_in_record`
--
DELIMITER $$
CREATE TRIGGER `after_production_add` AFTER INSERT ON `stack_in_record` FOR EACH ROW BEGIN
    -- If prod already exists in inventory, update quantity :)
    IF EXISTS (SELECT 1 FROM Inventory WHERE product_id = NEW.product_id) THEN
        UPDATE Inventory 
        SET total_stock = total_stock + NEW.quantity_produced, 
            last_updated = NOW()
        WHERE product_id = NEW.product_id;
    ELSE
        -- Else, add new inventory record :)
        INSERT INTO Inventory (record_id, product_id, total_stock) 
        VALUES (NEW.record_id, NEW.product_id, NEW.quantity_produced);
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `stack_out_record`
--

CREATE TABLE `stack_out_record` (
  `record_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `transaction_type` enum('SALE','WASTE') NOT NULL,
  `transaction_date` datetime DEFAULT current_timestamp(),
  `quantity_removed` int(11) NOT NULL CHECK (`quantity_removed` > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Triggers `stack_out_record`
--
DELIMITER $$
CREATE TRIGGER `after_stack_out_insert` AFTER INSERT ON `stack_out_record` FOR EACH ROW BEGIN
    -- Check if stock has enough stock to prevent negative
    IF (SELECT total_stock FROM Inventory WHERE product_id = NEW.product_id) < NEW.quantity_removed THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: Not enough stock available';
    ELSE
        -- Reduce stock
        UPDATE Inventory 
        SET total_stock = total_stock - NEW.quantity_removed, 
            last_updated = NOW()
        WHERE product_id = NEW.product_id;
    END IF;
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`employee_id`);

--
-- Indexes for table `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`inventory_id`),
  ADD UNIQUE KEY `record_id` (`record_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `product_category` (`product_category`);

--
-- Indexes for table `stack_in_record`
--
ALTER TABLE `stack_in_record`
  ADD PRIMARY KEY (`record_id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `baker_id` (`employee_id`);

--
-- Indexes for table `stack_out_record`
--
ALTER TABLE `stack_out_record`
  ADD PRIMARY KEY (`record_id`),
  ADD KEY `product_id` (`product_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `employee_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `inventory`
--
ALTER TABLE `inventory`
  MODIFY `inventory_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `stack_in_record`
--
ALTER TABLE `stack_in_record`
  MODIFY `record_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `stack_out_record`
--
ALTER TABLE `stack_out_record`
  MODIFY `record_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `inventory`
--
ALTER TABLE `inventory`
  ADD CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`record_id`) REFERENCES `stack_in_record` (`record_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `inventory_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE;

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`product_category`) REFERENCES `category` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `stack_in_record`
--
ALTER TABLE `stack_in_record`
  ADD CONSTRAINT `stack_in_record_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `stack_in_record_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`) ON DELETE CASCADE;

--
-- Constraints for table `stack_out_record`
--
ALTER TABLE `stack_out_record`
  ADD CONSTRAINT `stack_out_record_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
