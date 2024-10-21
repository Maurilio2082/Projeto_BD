-- Removendo as chaves estrangeiras das tabelas
ALTER TABLE ESPECIALIDADE_MEDICO
DROP FOREIGN KEY especialidade_especialidade_medico_fk;

ALTER TABLE ESPECIALIDADE_MEDICO
DROP FOREIGN KEY medico_especialidade_medico_fk;

ALTER TABLE HOSPITAL_MEDICO
DROP FOREIGN KEY medico_hospital_medico_fk;

ALTER TABLE HOSPITAL_MEDICO
DROP FOREIGN KEY hospital_hospital_medico_fk;

ALTER TABLE HOSPITAL DROP FOREIGN KEY endereco_hospital_fk;

ALTER TABLE PACIENTE DROP FOREIGN KEY endereco_paciente_fk;

ALTER TABLE HISTORICO DROP FOREIGN KEY paciente_historico_fk;

ALTER TABLE HISTORICO DROP FOREIGN KEY hospital_historico_fk;

DROP TABLE IF EXISTS ESPECIALIDADE;

DROP TABLE IF EXISTS MEDICO;

DROP TABLE IF EXISTS ESPECIALIDADE_MEDICO;

DROP TABLE IF EXISTS ENDERECO;

DROP TABLE IF EXISTS PACIENTE;

DROP TABLE IF EXISTS HOSPITAL;

DROP TABLE IF EXISTS HISTORICO;

DROP TABLE IF EXISTS HOSPITAL_MEDICO;

CREATE TABLE ESPECIALIDADE (
    ID_ESPECIALIDADE INT AUTO_INCREMENT NOT NULL,
    NOME_ESPECIALIDADE VARCHAR(100) NOT NULL,
    PRIMARY KEY (ID_ESPECIALIDADE)
);

CREATE TABLE MEDICO (
    ID_MEDICO INT AUTO_INCREMENT NOT NULL,
    NOME VARCHAR(100) NOT NULL,
    CONSELHO VARCHAR(50) NOT NULL,
    PRIMARY KEY (ID_MEDICO)
);

CREATE TABLE ESPECIALIDADE_MEDICO (
    ID_ESPECIALIDADE INT NOT NULL,
    ID_MEDICO INT NOT NULL,
    PRIMARY KEY (ID_ESPECIALIDADE, ID_MEDICO)
);

CREATE TABLE ENDERECO (
    ID_ENDERECO INT AUTO_INCREMENT NOT NULL,
    LOGRADOURO VARCHAR(150) NOT NULL,
    NUMERO VARCHAR(10) NOT NULL,
    BAIRRO VARCHAR(50) NOT NULL,
    CIDADE VARCHAR(50) NOT NULL,
    ESTADO VARCHAR(2) NOT NULL,
    CEP VARCHAR(8) NOT NULL,
    PRIMARY KEY (ID_ENDERECO)
);

CREATE TABLE PACIENTE (
    ID_PACIENTE INT AUTO_INCREMENT NOT NULL,
    NOME VARCHAR(100) NOT NULL,
    CPF VARCHAR(11) NOT NULL,
    EMAIL VARCHAR(100) NOT NULL,
    TELEOFNE VARCHAR(11) NOT NULL,
    DATA_NASCIMENTO DATE NOT NULL,
    ID_ENDERECO INT NOT NULL,
    PRIMARY KEY (ID_PACIENTE)
);

CREATE TABLE HOSPITAL (
    ID_HOSPITAL INT AUTO_INCREMENT NOT NULL,
    RAZAO_SOCIAL VARCHAR(150) NOT NULL,
    CNPJ VARCHAR(14) NOT NULL,
    EMAIL VARCHAR(100) NOT NULL,
    TELEFONE VARCHAR(11) NOT NULL,
    CATEGORIA VARCHAR(50) NOT NULL,
    ID_ENDERECO INT NOT NULL,
    PRIMARY KEY (ID_HOSPITAL)
);

CREATE TABLE HISTORICO (
    ID_HISTORICO INT AUTO_INCREMENT NOT NULL,
    DATA_CONSULTA DATE NOT NULL,
    OBSERVACAO VARCHAR(5000) NOT NULL,
    ID_PACIENTE INT NOT NULL,
    ID_HOSPITAL INT NOT NULL,
    PRIMARY KEY (ID_HISTORICO)
);

CREATE TABLE HOSPITAL_MEDICO (
    ID_MEDICO INT NOT NULL,
    ID_HOSPITAL INT NOT NULL,
    PRIMARY KEY (ID_MEDICO, ID_HOSPITAL)
);

ALTER TABLE ESPECIALIDADE_MEDICO
ADD CONSTRAINT especialidade_especialidade_medico_fk FOREIGN KEY (ID_ESPECIALIDADE) REFERENCES ESPECIALIDADE (ID_ESPECIALIDADE) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE ESPECIALIDADE_MEDICO
ADD CONSTRAINT medico_especialidade_medico_fk FOREIGN KEY (ID_MEDICO) REFERENCES MEDICO (ID_MEDICO) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE HOSPITAL_MEDICO
ADD CONSTRAINT medico_hospital_medico_fk FOREIGN KEY (ID_MEDICO) REFERENCES MEDICO (ID_MEDICO) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE HOSPITAL
ADD CONSTRAINT endereco_hospital_fk FOREIGN KEY (ID_ENDERECO) REFERENCES ENDERECO (ID_ENDERECO) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE PACIENTE
ADD CONSTRAINT endereco_paciente_fk FOREIGN KEY (ID_ENDERECO) REFERENCES ENDERECO (ID_ENDERECO) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE HISTORICO
ADD CONSTRAINT paciente_historico_fk FOREIGN KEY (ID_PACIENTE) REFERENCES PACIENTE (ID_PACIENTE) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE HOSPITAL_MEDICO
ADD CONSTRAINT hospital_hospital_medico_fk FOREIGN KEY (ID_HOSPITAL) REFERENCES HOSPITAL (ID_HOSPITAL) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE HISTORICO
ADD CONSTRAINT hospital_historico_fk FOREIGN KEY (ID_HOSPITAL) REFERENCES HOSPITAL (ID_HOSPITAL) ON DELETE NO ACTION ON UPDATE NO ACTION;