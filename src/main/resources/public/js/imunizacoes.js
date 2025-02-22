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

    async cadastrarImunizacao(event) {
        const ENDPOINT = 'inserir';
        event.preventDefault();
        
        try {
            const idPaciente = document.getElementById('id_paciente').value;
            const idDose = document.getElementById('id_dose').value;
            const dataAplicacao = document.getElementById('data_aplicacao').value;
            const fabricante = document.getElementById('fabricante').value;
            const lote = document.getElementById('lote').value;
            const localAplicacao = document.getElementById('local_aplicacao').value;
            const profissionalAplicador = document.getElementById('profissional_aplicador').value;
    
            // Converte dados do forms para URLSearchParams (formEncode)
            const dadosFormData = new URLSearchParams();
            dadosFormData.append('idPaciente', idPaciente);
            dadosFormData.append('idDose', idDose);
            dadosFormData.append('dataAplicacao', dataAplicacao);
            dadosFormData.append('fabricante', fabricante);
            dadosFormData.append('lote', lote);
            dadosFormData.append('localAplicacao', localAplicacao);
            dadosFormData.append('profissionalAplicador', profissionalAplicador);

            await apiBase.cadastrar(TABLE, ENDPOINT, dadosFormData);
            utils.mostrarMensagem('Sucesso', 'Imunizacao cadastrado com sucesso!');
            
            setTimeout(() => {
                window.location.href = '../imunizacao.html';
            }, 1500);
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
    async atualizarImunizacao(id, event) {
        const ENDPOINT = 'alterar';
        event.preventDefault();

        console.log(id);

        try {
            const idPaciente = document.getElementById('id_paciente').value;
            const idDose = document.getElementById('id_dose').value;
            const dataAplicacao = document.getElementById('data_aplicacao').value;
            const fabricante = document.getElementById('fabricante').value;
            const lote = document.getElementById('lote').value;
            const localAplicacao = document.getElementById('local_aplicacao').value;
            const profissionalAplicador = document.getElementById('profissional_aplicador').value;
    
            // Converte dados do forms para URLSearchParams (formEncode)
            const dadosFormData = new URLSearchParams();
            dadosFormData.append('idPaciente', idPaciente);
            dadosFormData.append('idDose', idDose);
            dadosFormData.append('dataAplicacao', dataAplicacao);
            dadosFormData.append('fabricante', fabricante);
            dadosFormData.append('lote', lote);
            dadosFormData.append('localAplicacao', localAplicacao);
            dadosFormData.append('profissionalAplicador', profissionalAplicador);

            await apiBase.atualizar(TABLE, ENDPOINT, id, dadosFormData);
            utils.mostrarMensagem('Sucesso', 'Paciente atualizado com sucesso!');

            setTimeout(() => {
                window.location.href = '../imunizacao.html';
            }, 1500);
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

            setTimeout(() => {
                window.location.href = '../../paciente/paciente.html';
            }, 1500);
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

        const hrefEditar = window.location.href.includes('porPaciente') ? 
        '../atualizar/atualizar.html' :
        './atualizar/atualizar.html';

        const imunizacoesArray = Array.isArray(imunizacoes) ? imunizacoes : [imunizacoes];

        tbody.innerHTML = imunizacoesArray.map(imunizacao => `
            <tr class = "border border-2 border-dark rounded table-primary">
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
                    <a href="${hrefEditar}?id=${imunizacao.idImunizacao}">
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
};

// Inicialização
document.addEventListener('DOMContentLoaded', () => {
    // Executa esse codigo apenas para a pagina Imunizacao e nao ImunizacaoPaciente
    const searchButton = document.getElementById('searchButton');
    
    if (document.getElementById('type-search')) {
        const typeSearch = document.getElementById('type-search');
        const additionalSearchContainer = document.getElementById('additional-search-container');

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
        const deleteAllButton = document.getElementById('deleteAllPaciente');

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
    const form = document.getElementById('form-cadastro-imunizacao') || document.getElementById('form-atualizar-imunizacao');

    if (form) {
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            try {
                const urlParams = new URLSearchParams(window.location.search);
                const id = urlParams.get('id');
                
                if (id) {
                    await imunizacoesModule.atualizarImunizacao(id, e);
                } else {
                    await imunizacoesModule.cadastrarImunizacao(e);
                }
            } catch (error) {
                utils.mostrarMensagem('Erro', error.message);
            }
        });
    }
});