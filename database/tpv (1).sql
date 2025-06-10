-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-06-2025 a las 18:07:18
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tpv`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuenta`
--

CREATE TABLE `cuenta` (
  `id` int(11) NOT NULL,
  `estado` tinyint(4) DEFAULT NULL CHECK (`estado` between 0 and 1),
  `fecha` datetime DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `metodo_pago` tinyint(4) DEFAULT NULL,
  `cliente` varchar(30) DEFAULT NULL,
  `total` double(6,2) DEFAULT NULL,
  `rol` enum('CAMARERO','ROOT','VITRINA') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cuenta`
--

INSERT INTO `cuenta` (`id`, `estado`, `fecha`, `id_usuario`, `metodo_pago`, `cliente`, `total`, `rol`) VALUES
(1, 0, '2025-04-08 18:59:05', 1, 0, 'Roberto', 10.00, ''),
(2, 0, '2025-04-08 18:59:21', 1, 0, 'Fuentes', 10.00, ''),
(3, 0, '2025-04-08 18:59:24', 1, 0, 'Javier', 5.00, ''),
(7, 0, '2025-04-10 17:37:05', 1, 2, 'Davis', 0.00, ''),
(8, 1, '2025-05-10 11:03:01', 1, 0, 'Prueba', 11.00, ''),
(9, 0, '2025-05-13 19:31:01', 1, 3, 'Prueba', 0.00, ''),
(10, 0, '2025-05-13 20:14:57', 1, 3, 'Francisco Javier', 0.00, ''),
(11, 0, '2025-05-14 20:13:09', 1, 3, 'Prueba', 11.00, ''),
(12, 1, '2025-05-17 13:05:04', 1, 1, 'David', 8.00, ''),
(13, 1, '2025-05-17 18:45:32', 1, 0, 'Roberto', 8.00, ''),
(14, 0, '2025-05-17 19:06:16', 1, 3, 'Prueba', 0.00, ''),
(15, 1, '2025-05-17 19:06:20', 1, 2, 'Prueba2', 5.00, ''),
(16, 0, '2025-05-19 09:47:25', 1, 3, 'Pablo', 5.00, ''),
(17, 1, '2025-05-20 10:17:47', 1, 1, 'Prueba', 6.00, ''),
(18, 1, '2025-05-20 10:35:44', 1, 0, 'Prueba', 1.00, ''),
(19, 1, '2025-05-22 18:28:22', 1, 0, 'mario', 14.00, ''),
(20, 1, '2025-05-27 20:40:45', 1, 0, 'sadadasd', 17.00, ''),
(21, 1, '2025-05-27 22:28:50', 3, 0, 'Jesus', 12.00, ''),
(22, 1, '2025-05-27 22:48:58', 3, 1, 'Prueba', 18.00, ''),
(23, 1, '2025-06-02 18:58:22', 1, 0, 'sASasASA', 15.00, ''),
(24, 1, '2025-06-06 17:57:14', 1, 0, 'Prueba', 3.00, ''),
(25, 0, '2025-06-06 17:58:17', 1, 3, 'asdsad', 0.00, ''),
(26, 0, '2025-06-07 12:33:21', 1, 3, 'dasdad', 0.00, ''),
(27, 1, '2025-06-08 12:31:33', 4, 0, 'dsadasd', 15.00, 'CAMARERO'),
(28, 1, '2025-06-08 12:32:42', 4, 1, 'dasdasdadadasd', 14.00, 'CAMARERO'),
(29, 0, '2025-06-08 12:37:15', 1, 3, 'Prueba2', 20.00, 'CAMARERO'),
(30, 1, '2025-06-09 19:42:05', 3, 1, 'dsadad', 1.00, 'CAMARERO'),
(31, 1, '2025-06-09 19:42:14', 4, 0, 'Prueba', 1.00, 'CAMARERO'),
(32, 0, '2025-06-09 19:54:32', 4, 3, 'asdasdasd', 0.00, 'CAMARERO'),
(33, 0, '2025-06-09 19:54:56', 3, 3, 'asdasdasd', 0.00, 'CAMARERO'),
(34, 1, '2025-06-10 17:53:04', 1, 0, 'Prueba2', 17.00, 'CAMARERO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle`
--

CREATE TABLE `detalle` (
  `id` int(11) NOT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `id_cuenta` int(11) DEFAULT NULL,
  `id_producto` int(11) DEFAULT NULL,
  `subtotal` double(6,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle`
--

INSERT INTO `detalle` (`id`, `cantidad`, `id_cuenta`, `id_producto`, `subtotal`) VALUES
(1, 10, 3, 1, 10.00),
(2, 10, 2, 1, 10.00),
(3, 10, 3, 6, 10.00),
(4, 2, 8, 1, 2.00),
(5, 2, 8, 5, 8.00),
(6, 1, 8, 1, 1.00),
(7, 2, 11, 1, 2.00),
(8, 1, 11, 2, 1.00),
(9, 1, 11, 4, 4.00),
(10, 1, 11, 5, 4.00),
(11, 1, 12, 1, 1.00),
(12, 1, 13, 1, 1.00),
(13, 1, 13, 5, 4.00),
(14, 1, 13, 8, 2.00),
(15, 1, 13, 3, 1.00),
(16, 1, 12, 3, 1.00),
(17, 1, 12, 6, 4.00),
(18, 1, 12, 7, 2.00),
(19, 1, 15, 2, 1.00),
(20, 1, 15, 5, 4.00),
(21, 3, 16, 3, 3.00),
(22, 2, 16, 1, 2.00),
(23, 1, 17, 1, 1.00),
(24, 1, 17, 2, 1.00),
(25, 1, 17, 4, 4.00),
(26, 1, 18, 2, 1.00),
(27, 1, 19, 1, 1.00),
(28, 1, 19, 2, 1.00),
(29, 1, 19, 5, 4.00),
(30, 1, 19, 4, 4.00),
(31, 1, 19, 6, 4.00),
(32, 1, 20, 1, 1.00),
(33, 1, 20, 4, 4.00),
(34, 1, 20, 7, 2.00),
(35, 1, 20, 8, 2.00),
(36, 1, 20, 9, 2.00),
(37, 1, 20, 6, 4.00),
(38, 1, 20, 2, 1.00),
(39, 1, 20, 3, 1.00),
(40, 3, 21, 1, 3.00),
(41, 1, 21, 2, 1.00),
(42, 1, 21, 5, 4.00),
(43, 1, 21, 6, 4.00),
(44, 2, 22, 1, 2.00),
(45, 2, 22, 2, 2.00),
(46, 2, 22, 3, 2.00),
(47, 1, 22, 4, 4.00),
(48, 1, 22, 5, 4.00),
(49, 1, 22, 6, 4.00),
(57, 1, 23, 1, 1.00),
(58, 1, 23, 2, 1.00),
(59, 1, 23, 3, 1.00),
(60, 1, 23, 6, 4.00),
(61, 1, 23, 5, 4.00),
(62, 1, 23, 4, 4.00),
(63, 1, 24, 1, 1.00),
(64, 1, 24, 2, 1.00),
(65, 1, 24, 3, 1.00),
(67, 1, 27, 1, 1.00),
(68, 1, 27, 2, 1.00),
(69, 1, 27, 3, 1.00),
(70, 1, 27, 4, 4.00),
(71, 1, 27, 5, 4.00),
(72, 1, 27, 6, 4.00),
(73, 1, 28, 10, 2.00),
(74, 2, 28, 11, 10.00),
(75, 2, 28, 12, 2.00),
(76, 2, 29, 1, 2.00),
(77, 2, 29, 2, 2.00),
(78, 1, 29, 4, 4.00),
(79, 2, 29, 3, 2.00),
(80, 1, 29, 6, 4.00),
(81, 1, 29, 8, 2.00),
(82, 1, 29, 9, 2.00),
(83, 1, 29, 7, 2.00),
(84, 1, 31, 3, 1.00),
(86, 1, 30, 12, 1.00),
(87, 1, 34, 1, 1.00),
(89, 2, 34, 3, 2.00),
(90, 1, 34, 4, 4.00),
(92, 1, 34, 6, 4.00),
(93, 1, 34, 7, 2.00),
(94, 1, 34, 8, 2.00),
(95, 1, 34, 9, 2.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `informe`
--

CREATE TABLE `informe` (
  `id` int(11) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `total_ventas` double(6,2) DEFAULT NULL,
  `ventas_efectivo` int(11) DEFAULT NULL,
  `ventas_tarjeta` int(11) DEFAULT NULL,
  `ventas_ticket` int(11) DEFAULT NULL,
  `total_efectivo` double(10,2) DEFAULT NULL,
  `total_tarjeta` double(10,2) DEFAULT NULL,
  `total_ticket` double(10,2) DEFAULT NULL,
  `detalles_productos` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id` int(11) NOT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `nombre` varchar(30) DEFAULT NULL,
  `precio` double(6,2) DEFAULT NULL,
  `sector` tinyint(4) DEFAULT NULL CHECK (`sector` between 0 and 3),
  `stock` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `imagen`, `nombre`, `precio`, `sector`, `stock`) VALUES
(1, 'imagen.jpg', 'Cerveza', 1.00, 1, 484),
(2, 'imagen2.jpg', 'CocaCola', 1.00, 1, 488),
(3, 'imagen3.jpg', 'Agua', 1.00, 1, 186),
(4, 'imagen4.jpg', 'Hamburguesa', 4.00, 0, 192),
(5, 'imagen5.jpg', 'Serranito', 4.00, 0, 195),
(6, 'imagen6.jpg', 'Pringa', 4.00, 0, 192),
(7, 'imagen7.jpg', 'Ensaladilla', 2.00, 0, 97),
(8, 'imagen8.jpg', 'Papas aliñadas', 2.00, 0, 97),
(9, 'imagen9.jpg', 'Tapa3', 2.00, 0, 97),
(10, 'imagen10.jpg', 'Estampitas', 2.00, 2, 299),
(11, 'imagen11.jpg', 'Medallas', 5.00, 2, 98),
(12, 'imagen12.jpg', 'Pulseras', 1.00, 2, 97);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `contrasenia` varchar(100) NOT NULL,
  `nombre` varchar(30) DEFAULT NULL,
  `rol` enum('CAMARERO','ROOT','VITRINA') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `contrasenia`, `nombre`, `rol`) VALUES
(1, '{bcrypt}$2a$10$A8ri49a5DFFa3iW1q9HjpOiiQTf9kCnSNA5WidEsp9HN8Sgh.mpbu', 'Hermandad', 'ROOT'),
(3, '{bcrypt}$2a$10$A8ri49a5DFFa3iW1q9HjpOiiQTf9kCnSNA5WidEsp9HN8Sgh.mpbu', 'Pablo', 'CAMARERO'),
(4, '{bcrypt}$2a$10$A8ri49a5DFFa3iW1q9HjpOiiQTf9kCnSNA5WidEsp9HN8Sgh.mpbu', 'Vitrina', 'VITRINA');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cuenta`
--
ALTER TABLE `cuenta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpuu4iaiq2yhyvn4ebxcj9uefk` (`id_usuario`);

--
-- Indices de la tabla `detalle`
--
ALTER TABLE `detalle`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjbsn10x9l46yu6do11ohnp7er` (`id_cuenta`),
  ADD KEY `FK9rjec6vq3ln3r145vqqapmxk0` (`id_producto`);

--
-- Indices de la tabla `informe`
--
ALTER TABLE `informe`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cuenta`
--
ALTER TABLE `cuenta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT de la tabla `detalle`
--
ALTER TABLE `detalle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=96;

--
-- AUTO_INCREMENT de la tabla `informe`
--
ALTER TABLE `informe`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cuenta`
--
ALTER TABLE `cuenta`
  ADD CONSTRAINT `FKpuu4iaiq2yhyvn4ebxcj9uefk` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `detalle`
--
ALTER TABLE `detalle`
  ADD CONSTRAINT `FK9rjec6vq3ln3r145vqqapmxk0` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id`),
  ADD CONSTRAINT `FKjbsn10x9l46yu6do11ohnp7er` FOREIGN KEY (`id_cuenta`) REFERENCES `cuenta` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
