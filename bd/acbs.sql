-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-04-2024 a las 04:27:24
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `acbs`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `vehiculo` varchar(20) DEFAULT NULL,
  `idcliente` int(11) NOT NULL,
  `cedula` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`vehiculo`, `idcliente`, `cedula`) VALUES
('SRL501', 1, 1193537703),
('UTF209', 2, 15034107);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contribucion`
--

CREATE TABLE `contribucion` (
  `fecha` date DEFAULT NULL,
  `nombre` varchar(20) DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `idsocio` int(11) DEFAULT NULL,
  `numero` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `egresos`
--

CREATE TABLE `egresos` (
  `nombre` varchar(20) DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `numero` int(11) NOT NULL,
  `fecha` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas`
--

CREATE TABLE `personas` (
  `fechanaci` date DEFAULT NULL,
  `cedula` int(11) NOT NULL,
  `nombre` varchar(40) DEFAULT NULL,
  `apellido` varchar(40) DEFAULT NULL,
  `telefono` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `personas`
--

INSERT INTO `personas` (`fechanaci`, `cedula`, `nombre`, `apellido`, `telefono`) VALUES
('1980-01-10', 15034107, 'Neovis', 'Negrette', 310742107),
('1985-03-23', 30660345, 'Deivis', 'Avila', 321551397),
('1975-04-21', 59896322, 'Pepito', 'Casillas', 123456789),
('2001-10-30', 1193537703, 'Carlos', 'Negrette', 123456789);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicio`
--

CREATE TABLE `servicio` (
  `precio` double DEFAULT NULL,
  `descripcion` varchar(60) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `codigoser` int(11) NOT NULL,
  `nombre` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicioprestado`
--

CREATE TABLE `servicioprestado` (
  `fecha` date DEFAULT NULL,
  `placa` varchar(20) DEFAULT NULL,
  `idcliente` int(11) DEFAULT NULL,
  `idtrabajador` int(11) DEFAULT NULL,
  `codigoser` int(11) DEFAULT NULL,
  `numero` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `socio`
--

CREATE TABLE `socio` (
  `contribucion` double DEFAULT NULL,
  `idsocio` int(11) NOT NULL,
  `cedula` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `socio`
--

INSERT INTO `socio` (`contribucion`, `idsocio`, `cedula`) VALUES
(300000, 1, 1193537703),
(300000, 2, 15034107),
(500000, 3, 59896322);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajador`
--

CREATE TABLE `trabajador` (
  `sueldo` double DEFAULT NULL,
  `idtrabajador` int(11) NOT NULL,
  `cedula` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculo`
--

CREATE TABLE `vehiculo` (
  `placa` varchar(20) NOT NULL,
  `tipo` varchar(20) DEFAULT NULL,
  `modelo` int(4) DEFAULT NULL,
  `marca` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `vehiculo`
--

INSERT INTO `vehiculo` (`placa`, `tipo`, `modelo`, `marca`) VALUES
('SRL501', 'Automóvil', 2021, 'Dodge'),
('UTF209', 'Camioneta', 2006, 'Chevrolet');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`idcliente`),
  ADD KEY `cedula` (`cedula`);

--
-- Indices de la tabla `contribucion`
--
ALTER TABLE `contribucion`
  ADD PRIMARY KEY (`numero`),
  ADD KEY `idsocio` (`idsocio`);

--
-- Indices de la tabla `egresos`
--
ALTER TABLE `egresos`
  ADD PRIMARY KEY (`numero`);

--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`cedula`);

--
-- Indices de la tabla `servicio`
--
ALTER TABLE `servicio`
  ADD PRIMARY KEY (`codigoser`);

--
-- Indices de la tabla `servicioprestado`
--
ALTER TABLE `servicioprestado`
  ADD PRIMARY KEY (`numero`),
  ADD KEY `idcliente` (`idcliente`),
  ADD KEY `placa` (`placa`),
  ADD KEY `idtrabajador` (`idtrabajador`),
  ADD KEY `codigoser` (`codigoser`);

--
-- Indices de la tabla `socio`
--
ALTER TABLE `socio`
  ADD PRIMARY KEY (`idsocio`),
  ADD KEY `cedula` (`cedula`);

--
-- Indices de la tabla `trabajador`
--
ALTER TABLE `trabajador`
  ADD PRIMARY KEY (`idtrabajador`),
  ADD KEY `cedula` (`cedula`);

--
-- Indices de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  ADD PRIMARY KEY (`placa`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `contribucion`
--
ALTER TABLE `contribucion`
  MODIFY `numero` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `servicioprestado`
--
ALTER TABLE `servicioprestado`
  MODIFY `numero` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`cedula`) REFERENCES `personas` (`cedula`);

--
-- Filtros para la tabla `contribucion`
--
ALTER TABLE `contribucion`
  ADD CONSTRAINT `contribucion_ibfk_1` FOREIGN KEY (`idsocio`) REFERENCES `socio` (`idsocio`);

--
-- Filtros para la tabla `servicioprestado`
--
ALTER TABLE `servicioprestado`
  ADD CONSTRAINT `servicioprestado_ibfk_1` FOREIGN KEY (`idcliente`) REFERENCES `cliente` (`idcliente`),
  ADD CONSTRAINT `servicioprestado_ibfk_2` FOREIGN KEY (`placa`) REFERENCES `vehiculo` (`placa`),
  ADD CONSTRAINT `servicioprestado_ibfk_3` FOREIGN KEY (`idtrabajador`) REFERENCES `trabajador` (`idtrabajador`),
  ADD CONSTRAINT `servicioprestado_ibfk_4` FOREIGN KEY (`codigoser`) REFERENCES `servicio` (`codigoser`);

--
-- Filtros para la tabla `socio`
--
ALTER TABLE `socio`
  ADD CONSTRAINT `socio_ibfk_1` FOREIGN KEY (`cedula`) REFERENCES `personas` (`cedula`);

--
-- Filtros para la tabla `trabajador`
--
ALTER TABLE `trabajador`
  ADD CONSTRAINT `trabajador_ibfk_1` FOREIGN KEY (`cedula`) REFERENCES `personas` (`cedula`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
