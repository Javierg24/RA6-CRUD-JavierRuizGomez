<%@page import="com.daw.Controladores.CriterioController"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.List"%>
<%@page import="com.daw.DAO.CriterioEvaluacionDAO"%>
<%@page import="com.daw.DAO.ComponenteBBDD"%>
<%@page import="com.daw.modelo.CriterioEvaluacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Gestión de Criterios de Evaluación</title>
        <link href="css/stylesAs.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <%@ include file="include/header.jsp" %>        
        <div class="container">
            <%

                CriterioController criterioController = new CriterioController();

                String accion = request.getParameter("accion");
                if (accion != null) {
                    try {
                        switch (accion) {
                            case "agregar":
                                int idCriterio = Integer.parseInt(request.getParameter("id_criterio"));
                                int idResultado = Integer.parseInt(request.getParameter("id_resultado"));
                                int idAsignatura = Integer.parseInt(request.getParameter("id_asignatura"));
                                BigDecimal porcentaje = new BigDecimal(request.getParameter("porcentaje"));
                                String nombre = request.getParameter("nombre");
                                criterioController.agregarCriterio(idCriterio, idResultado, idAsignatura, porcentaje, nombre);
                                break;
                            case "editar":
                                idCriterio = Integer.parseInt(request.getParameter("id_criterio"));
                                idResultado = Integer.parseInt(request.getParameter("RA"));
                                idAsignatura = Integer.parseInt(request.getParameter("idAsignatura"));
                                porcentaje = new BigDecimal(request.getParameter("porcentaje"));
                                nombre = request.getParameter("nombre");
                                criterioController.editarCriterio(idCriterio, idResultado, idAsignatura, porcentaje, nombre);
                                break;
                            case "borrar":
                                idCriterio = Integer.parseInt(request.getParameter("id_criterio"));
                                criterioController.eliminarCriterio(idCriterio);
                                break;
                        }
                    } catch (Exception e) {
                        out.println("<p class='text-danger'>Error al procesar la acción: " + e.getMessage() + "</p>");
                    }
                }

                List<Object> criterios = criterioController.obtenerCriterios();
            %>

            <h1>Criterios de Evaluación</h1>
            <div class="subject-table">
                <table>
                    <thead>
                        <tr>
                            <th>ID Criterio</th>
                            <th>ID Resultado</th>
                            <th>ID Asignatura</th>
                            <th>Porcentaje</th>
                            <th>Nombre</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Object obj : criterios) {
                                if (obj instanceof CriterioEvaluacion) {
                                    CriterioEvaluacion criterio = (CriterioEvaluacion) obj;
                        %>
                        <tr>
                            <td><%= criterio.getIdCriterio()%></td>
                            <td><%= criterio.getIdResultado()%></td>
                            <td><%= criterio.getIdAsignatura()%></td>
                            <td><%= criterio.getPorcentaje()%></td>
                            <td><%= criterio.getNombre()%></td>
                            <td class="actions-cell">
                                <div class="d-inline">
                                    <button type="submit" class="edit-btn" onclick="abrirModal('<%= criterio.getIdCriterio()%>', '<%= criterio.getIdResultado()%>', '<%= criterio.getIdAsignatura()%>', '<%= criterio.getNombre()%>', '<%= criterio.getPorcentaje()%>')">Editar</button>
                                </div>
                                <form  action="criterios.jsp" class="d-inline">
                                    <input type="hidden" name="accion" value="borrar">
                                    <input type="hidden" name="id_criterio" value="<%= criterio.getIdCriterio()%>">
                                    <button type="submit" class="delete-btn">Borrar</button>
                                </form>
                            </td>
                        </tr>
                        <% }
                            }%>
                    </tbody>
                </table>
            </div>
            <div class="form-container">
                <h2>Agregar Criterio</h2>
                <form method="get" action="criterios.jsp">
                    <input type="hidden" name="accion" value="agregar">
                    <div class="input-group">
                        <label>ID Criterio:</label>
                        <input type="number" name="id_criterio" required>
                    </div>
                    <div class="input-group">
                        <label>ID Resultado:</label>
                        <input type="number" name="id_resultado" required>
                    </div>
                    <div class="input-group">
                        <label>ID Asignatura:</label>
                        <input type="number" name="id_asignatura" required>
                    </div>
                    <div class="input-group">
                        <label>Porcentaje:</label>
                        <input type="number" step="0.01" name="porcentaje" required>
                    </div>
                    <div class="input-group">
                        <label>Nombre:</label>
                        <input type="text" name="nombre" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Agregar</button>
                </form>
            </div>

            <!-- Modal de edición -->
            <div id="modalEditar" class="modal">            
                <div class="modal-content">
                    <span class="close" onclick="cerrarModal()">&times;</span>
                    <h2 class="tituloModal">Editar Criterio</h2>
                    <form method="post" action="criterios.jsp">
                        <input type="hidden" name="id_criterio" id="modalIdCriterio">
                        <label  class="modal-intro-nombre-label" for="modalRA">Id Resultado Aprendizaje</label>
                        <input type="number" name="RA" id="modalRA" class="modal-intro-nombre">
                        <label class="modal-intro-nombre-label" for="modalIdAsignatura">Id Asignatura</label>
                        <input type="number" name="idAsignatura" id="modalIdAsignatura" class="modal-intro-nombre">
                        <label class="modal-intro-nombre-label" for="modalNombre">Nombre:</label>
                        <input type="text" name="nombre" id="modalNombre" class="modal-intro-nombre" >
                        <label class="modal-intro-nombre-label" for="modalPorcentaje">Porcentaje:</label>
                        <input type="number" step="0.01" name="porcentaje" id="modalPorcentaje"  class="modal-intro-nombre">
                        <button type="submit" name="accion" value="editar">Guardar Cambios</button>
                    </form>
                </div>
            </div>

        </div>
        <%@ include file="include/footer.jsp" %>
        <script>
            function abrirModal(idCriterio, idRa, idAsignatura, nombre, porcentaje) {
                document.getElementById('modalIdCriterio').value = idCriterio;
                document.getElementById('modalRA').value = idRa;
                document.getElementById('modalIdAsignatura').value = idAsignatura;
                document.getElementById('modalNombre').value = nombre;
                document.getElementById('modalPorcentaje').value = porcentaje;
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