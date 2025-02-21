const BASE_URL = 'http://localhost:8080';

// Funções base para API
export const apiVacina = {
    // [GET] Busca todos os valores
    async listar(table, endpoint) {
        try {
            const response = await fetch(`${BASE_URL}/${table}/${endpoint}`);
            if (!response.ok) {
                throw new Error(`Erro ao listar ${table}/${endpoint}: ${response.statusText}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Erro ao listar ${table}/${endpoint}:`, error);
            throw error;
        }
    },

    // GET by faixa etaria
    async buscarPorFaixaEtaria(table, endpoint, faixaEtaria) {
        try {
            const response = await fetch(`${BASE_URL}/${table}/${endpoint}/${faixaEtaria}`);
            if (!response.ok) {
                throw new Error(`Erro ao buscar ${table}/${endpoint}: ${response.statusText}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Erro ao buscar ${table}/${endpoint}:`, error);
            throw error;
        }
    },
    // GET by idade em meses
    async buscarPorFaixaEtaria(table, endpoint, meses) {
        try {
            console.log(`${BASE_URL}/${table}/${endpoint}/${meses}`);
            const response = await fetch(`${BASE_URL}/${table}/${endpoint}/${meses}`);
            if (!response.ok) {
                throw new Error(`Erro ao buscar ${table}/${endpoint}: ${response.statusText}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Erro ao buscar ${table}/${endpoint}:`, error);
            throw error;
        }
    },

    // GET by faixa etaria
    async buscarPorNaoAplicaveis(table, endpoint, idPaciente) {
        try {
            const response = await fetch(`${BASE_URL}/${table}/${endpoint}/${idPaciente}`);
            if (!response.ok) {
                throw new Error(`Erro ao buscar ${table}/${endpoint}: ${response.statusText}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Erro ao buscar ${table}/${endpoint}:`, error);
            throw error;
        }
    },

};