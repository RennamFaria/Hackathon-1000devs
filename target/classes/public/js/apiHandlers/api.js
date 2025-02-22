// api-base.js
const BASE_URL = 'http://localhost:8080';

// Funções base para API
export const apiBase = {
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

    // GET by id
    async buscarPorId(table, endpoint, id) {
        try {
            const response = await fetch(`${BASE_URL}/${table}/${endpoint}/${id}`);
            if (!response.ok) {
                throw new Error(`Erro ao buscar ${table}/${endpoint}: ${response.statusText}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Erro ao buscar ${table}/${endpoint}:`, error);
            throw error;
        }
    },

    // POST
    async cadastrar(table, endpoint, dados) {
        try {

            const response = await fetch(`${BASE_URL}/${table}/${endpoint}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: dados
            }); 
            if (!response.ok) {
                throw new Error(`Erro ao cadastrar ${endpoint}: ${response.statusText}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Erro ao cadastrar ${endpoint}:`, error);
            throw error;
        }
    },

    // PUT
    async atualizar(table, endpoint, id, dados) {
        try {
            const response = await fetch(`${BASE_URL}/${table}/${endpoint}/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: dados
            });
            if (!response.ok) {
                throw new Error(`Erro ao atualizar ${table}/${endpoint}: ${response.statusText}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Erro ao atualizar ${table}/${endpoint}:`, error);
            throw error;
        }
    },

    // DELETE
    async excluir(table, endpoint, id) {
        try {
            const response = await fetch(`${BASE_URL}/${table}/${endpoint}/${id}`, {
                method: 'DELETE'
            });
            if (!response.ok) {
                throw new Error(`Erro ao excluir ${table}/${endpoint}: ${response.statusText}`);
            }
            return true;
        } catch (error) {
            console.error(`Erro ao excluir ${table}/${endpoint}:`, error);
            throw error;
        }
    }
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
    },

    formatarData(data) {
        try {
            if (!data || data === '') return '---';
            const date = new Date(data);
            return date.toLocaleDateString('pt-BR', {
                day: '2-digit',
                month: '2-digit',
                year: 'numeric'
            });
        } catch (error) {
            console.error('Erro ao formatar data:', error);
            return '---';
        }
    }
};