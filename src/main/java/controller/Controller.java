package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAO;
import model.JavaBeans;

//main, insert, select são chaves de agenda.jsp, edita.jsp nas quais o Servlet consegue linkar para se comunicar.
@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();
		System.out.println(action);

		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContatos(request, response);
		} else if (action.equals("/update")) {
			editarContato(request, response);
		} else if (action.equals("/delete")) {

			removerContato(request, response);

		} else {
			response.sendRedirect("index.html");
		}

	}

	// Listar os contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Lista com os contatos vindos da classe DAO
		ArrayList<JavaBeans> lista = dao.listarContatos();

		// Agora, encaminhando tudo à agenda.jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);

	}

	// Criar um novo contato
	public void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();
		System.out.println(action);

		// ver se os dados do formulário estão ok
		System.out.println(request.getParameter("nome"));
		System.out.println(request.getParameter("fone"));
		System.out.println(request.getParameter("email"));

		// aqui, setto os valores na classe JavaBeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));

		// agora, passo o contato pelo método inserirContato criado na classe DAO
		dao.inserirContato(contato);

		response.sendRedirect("main");

	}

	public void listarContatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// recebimento do id do contato para edição
		String idcon = request.getParameter("idcon");
		contato.setIdcon(idcon);

		// Executar o método selecionarContato da classe DAO
		dao.selecionarContato(contato);

		// settar os conteúdos do forms com os valores vindos de JavaBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());

		// dispachando essas informações para editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);

	}

	// editar um contato
	public void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));

		// chamar o método para alterar o contato
		dao.alterarContato(contato);

		response.sendRedirect("main");

	}
	
	//Método para remover um contato
	private void removerContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		//aqui vem o id no qual passou pelo (confirmador.js)
		String idcon = request.getParameter("idcon");
		
		//agora, vou settar o idcon no contato JavaBeans
		contato.setIdcon(idcon);
		
		//agora, chamo o método deletar contato lá de DAO para deletar o contato
		dao.deletarContato(contato);
		
		//redirecionar para agenda.jsp já fazendo as alterações
		response.sendRedirect("main");
	}

}
