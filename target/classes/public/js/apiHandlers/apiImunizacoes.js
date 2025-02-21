// api-base.js
const BASE_URL = 'http://localhost:8080';

// Funções base para API
export const apiImunizacao = {
    // GET
    async buscarPorIdPacienteEIntervalo(table, endpoint1, endpoint2, idPaciente, intervIni, intervFim) {
        try {
            const response = await fetch(`${BASE_URL}/${table}/${endpoint1}/${idPaciente}/${endpoint2}/${intervIni}/${intervFim}`);
            if (!response.ok) {
                throw new Error(`Erro ao buscar ${table}/${endpoint}: ${response.statusText}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Erro ao buscar ${table}/${endpoint1}/${idPaciente}/${endpoint2}/${intervIni}/${intervFim}:`, error);
            throw error;
        }
    },

};