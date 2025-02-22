// api-base.js
const BASE_URL = 'http://localhost:8080';

// Funções base para API
export const apiEstatistica = {
    // GET
    async buscarPorDeterminadadaIdade(table, endpoint, meses) {
        try {
            const response = await fetch(`${BASE_URL}/${table}/${endpoint}/${meses}`);
            if (!response.ok) {
                throw new Error(`Erro ao buscar ${table}/${endpoint}: ${response.statusText}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Erro ao buscar ${table}/${endpoint}/${meses}:`, error);
            throw error;
        }
    },

};