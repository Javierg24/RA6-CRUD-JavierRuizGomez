<%@ page import="com.daw.Controladores.RAController"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.daw.DAO.ResultadoAprendizajeDAO, com.daw.modelo.ResultadoAprendizaje, com.daw.DAO.ComponenteBBDD" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Gestión de Resultados de Aprendizaje</title>
        <link href="css/stylesAs.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <%@ include file="include/header.jsp" %>
        <%
            RAController rAController = new RAController();
            String selected = null;
            // Procesar acciones del formulario
            String accion = request.getParameter("accion");
            if (accion != null) {
                if (accion.equals("agregar")) {
                    int idResultado = Integer.parseInt(request.getParameter("idResultado"));
                    int idAsignatura = Integer.parseInt(request.getParameter("idAsignatura"));
                    String nombre = request.getParameter("nombre");
                    rAController.aniadir(idResultado, idAsignatura, nombre);
                } else if (accion.equals("eliminar")) {
                    int idResultado = Integer.parseInt(request.getParameter("idResultado"));
                    rAController.eliminar(idResultado);
                } else if (accion.equals("editar")) {
                    int idResultado = Integer.parseInt(request.getParameter("idResultado"));
                    int idAsignatura = Integer.parseInt(request.getParameter("idAsignatura"));
                    String nombre = request.getParameter("nombre");
                    rAController.editar(idResultado, idAsignatura, nombre);
                }
            }

            // Obtener el id de la asignatura seleccionada desde el formulario
            String nombreAsignatura = request.getParameter("nombreAsignatura");
            List<Object> resultados = null;

            // Si se seleccionó una asignatura, obtener los resultados filtrados
            if (nombreAsignatura != null) {
                resultados = rAController.seleccionarPorAsignatura(nombreAsignatura);
            }
        %>

        <div class="container">
            <h1>Gestión de Resultados de Aprendizaje</h1>

            <div class="subject-filter">
                <h2>Filtrar Resultados</h2>
                <form>
                    <label for="nombreAsignatura">Selecciona una Asignatura:</label>
                    <select name="nombreAsignatura" id="nombreAsignatura" onchange="this.form.submit()">                        
                        <%
                            String[] asignaturas = rAController.obtenerAsignaturas();
                            for (String asignatura : asignaturas) {
                                selected = (asignatura.equals(nombreAsignatura)) ? "selected" : "";
                        %>
                        <option value="<%= asignatura%>" <%= selected%>><%= asignatura%></option>
                        <%
                            }
                        %>
                    </select>                   
                </form>
            </div>

            <%-- Mostrar la tabla solo si se seleccionó una asignatura --%>
            <% if (resultados != null && !resultados.isEmpty()) { %>
            <div class="subject-table">                
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID Resultado</th>
                            <th>Asignatura</th>
                            <th>Nombre</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%

                            for (Object obj : resultados) {
                                if (obj instanceof ResultadoAprendizaje) {
                                    ResultadoAprendizaje resultado = (ResultadoAprendizaje) obj;
                        %>
                        <tr>
                            <td><%= resultado.getIdResultado()%></td>
                            <td><%= nombreAsignatura%></td>
                            <td><%= resultado.getNombre()%></td>
                            <td class="actions-cell">
                                <!-- Botón editar -->
                                <div class="d-inline">
                                    <button type="submit" class="edit-btn" onclick="abrirModal('<%= resultado.getIdResultado()%>', '<%= resultado.getIdAsignatura()%>', '<%= resultado.getNombre()%>')">Editar</button>
                                </div>
                                <!-- Botón eliminar -->
                                <form action="resultados2.jsp" class="d-inline">
                                    <input type="hidden" name="idResultado" value="<%= resultado.getIdResultado()%>">
                                    <button type="submit" name="accion" value="eliminar" class="delete-btn">Eliminar</button>
                                </form>
                            </td>
                        </tr>
                        <%
                                }
                            }

                        %>
                    </tbody>
                </table>
            </div>
            <% } else { %>

            <% }%>
            <div class="form-container">
                <h2>Agregar Resultado de Aprendizaje</h2>
                <form method="post" class="subject-form">
                    <div class="input-group">
                        <label for="idAsignatura">ID Asignatura:</label>
                        <input type="number" name="idAsignatura" id="idAsignatura" required 
                               value="<%= (nombreAsignatura != null) ? rAController.obtenerIdAsignatura(nombreAsignatura) : ""%>">
                    </div>
                    <div class="input-group">
                        <label for="idResultado">ID Resultado:</label>
                        <input type="number" name="idResultado" id="idResultado" required>
                    </div>
                    <div class="input-group">
                        <label for="nombre">Nombre:</label>
                        <input type="text" name="nombre" id="nombre" required>
                    </div>
                    <button type="submit" name="accion" value="agregar">Agregar</button>
                </form>
            </div>

        </div>

        <!-- Modal de edición -->
        <div id="modalEditar" class="modal">            
            <div class="modal-content">
                <span class="close" onclick="cerrarModal()">&times;</span>
                <h2 class="tituloModal">Editar Resultado de Aprendizaje</h2>
                <form method="post">
                    <input type="hidden" name="idResultado" id="modalIdResultado">
                    <label class="modal-intro-nombre-label" for="modalIdAsignatura">Id Asignatura</label>
                    <input type="number" name="idAsignatura" id="modalIdAsignatura" class="modal-intro-nombre">
                    <label class="modal-intro-nombre-label" for="modalNombre">Nombre:</label>
                    <input type="text" name="nombre" id="modalNombre" class="modal-intro-nombre" required>
                    <button type="submit" name="accion" value="editar">Guardar Cambios</button>
                </form>
            </div>
        </div>

        <%@ include file="include/footer.jsp" %>

        <script>
            function abrirModal(idResultado, idAsignatura, nombre) {
                document.getElementById('modalIdResultado').value = idResultado;
                document.getElementById('modalIdAsignatura').value = idAsignatura;
                document.getElementById('modalNombre').value = nombre;
                document.getElementById('modalEditar').style.display = 'block';
            }

            function cerrarModal() {
                document.getElementById('modalEditar').style.display = 'none';
            }

            window.onclick = function (event) {
                var modal = document.getElementById('modalEditar');
                if (event.target === modal) {
                    cerrarModal();
                }
            }


        </script>
    </body>
</html>
