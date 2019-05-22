// Este script crea las tablas en DB2

CREATE TABLE /nombre/.Clientes (
  idCliente INTEGER NOT NULL,
  Cliente VARCHAR(45) NOT NULL,
  idZona INTEGER   NOT NULL,
  cuentaHabilitada INTEGER NOT NULL,
  PRIMARY KEY(idCliente)
);

CREATE TABLE /nombre/.Zonas  (
   idZona  INTEGER   NOT NULL,
   Zona  VARCHAR(45) NOT NULL,
  PRIMARY KEY( idZona )
);

CREATE TABLE /nombre/.Proveedores  (
   idProveedor  INTEGER   NOT NULL,
   Proveedor  VARCHAR(45) NOT NULL,
  PRIMARY KEY( idProveedor )
);

CREATE TABLE /nombre/.Rubros  (
   idRubro  INTEGER   NOT NULL,
   Rubro  VARCHAR(45) NOT NULL,
  PRIMARY KEY( idRubro )
);

CREATE TABLE /nombre/.Vendedores  (
   idVendedor  INTEGER   NOT NULL,
   Vendedor  VARCHAR(45) NOT NULL,
   Comision  FLOAT NOT NULL,
  PRIMARY KEY( idVendedor )
);

CREATE TABLE /nombre/.Productos  (
   idProducto  INTEGER   NOT NULL,
   Producto  VARCHAR(45) NOT NULL,
   idRubro  INTEGER   NOT NULL,
   idProveedor  INTEGER   NOT NULL,
   precio  FLOAT NOT NULL,
  PRIMARY KEY( idProducto )
);

CREATE TABLE /nombre/.FacturaCabecera  (
   idFactura  INTEGER   NOT NULL,
   sucursal  INTEGER   NOT NULL,
   numero  INTEGER   NOT NULL,
   fecha  DATE NOT NULL,
   anulada  INTEGER   NOT NULL ,
   idCliente  INTEGER   NOT NULL,
   idVendedor  INTEGER   NOT NULL,
  PRIMARY KEY( idFactura )
);

CREATE TABLE /nombre/.FacturaDetalle  (
   idFactura  int NOT NULL,
   idProducto  int  NOT NULL,
   cantidad  float NOT NULL,
  PRIMARY KEY  ( idFactura , idProducto )
);


ALTER TABLE /nombre/.Clientes  
    ADD CONSTRAINT FK_clientes_zona FOREIGN KEY ( idZona )
        REFERENCES  /nombre/.Zonas ( idZona )
        ON DELETE RESTRICT
        ON UPDATE RESTRICT;

ALTER TABLE  /nombre/.FacturaCabecera
    ADD CONSTRAINT FK_factcab_cli FOREIGN KEY ( idCliente )
        REFERENCES /nombre/.Clientes ( idCliente )
        ON DELETE RESTRICT
        ON UPDATE RESTRICT;

ALTER TABLE  /nombre/.FacturaCabecera
    ADD CONSTRAINT FK_factcab_ven FOREIGN KEY ( idVendedor )
        REFERENCES  /nombre/.Vendedores ( idVendedor )
        ON DELETE RESTRICT
        ON UPDATE RESTRICT;

ALTER TABLE  /nombre/.FacturaDetalle  
    ADD CONSTRAINT FK_facdet_faccab FOREIGN KEY ( idFactura )
        REFERENCES  /nombre/.FacturaCabecera ( idFactura )
        ON DELETE RESTRICT
        ON UPDATE RESTRICT;

ALTER TABLE  /nombre/.FacturaDetalle  
    ADD CONSTRAINT  FK_facdet_prod FOREIGN KEY ( idProducto )
    REFERENCES /nombre/.Productos ( idProducto )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE /nombre/.Productos 
    ADD CONSTRAINT FK_prod_rubros FOREIGN KEY ( idRubro )
        REFERENCES /nombre/.Rubros ( idRubro )
        ON DELETE RESTRICT
        ON UPDATE RESTRICT;

ALTER TABLE /nombre/.Productos 
    ADD CONSTRAINT FK_prod_prov FOREIGN KEY ( idProveedor )
        REFERENCES  /nombre/.Proveedores ( idProveedor )
        ON DELETE RESTRICT
        ON UPDATE RESTRICT;