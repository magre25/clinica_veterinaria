CREATE DATABASE vetclinica;
USE vetclinica;

set sql_safe_updates = 0; 
/*
CREATE TABLE Cliente(
	codCliente int auto_increment,
	nome VARCHAR(50),
    telefone VARCHAR(20),
	primary key(codCliente)
);*/

CREATE TABLE Especie
(
	codEspecie CHAR(6), 
    descricaoEspecie VARCHAR(100),
    primary key(codEspecie)
) ENGINE = INNODB; 

CREATE TABLE Raca
(
	codRaca CHAR(6), 
    descricaoRaca VARCHAR(100),
    primary key(codRaca)
) ENGINE = INNODB; 

CREATE TABLE Animal
( 
	codAnimal int auto_increment, 
    nome VARCHAR(50), 
    codRaca CHAR(6), 
    codEspecie CHAR(6),
    primary key(codAnimal),
    foreign key(codRaca) references Raca(codRaca) on delete cascade on update cascade,
    foreign key(codEspecie) references Especie(codEspecie) on delete cascade on update cascade
) ENGINE = INNODB;

CREATE TABLE Servicos
( 
	codServico CHAR(6), 
    descricaoServicos VARCHAR(100),
    precoHora NUMERIC(6,2),
    primary key(codServico)
);

CREATE TABLE Veterinario
 (
    CRMV CHAR(6),
	nome VARCHAR(50),
    telefone VARCHAR(20),
	primary key(CRMV)
) ENGINE = INNODB;

CREATE TABLE Consulta
(
	codConsulta int auto_increment,
    CRMV CHAR(6),
	codAnimal int,
    codServico CHAR(6),
    dataHora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    primary key(codConsulta),
    foreign key(CRMV) references Veterinario(CRMV) on delete cascade on update cascade,
    foreign key(codAnimal) references Animal(codAnimal) on delete cascade on update cascade,
    foreign key(codServico) references Servicos(codServico) on delete cascade on update cascade
) ENGINE = INNODB;


insert into servicos
values
('S19', 'Banho e Tosa', 30.00),
('S20', 'Internação 24h', 100.00),
('S21', 'Exames Laboratoriais',200.00);
 
insert into veterinario
values
('3040A', 'Karina Mattos', '(16)98129-9081');

insert into veterinario
values
('7852A', 'Lineu Augusto Filho', '(16)99415-0136');

insert into consulta
values
(60, '3040A', 11, 'S19', sysdate(), 55.00);

/*alter table consulta add column total numeric(6,2) after dataHora;*/

select * from consulta;
select * from servicos;
select * from animal;
select * from especie;
select * from raca;
select * from veterinario;

SELECT * FROM CONSULTAS;
/*
create view consultas as
select codConsulta as Código, a.nome as Animal, v.nome as Veterinario, 
s.descricaoServicos as Serviço, c.dataHora as Data_consulta, c.total as Preço_R$
from consulta c
join animal a
using(codAnimal)
join veterinario v
using(CRMV)
join servicos s 
using(codServico);
*/
