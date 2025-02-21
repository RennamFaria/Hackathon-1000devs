import { apiBase, utils } from './apiHandlers/api.js';
import { apiVacina } from './apiHandlers/apiVacinas.js';

const TABLE = 'vacinas';

export const vacinasModule = {
    async carregarVacinas() {
        const ENDPOINT = 'consultar';

        try {
            const vacinas = await apiBase.listar(TABLE, ENDPOINT);
            this.renderizarTabela(vacinas);
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    async carregarVacinasPorFaixaEtaria(faixa_etaria) {
        const ENDPOINT = 'consultar/faixa_etaria';

        if (!faixa_etaria) return;  

        // Troca o C de crianca por Ç
        faixa_etaria = faixa_etaria.replace(/c/g, (match, index) => 
            index === faixa_etaria.length - 2 ? "ç" : match
        );

        // Transforma em Maiuscula para entrar no formato da rota
        faixa_etaria = faixa_etaria.toUpperCase();

        try {
            const vacinas = await apiVacina.buscarPorFaixaEtaria(TABLE, ENDPOINT, faixa_etaria);
            this.renderizarTabela(vacinas);
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    async carregarVacinasPorIdade(idadeMeses) {
        const ENDPOINT = 'consultar/idade_maior';

        if (!idadeMeses) return;

        // Formata idadeMeses para funcionar em "<=" ao inves de "<"
        let temp = parseInt(idadeMeses);
        temp--;
        idadeMeses = temp.toString();

        try {
            const vacinas = await apiBase.buscarPorId(TABLE, ENDPOINT, idadeMeses);
            this.renderizarTabela(vacinas);
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    async carregarVacinasNaoAplicaveis(idPaciente) {
        const ENDPOINT = 'consultar/nao_aplivacaveis/paciente';

        if (!idPaciente) return;

        try {
            const vacina = await apiBase.buscarPorId(TABLE, ENDPOINT, idPaciente);
            this.renderizarTabela(vacina);
        } catch (error) {
            utils.mostrarMensagem('Erro! Paciente não encontrado ou não existe\n\n', error.message);
        }
    },

    renderizarTabela(vacinas) {
        const tbody = document.getElementById('resultTable-vacina');

        // Imprime valor na tela
        // Se vier vazio ou ''. Será formatado para sair como desejado
        tbody.innerHTML = vacinas.map(vacina => `
            <tr>
                <td>${vacina.doseId && vacina.doseId !== '' ? vacina.doseId : '---'}</td>
                <td>${vacina.vacinaNome && vacina.vacinaNome !== '' ? vacina.vacinaNome : '---'}</td>
                <td>${vacina.doseDescricao && vacina.doseDescricao !== '' ? vacina.doseDescricao : '---'}</td>
                <td>${vacina.idadeRecomendada && vacina.idadeRecomendada !== '' ? vacina.idadeRecomendada : 'Não especificada'}</td>
                <td>${vacina.limiteAplicacao && vacina.limiteAplicacao !== '' ? vacina.limiteAplicacao : 'Não informado'}</td>
                <td>${vacina.publicoAlvo && vacina.publicoAlvo !== '' ? vacina.publicoAlvo : 'Geral'}</td>
            </tr>
        `).join('');
    },
};

// Inicialização
document.addEventListener('DOMContentLoaded', () => {
    const searchButton = document.getElementById('searchButton');
    // Verifica se está na página de listagem
    if (document.getElementById('resultTable-vacina')) {
        vacinasModule.carregarVacinas();
    }

    // Verifica o tipo de pesquisa e executa a funcao correta
    if(searchButton) {
        searchButton.addEventListener('click', () => {
            const select = document.getElementById('type-search');
            const selectedValue = select.value;
            const valueSearchBox = document.getElementById('search-bar').value;
            
            switch(selectedValue) {
                case 'faixaEtaria':
                    vacinasModule.carregarVacinasPorFaixaEtaria(valueSearchBox);
                    break;
                case 'idade':
                    vacinasModule.carregarVacinasPorIdade(valueSearchBox);
                    break;
                case 'naoAplicavel':
                    vacinasModule.carregarVacinasNaoAplicaveis(valueSearchBox);
                    break;
                default:
                    alert('Tipo de pesquisa não selecionado');
            }
        });
    }
});