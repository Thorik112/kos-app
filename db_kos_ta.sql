-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 30, 2026 at 11:30 AM
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
-- Database: `db_kos_ta`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `id_booking` int(11) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `id_kamar` int(11) DEFAULT NULL,
  `tanggal_booking` date DEFAULT NULL,
  `tanggal_masuk` date DEFAULT NULL,
  `durasi_sewa` int(11) DEFAULT NULL,
  `total_harga` decimal(10,2) DEFAULT NULL,
  `status_booking` enum('pending','confirmed','cancelled') DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`id_booking`, `id_user`, `id_kamar`, `tanggal_booking`, `tanggal_masuk`, `durasi_sewa`, `total_harga`, `status_booking`, `created_at`) VALUES
(1, 1, 1, '2026-04-12', NULL, NULL, NULL, 'confirmed', '2026-04-12 07:08:20'),
(4, 1, 4, '2026-04-13', NULL, NULL, 90000.00, 'cancelled', '2026-04-12 22:49:56'),
(5, 1, 5, '2026-04-13', '2026-09-12', 4, 360000.00, 'cancelled', '2026-04-12 23:14:50'),
(6, 1, 5, '2026-04-13', '2026-08-16', 5, 450000.00, 'pending', '2026-04-13 04:12:33');

-- --------------------------------------------------------

--
-- Table structure for table `fasilitas`
--

