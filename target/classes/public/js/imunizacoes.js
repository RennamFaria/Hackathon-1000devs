import { apiBase, utils } from './apiHandlers/api.js';
import { apiImunizacao } from './apiHandlers/apiImunizacoes.js';

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

    // Ainda falta tela
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

    // Ainda falta tela
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

    async excluirImunizacao(id) {
        const ENDPOINT = 'excluir';

        if (!confirm('Deseja realmente excluir este imunizacao?\n\nSim - OK\nNão - Cancelar')) return;
        
        try {
            await apiBase.excluir(TABLE, ENDPOINT, id);
            utils.mostrarMensagem('Sucesso', 'Imunizacao excluído com sucesso!');
            await this.carregarImunizacoes();
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    async carregarImunizacaoPorId(id) {
        const ENDPOINT = 'consultar';

        if (!id) return;  

        try {
            const imunizacoes = await apiBase.buscarPorId(TABLE, ENDPOINT, id);
            this.renderizarTabela(imunizacoes);
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    // Essa funcao sera chamada pela tela Paciente
    async excluirImunizacaoPorIdPaciente(id) {
        const ENDPOINT = 'excluir';

        if (!confirm('Deseja realmente excluir TODAS imunizações do Paciente?\n\nSim - OK\nNão - Cancelar')) return;
        
        try {
            await apiBase.excluir(TABLE, ENDPOINT, id);
            utils.mostrarMensagem('Sucesso', 'Imunizacao excluído com sucesso!');
            await this.carregarImunizacoes();
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    async carregarImunizacaoPorIdPaciente(id) {
        const ENDPOINT = 'consultar/paciente';

        if (!id) return;  

        try {
            const imunizacoes = await apiBase.buscarPorId(TABLE, ENDPOINT, id);
            this.renderizarTabela(imunizacoes);
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    async carregarImunizacaoPorIdPacienteEIntervalo(id, intervIni, intervFim) {
        const ENDPOINT1 = 'consultar/paciente';
        const ENDPOINT2 = 'aplicacao';

        if (!id) return;  

        try {
            const imunizacoes = await apiImunizacao.buscarPorIdPacienteEIntervalo(TABLE, ENDPOINT1, ENDPOINT2, id, intervIni, intervFim);
            this.renderizarTabela(imunizacoes);
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    renderizarTabela(imunizacoes) {
        const tbody = document.getElementById(
            window.location.href.includes('porPaciente') ? 
            'resultTable-imunizacaoPaciente' : 
            'resultTable-imunizacao'
        );

        const imunizacoesArray = Array.isArray(imunizacoes) ? imunizacoes : [imunizacoes];

        tbody.innerHTML = imunizacoesArray.map(imunizacao => `
            <tr class = "border border-2 border-dark rounded">
                <td>${imunizacao.idImunizacao && imunizacao.idImunizacao !== '' ? imunizacao.idImunizacao : '---'}</td>
                <td>${imunizacao.nomePaciente && imunizacao.nomePaciente !== '' ? imunizacao.nomePaciente : 'Não informado'}</td>
                <td>${imunizacao.nomeVacina && imunizacao.nomeVacina !== '' ? imunizacao.nomeVacina : 'Não especificada'}</td>
                <td>${imunizacao.dose && imunizacao.dose !== '' ? imunizacao.dose : '---'}</td>
                <td>${utils.formatarData(imunizacao.dataAplicacao)}</td>
                <td>${imunizacao.fabricante && imunizacao.fabricante !== '' ? imunizacao.fabricante : 'Não informado'}</td>
                <td>${imunizacao.lote && imunizacao.lote !== '' ? imunizacao.lote : '---'}</td>
                <td>${imunizacao.localAplicacao && imunizacao.localAplicacao !== '' ? imunizacao.localAplicacao : 'Local não informado'}</td>
                <td>${imunizacao.profissionalAplicador && imunizacao.profissionalAplicador !== '' ? imunizacao.profissionalAplicador : 'Não informado'}</td>
                <td>
                    <a href="/frontend/imunizacao/atualizar/atualizar.html?id=${imunizacao.id}">
                        <button class="w3-button w3-green w3-round">Editar</button>
                    </a>
                    <button class="w3-button w3-red w3-round excluir-btn" 
                        data-id="${imunizacao.idImunizacao}">
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
    // Executa esse codigo apenas para a pagina Imunizacao e nao ImunizacaoPaciente
    if (document.getElementById('type-search')) {
        const typeSearch = document.getElementById('type-search');
        const additionalSearchContainer = document.getElementById('additional-search-container');
        const searchButton = document.getElementById('searchButton');

        function createDateInputs() {
            return `
                <input type="date" id="intervalo-inicial" class="form-control" placeholder="Data Inicial">
                <input type="date" id="intervalo-final" class="form-control" placeholder="Data Final">
            `;
        }

        typeSearch.addEventListener('change', function() {
            additionalSearchContainer.innerHTML = '';
            if (this.value === 'idPaciente-IntervaloApl') {
                additionalSearchContainer.innerHTML = createDateInputs();
            }
        });
    }
    
    // Verifica se está na página de listagem
    if (document.getElementById('resultTable-imunizacao')) {
        imunizacoesModule.carregarImunizacoes();
    }

    if (document.getElementById('resultTable-imunizacaoPaciente')) {
        const urlParams = new URLSearchParams(window.location.search);
        const id = urlParams.get('id');
        const deleteAllButton = document.getElementById('searchButton');

        if (id) {
            imunizacoesModule.carregarImunizacaoPorIdPaciente(id);
        }

        if (deleteAllButton) {
            deleteAllButton.addEventListener('click', () => {
                
                imunizacoesModule.excluirImunizacaoPorIdPaciente(id);
            });
        }
    }

    // Verifica o tipo de pesquisa e executa a funcao correta
    if(searchButton) {
        searchButton.addEventListener('click', () => {
            const select = document.getElementById('type-search');
            const selectedValue = select.value;
            const valueSearchBox = document.getElementById('search-bar').value;
            
            switch(selectedValue) {
                case 'idImunizacao':
                    imunizacoesModule.carregarImunizacaoPorId(valueSearchBox);
                    break;
                case 'idPaciente':
                    imunizacoesModule.carregarImunizacaoPorIdPaciente(valueSearchBox);
                    break;
                case 'idPaciente-IntervaloApl':
                    const intervIni = document.getElementById('intervalo-inicial').value;
                    const intervFim = document.getElementById('intervalo-final').value;
                    imunizacoesModule.carregarImunizacaoPorIdPacienteEIntervalo(valueSearchBox, intervIni, intervFim);
                    break;
                default:
                    alert('Tipo de pesquisa não selecionado');
            }
        });
    }

    document.addEventListener('click', (e) => {
        if (e.target.matches('.excluir-btn')) {
            const id = e.target.dataset.id;
            imunizacoesModule.excluirImunizacao(id);
        }
    });

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