package Servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.*;
import java.math.*;
import Model.Usuario;
import Service.UsuarioService;


@WebServlet("/session.do")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("userID") == null) {			
			RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			view.forward(request, response);
		}else {
			RequestDispatcher view = request.getRequestDispatcher("perfil.jsp");
			view.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsuarioService usuarioService = new UsuarioService();
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String senhaHash = usuarioService.convertMD5(senha);
		Usuario usuario = usuarioService.carregarPorEmailSenha(email, senhaHash);
		
		if(usuario.getUserID() > 0) {
			request.getSession().setAttribute("errors", null);
			request.getSession().setAttribute("userID", usuario.getUserID());
			request.getSession().setAttribute("nome", usuario.getNome());
			request.getSession().setAttribute("email", usuario.getEmail());
		}else {
			request.setAttribute("errors", "Combinação incorreta");
		}	
		
		RequestDispatcher view = request.getRequestDispatcher(usuario.getUserID() > 0 ? "perfil.jsp" : "index.jsp");
		view.forward(request, response);
		
	}
	
}
