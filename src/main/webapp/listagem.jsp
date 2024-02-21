<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="styles/style_listagem.css">
    </head>

    <%
        String listaHTML = request.getParameter("lista");
        String id = request.getParameter("id");
        String acao = request.getParameter("acao");
        String nome = request.getParameter("nome");
        String senha = request.getParameter("senha");
    %>
    <body>
        <header>
            <nav class="cabecalho">
                <div class="menu">
                    <ul>
                        <li class="links"><a href="telaUsuario.jsp?nome=<%= nome%>&senha=<%= senha%>">Voltar</a></li>
                    </ul>
                </div>
                <img id="logo" src="imagens/Rockstar_London-Logo.wine.png" alt="Logo Rockstar London">
            </nav>
        </header>

        <main>
            <div id="lista">
                <table>
                    <thead>
                        <% if (acao != null && acao.equals("listagemJogos")) { %>
                            <tr>
                                <th>Nome</th>
                                <th>Zerado</th>
                                <th></th>
                            </tr>
                        <% } %>
                        <% if (acao != null && acao.equals("listagem")) { %>
                            <tr>
                                <th>posicao</th>
                                <th>Nome</th>
                            </tr>
                        <% } %>
                        <% if (acao != null && (acao.equals("listagemAdmin") || acao.equals("exclusao"))) { %>
                            <tr>
                                <th>Posicao</th>
                                <th>Nome</th>
                                <th>senha</th>
                                <th>Email</th>
                                <th></th>
                                <th></th>
                            </tr>
                        <% } %>
                        <% if (acao != null && acao.equals("listarGamesAdmin")) { %>
                            <th>Posicao</th>
                            <th>nome</th>
                            <th></th>
                        <% } %>
                    </thead>
                    <tbody>
                        <%=listaHTML %>
                    </tbody>
                </table>
                <% if (acao != null && (acao.equals("listagemJogos") || acao.equals("editarGames"))) { %>
                    <center>
                        <form action="GamesSrv" method="POST">
                            <p>
                                <input type="hidden" name="acao" value="preAdicionarJogo">
                            </p>
                            
                            <p>
                                <input type="hidden" name="nome" value="<%=nome%>">
                            </p>
                            <p>
                                <input type="hidden" name="senha" value="<%=senha%>">
                            </p>
                            <p>
                                <input type="submit" value="Adicionar" id="btnEditar">
                            </p>
                        </form>
                    </center>
                <% } %>
                <% if (acao != null && acao.equals("listarGamesAdmin")) { %>
                    <center>
                        <form action="GamesSrv" method="POST">
                            <p>
                                <input type="hidden" name="acao" value="preAdicionarGamesAdmin">
                            </p>
                            
                            <p>
                                <input type="hidden" name="nome" value="<%=nome%>">
                            </p>
                            <p>
                                <input type="hidden" name="senha" value="<%=senha%>">
                            </p>
                            <p>
                                <input type="submit" value="Adicionar" id="btnEditar">
                            </p>
                        </form>
                    </center>
                <% } %>
            </div>
        </main>
    </body>
</html>
