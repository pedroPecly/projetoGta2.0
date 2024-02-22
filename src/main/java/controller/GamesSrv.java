package controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Games;
import model.ListaJogos;
import model.Perfil;
import model.dao.GamesDaoJpa;
import model.dao.ListaJogosDaoJpa;
import model.dao.PerfilDaoJpa;

public class GamesSrv extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = request.getParameter("acao");
            String nome = request.getParameter("nome");
            String senha = request.getParameter("senha");
            String nomeJogo = request.getParameter("nomeJogo");
            String zeradoParam = request.getParameter("zerado");
            String id = request.getParameter("id");
            ListaJogos l = null;
            Games g = null;
            GamesDaoJpa daoG = new GamesDaoJpa();
            ListaJogosDaoJpa dao = new ListaJogosDaoJpa();

            boolean zerado = "on".equals(zeradoParam);
            if (zeradoParam == null) {
                zerado = false;
            }
            Perfil p = verificarPerfil(nome, senha);

            RequestDispatcher rd;

            switch (acao) {
                case "listagemJogos":
                    rd = request.getRequestDispatcher("listagem.jsp?lista=" + listagemJogos(p)
                            + "&acao=listagemJogos&nome=" + nome + "&senha=" + senha);
                    rd.forward(request, response);
                    break;

                case "listarGamesAdmin":
                    rd = request.getRequestDispatcher(
                            "listagem.jsp?lista=" + listagemJogosAdmin(p) + "&nome=" + nome + "&senha=" + senha
                                    + "&acao=" + acao);
                    rd.forward(request, response);
                    break;

                case "preAdicionarJogo":
                    rd = request.getRequestDispatcher("formularioGames.jsp?lista=" + listaJogosBox()
                            + "&acao=adicionarJogo&nome=" + nome + "&senha=" + senha);
                    rd.forward(request, response);
                    break;

                case "preAdicionarGamesAdmin":
                    rd = request.getRequestDispatcher(
                            "formularioGames.jsp?&acao=adicionarGamesAdmin&nome=" + nome + "&senha=" + senha);
                    rd.forward(request, response);
                    break;

                case "adicionarGamesAdmin":
                    g = new Games(nomeJogo);
                    daoG.incluir(g);
                    rd = request.getRequestDispatcher("listagem.jsp?acao=listarGamesAdmin&lista="
                            + listagemJogosAdmin(p) + "&nome=" + p.getNome() + "&senha=" + p.getSenha());
                    rd.forward(request, response);
                    break;

                case "adicionarJogo":
                    l = new ListaJogos(p.getId(), nomeJogo, zerado);
                    dao.incluir(l);
                    rd = request.getRequestDispatcher("listagem.jsp?lista=" + listagemJogos(p)
                            + "&acao=listagemJogos&nome=" + nome + "&senha=" + senha);
                    rd.forward(request, response);
                    break;

                case "exclusao":
                    dao.excluir(dao.pesquisarPorId(Integer.parseInt(id)));
                    rd = request.getRequestDispatcher("listagem.jsp?lista=" + listagemJogos(p)
                            + "&acao=listagemJogos&nome=" + nome + "&senha=" + senha);
                    rd.forward(request, response);
                    break;

                case "exclusaoAdmin":
                    daoG.excluir(daoG.pesquisarPorId(Integer.parseInt(id)));
                    rd = request.getRequestDispatcher("listagem.jsp?acao=listarGamesAdmin&lista="
                            + listagemJogosAdmin(p) + "&nome=" + p.getNome() + "&senha=" + p.getSenha());
                    rd.forward(request, response);
                    break;
            }

        } catch (Exception ex) {
            Logger.getLogger(GamesSrv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String listagemJogosAdmin(Perfil p) {
        GamesDaoJpa dao = new GamesDaoJpa();
        List<Games> lista = null;
        try {
            lista = dao.listar();
        } catch (Exception ex) {
            Logger.getLogger(PerfilSrv.class.getName()).log(Level.SEVERE, null, ex);
        }

        String listaHTML = "";
        for (int i = 0; i < lista.size(); i++) {
            Games game = lista.get(i);
            listaHTML = listaHTML
                    + "<tr>"
                    + "<td>" + (i + 1)
                    + "<td>" + game.getNome() + "</td>"
                    + "<td><form action=GamesSrv?acao=exclusaoAdmin method='POST'>"
                    + "<input type='hidden' name='id' value=" + game.getId()
                    + "><input type='hidden' name='nome' value=" + p.getNome()
                    + "><input type='hidden' name='senha' value=" + p.getSenha()
                    + "><input type='submit' value=excluir id='btnExcluir'>" + "</form></td>"
                    + "</tr>";

        }
        return listaHTML;
    }

    private String listagemJogos(Perfil p) {
        ListaJogosDaoJpa dao = new ListaJogosDaoJpa();
        List<ListaJogos> lista = null;
        String listaHTML = "";

        try {
            lista = dao.listar();

        } catch (Exception ex) {
            Logger.getLogger(GamesSrv.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < lista.size(); i++) {
            if (p.getId() == lista.get(i).getIdPerfil()) {
                listaHTML = listaHTML
                        + "<tr>"
                        + "<td>" + lista.get(i).getNomeJogo() + "</td>"
                        + "<td>" + lista.get(i).getZerado() + "</td>"
                        + "<td><form action=GamesSrv?acao=exclusao method='POST'>"
                        + "<input type='hidden' name='id' value=" + lista.get(i).getId()
                        + "><input type='hidden' name='nome' value=" + p.getNome()
                        + "><input type='hidden' name='senha' value=" + p.getSenha()
                        + "><input type='submit' value=excluir id='btnExcluir'>" + "</form></td>"
                        + "</tr>";
            }
        }
        return listaHTML;
    }

    private String listaJogosBox() {
        GamesDaoJpa dao = new GamesDaoJpa();
        List<Games> lista = null;
        try {
            lista = dao.listar();
        } catch (Exception ex) {
            Logger.getLogger(GamesSrv.class.getName()).log(Level.SEVERE, null, ex);
        }

        String listaHTML = "";

        for (int i = 0; i < lista.size(); i++) {
            Games games = null;
            games = lista.get(i);
            listaHTML = listaHTML
                    + "<option value='" + games.getNome()
                    + "'>" + games.getNome() + "</option>";
        }

        return listaHTML;
    }

    private Perfil verificarPerfil(String nome, String senha) {
        PerfilDaoJpa dao = new PerfilDaoJpa();
        List<Perfil> lista = null;

        try {
            lista = dao.listar();
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getNome().equals(nome) && lista.get(i).getSenha().equals(senha)) {
                    return lista.get(i);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(GamesSrv.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
