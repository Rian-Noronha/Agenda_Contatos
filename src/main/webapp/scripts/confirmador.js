/**
 * Confirmação para exclusão de um contato
	@author Rian Noronha in Developing Web
 */

function confirmar(idcon){
	let resposta = confirm("Quer mesmo excluir o contato?")
	
	if(resposta === true){
		//alert(idcon)
		
		//aqui, estou mandando o idcon para minha Controller, logo, um redirecionamento
		window.location.href = "delete?idcon=" + idcon;
	}
	
}