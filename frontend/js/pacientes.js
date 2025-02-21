import { apiBase, utils } from './apiHandlers/api.js';

const TABLE = 'paciente';

export const pacientesModule = {
    async carregarPacientes() {
        const ENDPOINT = 'consultar';

        console.log("testando");

        try {
            const pacientes = await apiBase.listar(TABLE, ENDPOINT);
            this.renderizarTabela(pacientes);
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    async cadastrarPaciente(event) {
        const ENDPOINT = 'inserir';

        event.preventDefault();
        try {
            const dados = utils.getFormData(event.target);
            await apiBase.cadastrar(TABLE, ENDPOINT, dados);
            utils.mostrarMensagem('Sucesso', 'Paciente cadastrado com sucesso!');
            event.target.reset();
            await this.carregarPacientes();
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    async carregarPacientePorId() {
        const ENDPOINT = 'consultar';

        if (!id) return;

        try {
            const paciente = await apiBase.carregarPacientes(TABLE, ENDPOINT, id);
            this.preencherFormulario(paciente);
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    async atualizarPaciente(event) {
        const ENDPOINT = 'cadastrar';

        event.preventDefault();
        const id = document.getElementById('id').value;
        try {
            const dados = utils.getFormData(event.target);
            await apiBase.atualizar(TABLE, ENDPOINT, id, dados);
            utils.mostrarMensagem('Sucesso', 'Paciente atualizado com sucesso!');
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    async excluirPaciente(id) {
        const ENDPOINT = 'excluir';

        if (!confirm('Deseja realmente excluir este paciente?')) return;
        
        try {
            await apiBase.excluir(TABLE, ENDPOINT, id);
            utils.mostrarMensagem('Sucesso', 'Paciente excluído com sucesso!');
            await this.carregarPacientes();
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    renderizarTabela(pacientes) {
        const tbody = document.getElementById('patientTable');
        tbody.innerHTML = pacientes.map(paciente => `
            <tr>
                <td>${paciente.nome}</td>
                <td>${paciente.cpf}</td>
                <td>${paciente.sexo}</td>
                <td>${paciente.data_nascimento}</td>
                <td>
                    <a href="/frontend/cadastro/editar/paciente.html?id=${paciente.id}">
                        <button class="w3-button w3-green w3-round">Editar</button>
                    </a>
                    <button class="w3-button w3-red w3-round" 
                            onclick=this.excluirPaciente('${paciente.id}')>
                        Excluir
                    </button>
                </td>
            </tr>
        `).join('');
    },

    preencherFormulario(paciente) {
        Object.keys(paciente).forEach(key => {
            const input = document.getElementById(key);
            if (input) input.value = paciente[key];
        });
    }
};

// Inicialização
document.addEventListener('DOMContentLoaded', () => {
    // Verifica se está na página de listagem
    if (document.getElementById('resultTable-paciente')) {
        pacientesModule.carregarPacientes();
    }

    // Verifica se está na página de edição
    if (utils.obterParametroUrl('id')) {
        pacientesModule.carregarPacientePorId(id);
    }

    // Configura o formulário
    const form = document.querySelector('form');
    if (form) {
        form.addEventListener('submit', (e) => {
            if (utils.obterParametroUrl('id')) {
                pacientesModule.atualizarPaciente(e);
            } else {
                pacientesModule.cadastrarPaciente(e);
            }
        });
    }
});