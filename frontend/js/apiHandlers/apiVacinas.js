// api-base.js
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

// Funções utilitárias
export const utils = {
    obterParametroUrl(parametro) {
        const url = new URL(window.location.href);
        return url.searchParams.get(parametro);
    },

    // funcao generica para buscar os dados do formulario idependente do tipo do formulario
    getFormData(form) {
        const formData = new FormData(form);
        const data = {};
        for (let [key, value] of formData.entries()) {
            data[key] = value;
        }
        return data;
    },

    mostrarMensagem(tipo, mensagem) {
        alert(`${tipo}: ${mensagem}`);
    }
};