CREATE TABLE `fasilitas` (
  `id_fasilitas` int(11) NOT NULL,
  `id_kamar` int(11) DEFAULT NULL,
  `fasilitas` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `fasilitas`
--

INSERT INTO `fasilitas` (`id_fasilitas`, `id_kamar`, `fasilitas`) VALUES
(6, 4, 'WiFi'),
(7, 4, 'KM Dalam'),
(8, 1, 'KM Dalam'),
(9, 5, 'Lemari');

-- --------------------------------------------------------

--
-- Table structure for table `foto_kos`
--

CREATE TABLE `foto_kos` (
  `id_foto` int(11) NOT NULL,
  `id_kos` int(11) DEFAULT NULL,
  `nama_file` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `foto_kos`
--

INSERT INTO `foto_kos` (`id_foto`, `id_kos`, `nama_file`, `created_at`) VALUES
(3, 1, 'Logo_light.png', '2026-04-12 01:37:02'),
(4, 2, 'Logo_dark.png', '2026-04-12 01:45:23'),
(6, 3, '2_flowchart.png', '2026-04-12 06:39:42'),
(7, 1, 'Logo_dark.png', '2026-04-12 13:50:15'),
(8, 1, 'Ami-Happy.png', '2026-04-12 14:02:08');

-- --------------------------------------------------------

--
-- Table structure for table `kamar`
--

CREATE TABLE `kamar` (
  `id_kamar` int(11) NOT NULL,
  `id_kos` int(11) DEFAULT NULL,
  `nomor_kamar` varchar(50) DEFAULT NULL,
  `status_kamar` enum('tersedia','penuh') DEFAULT 'tersedia',
  `deskripsi` text DEFAULT NULL,
  `foto` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kamar`
--

INSERT INTO `kamar` (`id_kamar`, `id_kos`, `nomor_kamar`, `status_kamar`, `deskripsi`, `foto`, `created_at`) VALUES
(1, 2, '24', 'penuh', 'hahahaha', 'Logo_light_2.png', '2026-04-12 06:29:43'),
(4, 2, 'hal1', 'tersedia', 'nanan', 'laptop.jpg', '2026-04-12 14:06:07'),
(5, 1, 'r23', 'tersedia', 'hahahahha', '1000006212.jpg', '2026-04-12 23:12:47');

-- --------------------------------------------------------

--
-- Table structure for table `kos`
--

CREATE TABLE `kos` (
  `id_kos` int(11) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `nama_kos` varchar(100) DEFAULT NULL,
  `deskripsi` text DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `kota` varchar(100) DEFAULT NULL,
  `tipe_kos` enum('bebas','muslim','muslimah') DEFAULT NULL,
  `harga` decimal(10,2) DEFAULT NULL,
  `aturan_kos` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kos`
--

INSERT INTO `kos` (`id_kos`, `id_user`, `nama_kos`, `deskripsi`, `alamat`, `kota`, `tipe_kos`, `harga`, `aturan_kos`, `created_at`) VALUES
(1, 2, 'Berkah Selalu', 'eeeeee', 'Jl. Tirto Rahayu No.72', 'Malang', 'bebas', 90000.00, '123 + 1 = 4', '2026-04-12 01:15:31'),
(2, 2, 'Labubu', 'we', 'Jl. Malang', 'Maduor', 'bebas', 90000.00, 'we', '2026-04-12 01:44:56'),
(3, 2, 'Haloww', 'Gak tau', 'jl. mmamamamma', 'Malang', 'muslimah', 9000000.00, 'Gak ada', '2026-04-12 06:38:35');

-- --------------------------------------------------------

--
-- Table structure for table `pembayaran`
--

CREATE TABLE `pembayaran` (
  `id_pembayaran` int(11) NOT NULL,
  `id_booking` int(11) DEFAULT NULL,
  `metode_pembayaran` varchar(50) DEFAULT NULL,
  `total_bayar` decimal(10,2) DEFAULT NULL,
  `tanggal_bayar` date DEFAULT NULL,
  `bukti_pembayaran` varchar(255) DEFAULT NULL,
  `status_pembayaran` enum('pending','lunas','gagal') DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pembayaran`
--

INSERT INTO `pembayaran` (`id_pembayaran`, `id_booking`, `metode_pembayaran`, `total_bayar`, `tanggal_bayar`, `bukti_pembayaran`, `status_pembayaran`, `created_at`) VALUES
(3, 1, 'Dana', 0.00, '2026-04-12', '1775983918325_Ami-Cry.png', 'lunas', '2026-04-12 08:51:58');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `no_telp` varchar(20) DEFAULT NULL,
  `jenis_kelamin` enum('L','P') DEFAULT 'L',
  `pekerjaan` varchar(100) DEFAULT NULL,
  `foto_profil` varchar(255) DEFAULT NULL,
  `role` enum('pengguna','pemilik_kos') DEFAULT 'pengguna',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `nama`, `email`, `password`, `no_telp`, `jenis_kelamin`, `pekerjaan`, `foto_profil`, `role`, `created_at`) VALUES
(1, 'Moch Thoriqul Firdaus', 'mochthoriqul12@gmail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '', 'L', '', NULL, 'pengguna', '2026-04-02 15:05:50'),
(2, 'Malva', 'malva21@gmail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '085738288914', 'P', 'nganggur', '1775920306242_IMG_20250331_191829.jpg', 'pemilik_kos', '2026-04-06 07:17:11'),
(3, 'Fina', 'fina@gmail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '', 'L', '', NULL, 'pemilik_kos', '2026-04-12 10:03:37'),
(4, 'Thoriq', 'thoriq23@gmail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', NULL, 'L', NULL, NULL, 'pengguna', '2026-04-13 03:27:20');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`id_booking`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_kamar` (`id_kamar`);

--
-- Indexes for table `fasilitas`
--
ALTER TABLE `fasilitas`
  ADD PRIMARY KEY (`id_fasilitas`),
  ADD KEY `id_kamar` (`id_kamar`);

--
-- Indexes for table `foto_kos`
--
ALTER TABLE `foto_kos`
  ADD PRIMARY KEY (`id_foto`),
  ADD KEY `id_kos` (`id_kos`);

--
-- Indexes for table `kamar`
--
ALTER TABLE `kamar`
  ADD PRIMARY KEY (`id_kamar`),
  ADD KEY `id_kos` (`id_kos`);

--
-- Indexes for table `kos`
--
ALTER TABLE `kos`
  ADD PRIMARY KEY (`id_kos`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD PRIMARY KEY (`id_pembayaran`),
  ADD KEY `id_booking` (`id_booking`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `id_booking` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `fasilitas`
--
ALTER TABLE `fasilitas`
  MODIFY `id_fasilitas` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `foto_kos`
--
ALTER TABLE `foto_kos`
  MODIFY `id_foto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `kamar`
--
ALTER TABLE `kamar`
  MODIFY `id_kamar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `kos`
--
ALTER TABLE `kos`
  MODIFY `id_kos` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `pembayaran`
--
ALTER TABLE `pembayaran`
  MODIFY `id_pembayaran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`id_kamar`) REFERENCES `kamar` (`id_kamar`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `fasilitas`
--
ALTER TABLE `fasilitas`
  ADD CONSTRAINT `fasilitas_ibfk_1` FOREIGN KEY (`id_kamar`) REFERENCES `kamar` (`id_kamar`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `foto_kos`
--
ALTER TABLE `foto_kos`
  ADD CONSTRAINT `foto_kos_ibfk_1` FOREIGN KEY (`id_kos`) REFERENCES `kos` (`id_kos`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `kamar`
--
ALTER TABLE `kamar`
  ADD CONSTRAINT `kamar_ibfk_1` FOREIGN KEY (`id_kos`) REFERENCES `kos` (`id_kos`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `kos`
--
ALTER TABLE `kos`
  ADD CONSTRAINT `kos_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD CONSTRAINT `pembayaran_ibfk_1` FOREIGN KEY (`id_booking`) REFERENCES `booking` (`id_booking`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
