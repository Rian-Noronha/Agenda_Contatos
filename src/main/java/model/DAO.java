package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
	
	
	
	//M?dulo de conex?o e seus Par?metros
	
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "I&you123";
	
	//M?todo de conex?o
	private Connection conectar() {
		Connection con = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		}catch(Exception e) {
			System.out.println(e);
			return null;
		}
		
	}
	
	//M?todo para testar a conex?o
	/*public void testeConexao() {
		try {
			Connection con = conectar();
			System.out.println(con);
			con.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}*/
	
	//CRUD CREAT
	public void inserirContato(JavaBeans contato) {
		String create = "insert into contatos(nome, fone, email)"
				+ "values(?, ?, ?)";
		
		try {
			
			//abrir a conex?o com o bd
			Connection con = conectar();
			
			//Preparar a query para execu??o no BD
			PreparedStatement pst = con.prepareStatement(create);
			
			//Substituir os par?metros (?) pelo conte?do das vari?veis JavaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			
			//Executar a query
			pst.executeUpdate();
			
			//Encerrar a conex?o com o bd
			con.close();
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	//CRUD READ
	public ArrayList<JavaBeans> listarContatos(){
		
		//onde salvarei os contatos
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		
		//selecionar os contatos
		String read = "select * from contatos order by idcon";
		
		try {
			
			//Parte da conex?o
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				//vari?veis de apoio
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				
				//salvando cada contato
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			
			con.close();
			return contatos;
			
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
		
		
	}
	
	//CRUD UPDATE
	public void selecionarContato(JavaBeans contato) {
		
		//mandar instru??o que o bd entende
		String read2 = "select * from contatos where idcon = ?";
		
		try {
			Connection con = conectar();
			
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				//settar as vari?veis do JavaBeans
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			
			con.close();
			
		}catch(Exception e) {
			
		}
		
		
	}
	
	//criando o m?todo para editar
	public void alterarContato(JavaBeans contato) {
		String create = "update contatos set nome=?, fone=?, email=? where idcon=?";
		
		try {
			Connection con = conectar();
			
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		}catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	public void deletarContato(JavaBeans contato) {
		String delete = "delete from contatos where idcon = ?";
		
		try {
			Connection con = conectar();
			
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, contato.getIdcon());
			pst.executeUpdate();
			con.close();
			
		}catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	
	
	
	
	
	
	
}
