INSERT INTO ESPECIALIDADE (NOME_ESPECIALIDADE) VALUES
('Cardiologia'),
('Pediatria'),
('Dermatologia'),
('Ortopedia'),
('Ginecologia'),
('Neurologia'),
('Oftalmologia'),
('Psiquiatria'),
('Endocrinologia'),
('Urologia');



INSERT INTO MEDICO (NOME, CONSELHO) VALUES
('Dr. João Silva', 'CRM-ES 12345'),
('Dra. Maria Oliveira', 'CRM-ES 54321'),
('Dr. Carlos Pereira', 'CRM-ES 67890'),
('Dra. Ana Souza', 'CRM-ES 09876'),
('Dr. Lucas Mendes', 'CRM-ES 13579'),
('Dra. Fernanda Costa', 'CRM-ES 24680'),
('Dr. Ricardo Almeida', 'CRM-ES 11223'),
('Dra. Juliana Rocha', 'CRM-ES 33445'),
('Dr. André Lima', 'CRM-ES 55667'),
('Dra. Tatiane Santos', 'CRM-ES 77889');





INSERT INTO ENDERECO (LOGRADOURO, NUMERO, BAIRRO, CIDADE, ESTADO, CEP) VALUES
('Rua das Flores', '100', 'Centro', 'Vitória', 'ES', '29010000'),
('Avenida Paulista', '200', 'Jardins', 'Vila Velha', 'ES', '29100000'),
('Praça da Paz', '300', 'Praia do Canto', 'Vitória', 'ES', '29055000'),
('Rua do Comércio', '400', 'Centro', 'Cariacica', 'ES', '29140000'),
('Avenida Brasília', '500', 'Morada de Laranjeiras', 'Serra', 'ES', '29160000'),
('Rua da Esperança', '600', 'Jardim da Penha', 'Vitória', 'ES', '29065000'),
('Avenida Vitória', '700', 'Bela Vista', 'Vila Velha', 'ES', '29101000'),
('Rua das Acácias', '800', 'Mário Cypreste', 'Viana', 'ES', '29116000'),
('Rua dos Coqueiros', '900', 'Jardim América', 'Serra', 'ES', '29161000'),
('Avenida das Américas', '1000', 'Praia da Costa', 'Vila Velha', 'ES', '29102000'),
('Rua da Liberdade', '1100', 'Centro', 'Vitória', 'ES', '29012000'),
('Avenida do Sol', '1200', 'Morro do Moreno', 'Vila Velha', 'ES', '29102001'),
('Praça da Alegria', '1300', 'Santa Lúcia', 'Vitória', 'ES', '29050000'),
('Rua do Bem-Te-Vi', '1400', 'Bela Vista', 'Vila Velha', 'ES', '29102002'),
('Avenida da Praia', '1500', 'Praia do Canto', 'Vitória', 'ES', '29056000'),
('Rua da União', '1600', 'Morada de Laranjeiras', 'Serra', 'ES', '29161001'),
('Avenida Central', '1700', 'Jardim da Penha', 'Vitória', 'ES', '29066000'),
('Rua da Amizade', '1800', 'Ponta da Fruta', 'Vila Velha', 'ES', '29102003'),
('Praça da Liberdade', '1900', 'Centro', 'Cariacica', 'ES', '29141000'),
('Rua do Trabalhador', '2000', 'Jardim Camburi', 'Vitória', 'ES', '29065001'),
('Avenida do Futuro', '2100', 'Serra Dourada', 'Serra', 'ES', '29161002');




