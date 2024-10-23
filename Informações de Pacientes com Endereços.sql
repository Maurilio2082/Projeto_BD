CREATE VIEW PacientesComEndereco AS
SELECT 
    p.NOME, 
    p.CPF, 
    p.EMAIL, 
    e.LOGRADOURO, 
    e.NUMERO, 
    e.BAIRRO, 
    e.CIDADE, 
    e.ESTADO, 
    e.CEP
FROM 
    PACIENTE p
JOIN 
    ENDERECO e ON p.ID_ENDERECO = e.ID_ENDERECO;