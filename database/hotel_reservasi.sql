-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 02 Jul 2026 pada 10.53
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotel_reservasi`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `kamar`
--

CREATE TABLE `kamar` (
  `id_kamar` int(11) NOT NULL,
  `nomor_kamar` varchar(10) DEFAULT NULL,
  `tipe` varchar(50) DEFAULT NULL,
  `harga` decimal(12,2) DEFAULT NULL,
  `status` enum('Kosong','Terisi') DEFAULT 'Kosong'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `kamar`
--

INSERT INTO `kamar` (`id_kamar`, `nomor_kamar`, `tipe`, `harga`, `status`) VALUES
(1, '101', 'Standard', 350000.00, 'Terisi'),
(2, '102', 'Standard', 350000.00, 'Kosong'),
(3, '201', 'Deluxe', 500000.00, 'Terisi'),
(4, '202', 'Deluxe', 500000.00, 'Terisi'),
(7, '919', 'Deluxe', 200000.00, 'Terisi'),
(8, '101', 'Deluxe', 10000000.00, 'Terisi'),
(9, '777', 'Deluxe', 5000000.00, 'Kosong'),
(10, '621', 'Standard', 50000000.00, 'Terisi');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pembayaran`
--

CREATE TABLE `pembayaran` (
  `id_pembayaran` int(11) NOT NULL,
  `id_reservasi` int(11) DEFAULT NULL,
  `tanggal_bayar` date DEFAULT NULL,
  `metode` varchar(50) DEFAULT NULL,
  `jumlah` decimal(12,2) DEFAULT NULL,
  `status` enum('Lunas','Belum Lunas') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `pembayaran`
--

INSERT INTO `pembayaran` (`id_pembayaran`, `id_reservasi`, `tanggal_bayar`, `metode`, `jumlah`, `status`) VALUES
(1, 2, '2026-07-08', 'Transfer', 1000000.00, 'Lunas'),
(2, 3, '2026-07-01', 'QRIS', 14500000.00, 'Lunas'),
(3, 5, '2026-07-30', 'Cash', 90000000.00, 'Lunas'),
(4, 6, '2026-07-01', 'Transfer', 2000000000.00, 'Lunas');

-- --------------------------------------------------------

--
-- Struktur dari tabel `reservasi`
--

CREATE TABLE `reservasi` (
  `id_reservasi` int(11) NOT NULL,
  `id_tamu` int(11) DEFAULT NULL,
  `id_kamar` int(11) DEFAULT NULL,
  `checkin` date DEFAULT NULL,
  `checkout` date DEFAULT NULL,
  `lama_menginap` int(11) DEFAULT NULL,
  `total_bayar` decimal(12,2) DEFAULT NULL,
  `status` enum('Booking','Check In','Check Out') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `reservasi`
--

INSERT INTO `reservasi` (`id_reservasi`, `id_tamu`, `id_kamar`, `checkin`, `checkout`, `lama_menginap`, `total_bayar`, `status`) VALUES
(2, 2, 2, '2026-07-14', '2026-07-25', 11, 3850000.00, 'Check In'),
(3, 3, 4, '2026-07-01', '2026-07-30', 29, 14500000.00, 'Check In'),
(4, 6, 3, '2026-07-11', '2026-07-17', 6, 3000000.00, 'Check In'),
(5, 4, 8, '2026-07-22', '2026-07-31', 9, 90000000.00, 'Check In'),
(6, 7, 10, '2026-08-01', '2026-09-10', 40, 2000000000.00, 'Check In');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tamu`
--

CREATE TABLE `tamu` (
  `id_tamu` int(11) NOT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `nik` varchar(30) DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `no_hp` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tamu`
--

INSERT INTO `tamu` (`id_tamu`, `nama`, `nik`, `alamat`, `no_hp`) VALUES
(2, 'AJI', '3275010101010001', 'Bogor', '081199223344'),
(3, 'Reza', '38371781361293829', 'Jakarta', '08992211755'),
(4, 'beta', '73781237167163', 'Jawa', '089911833278'),
(5, 'Rey', '316137713112', 'Bogor', '0877663311'),
(6, 'Hanip', '31617372765125', 'Tangerang', '08976452327'),
(7, 'Rizho', '31456253711313', 'Jakarta', '089955663322');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `level` enum('Admin','Resepsionis') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id_user`, `username`, `password`, `level`) VALUES
(1, 'admin', '123', 'Admin');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `kamar`
--
ALTER TABLE `kamar`
  ADD PRIMARY KEY (`id_kamar`);

--
-- Indeks untuk tabel `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD PRIMARY KEY (`id_pembayaran`),
  ADD KEY `id_reservasi` (`id_reservasi`);

--
-- Indeks untuk tabel `reservasi`
--
ALTER TABLE `reservasi`
  ADD PRIMARY KEY (`id_reservasi`),
  ADD KEY `id_tamu` (`id_tamu`),
  ADD KEY `id_kamar` (`id_kamar`);

--
-- Indeks untuk tabel `tamu`
--
ALTER TABLE `tamu`
  ADD PRIMARY KEY (`id_tamu`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `kamar`
--
ALTER TABLE `kamar`
  MODIFY `id_kamar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `pembayaran`
--
ALTER TABLE `pembayaran`
  MODIFY `id_pembayaran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `reservasi`
--
ALTER TABLE `reservasi`
  MODIFY `id_reservasi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `tamu`
--
ALTER TABLE `tamu`
  MODIFY `id_tamu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD CONSTRAINT `pembayaran_ibfk_1` FOREIGN KEY (`id_reservasi`) REFERENCES `reservasi` (`id_reservasi`);

--
-- Ketidakleluasaan untuk tabel `reservasi`
--
ALTER TABLE `reservasi`
  ADD CONSTRAINT `reservasi_ibfk_1` FOREIGN KEY (`id_tamu`) REFERENCES `tamu` (`id_tamu`),
  ADD CONSTRAINT `reservasi_ibfk_2` FOREIGN KEY (`id_kamar`) REFERENCES `kamar` (`id_kamar`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
