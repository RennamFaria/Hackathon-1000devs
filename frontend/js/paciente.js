//realiza o processamento do conteudo no frontend assim que a pagina for carregada
window.onload = carregarPagina;

function carregarPagina() {
    carregarPacientes();
}

// Recebe os dados dos pacientes vindo do backend
async function carregarPacientes() {
    try {
        const response = await fetch('http://localhost:8080/paciente/consultar'); 

        // Se a resposta dar erro
        if(!response.ok){
            const codigoStatus = determinarCodigoStatus(response);

            mensagem = retornarMensagem(codigoStatus);

            abrirModalMensagem("Consultar Usuários", mensagem);

            return;
        }

        //converte os dados JSON para objeto Java Script
        const pacientes = await response.json();
        
        //adiciona os dados dos usuários na div de listagem de usuarios
        mostraTabelaPacientes(pacientes);
    } catch (error) {
        abrirModalMensagem("Consultar Usuários", 'Erro: ' + error.message);
    }
}

// Mostra na tela a lista de todos os pacientes na API backend
function mostraTabelaPacientes(pacientes) {
    const divPaciente = document.getElementById("idDaLista");

    //limpa os dados da div para atualizar com os novos dados
    divPaciente.innerHTML = '';

    for(let i = 0; i < pacientes.length; i++){
        const novaLinha = document.createElement('tr');

        novaLinha.innerHTML = `
                <td>${pacientes[i].nome}</td>
                <td>${pacientes[i].cpf}</td>
                <td>${pacientes[i].sexo}</td>
                <td>${pacientes[i].data_nascimento}</td>
                <td></td>
                `;
                /*adiciona no inner HTMl quando a tela estiver pronta
                <td>
                  <a href="/frontend/cadastro/imunizacao/consultar/paciente.html?id=${pacientes[i].id}"><button class="w3-button w3-green  w3-round">Imunizacoes</button></a>
                  <a href="/frontend/cadastro/editar/paciente.html?id=${pacientes[i].id}"><button class="w3-button w3-green  w3-round">Editar</button></a>
                  <button class="w3-button w3-red  w3-round" onclick=abrirModalExcluir('${pacientes[i].id}')>Excluir</button>
                </td>
                */

        divPaciente.appendChild(linhaTabela);
    }
}

// Realiza cadastro paciente através da API backend
async function cadastrarPaciente() {
    evento.preventDefault();

    const nome = document.getElementById('nome').value;
    const cpf = document.getElementById('cpf').value;
    const data_nascimento = document.getElementById('data_nascimento').value;
    if(document.getElementById('sexo').value == 'Masculino'){
        const sexo = 'M';
    }
    else if(document.getElementById('sexo').value == 'Feminino'){
        const sexo = 'F';
    }

    const paciente = {
        nome,
        cpf,
        sexo,
        data_nascimento
    }

    try {
        //constroi o objeto contendo o conteudo a ser inserido na requisicao http
        const conteudoHttp = {
            method: 'POST',
            headers: { "Content-type": "application/json;" },
            body: JSON.stringify(paciente)
        }

        console.log(paciente);

        const httpResponse = await fetch('http://localhost:8080/paciente/inserir', conteudoHttp);
        
        if (!httpResponse.ok) {
            abrirModalMensagem("Cadastrar Usuário", `Erro: ${httpResponse.statusText}`);
        }
        
        alert('Paciente cadastrado com sucesso');

        
        carregarPacientes();
    } catch (error) {
        alert('Erro ao cadastrar: ' + error.message);
    }
}

