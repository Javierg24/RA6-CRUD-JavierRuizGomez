
<%@page import="com.daw.DAO.ComponenteBBDD"%>
<%@ page import="com.daw.Controladores.AsignaturasController"%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.daw.modelo.Asignatura" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gestión de Asignaturas</title>    
        <link href="css/stylesAs.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>

        <%@ include file="include/header.jsp" %>
        <%
            AsignaturasController asignaturasController = new AsignaturasController();

            String accion = request.getParameter("accion");
            if (accion != null) {
                if (accion.equals("agregar")) {
                    int idAsignatura = Integer.parseInt(request.getParameter("idAsignatura"));
                    String nombre = request.getParameter("nombre");
                    asignaturasController.agregarAsignatura(idAsignatura, nombre);

                } else if (accion.equals("eliminar")) {
                    int idAsignatura = Integer.parseInt(request.getParameter("idAsignatura"));
                    asignaturasController.eliminarAsignatura(idAsignatura);
                } else if (accion.equals("editar")) {
                    int idAsignatura = Integer.parseInt(request.getParameter("idAsignatura"));
                    String nombre = request.getParameter("nombre");
                    asignaturasController.editarAsignatura(idAsignatura, nombre);
                }
            }

            List<Object> asignaturas = asignaturasController.obtenerAsignaturas();
        %>
        <div class="container">
            <h1>Gestión de Asignaturas</h1>

            <!-- Tabla con las asignaturas -->
            <div class="subject-table">        
                <table>
                    <thead>
                        <tr>
                            <th>ID Asignatura</th>
                            <th>Nombre</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%                        // Obtener la lista de asignaturas desde el atributo "asignaturas" que se pasa desde el controlador
                            for (Object obj : asignaturas) {
                                if (obj instanceof Asignatura) {
                                    Asignatura asignatura = (Asignatura) obj;
                        %>
                        <tr>
                            <td><%= asignatura.getIdAsignatura()%></td>
                            <td><%= asignatura.getNombre()%></td>
                            <td class="actions-cell">
                                <!-- Botón de Editar con apertura del modal -->
                                <div class="d-inline">
                                    <button type="submit" class="edit-btn" onclick="abrirModal('<%= asignatura.getIdAsignatura()%>', '<%= asignatura.getNombre()%>')">Editar</button>
                                </div>
                                <!-- Formulario de eliminación -->
                                <form method="post" class="d-inline">
                                    <input type="hidden" name="idAsignatura" value="<%= asignatura.getIdAsignatura()%>">
                                    <button type="submit" name="accion" value="eliminar" class="delete-btn">Borrar</button>
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

            <!-- Formulario para agregar una nueva asignatura -->
            <div class="form-container">
                <h2>Agregar Asignatura</h2>
                <form method="post" class="subject-form">
                    <div class="input-group">
                        <label for="idAsignatura">ID Asignatura:</label>
                        <input type="number" name="idAsignatura" id="idAsignatura" required>
                    </div>

                    <div class="input-group">
                        <label for="nombre">Nombre:</label>
                        <input type="text" name="nombre" id="nombre" required>
                    </div>
                    <button type="submit" name="accion" value="agregar">Agregar</button>
                </form>
            </div>

            <!-- Modal de edición -->
            <div id="modalEditar" class="modal">            
                <div class="modal-content">
                    <span class="close" onclick="cerrarModal()">&times;</span>
                    <h2 class="tituloModal">Editar Asignatura</h2>
                    <form method="post">
                        <input type="hidden" name="idAsignatura" id="modalIdAsignatura">
                        <label class="modal-intro-nombre-label" for="modalNombre">Nombre:</label>
                        <input type="text" name="nombre" id="modalNombre" class="modal-intro-nombre" required>
                        <button type="submit" name="accion" value="editar">Guardar Cambios</button>
                    </form>
                </div>
            </div>
        </div>

        <%@ include file="include/footer.jsp" %>

        <!-- Scripts para manejar el modal de edición -->
        <script>
            // Abre el modal de edición con los valores pasados
            function abrirModal(idAsignatura, nombre) {
                document.getElementById('modalIdAsignatura').value = idAsignatura;
                document.getElementById('modalNombre').value = nombre;
                document.getElementById('modalEditar').style.display = 'block';
            }

            // Cierra el modal de edición
            function cerrarModal() {
                document.getElementById('modalEditar').style.display = 'none';
            }

            // Cierra el modal si el usuario hace clic fuera del modal
            window.onclick = function (event) {
                var modal = document.getElementById('modalEditar');
                if (event.target === modal) {
                    cerrarModal();
                }
            }
        </script>

    </body>

</html>
