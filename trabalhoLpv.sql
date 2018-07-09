CREATE SEQUENCE "seq_aluno" 
  START with 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

CREATE SEQUENCE "seq_exercicio" 
  START with 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

CREATE SEQUENCE "seq_exercicio_detalhado" 
  START with 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

CREATE SEQUENCE "seq_ritmo" 
  START with 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

CREATE SEQUENCE "seq_usuario" 
  START with 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;  

CREATE TABLE aluno (
  cod_aluno bigint DEFAULT nextval('"seq_aluno"'::regclass) NOT NULL,
  nome character varying(100),
  sexo character varying(10),
  altura integer,
  peso float,
  dataNascimento date,
  email character varying(100),
  cpf character varying(20),
  whatsapp character varying(30),

  CONSTRAINT pk_aluno PRIMARY KEY (cod_aluno)
);

CREATE TABLE exercicio (
  cod_exercicio bigint DEFAULT nextval('"seq_exercicio"'::regclass) NOT NULL,
  cod_aluno bigint NOT NULL,
  nome character varying(150),
  data_exercicio date,
  tempo_inicio bigint,
  tempo_final bigint,
  duracao bigint,
  distancia float,
  calorias_perdidas float,
  passos integer,

  CONSTRAINT pk_exercicio PRIMARY KEY (cod_exercicio),
  CONSTRAINT fk_exercicio
      FOREIGN KEY(cod_aluno)
      REFERENCES aluno (cod_aluno)
      ON DELETE CASCADE
);

CREATE TABLE exercicio_detalhado (
  cod_exercicio_detalhado bigint DEFAULT nextval('"seq_exercicio_detalhado"'::regclass) NOT NULL,
  cod_aluno bigint NOT NULL,
  nome character varying(150),
  data_exercicio date,
  tempo_inicio bigint,
  tempo_final bigint,
  duracao bigint,
  distancia float,
  calorias_perdidas float,
  passos integer,
  velocidade_media float,
  velocidade_maxima float,
  ritmo_medio bigint,
  ritmo_maximo bigint,
  maior_elevacao float,
  menor_elevacao float,
  
  CONSTRAINT pk_exercicio_detalhado PRIMARY KEY (cod_exercicio_detalhado),
  CONSTRAINT fk_exercicio_detalhado
      FOREIGN KEY(cod_aluno)
      REFERENCES aluno (cod_aluno)
      ON DELETE CASCADE
);

CREATE TABLE ritmo(
  cod_ritmo bigint DEFAULT nextval('"seq_ritmo"'::regclass) NOT NULL,
  cod_exercicio_detalhado bigint NOT NULL,
  distancia float,
  ritmo bigint,
  

  CONSTRAINT pk_ritmo PRIMARY KEY (cod_ritmo),
  CONSTRAINT fk_ritmo
      FOREIGN KEY(cod_exercicio_detalhado)
      REFERENCES exercicio_detalhado (cod_exercicio_detalhado)
      ON DELETE CASCADE
);

CREATE TABLE usuario(
  cod_usuario bigint DEFAULT nextval('"seq_usuario"'::regclass) NOT NULL,
  nome_usuario character varying(100),
  senha character varying(100),
  papel character varying(100),

  CONSTRAINT pk_usuario PRIMARY KEY(cod_usuario)
);