// Realiza alteracao paciente através da API backend
function alterarPaciente() {
    evento.preventDefault();

    const nome = document.getElementById('nome').value;
    const cpf = document.getElementById('cpf').value;
    const data_nascimento = document.getElementById('data_nascimento').value;
    if(document.getElementById('sexo').value == 'Masculino'){
        const sexo = 'M';
    }
    else if(document.getElementById('sexo').value == 'Feminino'){
        const sexo = 'F';
    }

    const paciente = {
        nome,
        cpf,
        sexo,
        data_nascimento
    }

    try {
        //constroi o objeto contendo o conteudo a ser inserido na requisicao http
        const conteudoHttp = {
            method: 'POST',
            headers: { "Content-type": "application/json;" },
            body: JSON.stringify(paciente)
        }

        console.log(paciente);

        const httpResponse = await fetch('http://localhost:8080/paciente/inserir', conteudoHttp);
        
        if (!httpResponse.ok) {
            abrirModalMensagem("Cadastrar Usuário", `Erro: ${httpResponse.statusText}`);
        }
        
        alert('Paciente cadastrado com sucesso');

        
        carregarPacientes();
    } catch (error) {
        alert('Erro ao cadastrar: ' + error.message);
    }
}

// Realiza a remocao do paciente através da API backend
async function removerPaciente() {
    let mensagem;

    try {
        // Pega o id do paciente no html
        const id = botao.getAttribute('data-id');
        console.log(id);

        const conteudoHttp = {
            method: 'DELETE'
        }

        const httpResponse = await fetch(`http://localhost:8080/paciente/excluir/${id}`, conteudoHttp);

        if (!httpResponse.ok) {
            abrirModalMensagem("Excluir Usuário", `Erro: ${httpResponse.statusText}`);
        }
        
        //determina o codigo de status baseado no http response
        const codigoStatus = determinarCodigoStatus(response);
        console.log(response);

        //determina a mensagem a ser exibida para o usuario
        mensagem = retornarMensagem(codigoStatus);
    } catch (error) {
        alert('Erro ao excluir: ' + error.message);
    }

    //fecha o modal excluir
    fecharModalExcluir();   

    //abre o modal de status para informar o resultado da exclusao, passando os parametros
    //do titulo do modal e a mensagem que será exibida
    abrirModalMensagem("Exclusão Usuário", mensagem);

    //atualiza pagina
    carregarPacientes();
}

async function procurarPacientePorId(){
    try {
        const id = document.getElementById(`search-bar`);

        const httpResponse = await fetch(`http://localhost:8080/paciente/consultar/${id}`);


    } catch (error) {
        alert('Erro ao filtrar paciente id: ' + error.message);
    }
}




/* Utils */

function determinarCodigoStatus(response){
    if (!response.ok) 
        return statusOperacao.ENDPOINT_NAO_ENCONTRADO;
    else if (response.status == 204) 
        return statusOperacao.ID_NAO_ENCONTRADO; 
    else if (response.status == 200)
        return statusOperacao.SUCESSO;
    else  
    return statusOperacao.ERRO_GERAL;
}

// Checa a mensagem que foi recebida e gera a mensagem do codigo status apropriado
function retornarMensagem(codigoStatus){
    let mensagem = "Status informado desconhecido.";
    
    switch (codigoStatus) {
        case statusOperacao.SUCESSO:
            mensagem = "Sucesso! Operação realizada.";
            break;
            case statusOperacao.ID_NAO_INFORMADO: 
            mensagem = "ID não informado! Erro ao executar operação.";
            break;
            case statusOperacao.ERRO_GERAL: 
            mensagem = "Erro ao processar a operação.";
            break;
            case statusOperacao.ID_NAO_ENCONTRADO: 
            mensagem = "ID informado não encontrado! Erro ao executar operação.";
            break;
            case statusOperacao.ENDPOINT_NAO_ENCONTRADO: 
            mensagem = "Endpoint não encontrado! Erro ao executar operação.";
            break;
            default:
                break;
            }
            
            return mensagem;
}

function abrirModalMensagem(titulo, mensagem) {
    // Configura o html do titulo do modal
    document.getElementById('mensagemTitulo').innerHTML = titulo;
    // Configura o html da mensagem do modal
    document.getElementById('mensagemStatus').innerHTML = mensagem;
    // Altera o atributo css do modal para ser exibido
    document.getElementById('modalMensagem').style.display = 'block';
}

function fecharModalMensagem() {
    const modalExcluir = document.getElementById('modalMensagem');
    modalExcluir.style.display = 'none';
}