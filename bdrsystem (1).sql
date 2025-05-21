-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 21, 2025 at 09:44 PM
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
-- Database: `bdrsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `balance`
--

CREATE TABLE `balance` (
  `balance_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `request_id` int(11) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `payment_method` enum('Cash','GCash','Credit') NOT NULL,
  `payment_date` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `balance`
--

INSERT INTO `balance` (`balance_id`, `user_id`, `request_id`, `amount`, `payment_method`, `payment_date`) VALUES
(2, 8, 3, 100.00, 'Cash', '2025-05-21 14:44:06'),
(3, 8, 3, 100.00, 'Cash', '2025-05-21 14:57:35'),
(4, 8, 4, 1000.00, 'Cash', '2025-05-21 15:07:15'),
(5, 9, 2, 500.00, 'Cash', '2025-05-21 15:13:03'),
(6, 8, 1, 50.00, 'Cash', '2025-05-21 20:40:12');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_documents`
--

CREATE TABLE `tbl_documents` (
  `d_id` int(11) NOT NULL,
  `d_type` varchar(255) NOT NULL,
  `d_price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_documents`
--

INSERT INTO `tbl_documents` (`d_id`, `d_type`, `d_price`) VALUES
(1, 'Barangay Clearance', 50.00),
(2, 'Certificate of Residency', 30.00),
(3, 'Barangay Indigency', 20.00),
(4, 'Barangay Business Permit', 70.00),
(7, 'Barangay Certificate', 50.00);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_requested_documents`
--

CREATE TABLE `tbl_requested_documents` (
  `request_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `d_id` int(11) NOT NULL,
  `request_date` date NOT NULL,
  `status` enum('Pending','Approved','Rejected') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_requested_documents`
--

INSERT INTO `tbl_requested_documents` (`request_id`, `user_id`, `d_id`, `request_date`, `status`) VALUES
(1, 8, 1, '2025-05-21', 'Approved'),
(2, 9, 3, '2025-05-21', 'Approved'),
(3, 8, 2, '2025-05-21', 'Approved'),
(4, 8, 7, '2025-05-21', 'Approved'),
(5, 8, 4, '2025-05-21', 'Approved'),
(6, 9, 7, '2025-05-21', 'Approved'),
(7, 8, 2, '2025-05-22', 'Approved');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `gender` enum('Male','Female') NOT NULL,
  `contact` varchar(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `balance` decimal(10,2) NOT NULL DEFAULT 0.00,
  `user_type` enum('Admin','Citizen') NOT NULL DEFAULT 'Citizen',
  `status` enum('Active','Inactive','Pending') DEFAULT 'Pending',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `first_name`, `last_name`, `gender`, `contact`, `username`, `password`, `balance`, `user_type`, `status`, `created_at`) VALUES
(2, 'JohnMicole', 'Mangila', 'Male', '09123456789', 'admin', 'admin1234', 0.00, 'Admin', 'Active', '2025-05-19 04:04:20'),
(3, 'John', 'Mangila', 'Male', '09123456987', 'micole', '93f0c0cf3540055cc1e18917ff279976f7474b3501870546fb9fc37caf122ecd', 0.00, 'Citizen', 'Active', '2025-03-10 15:25:02'),
(4, 'testing', 'test', 'Male', '012345648', 'test', '937e8d5fbb48bd4949536cd65b8d35c426b80d2f830c5c308e2cdec422ae2244', 0.00, 'Citizen', 'Active', '2025-03-13 23:06:49'),
(6, 'testing', 'testings', 'Male', '09123456789', 'newtest', 'Admin1234!', 0.00, 'Admin', 'Active', '2025-05-15 13:15:30'),
(7, 'micole', 'mangila', 'Male', '09987456321', 'coleme', '1234', 0.00, 'Admin', 'Active', '2025-05-19 04:04:42'),
(8, 'haro', 'rorot', 'Female', '09987456321', 'micolele', '123456789', 0.00, 'Citizen', 'Active', '2025-05-21 12:20:50'),
(9, 'hatdog', 'cheesedog', 'Female', '09123456789', 'cheese', '123456789', 0.00, 'Citizen', 'Active', '2025-05-21 04:57:49'),
(10, 'test', 'testing', 'Male', '0987456321', 'testify', '73l8gRjwLftklgfdXT+MdiMEjJwGPVMsyVxe16iYpk8=', 0.00, 'Admin', 'Active', '2025-05-21 19:33:50'),
(11, 'test', 'testttt', 'Male', '09123456789', 'tess', '123456789', 0.00, 'Admin', 'Active', '2025-05-21 19:43:32');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `balance`
--
ALTER TABLE `balance`
  ADD PRIMARY KEY (`balance_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `request_id` (`request_id`);

--
-- Indexes for table `tbl_documents`
--
ALTER TABLE `tbl_documents`
  ADD PRIMARY KEY (`d_id`);

--
-- Indexes for table `tbl_requested_documents`
--
ALTER TABLE `tbl_requested_documents`
  ADD PRIMARY KEY (`request_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `d_id` (`d_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `balance`
--
ALTER TABLE `balance`
  MODIFY `balance_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tbl_documents`
--
ALTER TABLE `tbl_documents`
  MODIFY `d_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `tbl_requested_documents`
--
ALTER TABLE `tbl_requested_documents`
  MODIFY `request_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `balance`
--
ALTER TABLE `balance`
  ADD CONSTRAINT `balance_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `balance_ibfk_2` FOREIGN KEY (`request_id`) REFERENCES `tbl_requested_documents` (`request_id`) ON DELETE CASCADE;

--
-- Constraints for table `tbl_requested_documents`
--
ALTER TABLE `tbl_requested_documents`
  ADD CONSTRAINT `tbl_requested_documents_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tbl_requested_documents_ibfk_2` FOREIGN KEY (`d_id`) REFERENCES `tbl_documents` (`d_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
