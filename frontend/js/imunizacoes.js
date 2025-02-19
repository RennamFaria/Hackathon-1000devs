import { apiBase, utils } from './apiHandlers/api.js';

const TABLE = 'imunizacao';

export const imunizacoesModule = {
    async carregarImunizacoes() {
        const ENDPOINT = 'consultar';

        try {
            const imunizacoes = await apiBase.listar(TABLE, ENDPOINT);
            this.renderizarTabela(imunizacoes);
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    //      /imunizacao/inserir
    async cadastrarImunizacao(event) {
        const ENDPOINT = 'inserir';

        event.preventDefault();
        try {
            const dados = utils.getFormData(event.target);
            await apiBase.cadastrar(TABLE, ENDPOINT, dados);
            utils.mostrarMensagem('Sucesso', 'Imunizacao cadastrado com sucesso!');
            event.target.reset();
            await this.carregarImunizacoes();
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },
    
    //   /imunizacao/alterar/:id
    async atualizarImunizacao(event) {
        const ENDPOINT = 'cadastrar';

        event.preventDefault();
        const id = document.getElementById('id').value;
        try {
            const dados = utils.getFormData(event.target);
            await apiBase.atualizar(TABLE, ENDPOINT, id, dados);
            utils.mostrarMensagem('Sucesso', 'Imunizacao atualizado com sucesso!');
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    //    /imunizacao/excluir/:id
    async excluirImunizacao(id) {
        const ENDPOINT = 'excluir';

        if (!confirm('Deseja realmente excluir este imunizacao?')) return;
        
        try {
            await apiBase.excluir(TABLE, ENDPOINT, id);
            utils.mostrarMensagem('Sucesso', 'Imunizacao excluído com sucesso!');
            await this.carregarImunizacoes();
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    //    /imunizacao/excluir/paciente/:id
    async excluirImunizacaoPorPaciente(id) {
        const ENDPOINT = 'excluir';

        if (!confirm('Deseja realmente excluir este imunizacao?')) return;
        
        try {
            await apiBase.excluir(TABLE, ENDPOINT, id);
            utils.mostrarMensagem('Sucesso', 'Imunizacao excluído com sucesso!');
            await this.carregarImunizacoes();
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    //   /imunizacao/consultar
    async carregarImunizacao() {
        const ENDPOINT = 'consultar';

        const id = utils.obterParametroUrl('id');
        if (!id) return;

        try {
            const imunizacao = await apiBase.buscarPorId(TABLE, ENDPOINT, id);
            this.preencherFormulario(imunizacao);
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    //   /imunizacao/consultar/:id
    async carregarImunizacao() {
        const ENDPOINT = 'consultar';

        const id = utils.obterParametroUrl('id');
        if (!id) return;

        try {
            const imunizacao = await apiBase.buscarPorId(TABLE, ENDPOINT, id);
            this.preencherFormulario(imunizacao);
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    //    /imunizacao/consultar/paciente/:id
    async carregarImunizacao() {
        const ENDPOINT = 'consultar';

        const id = utils.obterParametroUrl('id');
        if (!id) return;

        try {
            const imunizacao = await apiBase.buscarPorId(TABLE, ENDPOINT, id);
            this.preencherFormulario(imunizacao);
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    //      imunizacao/consultar/paciente/:id/aplicacao/:dt_ini/:dt_fim
    async carregarImunizacao() {
        const ENDPOINT = 'consultar';

        const id = utils.obterParametroUrl('id');
        if (!id) return;

        try {
            const imunizacao = await apiBase.buscarPorId(TABLE, ENDPOINT, id);
            this.preencherFormulario(imunizacao);
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },



    renderizarTabela(imunizacoes) {
        const tbody = document.getElementById('resultTable-imunizacao');
        tbody.innerHTML = imunizacoes.map(imunizacao => `
            <tr>
                <td>${imunizacao.nome}</td>
                <td>${imunizacao.cpf}</td>
                <td>${imunizacao.sexo}</td>
                <td>${imunizacao.data_nascimento}</td>
                <td>
                    <a href="/frontend/cadastro/editar/imunizacao.html?id=${imunizacao.id}">
                        <button class="w3-button w3-green w3-round">Editar</button>
                    </a>
                    <button class="w3-button w3-red w3-round" 
                            onclick=this.excluirImunizacao('${imunizacao.id}')>
                        Excluir
                    </button>
                </td>
            </tr>
        `).join('');
    },

    preencherFormulario(imunizacao) {
        Object.keys(imunizacao).forEach(key => {
            const input = document.getElementById(key);
            if (input) input.value = imunizacao[key];
        });
    }
};

// Inicialização
document.addEventListener('DOMContentLoaded', () => {
    // Verifica se está na página de listagem
    if (document.getElementById('resultTable-imunizacao')) {
        imunizacoesModule.carregarImunizacoes();
    }

    // Verifica se está na página de edição
    if (utils.obterParametroUrl('id')) {
        imunizacoesModule.carregarImunizacao();
    }

    // Configura o formulário
    const form = document.querySelector('form');
    if (form) {
        form.addEventListener('submit', (e) => {
            if (utils.obterParametroUrl('id')) {
                imunizacoesModule.atualizarImunizacao(e);
            } else {
                imunizacoesModule.cadastrarImunizacao(e);
            }
        });
    }
});