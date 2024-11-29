use("labdatabase"); 

function gerarObjectId() {
    return new ObjectId();
}

db.enderecos.drop();
db.pacientes.drop();
db.hospitais.drop();
db.medicos.drop();
db.especialidades.drop();
db.especialidades_medicos.drop();
db.hospitais_medicos.drop();
db.historicos.drop();


const enderecos = [
    { _id: gerarObjectId(), logradouro: "Rua A", numero: "123", bairro: "Centro", cidade: "Cidade A", estado: "Estado A", cep: "12345-000" },
    { _id: gerarObjectId(), logradouro: "Rua B", numero: "456", bairro: "Bairro B", cidade: "Cidade B", estado: "Estado B", cep: "54321-000" },
    { _id: gerarObjectId(), logradouro: "Rua C", numero: "789", bairro: "Centro", cidade: "Cidade C", estado: "Estado C", cep: "67890-000" }
];
db.enderecos.insertMany(enderecos);

const pacientes = [
    { _id: gerarObjectId(), nome: "Paciente 1", email: "paciente1@email.com", telefone: "11999999999", dataNascimento: "1990-01-01", cpf: "11111111111", enderecoId: enderecos[0]._id },
    { _id: gerarObjectId(), nome: "Paciente 2", email: "paciente2@email.com", telefone: "21988888888", dataNascimento: "1985-05-20", cpf: "22222222222", enderecoId: enderecos[1]._id }
];
db.pacientes.insertMany(pacientes);

const hospitais = [
    { _id: gerarObjectId(), razaoSocial: "Hospital A", cnpj: "12345678900001", email: "hospitalA@email.com", telefone: "31977777777", categoria: "Geral", enderecoId: enderecos[2]._id },
    { _id: gerarObjectId(), razaoSocial: "Hospital B", cnpj: "98765432100001", email: "hospitalB@email.com", telefone: "31966666666", categoria: "Especializado", enderecoId: enderecos[1]._id }
];
db.hospitais.insertMany(hospitais);

const medicos = [
    { _id: gerarObjectId(), nome: "Dr. João", conselho: "CRM 12345" },
    { _id: gerarObjectId(), nome: "Dra. Maria", conselho: "CRM 67890" }
];
db.medicos.insertMany(medicos);

const especialidades = [
    { _id: gerarObjectId(), nomeEspecialidade: "Cardiologia" },
    { _id: gerarObjectId(), nomeEspecialidade: "Ortopedia" }
];
db.especialidades.insertMany(especialidades);

const especialidadesMedicos = [
    { _id: gerarObjectId(), medicoId: medicos[0]._id, especialidadeId: especialidades[0]._id },
    { _id: gerarObjectId(), medicoId: medicos[1]._id, especialidadeId: especialidades[1]._id }
];
db.especialidades_medicos.insertMany(especialidadesMedicos);

const hospitaisMedicos = [
    { _id: gerarObjectId(), medicoId: medicos[0]._id, hospitalId: hospitais[0]._id },
    { _id: gerarObjectId(), medicoId: medicos[1]._id, hospitalId: hospitais[1]._id }
];
db.hospitais_medicos.insertMany(hospitaisMedicos);

const historicos = [
    {
        _id: gerarObjectId(),
        dataConsulta: "2024-01-01",
        observacao: "Consulta inicial",
        pacienteId: pacientes[0]._id,
        hospitalId: hospitais[0]._id,
        medicoId: medicos[0]._id,
        especialidadeId: especialidades[0]._id
    },
    {
        _id: gerarObjectId(),
        dataConsulta: "2024-01-15",
        observacao: "Retorno",
        pacienteId: pacientes[1]._id,
        hospitalId: hospitais[1]._id,
        medicoId: medicos[1]._id,
        especialidadeId: especialidades[1]._id
    }
];
db.historicos.insertMany(historicos);

print("Banco de dados populado com sucesso!");
