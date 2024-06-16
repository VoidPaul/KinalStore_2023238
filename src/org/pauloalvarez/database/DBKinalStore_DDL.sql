-- drop database if exists DBKinalStore;

create database if not exists DBKinalStore;
use DBKinalStore;

-- Tablas Kinal Store --

create table Clientes (
	codigoCliente int not null, -- auto_increment,
    NITCliente varchar(10) not null,
    nombresCliente varchar(50) not null,
    apellidosCliente varchar(50) not null,
    direccionCliente varchar(150),
    telefonoCliente varchar(8),
    emailCliente varchar(45),
    primary key PK_codigoCliente (codigoCliente)
);

create table CargoEmpleado (
	codigoCargoEmpleado int not null auto_increment,
    nombreCargo varchar(45),
    descripcionCargo varchar(45),
    primary key PK_cargoEmpleado (codigoCargoEmpleado)
);

create table Empleados (
	codigoEmpleado int not null auto_increment,
    nombresEmpleado varchar(50),
    apellidosEmpleado varchar(50),
    sueldo decimal(10,2),
    direccion varchar(150),
    turno varchar(15),
    cargoEmpleado int,
    primary key PK_codigoEmpleado (codigoEmpleado),
    constraint FK_cargoEmpleado foreign key (cargoEmpleado)
		references CargoEmpleado (codigoCargoEmpleado)
);

create table Proveedores (
	codigoProveedor int not null,
    NITProveedor varchar(10),
    nombresProveedor varchar(60),
    apellidosProveedor varchar(60),
    direccionProveedor varchar(60),
    razonSocial varchar(150),
    contactoPrincipal varchar(100),
    paginaWeb varchar(50),
    primary key PK_codigoProveedor (codigoProveedor)
);

create table TelProveedor (
	codigoTelefonoProveedor int not null auto_increment,
    numeroPrincipal varchar(8),
    numeroSecundario varchar(8),
    observaciones varchar(45),
    codigoProveedor int,
    primary key PK_codigoTelefonoProveedor (codigoTelefonoProveedor),
    constraint FK_telefonoCodigoProveedor foreign key (codigoProveedor)
		references Proveedores (codigoProveedor)
); 

create table EmailProveedor (
	codigoEmailProveedor int not null auto_increment,
    emailProveedor varchar(50),
    descripcion varchar(100),
    codigoProveedor int,
    primary key PK_codigoEmailProveedor (codigoEmailProveedor),
    constraint FK_emailCodigoProveedor foreign key (codigoProveedor)
		references Proveedores (codigoProveedor)
);

create table TipoProducto (
	codigoTipoProducto int,
    descripcion varchar(45),
    primary key PK_codigoTipoProducto (codigoTipoProducto)
);

create table Productos (
	codigoProducto varchar(45),
    descripcionProducto varchar(45),
    precioUnitario decimal(10,2),
    precioDocena decimal(10,2),
    precioMayor decimal(10,2),
    cantidadExistencia int,
    codigoTipoProducto int,
    codigoProveedor int,
    primary key PK_codigoProducto (codigoProducto),
    constraint FK_tipoProducto foreign key (codigoTipoProducto)
		references TipoProducto (codigoTipoProducto),
	constraint FK_proveedorProducto foreign key (codigoProveedor)
		references Proveedores (codigoProveedor)
);

create table Compras (
	numeroCompra int,
    fechaCompra date,
    descripcion varchar(60),
    totalCompra decimal(10,2),
    primary key PK_numeroCompra (numeroCompra)
);

create table Factura (
	numeroFactura int,
    estado varchar(50),
    totalFactura decimal(10,2),
    fechaFactura date,
    codigoCliente int,
    codigoEmpleado int,
    primary key PK_numeroFactura (numeroFactura),
    constraint FK_clienteFactura foreign key (codigoCliente)
		references Clientes (codigoCliente),
	constraint FK_empleadoFactura foreign key (codigoEmpleado)
		references Empleados (codigoEmpleado)
);

create table InfoCompra (
	codigoInfoCompra int,
    costoUnitario decimal(10,2),
    cantidad int,
    codigoProducto varchar(15),
    numeroDocumento int,
    primary key PK_codigoInfoCompra (codigoInfoCompra),
    constraint FK_productoCompra foreign key (codigoProducto)
		references Productos (codigoProducto),
	constraint FK_numeroCompra foreign key (numeroDocumento)
		references Compras (numeroCompra)
);

create table InfoFactura (
	codigoInfoFactura int,
    precioUnitario decimal(10,2),
    cantidad int,
    numeroFactura int,
    codigoProducto varchar(15),
    primary key PK_codigoInfoFactura (codigoInfoFactura),
    constraint FK_numeroFactura foreign key (numeroFactura)
		references Factura (numeroFactura),
	constraint FK_productoFactura foreign key (codigoProducto)
		references Productos (codigoProducto)
);