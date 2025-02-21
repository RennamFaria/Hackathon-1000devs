import { apiBase, utils } from './apiHandlers/api.js';
import { apiEstatistica } from './apiHandlers/apiEstatisticas.js';

const TABLE = 'estatisticas';

export const estatisticasModule = {
    async qntVacinasPorPaciente(id) {
        const ENDPOINT = 'imunizacoes/paciente';
        
        try {
            const estatisticas = await apiBase.buscarPorId(TABLE, ENDPOINT, id);
            return estatisticas;
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    async qntVacinasAplicaveisProxMes(id) {
        const ENDPOINT = 'proximas_imunizacoes/paciente';
        
        try {
            const estatisticas = await apiBase.buscarPorId(TABLE, ENDPOINT, id);
            return estatisticas;
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    async qntVacinasAtrasadas(id) {
        const ENDPOINT = 'imunizacoes_atrasadas/paciente';
        
        try {
            const estatisticas = await apiBase.buscarPorId(TABLE, ENDPOINT, id);
            return estatisticas;
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    async qntVacinasAcimaIdade(meses) {
        const ENDPOINT = 'imunizacoes/idade_maior';
        
        try {
            const estatisticas = await apiEstatistica.buscarPorDeterminadadaIdade(TABLE, ENDPOINT, meses);
            return estatisticas;
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    async qntVacinasNaoAplicavel(id) {
        const ENDPOINT = 'vacinas/nao_aplivacaveis/paciente';
        
        try {
            const estatisticas = await apiBase.buscarPorId(TABLE, ENDPOINT, id);
            return estatisticas;
        } catch (error) {
            utils.mostrarMensagem('Erro', error.message);
        }
    },

    async carregarTodasEstatisticas(id, meses) {
        try {
            const vacinasPorPaciente = await this.qntVacinasPorPaciente(id) || 0;
            const vacinasProxMes = await this.qntVacinasAplicaveisProxMes(id) || 0;
            const vacinasAtrasadas = await this.qntVacinasAtrasadas(id) || 0;
            const vacinasAcimaIdade = await this.qntVacinasAcimaIdade(meses) || 0;
            const vacinasNaoAplicavel = await this.qntVacinasNaoAplicavel(id) || 0;
    
            return { 
                vacinasPorPaciente, 
                vacinasProxMes, 
                vacinasAtrasadas, 
                vacinasAcimaIdade, 
                vacinasNaoAplicavel 
            };
        } catch (error) {
            console.error('Erro ao carregar estatísticas:', error);
            return {
                vacinasPorPaciente: 0,
                vacinasProxMes: 0,
                vacinasAtrasadas: 0,
                vacinasAcimaIdade: 0,
                vacinasNaoAplicavel: 0
            };
        }
    }
};