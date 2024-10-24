CREATE VIEW MedicosEmHospitais AS
SELECT 
    m.NOME AS NomeMedico, 
    h.RAZAO_SOCIAL AS NomeHospital
FROM 
    HOSPITAL_MEDICO hm
JOIN 
    MEDICO m ON hm.ID_MEDICO = m.ID_MEDICO
JOIN 
    HOSPITAL h ON hm.ID_HOSPITAL = h.ID_HOSPITAL;