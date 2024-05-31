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

#------- CRUDS -------#

/* CRUD Proveedores */

delimiter $$
create procedure sp_agregarProveedor(in _codigoProveedor int,
									_NITProveedor varchar(10),
									_nombresProveedor varchar(60),
									_apellidosProveedor varchar(60),
									_direccionProveedor varchar(60),
									_razonSocial varchar(150),
									_contactoPrincipal varchar(100),
									_paginaWeb varchar(50))
begin
	insert into Proveedores(codigoProveedor, NITProveedor, nombresProveedor, apellidosProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
    values (_codigoProveedor, _NITProveedor, _nombresProveedor, _apellidosProveedor, _direccionProveedor, _razonSocial, _contactoPrincipal, _paginaWeb);
end$$
delimiter;

delimiter $$
create procedure sp_eliminarProveedor(in _codigoProveedor int)
begin
	delete from Proveedores
    where codigoProveedor = _codigoProveedor;
end$$
delimiter;

delimiter $$
create procedure sp_editarProveedor(in _codigoProveedor int,
									_NITProveedor varchar(10),
									_nombresProveedor varchar(60),
									_apellidosProveedor varchar(60),
									_direccionProveedor varchar(60),
									_razonSocial varchar(150),
									_contactoPrincipal varchar(100),
									_paginaWeb varchar(50))
begin
	update Proveedores
    set NITProveedor = _NITProveedor,
    nombresProveedor = _nombresProveedor,
    apellidosProveedor = _apellidosProveedor,
    direccionProveedor = _direccionProveedor,
    razonSocial = _razonSocial,
    contactoPrincipal = _contactoPrincipal,
    paginaWeb = _paginaWeb
    where codigoProveedor = _codigoProveedor;
end$$
delimiter;

delimiter $$
create procedure sp_reporteProveedor()
begin
	select P.codigoProveedor as 'ID',
    P.NITProveedor as 'NIT',
    P.nombresProveedor as 'Nombres',
    P.apellidosProveedor as 'Apellidos',
    P.direccionProveedor as 'Dirección',
    P.razonSocial as 'Razón Social',
    P.contactoPrincipal as 'Contacto Principal',
    P.paginaWeb as 'Página Web'
    from Proveedores P;
end$$
delimiter;

-- CRUD Telefono Proveedores --

delimiter $$
create procedure sp_agregarTelProveedor(in _codigoTelefonoProveedor int,
											_numeroPrincipal varchar(8),
											_numeroSecundario varchar(8),
											_observaciones varchar(45),
											_codigoProveedor int)
begin
	insert into TelProveedor (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor)
    values (_codigoTelefonoProveedor, _numeroPrincipal, _numeroSecundario, _observaciones, _codigoProveedor);
end$$
delimiter;

delimiter $$
create procedure sp_eliminarTelProveedor(in codigoTelefonoProveedor int)
begin
	delete from TelProveedor
    where codigoTelProveedor = _codigoTelProveedor;
end$$
delimiter;

delimiter $$
create procedure sp_editarTelProveedor(in _codigoTelefonoProveedor int,
											_numeroPrincipal varchar(8),
											_numeroSecundario varchar(8),
											_observaciones varchar(45),
											_codigoProveedor int)
begin
	update TelProveedor
	set numeroPrincipal = _numeroPrincipal,
    numeroSecundario = _numeroSecundario,
    observaciones = _observaciones,
    codigoProveedor = _codigoProveedor
    where codigoTelProveedor = codigoTelProveedor;
end$$
delimiter;

delimiter $$
create procedure sp_reporteTelProveedor()
begin
	select TP.codigoTelProveedor as 'ID',
    TP.numeroPrincipal as 'No. Principal',
    TP.numeroSecundario as 'No. Secundario',
    TP.observaciones as 'Observaciones',
    TP.codigoProveedor as 'Proveedor'
    from TelProveedor TP;
end$$
delimiter;

-- CRUD Email Proveedores --

delimiter $$
create procedure sp_agregarEmailProveedor(in _codigoEmailProveedor int,
												_emailProveedor varchar(50),
												_descripcion varchar(100),
												_codigoProveedor int)
begin
	insert into EmailProveedor (codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor)
    values (_codigoEmailProveedor, _emailProveedor, _descripcion, _codigoProveedor);
end$$
delimiter;

delimiter $$
create procedure sp_eliminarEmailProveedor(in _codigoEmailProveedor int)
begin
	delete from EmailProveedor
    where codigoEmailProveedor = _codigoEmailProveedor;
end$$
delimiter;

delimiter $$
create procedure sp_editarEmailProveedor(in _codigoEmailProveedor int,
											_emailProveedor varchar(50),
											_descripcion varchar(100),
											_codigoProveedor int)
begin
	update EmailProveedor
    set emailProveedor = _emailProveedor,
    descripcion = _descripcion,
    codigoProveedor = _codigoProveedor
    where codigoEmailProveedor = _codigoEmailProveedor;
end$$
delimiter;

delimiter $$
create procedure sp_reporteEmailProveedor()
begin
	select EP.codigoEmailProveedor as 'ID',
    EP.emailProveedor as 'E-Mail',
    EP.descripcion as 'Descripción',
    EP.codigoProveedor as 'Proveedor'
    from EmailProveedor EP;
end$$
delimiter;

/* CRUD Empleados */ 

delimiter $$
create procedure sp_agregarEmpleado(in _codigoEmpleado int,
										_nombresEmpleado varchar(50),
										_apellidosEmpleado varchar(50),
										_sueldo decimal(10,2),
										_direccion varchar(150),
										_turno varchar(15),
                                        _cargoEmpleado int)
begin
	insert into Empleados (codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, cargoEmpleado)
    values (_codigoEmpleado, _nombresEmpleado, _apellidosEmpleado, _sueldo, _direccion, _turno, _cargoEmpleado);
end$$
delimiter;

delimiter $$
create procedure sp_eliminarEmpleado(in _codigoEmpleado int)
begin
	delete from Empleados
    where codigoEmpleado = _codigoEmpleado;
end$$
delimiter;

delimiter $$
create procedure sp_editarEmpleado(in _codigoEmpleado int,
										_nombresEmpleado varchar(50),
										_apellidosEmpleado varchar(50),
										_sueldo decimal(10,2),
										_direccion varchar(150),
										_turno varchar(15),
                                        _cargoEmpleado int)
begin
	update Empleados
    set nombresEmpleado = _nombresEmpleado,
    apellidosEmpleado = _apellidosEmpleado,
    sueldo = _sueldo,
    direccion = _direccion,
    turno = _turno,
    cargo = _cargo;
end$$
delimiter;

delimiter $$
create procedure sp_reporteEmpleado()
begin
	select E.codigoEmpleado as 'ID',
    E.nombresEmpleado as 'Nombres',
    E.apellidosEmpleado as 'Apellidos',
    E.sueldo as 'Sueldo',
    E.direccion = direccion,
    E.turno as 'turno',
    E.cargoEmpleado as 'Puesto'
    from Empleados E;
end$$
delimiter;

-- CRUD Cargo Empleado --

delimiter $$
create procedure sp_agregarCargoEmpleado(in _codigoCargoEmpleado int,
											_nombreCargo varchar(45),
											_descripcionCargo varchar(45))
begin
	insert into CargoEmpleado (codigoCargoEmpleado, nombreCargo, descripcionCargo)
    values (_codigoCargoEmpleado, _nombreCargo, _descripcionCargo);
end$$
delimiter;

delimiter $$
create procedure sp_eliminarCargoEmpleado(in _codigoCargoEmpleado int)
begin
	delete from CargoEmpleado
    where codigoCargoEmpleado = _codigoCargoEmpleado;
end$$
delimiter;

delimiter $$
create procedure sp_editarCargoEmpleado(in _codigoCargoEmpleado int,
											_nombreCargo varchar(45),
											_descripcionCargo varchar(45))
begin
	select CE.codigoCargoEmpleado as 'ID',
    CE.nombreCargo as 'Cargo',
    CE.descripcionCargo as 'Descipción'
    from CargoEmpleado CE;
end$$
delimiter;

delimiter $$
create procedure sp_reporteCargoEmpleado()
begin
	
end$$
delimiter;

/* CRUD Clientes */

delimiter $$
create procedure sp_agregarCliente(in _codigoCliente int, 
										_NITCliente varchar(10), 
										_nombresCliente varchar(50),
                                        _apellidosCliente varchar(50),
                                        _direccionCliente varchar(150),
                                        _telefonoCliente varchar(8),
                                        _emailCliente varchar(45))
begin
	insert into Clientes (codigoCliente, NITCliente, nombresCliente, apellidosCliente, direccionCliente, telefonoCliente, emailCliente)
    values (_codigoCliente, _NITCliente, _nombresCliente, _apellidosCliente, _direccionCliente, _telefonoCliente, _emailCliente);
end$$
delimiter ;

delimiter $$
create procedure sp_eliminarCliente(in _codigoCliente int)
begin
	delete from Clientes
	where codigoCliente = _codigoCliente;
end$$
delimiter ;

delimiter $$
create procedure sp_editarCliente(in _codigoCliente int,
										_NITCliente varchar(10), 
										_nombresCliente varchar(50),
                                        _apellidosCliente varchar(50),
                                        _direccionCliente varchar(150),
                                        _telefonoCliente varchar(8),
                                        _emailCliente varchar(45))
begin
	update Clientes
    set NITCliente = _NITCliente,
    nombresCliente = _nombresCliente,
    apellidosCliente = _apellidosCliente,
    direccionCliente = _direccionCliente,
    telefonoCliente = _telefonoCliente,
    emailCliente = _emailCliente
    where codigoCliente = _codigoCliente;
end$$
delimiter ;

delimiter $$
create procedure sp_reporteCliente()
begin
	select C.codigoCliente as 'ID',
    C.NITCliente as 'NIT',
    C.nombresCliente as 'Nombres',
    C.apellidosCliente as 'Apellidos',
    C.direccionCliente as 'Dirección',
    C.telefonoCliente as 'Teléfono',
    C.emailCliente as 'E-mail'
    from Clientes C;
end$$
delimiter;

/* CRUD Productos */

delimiter $$
create procedure sp_agregarProducto(in _codigoProducto varchar(45),
										_descripcionProducto varchar(45),
										_precioUnitario decimal(10,2),
										_precioDocena decimal(10,2),
										_precioMayor decimal(10,2),
										_cantidadExistencia int,
										_codigoTipoProducto int,
										_codigoProveedor int)
begin
	insert into Productos (codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, cantidadExistencia, codigoTipoProducto, codigoProveedor)
    values (_codigoProducto, _descripcionProducto, _precioUnitario, _precioDocena, _precioMayor, _cantidadExistencia, _codigoTipoProducto, _codigoProveedor);
end$$
delimiter;

delimiter $$
create procedure sp_eliminarProducto(in _codigoProducto varchar(45))
begin
	delete from Productos
    where codigoProducto = _codigoProducto;
end$$
delimiter;

delimiter $$
create procedure sp_editarProducto(in _codigoProducto varchar(45),
										_descripcionProducto varchar(45),
										_precioUnitario decimal(10,2),
										_precioDocena decimal(10,2),
										_precioMayor decimal(10,2),
										_cantidadExistencia int,
										_codigoTipoProducto int,
										_codigoProveedor int)
begin
	update Productos
    set descripcionProducto = _descripcionProducto,
    precioUnitario = _precioUnitario,
    precioDocena = _precioDocena,
    precioMayor = _precioMayor,
    cantidadExistencia = _cantidadExistencia,
    codigoTipoProducto = _codigoTipoProducto,
    codigoProveedor = _codigoProveedor
    where codigoProducto = _codigoProducto;
end$$
delimiter;

delimiter $$
create procedure sp_reporteProducto()
begin
	select Pr.codigoProducto as 'ID',
    Pr.descripcionProducto as 'Descripción',
    Pr.precioUnitario as 'Precio/Unidad',
    Pr.precioDocena as 'Precio/Docena',
    Pr.precioMayor as 'Precio/Mayor',
    Pr.cantidadExistencia as 'Cant. Existencia',
    Pr.codigoTipoProducto as 'Categoría ID',
    Pr.codigoProveedor as 'Proveedor ID'
    from Productos Pr;
end$$
delimiter;

-- CRUD Tipo Producto (Categoria) --

delimiter $$
create procedure sp_agregarTipoProducto(in _codigoTipoProducto int, _descripcion varchar(45))
begin
	insert into TipoProducto (codigoTipoProducto, descripcion)
    values (_codigoTipoProducto, _descripcion);
end$$
delimiter;

delimiter $$
create procedure sp_eliminarTipoProducto(in _codigoTipoProducto int)
begin
	delete from TipoProdcuto
    where codigoTipoProducto = _codigoTipoProducto;
end$$
delimiter;

delimiter $$
create procedure sp_editarTipoProducto(in _codigoTipoProducto int, _descripcion varchar(45))
begin
	update TipoProducto
    set descripcion = _descripcion
    where codigoProducto = _codigoProducto;
end$$
delimiter;

delimiter $$
create procedure sp_reporteTipoProducto()
begin
	select TPr.codigoTipoProducto as 'ID',
    TPr.descripcion as 'Descripción'
    from TipoProducto TPr;
end$$
-- delimiter;

/* Triggers */

