// Este script crea las tablas en MySQL

CREATE TABLE `Clientes` (
  `idCliente` INTEGER UNSIGNED NOT NULL,
  `Cliente` VARCHAR(45) NOT NULL,
  `idZona` INTEGER UNSIGNED NOT NULL,
  `cuentaHabilitada` INTEGER UNSIGNED NOT NULL COMMENT '1=Habilitada',
  PRIMARY KEY(`idCliente`)
);

CREATE TABLE `Zonas` (
  `idZona` INTEGER UNSIGNED NOT NULL,
  `Zona` VARCHAR(45) NOT NULL,
  PRIMARY KEY(`idZona`)
);

CREATE TABLE `Proveedores` (
  `idProveedor` INTEGER UNSIGNED NOT NULL,
  `Proveedor` VARCHAR(45) NOT NULL,
  PRIMARY KEY(`idProveedor`)
);

CREATE TABLE `Rubros` (
  `idRubro` INTEGER UNSIGNED NOT NULL,
  `Rubro` VARCHAR(45) NOT NULL,
  PRIMARY KEY(`idRubro`)
);

CREATE TABLE `Vendedores` (
  `idVendedor` INTEGER UNSIGNED NOT NULL,
  `Vendedor` VARCHAR(45) NOT NULL,
  `Comision` FLOAT NOT NULL,
  PRIMARY KEY(`idVendedor`)
);

CREATE TABLE `Productos` (
  `idProducto` INTEGER UNSIGNED NOT NULL,
  `Producto` VARCHAR(45) NOT NULL,
  `idRubro` INTEGER UNSIGNED NOT NULL,
  `idProveedor` INTEGER UNSIGNED NOT NULL,
  `precio` FLOAT NOT NULL,
  PRIMARY KEY(`idProducto`)
);

CREATE TABLE `FacturaCabecera` (
  `idFactura` INTEGER UNSIGNED NOT NULL,
  `sucursal` INTEGER UNSIGNED NOT NULL,
  `numero` INTEGER UNSIGNED NOT NULL,
  `fecha` DATETIME NOT NULL,
  `anulada` INTEGER UNSIGNED NOT NULL COMMENT '1=Anulada',
  `idCliente` INTEGER UNSIGNED NOT NULL,
  `idVendedor` INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(`idFactura`)
);

CREATE TABLE `FacturaDetalle` (
  `idFactura` int(10) unsigned NOT NULL default '0',
  `idProducto` int(10) unsigned NOT NULL default '0',
  `cantidad` float NOT NULL default '0',
  PRIMARY KEY  (`idFactura`,`idProducto`)
);


ALTER TABLE `clientes` 
    ADD CONSTRAINT `FK_clientes_zona` FOREIGN KEY `FK_clientes_zona` (`idZona`)
    REFERENCES `zonas` (`idZona`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE `facturacabecera` 
    ADD CONSTRAINT `FK_facturacabecera_cliente` FOREIGN KEY `FK_facturacabecera_cliente` (`idCliente`)
    REFERENCES `clientes` (`idCliente`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
    ADD CONSTRAINT `FK_facturacabecera_vendedor` FOREIGN KEY `FK_facturacabecera_vendedor` (`idVendedor`)
    REFERENCES `vendedores` (`idVendedor`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE `facturadetalle` 
    ADD CONSTRAINT `FK_facturadetalle_facturacabecera` FOREIGN KEY `FK_facturadetalle_facturacabecera` (`idFactura`)
    REFERENCES `facturacabecera` (`idFactura`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
    ADD CONSTRAINT `FK_facturadetalle_producto` FOREIGN KEY `FK_facturadetalle_producto` (`idProducto`)
    REFERENCES `productos` (`idProducto`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE `productos` ADD CONSTRAINT `FK_productos_rubros` FOREIGN KEY `FK_productos_rubros` (`idRubro`)
    REFERENCES `rubros` (`idRubro`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
    ADD CONSTRAINT `FK_productos_proveedores` FOREIGN KEY `FK_productos_proveedores` (`idProveedor`)
    REFERENCES `proveedores` (`idProveedor`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;