INSERT INTO HOSPITAL (RAZAO_SOCIAL, CNPJ, EMAIL, TELEFONE, CATEGORIA, ID_ENDERECO) VALUES
('Hospital de Câncer de Barretos', '12345678000190', 'contato@hcbarretos.com.br', '27990000001', 'Oncológico', 1),
('Hospital Universitário da UFES', '23456789000101', 'contato@huufes.br', '27990000002', 'Universitário', 2),
('Hospital Santa Rita', '34567890000112', 'santarita@hospital.com', '27990000003', 'Geral', 3),
('Hospital São Lucas', '45678901000123', 'sao.lucas@hospital.com.br', '27990000004', 'Geral', 4),
('Hospital Metropolitano', '56789012000134', 'metropolitano@hospital.com.br', '27990000005', 'Geral', 5),
('Hospital da Mulher', '67890123000145', 'hospitalmulher@hospital.com.br', '27990000006', 'Ginecológico', 6),
('Hospital Evangélico', '78901234000156', 'hospital@evangelico.com.br', '27990000007', 'Geral', 7),
('Hospital Infantil', '89012345000167', 'infantil@hospital.com.br', '27990000008', 'Pediátrico', 8),
('Hospital de Trauma', '90123456000178', 'trauma@hospital.com.br', '27990000009', 'Traumatológico', 9),
('Hospital Prontoclínica', '01234567000189', 'prontoclinica@hospital.com.br', '27990000010', 'Geral', 10);




INSERT INTO PACIENTE (NOME, CPF, EMAIL, TELEFONE, DATA_NASCIMENTO, ID_ENDERECO) VALUES
('Tatiane Almeida', '12345678902', 'tatiane.almeida@gmail.com', '27999988888', '1991-01-15', 11),
('Leonardo Silva', '23456789013', 'leonardo.silva@gmail.com', '27988877777', '1983-05-20', 12),
('Aline Ferreira', '34567890124', 'aline.ferreira@gmail.com', '27977766666', '1986-03-10', 13),
('Juliano Santos', '45678901235', 'juliano.santos@gmail.com', '27966655555', '1994-11-25', 14),
('Marcos Paulo', '56789012346', 'marcos.paulo@gmail.com', '27955544444', '1989-02-18', 15),
('Isabela Oliveira', '67890123457', 'isabela.oliveira@gmail.com', '27944433333', '1992-12-05', 16),
('Rafael Martins', '78901234568', 'rafael.martins@gmail.com', '27933322222', '1981-09-30', 17),
('Larissa Souza', '89012345679', 'larissa.souza@gmail.com', '27922211111', '1990-06-22', 18),
('Gustavo Costa', '90123456780', 'gustavo.costa@gmail.com', '27911100000', '1985-04-14', 19),
('Daniele Lima', '01234567891', 'daniele.lima@gmail.com', '27900099999', '1987-08-28', 20);



INSERT INTO HISTORICO (DATA_CONSULTA, OBSERVACAO, ID_PACIENTE, ID_HOSPITAL, ID_MEDICO, ID_ESPECIALIDADE) VALUES
('2023-01-15', 'Consulta de rotina, sem anormalidades.', 1, 1, 1, 1),
('2023-02-20', 'Exame de sangue solicitado.', 2, 2, 2, 2),
('2023-03-05', 'Diagnóstico de dermatite.', 3, 3, 3, 3),
('2023-04-10', 'Avaliação ortopédica, necessidade de fisioterapia.', 4, 4, 4, 4),
('2023-05-15', 'Acompanhamento ginecológico, exames normais.', 5, 5, 5, 5),
('2023-06-20', 'Consulta neurológica, recomendações para tratamento.', 6, 6, 6, 6),
('2023-07-25', 'Avaliação oftalmológica, prescrição de óculos.', 7, 7, 7, 7),
('2023-08-30', 'Consulta psiquiátrica, aumento da medicação.', 8, 8, 8, 8),
('2023-09-15', 'Exame de sangue e acompanhamento endocrinológico.', 9, 9, 9, 9),
('2023-10-20', 'Consulta urológica, sem anormalidades.', 10, 10, 10, 10);



INSERT INTO ESPECIALIDADE_MEDICO (ID_ESPECIALIDADE, ID_MEDICO) VALUES
(1, 1), (1, 3), (2, 2), (3, 4),
(4, 5), (5, 6), (6, 7), (7, 8),
(8, 9), (9, 10);


INSERT INTO HOSPITAL_MEDICO (ID_MEDICO, ID_HOSPITAL) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);