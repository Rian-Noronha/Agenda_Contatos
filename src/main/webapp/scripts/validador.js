/**
 * Validação de formulário
	@author Rian Noronha
 */

function validador(){
	let nome = frmContato.nome.value
	let fone = frmContato.nome.value
	
	if(nome === ""){
		alert('Preencha o campo Nome')
		frmContato.nome.focus()
		return false
	}else if(fone === ""){
		alert('Preencha o campo Fone')
		frmContato.fone.focus()
		return false
	}else{
		document.forms["frmContato"].submit()
	}
	
}